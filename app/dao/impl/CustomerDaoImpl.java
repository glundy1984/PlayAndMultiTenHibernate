package dao.impl;

import dao.BaseDao;
import dao.CustomerDao;
import models.Customer;

import java.util.List;


public class CustomerDaoImpl extends BaseDao implements CustomerDao {

	@Override
	public List<Customer> getAll(String tenantId) {
		return withSession(tenantId, session -> (List<Customer>) session.createQuery("from Customer").list());
	}
}
