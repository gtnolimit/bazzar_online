package com.bazzar.base.dao.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.CacheMode;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bazzar.base.dao.ItemDao;
import com.bazzar.base.domain.item.Item;
import com.bazzar.base.domain.menu.Category;
import com.bazzar.base.domain.menu.Product;

@Repository
@Transactional
public class ItemDaoImpl implements ItemDao {

	protected static Logger logger = Logger.getLogger("ItemDao");

	@Autowired
	private SessionFactory sessionFactory;

	public Item getItem(Long id) {
		return (Item) sessionFactory.getCurrentSession().get(Item.class, id);
	}

	public Item getItemQuestions(Long id) {
		return (Item) sessionFactory
		        .getCurrentSession()
		        .createQuery("FROM Item i left join Qestion q WHERE i.id = :id")
		        .setParameter("id", id).uniqueResult();
	}

	public Item getItemReview(Long id) {
		return (Item) sessionFactory
		        .getCurrentSession()
		        .createQuery(
		                "FROM Item i left outer join Review r WHERE i.id = :id")
		        .setParameter("id", id).uniqueResult();
	}

	public Item getItemAccessories(Long id) {
		return (Item) sessionFactory
		        .getCurrentSession()
		        .createQuery(
		                "FROM Item i left outer join Accessories a WHERE i.id = :id")
		        .setParameter("id", id).uniqueResult();

	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<Item> getAllItems() {
		logger.debug("Retrieving all Items");
		Query query = sessionFactory.getCurrentSession().createQuery(
		        "FROM Item i");
		return query.list();
	}

	@SuppressWarnings("unchecked")
	public List<Item> findItemsByName(String itemName) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
		        Item.class);
		criteria.add(Restrictions.ilike("subgect", itemName + "%"));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<Item> findItemsByManufactureNumber(String manufactureNumber) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
		        Item.class);
		criteria.add(Restrictions.ilike("manufactureModelNumber",
		        manufactureNumber + "%"));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<Item> findItemsByManufacture(String manufacture) {
		Query q = sessionFactory
		        .getCurrentSession()
		        .createQuery(
		                "SELECT i FROM Item i LEFT JOIN FETCH i.manufacture where i.status<>0 and i.name=:manufacture");
		q.setParameter("manufacture", manufacture);
		return q.list();
	}

	@SuppressWarnings("unchecked")
	public List<Item> findItemsByDescription(String description) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
		        Item.class);
		criteria.add(Restrictions.ilike("description", description + "%"));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<Item> findItemsByBarCode(String barCode) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
		        Item.class);
		criteria.add(Restrictions.ilike("barCode", barCode + "%"));
		return criteria.list();
	}

	public void editItem(Item item) {
		sessionFactory.getCurrentSession().merge(item);
	}

	public int addItem(Item item) {
		return (Integer) sessionFactory.getCurrentSession().save(item);
	}

	public void delete(Long id) {
		Item i = this.getItem(id);
		i.setActive(false);
		editItem(i);
	}

	public void delete(Item item) {
		item.setActive(false);
		editItem(item);
	}

	public void batchInsert(List<Item> items, Product product) {
		Session session = sessionFactory.openSession();
		session.setCacheMode(CacheMode.IGNORE);
		Transaction tx = session.beginTransaction();
		for (int i = 0; i < items.size(); i++) {
			Item item = items.get(i);
			item.setCPD(new Date());
			item.setUPD(new Date());
			item.setParent(product);
			session.saveOrUpdate(item);
			if (i % 49 == 0) { // 20, same as the JDBC batch size
				// flush a batch of inserts and release memory:
				session.flush();
				session.clear();
			}
		}
		session.evict(product);
		product = null;
		tx.commit();
		session.close();
	}

	@SuppressWarnings("unchecked")
	public List<Category> findItemParent(List<String> categories) {
		Query q = sessionFactory
		        .getCurrentSession()
		        .createQuery(
		                "select cat FROM Category cat left join fetch cat.subCategory subCat "
		                        + "left join fetch subCat.product prd where cat.attribute = :catName "
		                        + "and subCat.attribute = :subCatName and "
		                        + "prd.attribute = :prdName");
		q.setParameter("catName", categories.get(0))
		        .setParameter("subCatName", categories.get(1))
		        .setParameter("prdName", categories.get(2));
		return q.list();
	}

	public void createCategory(Category category) {
		sessionFactory.getCurrentSession().saveOrUpdate(category);
	}

}
