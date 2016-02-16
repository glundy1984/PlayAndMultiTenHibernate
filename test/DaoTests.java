import dao.hibernate.HibernateUtil;
import models.*;
import org.junit.Assert;
import org.junit.Test;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.ArrayList;

public class DaoTests {

    @Test
    public void canCreateAndRetrieveCreditCard() throws Exception {

        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(1024);

        KeyPair keyPair = keyGen.generateKeyPair();

        Context context = new Context("schema_a", keyPair);

        Customer customer = new Customer();
        customer.setName("name");

        CreditCard creditCard = new CreditCard();
        creditCard.getModelKey().setKeyPair(keyPair);
        creditCard.setNumber("123456789");
        creditCard.setCustomer(customer);

        customer.getCreditCards().add(creditCard);

        HibernateUtil.withSession(context, session -> {

            session.save(customer);
            session.flush();
            session.clear();

            Integer creditCardId = creditCard.getId();

            Assert.assertNotNull(creditCardId);

            CreditCard cardFromDB = session.get(CreditCard.class, creditCardId);

            cardFromDB.getModelKey().setKeyPair(keyPair);

            Assert.assertEquals(cardFromDB.getNumber(), "123456789");

            session.clear();

            session.delete(customer);
        });
    }
}
