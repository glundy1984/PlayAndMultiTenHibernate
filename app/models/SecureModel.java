package models;

import encryption.ModelEncryptionKey;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class SecureModel extends BaseModel {

    @Type(type = "dao.hibernate.ModelEncryptionKeyType")
    @Column(name = "key")
    private ModelEncryptionKey modelKey;

    public ModelEncryptionKey getModelKey() {
        if (modelKey == null) {
            modelKey = new ModelEncryptionKey();
        }
        return modelKey;
    }

    public void setModelKey(ModelEncryptionKey modelKey) {
        this.modelKey = modelKey;
    }
}
