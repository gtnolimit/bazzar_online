package com.bazzar.dao.impl;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bazzar.dao.ShoppingCartDao;
import com.bazzar.domain.order.ShoppingCart;

@Service ( "shoppingcartService" )
@Transactional
public class ShoppingCartDaoImpl implements ShoppingCartDao {

	protected static Logger logger = Logger.getLogger ( "ShoppingCartDao" );
	@Autowired
	private SessionFactory sessionFactory;
	
	public void add ( ShoppingCart shoppingCart ){
		logger.debug ( "Adding new ShoppingCart" );
		sessionFactory.getCurrentSession ( ).save ( shoppingCart );
	}
	
	public ShoppingCart get ( Long id ){
		return ( ShoppingCart ) sessionFactory.getCurrentSession ( ).
				get ( ShoppingCart.class, id );
	}
	public void edit ( ShoppingCart shoppingCart ){
		logger.debug ( "Updating existing ShoppingCart : " + shoppingCart.getId () );
		sessionFactory.getCurrentSession ( ).save ( shoppingCart );
	}
	public void delete ( ShoppingCart shoppingCart ){
		logger.debug ( "Deleting existing ShoppingCart : " + shoppingCart.getId () );
		sessionFactory.getCurrentSession ( ).delete ( shoppingCart );
	}
		
}
