package com.bazzar.base.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bazzar.base.dao.OrderDao;
import com.bazzar.base.domain.order.Order;

@Repository
@Transactional
public class OrderDaoImpl implements OrderDao {

protected static Logger logger = Logger.getLogger ( "OrderDao" );
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	public List<Order> getOrders ( ) {
		return sessionFactory.getCurrentSession ( ).createCriteria ( Order.class ).list ();
	} 

	@SuppressWarnings("unchecked")
	public List<Order> getOrdersForCustomer ( Long customerId ) {
		return sessionFactory.getCurrentSession ( ).
				createQuery ( "FROM Order o WHERE o.customer_id = :customerId")
				.setLong ( "customerId", customerId ).list();
	}

	public Order getOrderByInvoice ( Long invoiceNumber ) {
		return (Order) sessionFactory.getCurrentSession ( ).
				createQuery ( "FROM Order o WHERE o.INVOICE_NUMBER = :invoiceNumber")
				.setLong ( "invoiceNumber", invoiceNumber ).uniqueResult();
	}
	
	public Order getOrder ( Long orderId ) {
		return ( Order ) sessionFactory.getCurrentSession ( ).get ( Order.class, orderId );
	}

	public int createOrder ( Order order ) {
		return ( Integer ) sessionFactory.getCurrentSession ( ).save ( order );
	}

	public void editOrder ( Order order ) {
		sessionFactory.getCurrentSession ( ).merge ( order );
	}

}
