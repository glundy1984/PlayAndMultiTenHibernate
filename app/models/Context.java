package models;

import java.security.KeyPair;

public class Context {

    private String schemaId;
    private KeyPair keyPair;

    public Context(String schemaId, KeyPair keyPair) {
        this.schemaId = schemaId;
        this.keyPair = keyPair;
    }

    public String getSchemaId() {
        return schemaId;
    }

    public KeyPair getKeyPair() {
        return keyPair;
    }
}
