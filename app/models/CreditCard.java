package models;

import encryption.SymmetricEncryption;

import javax.persistence.*;

@Entity
@Table(name="credit_card")
@SequenceGenerator(name="default_gen", sequenceName="credit_card_id_seq")
public class CreditCard extends SecureModel {

    @OneToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column
    private byte[] number;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getNumber() {
        return new String(SymmetricEncryption.decrypt(number, getModelKey().getDecryptedKey()));
    }

    public void setNumber(String number) {
        this.number = SymmetricEncryption.encrypt(number.getBytes(), getModelKey().getDecryptedKey());
    }

}
