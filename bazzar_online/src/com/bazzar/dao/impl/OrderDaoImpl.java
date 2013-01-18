package com.bazzar.dao.impl;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bazzar.dao.OrderDao;
import com.bazzar.domain.order.Order;

@Service ( "orderService" )
@Transactional
public class OrderDaoImpl implements OrderDao {

	protected static Logger logger = Logger.getLogger ( "OrderDao" );
	@Autowired
	private SessionFactory sessionFactory;
	
	public void add ( Order order ){
		logger.debug ( "Adding new Order" );
		sessionFactory.getCurrentSession ( ).save ( order );
	}
	
	public Order get ( Long id ){
		return ( Order ) sessionFactory.getCurrentSession ( ).
				get ( Order.class, id );
	}
	public void edit ( Order order ){
		logger.debug ( "Updating existing Order : " + order.getId () );
		sessionFactory.getCurrentSession ( ).save ( order );
	}
	public void delete ( Order order ){
		logger.debug ( "Deleting existing Order : " + order.getId () );
		sessionFactory.getCurrentSession ( ).delete ( order );
	}
}
