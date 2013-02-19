package com.bazzar.base.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bazzar.base.dao.HomeDao;
import com.bazzar.base.domain.Home;

@Repository
@Transactional
public class homeDaoImpl implements HomeDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	public int create ( Home home ) {
		return ( Integer ) sessionFactory.getCurrentSession ().save ( home );
	}

	public void edit ( Home home ) {
		sessionFactory.getCurrentSession ().merge ( home );
	}

	public Home get ( Long homeId ) {
		return ( Home ) sessionFactory.getCurrentSession ().get ( Home.class, homeId);
	}

}
