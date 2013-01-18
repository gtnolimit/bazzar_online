package com.bazzar.dao.impl;

import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bazzar.dao.ItemDao;
import com.bazzar.domain.item.Item;
import com.bazzar.domain.item.Manufacture;
import com.bazzar.domain.menu.Category;

@Service ( "itemService" )
@Transactional
public class ItemDaoImpl implements ItemDao {

	protected static Logger logger = Logger.getLogger ( "ItemDao" );
	@Autowired
	private SessionFactory sessionFactory;
	
	public void add(Item item) {
		logger.debug ( "Adding new Item" );
		sessionFactory.getCurrentSession ( ).save ( item );
	}

	public Item getItem(Long id) {
		return ( Item ) sessionFactory.getCurrentSession ( ).
				get ( Item.class, id );
	}


	public void deleteItem(Long id) {
		logger.debug ( "Deleting existing Item : " + id);
		//sessionFactory.getCurrentSession ( ).delete ( 
		//		( Item ) sessionFactory.getCurrentSession ( ).get ( Item.class, id ) );
		Item item = ( Item ) sessionFactory.getCurrentSession ( ).get ( Item.class, id );
		item.setActive(false);
		sessionFactory.getCurrentSession ( ).save ( item );
	}

	public void delete(Item item) {
		logger.debug ( "Deleting existing Item : " );
		//sessionFactory.getCurrentSession ( ).delete ( item  );
		item.setActive(false);
		sessionFactory.getCurrentSession ( ).save ( item );

	}
	@Override
	public void edit(Item item) {
		logger.debug ( "Editing existing Item : " );
		sessionFactory.getCurrentSession ( ).save ( item );

	}

	@SuppressWarnings("unchecked")
	public Set<Item> getAllReviewsForItem(Long id) {
		logger.debug ( "Retrieving all Items for Reviews" );
		Query query = sessionFactory.getCurrentSession ( ).createQuery 
				( "FROM Item p left outer join Review i WHERE p.id = :Id")
				.setParameter("Id", id);
		return (Set<Item>) query.list();
	}

	@SuppressWarnings("unchecked")
	public Set<Item> getAllQuestionsForItem(Long id) {
		logger.debug ( "Retrieving all Items for Reviews" );
		Query query = sessionFactory.getCurrentSession ( ).createQuery 
				( "FROM Item p left outer join QA i WHERE p.id = :Id")
				.setParameter("Id", id);
		return (Set<Item>) query.list();
	}

	public void add(Manufacture manufacture) {
		logger.debug ( "Adding new Manufacture" );
		sessionFactory.getCurrentSession ( ).save ( manufacture );
	}

	public Manufacture getManufacture(Long id) {
		return ( Manufacture ) sessionFactory.getCurrentSession ( ).
				get ( Manufacture.class, id );
	}

	public void deleteManufacture(Long id) {
		logger.debug ( "Deleting existing Manufacture : " + id);
		sessionFactory.getCurrentSession ( ).delete ( 
				( Manufacture ) sessionFactory.getCurrentSession ( ).get ( Manufacture.class, id ) );
	}

	public void delete(Manufacture manufacture) {
		logger.debug ( "Deleting existing Manufacture: " );
		sessionFactory.getCurrentSession ( ).delete ( manufacture  );
	}

	public void edit(Manufacture manufacture) {
		logger.debug ( "Editing Category : " + manufacture.getId ( ) );
		Session session = sessionFactory.getCurrentSession ( );
		Manufacture existingManufacture = ( Manufacture ) session.get ( Category.class, manufacture.getId ( ) );
		existingManufacture.setName( manufacture.getName());
		existingManufacture.setNumber( manufacture.getNumber());
		existingManufacture.setAuthorizePicture( manufacture.isAuthorizePicture());
		//existingManufacture.setUpdated( manufacture.getUpdated());
		existingManufacture.setPicture( manufacture.getPicture());
		session.save ( existingManufacture );	}

}
