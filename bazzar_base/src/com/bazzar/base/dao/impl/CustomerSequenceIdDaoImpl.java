package com.bazzar.base.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bazzar.base.dao.CustomerSequenceIdDao;
import com.bazzar.base.domain.order.CustomerSequenceId;

@Repository
@Transactional
public class CustomerSequenceIdDaoImpl implements CustomerSequenceIdDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	public int create ( CustomerSequenceId customerSequence) {
		return ( Integer ) sessionFactory.getCurrentSession ().save ( customerSequence );
	}

}
