package com.bazzar.ui.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
//import java.util.HashSet;
//import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
//import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bazzar.dao.ItemDao;
import com.bazzar.dao.LookupTypeDao;
import com.bazzar.domain.menu.Category;
import com.bazzar.domain.menu.SubCategory;
import com.bazzar.test.ItemTest;
import com.bazzar.test.MenuTest;
import com.bazzar.ui.model.FormValidator;
import com.bazzar.dao.MenuDao;


@Controller
public class HomeController {

	@Resource
	MenuDao menuDao;
	@Resource
	ItemDao itemDao;
	
	@Autowired
	private LookupTypeDao lookupTypeDao;
	
	@Autowired
	private FormValidator validator;
	
	@RequestMapping("/home")
	public String home()
	{
		return "home";
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) 
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	@RequestMapping("/runMenuTest")
	public String runMenuTest (){
		MenuTest test = new MenuTest ( );
		test.createMenu ( menuDao );
		return "home";
	}
	@RequestMapping("/runItemTest")
	public String runItemTest (){
		ItemTest itemTest = new ItemTest ();
		itemTest.createItem(itemDao, menuDao);
		return "home";
	}
	@RequestMapping("/viewAllCategory")
	public ModelAndView getAllContacts()
	{

		ModelAndView mav = new ModelAndView("menu/showCategory");
		
		Category _cat = new Category();
		_cat.setAttribute("test");
		_cat.setUPD(new Date());
		_cat.setCPD(new Date());
		_cat.setActive(true);
		
		Set<SubCategory> _subSet = _cat.getSubCategory();
		
		SubCategory _sub = new SubCategory();
		_sub.setAttribute("test2");
		_sub.setUPD(new Date());
		_sub.setCPD(new Date());
		_sub.setActive(true);
		_subSet.add(_sub);
				
		SubCategory _sub1 = new SubCategory();
		_sub1.setAttribute("test3");
		_sub1.setUPD(new Date());
		_sub1.setCPD(new Date());
		_sub1.setActive(true);
		_subSet.add(_sub1);
		
		
		SubCategory _sub2 = new SubCategory();
		_sub2.setAttribute("test4");
		_sub2.setUPD(new Date());
		_sub2.setCPD(new Date());
		_sub2.setActive(true);
		_subSet.add(_sub2);
		
		_cat.setSubCategory(_subSet);
		menuDao.add(_cat);
		
		List<Category> categories = menuDao.getAllCategories ();
		for (int i=0; i < categories.size(); i++){
			Category cat = categories.get(i);
			//System.out.println(cat.getValue());
			Set <SubCategory> subSet = (Set<SubCategory>) cat.getSubCategory();
			mav.addObject("SEARCH_SUBCATEGORY_RESULTS_KEY", subSet);
			//Iterator<SubCategory> subSetIt = subSet.iterator();
			//while ( subSetIt.hasNext()){
			//	SubCategory sub = (SubCategory) subSetIt.next();
			//	System.out.println(sub.getValue());
			//}
			//List <SubCategory> subCategory = getSubCategory ( cat.getId());
		}
		mav.addObject("SEARCH_CATEGORY_RESULTS_KEY", categories);
		return mav;
	}
	
}
