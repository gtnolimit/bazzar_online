package com.bazzar.ui.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

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

import com.bazzar.dao.ItemDao;
import com.bazzar.domain.item.Item;
import com.bazzar.ui.model.FormValidator;

@Controller
@RequestMapping("/item")
public class ItemController {
	
	@Resource
	ItemDao itemDao;
	
	@Autowired
	private FormValidator validator;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) 
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	@RequestMapping ( value="/{item_id}", method = RequestMethod.GET )
	public ModelAndView getItem ( @PathVariable String item_id ){
		ModelAndView mav = new ModelAndView ( "showItem" );
		Item item = itemDao.getItem ( new Long ( item_id ) );
		mav.addObject ( "ITEM_RESULT_KEY", item );
		return mav;
	}
}
