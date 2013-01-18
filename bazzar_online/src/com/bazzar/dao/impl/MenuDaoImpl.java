package com.bazzar.dao.impl;

import java.util.List;
import java.util.Set;

//import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bazzar.dao.MenuDao;
import com.bazzar.domain.item.Item;
import com.bazzar.domain.menu.Category;
import com.bazzar.domain.menu.Product;
import com.bazzar.domain.menu.SubCategory;

@Service ( "menuService" )
@Transactional
public class MenuDaoImpl implements MenuDao {
	protected static Logger logger = Logger.getLogger ( "MenuDao" );
	
	@Autowired
	private SessionFactory sessionFactory;
	  
	public void deleteCategory ( Long id ){
		logger.debug ( "Deleting existing Category : " + id);
		//sessionFactory.getCurrentSession ( ).delete ( 
		//		( Category ) sessionFactory.getCurrentSession ( ).get ( Category.class, id ) );		
		Category category = ( Category ) sessionFactory.getCurrentSession ( ).get ( Category.class, id ) ;
		category.setActive(false);
		sessionFactory.getCurrentSession ( ).save(category);
	}
	public void deleteSubCategory ( Long id ){
		logger.debug ( "Deleting existing SubCategory : " + id);
		//sessionFactory.getCurrentSession ( ).delete ( 
		//		( SubCategory ) sessionFactory.getCurrentSession ( ).get ( SubCategory.class, id ));
		SubCategory subCategory = ( SubCategory ) sessionFactory.getCurrentSession ( ).get ( SubCategory.class, id );
		subCategory.setActive(false);
		sessionFactory.getCurrentSession ( ).save(subCategory);
	}
	public void deleteProduct ( Long id ){
		logger.debug ( "Deleting existing Product : " + id);
		//sessionFactory.getCurrentSession ( ).delete ( 
		//		( Product ) sessionFactory.getCurrentSession ( ).get ( Product.class, id ) );
		Product product = ( Product ) sessionFactory.getCurrentSession ( ).get ( Product.class, id );
		product.setActive(false);
		sessionFactory.getCurrentSession ( ).save(product);
	}
	
	public void add ( Category category ){
		logger.debug ( "Adding new Category" );
		sessionFactory.getCurrentSession ( ).save ( category );		
	}
	
	public Long add_ReturnId ( Category category ){
		logger.debug ( "Adding new Category" );
		sessionFactory.getCurrentSession ( ).save ( category );
		return category.getId();
	}
	
	public void add ( SubCategory subCategory ){
		logger.debug ( "Adding new SubCategory" );
		sessionFactory.getCurrentSession ( ).save ( subCategory );		
	}
	public void add ( Product product ){
		logger.debug ( "Adding new Product" );
		sessionFactory.getCurrentSession ( ).save ( product );		
	}
	
	public Category getCategory ( Long id ){
		return ( Category ) sessionFactory.getCurrentSession ( ).
				get ( Category.class, id );
	}
	public SubCategory getSubCategory ( Long id ){
		return ( SubCategory ) sessionFactory.getCurrentSession ( ).
				get ( SubCategory.class, id );
	}
	public Product getProduct ( Long id ){
		return ( Product ) sessionFactory.getCurrentSession ( ).
				get ( Product.class, id );
	}
	
	@SuppressWarnings("unchecked")
	public List <Category> getAllCategories ( ){
		logger.debug ( "Retrieving all Categories" );
		return (List<Category>) sessionFactory.getCurrentSession ( ).
				createQuery("FROM Category c").list();
	}
	@SuppressWarnings("unchecked")
	public Set <SubCategory> getAllSubCategories ( ){
		logger.debug ( "Retrieving all SubCategory" );
		return  (Set<SubCategory>) sessionFactory.getCurrentSession ( ).
				createQuery ( "FROM SubCategory" ).list ( );
	}
	@SuppressWarnings("unchecked")
	public Set <Item> getItemsForProduct ( Long id){
		logger.debug ( "Retrieving all Items for Product" );
		Query query = sessionFactory.getCurrentSession ( ).createQuery 
				( "FROM Product p left outer join Item i WHERE p.id = :Id")
				.setParameter("Id", id);
		return (Set<Item>) query.list();
	}
	
	public Product findProductByName ( String name ){
		logger.debug ( "Retrieving Product by name :" + name );
		Query query = sessionFactory.getCurrentSession ( ).createQuery 
				( "FROM Product p WHERE p.attribute = :name")
				.setParameter("name", name);
		return (Product) query.uniqueResult ();
	}
	
	@SuppressWarnings("unchecked")
	public Set <Product> getAllProducts ( ){
		logger.debug ( "Retrieving all Product" );
		return  (Set<Product>) sessionFactory.getCurrentSession ( ).createQuery ( "FROM Product" ).list ( );
	}
	
	public void edit ( Category category ){
		logger.debug ( "Editing Category : " + category.getId ( ) );
		sessionFactory.getCurrentSession ( ).save ( category );
	}
	public void edit ( SubCategory subCategory ){
		logger.debug ( "Editing SubCategory : " + subCategory .getId ( ) );
		sessionFactory.getCurrentSession ( ).save ( subCategory );
	}
	public void edit ( Product product ){
		logger.debug ( "Editing Product : " + product.getId ( ) );
		sessionFactory.getCurrentSession ( ).save ( product );
	}

	
}
