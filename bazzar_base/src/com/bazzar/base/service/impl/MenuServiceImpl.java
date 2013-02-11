package com.bazzar.base.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bazzar.base.dao.MenuDao;
import com.bazzar.base.domain.menu.Category;
import com.bazzar.base.domain.menu.Product;
import com.bazzar.base.domain.menu.SubCategory;
import com.bazzar.base.service.MenuService;

@Service
public class MenuServiceImpl implements MenuService {
	
	@Autowired
	MenuDao menuDao;
	
	public List <Category> getAllCategories ( ){
		return menuDao.getAllCategories ( );
	}
	public List <SubCategory> getAllSubCategories ( ){
		return menuDao.getAllSubCategories ( );
	}
	public List <Product> getAllProducts ( ){
		return menuDao.getAllProducts ( );
	}
	public Category getCategory ( Long id ){
		return menuDao.getCategory ( id ); 
	}
	public SubCategory getSubCategory ( Long id ){
		return menuDao.getSubCategory ( id ); 
	}
	public Product getProduct ( Long id ){
		return menuDao.getProductById ( id );
	}
	public Long create(Category category) {
		return menuDao.add( category );
	}
	public void update ( Category category ) {
		menuDao.edit ( category );
	}
	public void delete ( Category category ) {
		menuDao.delete ( category );
	}
	public void deleteCategoryById(Long id) {
		menuDao.deleteCategory ( id );
	}
	public Long create(SubCategory subCategory) {
		return menuDao.add(subCategory);
	}
	public void update(SubCategory subCategory) {
		menuDao.edit(subCategory);
	}
	public void delete(SubCategory subCategory) {
		menuDao.delete(subCategory);
	}
	public void deleteSubCategoryById(Long id) {
		menuDao.deleteSubCategory(id);
	}
	public Long create(Product product) {
		return menuDao.add(product);
	}
	public void update(Product product) {
		menuDao.edit(product);
	}
	public void delete(Product product) {
		menuDao.delete(product);
	}
	public void deleteProductById(Long id) {
		menuDao.deleteProduct(id);
	}
	public void edit(Category category_p) {
		// TODO Auto-generated method stub
		
	}
}
