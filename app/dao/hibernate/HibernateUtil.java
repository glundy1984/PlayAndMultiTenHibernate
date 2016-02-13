package dao.hibernate;

import models.Context;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.security.KeyPair;
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

    public static <T> T withSession(Context context, Function<Session, T> function) {
        Session session = null;
        try {
            session = SESSION_FACTORY.withOptions().tenantIdentifier(context.getSchemaId()).openSession();
            return setKeyPairOnObjectTree(function.apply(session), context.getKeyPair());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public static <T> T withTransaction(Context context, Function<Session, T> function) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = SESSION_FACTORY.withOptions().tenantIdentifier(context.getSchemaId()).openSession();
            transaction = session.beginTransaction();
            return setKeyPairOnObjectTree(function.apply(session), context.getKeyPair());
        } finally {
            try {
                if (transaction != null) {
                    transaction.commit();
                }
            } finally {
                if (session != null) {
                    session.close();
                }
            }
        }
    }

    //TODO: need to navigate object graph and setKeyPair
    private static <T> T setKeyPairOnObjectTree(T objectTree, KeyPair keyPair) {
        return objectTree;
    }
}
