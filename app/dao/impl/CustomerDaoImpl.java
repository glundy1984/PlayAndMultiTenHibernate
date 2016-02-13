package dao.impl;

import dao.BaseDao;
import dao.CustomerDao;
import dao.hibernate.HibernateUtil;
import models.Context;
import models.Customer;

import java.util.List;


public class CustomerDaoImpl extends BaseDao implements CustomerDao {

	@Override
	public List<Customer> getAll(Context context) {
		return HibernateUtil.withSession(context, session -> {
			List<Customer> customers = session.createQuery("from Customer").list();
			//TODO: temporary solution
			customers.stream().forEach(c ->
					c.getCreditCards().forEach(cc -> cc.getModelKey().setKeyPair(context.getKeyPair())));
			return customers;
		});
	}
}
