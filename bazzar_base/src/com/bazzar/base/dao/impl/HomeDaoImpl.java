package com.bazzar.base.dao.impl;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bazzar.base.dao.HomeDao;
import com.bazzar.base.domain.customer.Home;

@Repository
@Transactional
public class HomeDaoImpl implements HomeDao {
	
protected static Logger logger = Logger.getLogger ( "homeDao" );
	
	@Autowired
	private SessionFactory sessionFactory;

	public int add(Home company) {
		return ( Integer ) sessionFactory.getCurrentSession ( ).save ( company );
	}

	public void edit(Home comoany) {
		sessionFactory.getCurrentSession ( ).merge ( comoany );
	}

	public Home get(Long id) {
		return ( Home ) sessionFactory.getCurrentSession ( ).
				get ( Home.class, id );
	}

}
