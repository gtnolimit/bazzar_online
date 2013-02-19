package com.bazzar.base.dao.impl;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bazzar.base.dao.CartDao;
import com.bazzar.base.domain.order.Cart;

@Repository
@Transactional
public class CartDaoImpl implements CartDao {

protected static Logger logger = Logger.getLogger ( "CartDao" );
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public Long create ( Cart cart ) {
		return ( Long ) sessionFactory.getCurrentSession ().save ( cart );
	}
	public void delete ( Long cartId ){
		delete ( get ( cartId ) );
		
	}
	public void delete ( Cart cart ) {
		sessionFactory.getCurrentSession ().delete ( cart );
	}
	public void edit ( Cart cart ) {
		sessionFactory.getCurrentSession ().merge ( cart );
	}
	public Cart get ( Long cartId ) {
		return ( Cart ) sessionFactory.getCurrentSession ().get ( Cart.class, cartId);
	}
	public Cart findCartByCustomerId ( Long customerId ) {
		return (Cart) sessionFactory.getCurrentSession ( ).
				createQuery ( "FROM CART c WHERE c.customer_id = :customerId")
				.setLong ( "customerId", customerId ).uniqueResult();
	}
}
