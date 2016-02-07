package dao;

import dao.hibernate.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.function.Function;

public abstract class BaseDao {

    protected <T> T withSession(String tenantId, Function<Session, T> function) {
        Session session = null;
        try {
            session = HibernateUtil.SESSION_FACTORY.withOptions().tenantIdentifier(tenantId).openSession();
            return function.apply(session);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    protected <T> T withTransaction(String tenantId, Function<Session, T> function) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.SESSION_FACTORY.withOptions().tenantIdentifier(tenantId).openSession();
            transaction = session.beginTransaction();
            return function.apply(session);
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

}
