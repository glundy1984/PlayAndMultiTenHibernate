package encryption;

import javax.crypto.Cipher;
import java.security.PrivateKey;
import java.security.PublicKey;

public class AsymmetricEncryption {

    private static final String algorithm = "RSA/ECB/PKCS1Padding";

    public static byte[] decrypt(byte[] encryptedSource, PrivateKey privateKey) throws RuntimeException {
        byte[] decryptedData = null;
        try {
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            decryptedData = cipher.doFinal(encryptedSource);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return decryptedData;
    }

    public static byte[] encrypt(byte[] dataToEncrypt, PublicKey publicKey) throws RuntimeException {
        byte[] encryptedData = null;
        try {
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            encryptedData = cipher.doFinal(dataToEncrypt);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return encryptedData;
    }
}
