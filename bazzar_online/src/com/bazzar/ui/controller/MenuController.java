package com.bazzar.ui.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
//import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.servlet.ModelAndView;

import com.bazzar.dao.MenuDao;
import com.bazzar.domain.menu.Category;
//import com.bazzar.domain.menu.SubCategory;
import com.bazzar.ui.model.FormValidator;

@Controller
@RequestMapping("/menu")
public class MenuController {
	
	@Resource
	MenuDao menuDao;
	
	@Autowired
	private FormValidator validator;
	
	//@PersistenceContext(unitName="menuService", 
    //        type=PersistenceContextType.TRANSACTION)
	@POST
    @Consumes(MediaType.APPLICATION_XML)
    public void create(Category category) {
		menuDao.add(category);
    }
	@GET
    @Produces(MediaType.APPLICATION_XML)
    @Path("{id}")
    public Category read(@PathParam("id") long id) {
        return menuDao.getCategory ( id );
    }
	@PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void update(Category category) {
		menuDao.edit ( category );
    }
	@DELETE
    @Path("{id}")
    public void delete(@PathParam("id") long id) {
		Category category = read(id);
        if(null != category) {
        	menuDao.deleteCategory ( id );
        }
    }
	@GET
    @Produces(MediaType.APPLICATION_XML)
    public List <Category> readAll() {
        return menuDao.getAllCategories();
    }
	
	@InitBinder
	public void initBinder(WebDataBinder binder) 
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	/*
	@RequestMapping ( "/viewAllCategories" )
	public ModelAndView getAllCategories ( ){
		ModelAndView mav = new ModelAndView ( "showCategories" );
		List<Category> category = menuDao.getAllCategories ( );
		mav.addObject ( "ALL_CATEGORY_RESULTS_KEY", category );
		return mav;
	}

	@RequestMapping ( value="/{category_id}", method = RequestMethod.GET )
	public ModelAndView getCategory ( @PathVariable String category_id ){
		ModelAndView mav = new ModelAndView ( "showCategories" );
		Category category = menuDao.getCategory ( new Long ( category_id ) );
		mav.addObject ( "CATEGORY_RESULT_KEY", category );
		return mav;
	}
	
	@RequestMapping ( value="/{subCategory_id}", method = RequestMethod.GET )
	public ModelAndView getSubCategory ( @PathVariable String subCategory_id ){
		ModelAndView mav = new ModelAndView ( "showSubCategory" );
		SubCategory subCategory = menuDao.getSubCategory ( new Long ( subCategory_id ) );
		mav.addObject ( "SUBCATEGORY_RESULT_KEY", subCategory );
		return mav;
	}
	
	@RequestMapping ( value="/{product_id}", method = RequestMethod.GET )
	public ModelAndView getProduct ( @PathVariable String product_id ){
		ModelAndView mav = new ModelAndView ( "showSubCategory" );
		SubCategory product = menuDao.getSubCategory ( new Long ( product_id ) );
		mav.addObject ( "PRODUCT_RESULT_KEY", product );
		return mav;
	}
	*/
}
