package dao;

import models.Context;
import models.Customer;

import java.util.List;

public interface CustomerDao {

	List<Customer> getAll(Context context);
}
