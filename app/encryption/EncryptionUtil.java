package encryption;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;

public class EncryptionUtil {

    public static final String TEST_KEYPAIR_FILENAME = "TestKeyPair.ser";

    //For testing purposes.
    //A strategy for generating and persisting an IVector would need to be developed for real world applications.
    public static final byte[] IVECTOR = new byte[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};

    //For testing purposes
    public static void saveTestKeyPairToFile() throws Exception {

        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(1024);

        KeyPair keyPair = keyGen.generateKeyPair();

        FileOutputStream fout = new FileOutputStream(TEST_KEYPAIR_FILENAME);
        ObjectOutputStream oos = new ObjectOutputStream(fout);
        oos.writeObject(keyPair);
    }

    //For testing purposes
    public static KeyPair getTestKeyPair() throws Exception {
        String path = EncryptionUtil.class.getClassLoader().getResource(EncryptionUtil.TEST_KEYPAIR_FILENAME).getPath();
        FileInputStream fin = new FileInputStream(path);
        ObjectInputStream ois = new ObjectInputStream(fin);
        return (KeyPair) ois.readObject();
    }
}
