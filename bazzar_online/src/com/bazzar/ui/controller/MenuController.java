package com.bazzar.ui.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.bazzar.dao.MenuDao;
import com.bazzar.domain.menu.Category;
import com.bazzar.domain.menu.SubCategory;
import com.bazzar.ui.model.FormValidator;

@Controller
@RequestMapping("/menu")
public class MenuController {
	
	@Resource
	MenuDao menuDao;
	
	@Autowired
	private FormValidator validator;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) 
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
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
}
