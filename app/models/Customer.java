package models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="customer")
public class Customer {

	@Id
	@SequenceGenerator(name="customer_id_seq", sequenceName="customer_id_seq")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="customer_id_seq")
	private Integer id;

	@Column
	private String name;

	@OneToMany(mappedBy="customer", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<CustomerAddress> addresses;

	@OneToMany(mappedBy="customer", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<CreditCard> creditCards;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<CustomerAddress> getAddresses() {
		if (addresses == null) {
			addresses = new ArrayList<>();
		}
		return addresses;
	}

	public void setAddresses(List<CustomerAddress> addresses) {
		this.addresses = addresses;
	}

	public List<CreditCard> getCreditCards() {
		if (creditCards == null) {
			creditCards = new ArrayList<>();
		}
		return creditCards;
	}

	public void setCreditCards(List<CreditCard> creditCards) {
		this.creditCards = creditCards;
	}
}
