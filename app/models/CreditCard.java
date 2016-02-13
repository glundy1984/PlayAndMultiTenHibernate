package models;

import encryption.SymmetricEncryption;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name="credit_card")
public class CreditCard {

    @Id
    @SequenceGenerator(name="credit_card_id_seq", sequenceName="credit_card_id_seq")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="credit_card_id_seq")
    private Integer id;

    @Type(type = "dao.hibernate.ModelEncryptionKeyType")
    @Column(name = "key")
    private ModelEncryptionKey modelKey;

    @OneToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column
    private byte[] number;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ModelEncryptionKey getModelKey() {
        if (modelKey == null) {
            modelKey = new ModelEncryptionKey();
        }
        return modelKey;
    }

    public void setModelKey(ModelEncryptionKey modelKey) {
        this.modelKey = modelKey;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getNumber() {
        return new String(SymmetricEncryption.decrypt(number, modelKey.getDecryptedKey()));
    }

    public void setNumber(String number) {
        this.number = SymmetricEncryption.encrypt(number.getBytes(), modelKey.getDecryptedKey());
    }

}
