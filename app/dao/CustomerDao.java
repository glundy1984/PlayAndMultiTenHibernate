package dao;

import models.Customer;

import java.util.List;

public interface CustomerDao {

	List<Customer> getAll(String tenantId);
}
