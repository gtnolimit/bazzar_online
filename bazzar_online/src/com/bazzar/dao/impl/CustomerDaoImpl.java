package com.bazzar.dao.impl;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bazzar.dao.CustomerDao;

@Service ( "customerService" )
@Transactional
public class CustomerDaoImpl implements CustomerDao {

	protected static Logger logger = Logger.getLogger ( "CustomerDao" );
	@Autowired
	private SessionFactory sessionFactory;
}
