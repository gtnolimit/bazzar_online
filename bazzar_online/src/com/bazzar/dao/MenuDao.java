package com.bazzar.dao;

import java.util.List;
import java.util.Set;

import com.bazzar.domain.item.Item;
import com.bazzar.domain.menu.Category;
import com.bazzar.domain.menu.Product;
import com.bazzar.domain.menu.SubCategory;

public interface MenuDao {

	public void deleteCategory ( Long id );
	public void deleteSubCategory ( Long id );
	public void deleteProduct ( Long id );
	
	public void add ( Category category );
	public Long add_ReturnId ( Category category );
	public void add ( SubCategory subcategory );
	public void add ( Product product );
	
	public Category getCategory ( Long id );
	public SubCategory getSubCategory ( Long id );
	public Product getProduct ( Long id );
	
	public Product findProductByName ( String name );
	
	public Set <Item> getItemsForProduct ( Long id);
	
	public List <Category> getAllCategories ();
	public Set <SubCategory> getAllSubCategories ();
	public Set <Product> getAllProducts ();
	
	public void edit ( Category category );
	public void edit ( SubCategory subCategory );
	public void edit ( Product product );
}
