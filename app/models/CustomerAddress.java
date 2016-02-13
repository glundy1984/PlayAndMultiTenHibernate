package models;

import javax.persistence.*;

@Entity
@Table(name="customer_address")
public class CustomerAddress {

	@Id
	@SequenceGenerator(name="customer_address_id_seq", sequenceName="customer_address_id_seq")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="customer_address_id_seq")
	private Integer id;

	@Column
	private String address;

	@OneToOne
	@JoinColumn(name = "customer_id", nullable = false)
	private Customer customer;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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
