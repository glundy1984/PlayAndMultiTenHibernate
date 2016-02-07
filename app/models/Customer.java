package models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="customer")
public class Customer {

	@Id
	public int id;

	@Column
	public String name;

	@OneToMany(mappedBy="customerId", fetch = FetchType.EAGER)
	public List<CustomerAddress> addresses;
}
