package dao.hibernate;

import models.Context;
import models.SecureModel;
import net.bramp.objectgraph.ObjectGraph;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.security.KeyPair;
import java.util.function.Consumer;
import java.util.function.Function;

public class HibernateUtil {

    public final static SessionFactory SESSION_FACTORY = createSessionFactory();

    private static SessionFactory createSessionFactory() {
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            return new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
            // so destroy it manually.
            StandardServiceRegistryBuilder.destroy(registry);
            throw new RuntimeException(e);
        }
    }

    /*
     * Used to retrieve objects from the database / session
     */
    public static <T> T withSession(Context context, Function<Session, T> function) {
        Session session = null;
        Transaction tx = null;
        try {
            session = SESSION_FACTORY.withOptions().tenantIdentifier(context.getSchemaId()).openSession();
            tx = session.beginTransaction();
            // Objects being returned from the database / session have no KeyPair
            // so we need to attach a KeyPair in order to decrypt data
            T result = setKeyPairOnObjectGraph(function.apply(session), context.getKeyPair());
            tx.commit();
            return result;
        } catch (Exception ex) {
            if (tx != null) tx.rollback();
            throw ex;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    /*
     * Used to insert objects into the database / session.
     * It is assumed that all objects that require encryption have an attached KeyPair
     */
    public static void withSession(Context context, Consumer<Session> consumer) {
        Session session = null;
        Transaction tx = null;
        try {
            session = SESSION_FACTORY.withOptions().tenantIdentifier(context.getSchemaId()).openSession();
            tx = session.beginTransaction();
            consumer.accept(session);
            tx.commit();
        } catch (Exception ex) {
            if (tx != null) tx.rollback();
            throw ex;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    private static <T> T setKeyPairOnObjectGraph(T objectGraph, KeyPair keyPair) {
        ObjectGraph
                .visitor(new ObjectGraph.Visitor() {
                    @Override
                    public boolean visit(Object object, Class clazz) {
                        if (object instanceof SecureModel) {
                            ((SecureModel) object).getModelKey().setKeyPair(keyPair);
                        }
                        return false;
                    }
                })
                .excludeStatic()
                .excludeTransient()
                .traverse(objectGraph);
        return objectGraph;
    }
}
