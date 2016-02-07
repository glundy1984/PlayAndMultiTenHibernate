package models;

import javax.persistence.*;

@Entity
@Table(name="customer_address")
public class CustomerAddress {

	@Id
	public Integer id;

	@Column
	public String address;

	@Column(name="customer_id")
	private Integer customerId;
}
