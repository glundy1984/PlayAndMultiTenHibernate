package models;

import javax.persistence.*;

@Entity
@Table(name="customer_address")
@SequenceGenerator(name="default_gen", sequenceName="customer_address_id_seq")
public class CustomerAddress extends BaseModel {

	@Column
	private String address;

	@OneToOne
	@JoinColumn(name = "customer_id", nullable = false)
	private Customer customer;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
}
