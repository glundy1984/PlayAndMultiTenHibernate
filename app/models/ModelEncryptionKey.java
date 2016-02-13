package models;

import encryption.AsymmetricEncryption;
import encryption.SymmetricEncryption;

import java.io.Serializable;
import java.security.KeyPair;

public class ModelEncryptionKey implements Serializable {

    private byte[] encryptedKey;
    private transient byte[] decryptedKey;
    private transient KeyPair keyPair;

    public ModelEncryptionKey() {
        decryptedKey = SymmetricEncryption.generateSecretKey();
    }

    public ModelEncryptionKey(byte[] encryptedKey) {
        this.encryptedKey = encryptedKey;
    }

    public void setKeyPair(KeyPair keyPair) {
        this.keyPair = keyPair;
    }

    public byte[] getDecryptedKey() {
        if (decryptedKey == null) {
            decryptedKey = AsymmetricEncryption.decrypt(getEncryptedKey(), keyPair.getPrivate());
        }
        return decryptedKey;
    }

    public byte[] getEncryptedKey() {
        if (encryptedKey == null) {
            encryptedKey = AsymmetricEncryption.encrypt(getDecryptedKey(), keyPair.getPublic());
        }
        return encryptedKey;
    }


}
