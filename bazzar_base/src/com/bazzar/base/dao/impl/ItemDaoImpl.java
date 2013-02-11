package com.bazzar.base.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bazzar.base.dao.ItemDao;
import com.bazzar.base.domain.item.Item;

@Repository
@Transactional
public class ItemDaoImpl implements ItemDao {

protected static Logger logger = Logger.getLogger ( "ItemDao" );
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public Item getItem(Long id) {
		return ( Item ) sessionFactory.getCurrentSession ( ).
				get ( Item.class, id );
	}

	public Item getItemQuestions(Long id) {
		return (Item) sessionFactory.getCurrentSession ( )
				.createQuery("FROM Item i left outer join Qestion q WHERE i.id = :id")
				.setParameter("id", id).uniqueResult();
	}

	public Item getItemReview(Long id) {
		return (Item) sessionFactory.getCurrentSession ( )
				.createQuery("FROM Item i left outer join Review r WHERE i.id = :id")
				.setParameter("id", id).uniqueResult();
	}

	public Item getItemAccessories(Long id) {
		return (Item) sessionFactory.getCurrentSession ( )
				.createQuery("FROM Item i left outer join Accessories a WHERE i.id = :id")
				.setParameter("id", id).uniqueResult();

	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<Item> getAllItems() {
		logger.debug ( "Retrieving all Items" );
        Query query = sessionFactory.getCurrentSession ().createQuery ( "FROM Item i" );
        return query.list ();
	}

	@SuppressWarnings("unchecked")
	public List<Item> findItemsByName ( String itemName ) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Item.class);
		criteria.add(Restrictions.ilike ("SUBJECT", itemName +"%" ) );
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<Item> findItemsByManufactureNumber(String manufactureNumber) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Item.class);
		criteria.add(Restrictions.ilike ("MANUFACTURE_MODEL_NUMBER", manufactureNumber +"%" ) );
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<Item> findItemsByManufacture(String manufacture) {
		Query q = sessionFactory.getCurrentSession()
				.createQuery("SELECT i FROM Item i LEFT JOIN FETCH i.manufacture where i.status<>0 and i.name=:manufacture");
		q.setParameter("manufacture", manufacture);
		return q.list();
	}

	@SuppressWarnings("unchecked")
	public List<Item> findItemsByDescription(String description) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Item.class);
		criteria.add(Restrictions.ilike ("DESCRIPTION", description +"%" ) );
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<Item> findItemsByBarCode ( String barCode ) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Item.class);
		criteria.add(Restrictions.ilike ("BAR_CODE", barCode +"%" ) );
		return criteria.list();
	}
	
	public void editItem(Item item) {
		sessionFactory.getCurrentSession ( ).merge ( item );
	}

	public int addItem(Item item) {
		return ( Integer ) sessionFactory.getCurrentSession ( ).save ( item );
	}

	public void delete(Long id) {
		Item i = this.getItem ( id );
		i.setActive ( false );
		editItem ( i );
	}

	public void delete(Item item) {
		item.setActive ( false );
		editItem ( item );
	}

}
