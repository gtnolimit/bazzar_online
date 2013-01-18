package com.bazzar.test;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.bazzar.dao.MenuDao;
import com.bazzar.domain.menu.Category;
import com.bazzar.domain.menu.Product;
import com.bazzar.domain.menu.SubCategory;

public class MenuTest extends AppTestSupport{
	
	
	@Test
	public void createMenu( MenuDao menuDao) {

		Category category = getCategory();
		menuDao.add( category );
		
		Long categoryId = category.getId();
		System.out.println("categoryId="+categoryId);
		
		assertNotNull ( menuDao.getCategory ( categoryId ) );
	}

	private Category getCategory (){
		Set <Product> products = new HashSet <Product> ();
		products.add( setProduct ("Trash Compactors") );
		products.add( setProduct ("Small Appliances") );
		products.add( setProduct ("Refrigerators & Freezers") );
		products.add( setProduct ("Microwaves & Microwave Ovens") );
		products.add( setProduct ("Ice Makers") );
		products.add( setProduct ("Hoods") );
		products.add( setProduct ("Garbage Disposals") );
		products.add( setProduct ("Dishwashers") );
		products.add( setProduct ("Cooking Products") );
		products.add( setProduct ("Appliance Packages") );
		Set <SubCategory> subCategories = new HashSet <SubCategory> ();
		subCategories.add ( setSubCategory ("Kitchen", products));
		return setCategory ( "Appliances", subCategories);
	}
	
	private Product setProduct ( String attribute ){
		Product product = new Product ();
		product.setAttribute(attribute);
		product.setActive(true);
		product.setCPD( new Date () );
		product.setUPD( new Date () );

		return product;
	}
	
	private SubCategory setSubCategory (String attribute, Set <Product> products){
		SubCategory subCategory = new SubCategory ();
		subCategory.setAttribute(attribute);
		subCategory.setProduct(products);
		subCategory.setActive(true);
		subCategory.setCPD( new Date () );
		subCategory.setUPD( new Date () );
		return subCategory;
	}
	
	private Category setCategory (String attribute, Set <SubCategory> subCategories) {
		Category category = new Category ();
		category.setAttribute(attribute);
		category.setSubCategory(subCategories);
		category.setActive(true);
		category.setCPD( new Date () );
		category.setUPD( new Date () );

		return category;
	}
}
