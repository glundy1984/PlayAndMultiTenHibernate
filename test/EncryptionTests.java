import models.CreditCard;
import encryption.ModelEncryptionKey;
import org.junit.Assert;
import org.junit.Test;

import java.security.KeyPair;
import java.security.KeyPairGenerator;

public class EncryptionTests {

    @Test
    public void canEncryptAndDecryptModel() throws Exception {

        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(1024);

        KeyPair keyPair = keyGen.generateKeyPair();

        CreditCard creditCard = new CreditCard();
        creditCard.getModelKey().setKeyPair(keyPair);

        creditCard.setNumber("123456789");

        // Test Symmetric encryption i.e. we have an unencrypted model key
        Assert.assertEquals(creditCard.getNumber(), "123456789");

        // Create encrypted model key using public key
        // This is how a ModelEncryptionKey is created from the database
        ModelEncryptionKey modelKeyFromEncryptedKey = new ModelEncryptionKey(creditCard.getModelKey().getEncryptedKey());
        modelKeyFromEncryptedKey.setKeyPair(keyPair);

        creditCard.setModelKey(modelKeyFromEncryptedKey);

        // Test Asymmetric encryption i.e. decrypt model key using private key
        Assert.assertEquals(creditCard.getNumber(), "123456789");
    }
}
