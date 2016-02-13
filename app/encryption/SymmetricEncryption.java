package encryption;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

public class SymmetricEncryption {

    public static final String ALGORITHM_FULL = "AES/CBC/PKCS5Padding";
    public static final String ALGORITHM = "AES";

    private static final SecureRandom random = new SecureRandom();

    public static byte[] generateSecretKey() {
        byte[] secretKey = new byte[32];
        random.nextBytes(secretKey);
        return secretKey;
    }

    public static byte[] encrypt(byte[] dataToEncrypt, byte[] secretKey) throws RuntimeException {
        byte[] encryptedData = null;
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM_FULL);
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(secretKey, ALGORITHM), new IvParameterSpec(EncryptionUtil.IVECTOR));
            encryptedData = cipher.doFinal(dataToEncrypt);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return encryptedData;
    }

    public static byte[] decrypt(byte[] encryptedData, byte[] secretKey) throws RuntimeException {
        byte[] decryptedData = null;
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM_FULL);
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(secretKey, ALGORITHM), new IvParameterSpec(EncryptionUtil.IVECTOR));
            decryptedData = cipher.doFinal(encryptedData);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return decryptedData;
    }
}
