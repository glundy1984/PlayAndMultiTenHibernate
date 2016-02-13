import dao.CustomerDao;
import dao.hibernate.HibernateUtil;
import dao.impl.CustomerDaoImpl;
import encryption.EncryptionUtil;
import models.Context;
import models.CreditCard;
import models.Customer;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.security.KeyPair;
import java.util.List;

public class TestDataSetup {

    /*
     * Adds a credit card for each customer in the database for testing purposes.
     * Uses the TestKeyPair so the web app can decrypt the data.
     */
    @Test
    public void setupTestData() throws Exception {

        KeyPair keyPair = EncryptionUtil.getTestKeyPair();

        addCreditCardToCustomers(new Context("schema_a", keyPair));
        addCreditCardToCustomers(new Context("schema_b", keyPair));
    }

    private void addCreditCardToCustomers(Context context) throws Exception {

        CustomerDao customDao = new CustomerDaoImpl();

        List<Customer> customers = customDao.getAll(context);

        for (Customer customer : customers) {

            HibernateUtil.withTransaction(context, session -> {

                CreditCard creditCard = new CreditCard();
                creditCard.getModelKey().setKeyPair(context.getKeyPair());
                creditCard.setNumber("123456789");

                creditCard.setCustomer(customer);

                session.save(creditCard);

                return null;
            });
        }
    }

}
