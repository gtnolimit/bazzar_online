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

import com.bazzar.dao.OrderDao;
import com.bazzar.domain.order.Order;
import com.bazzar.ui.model.FormValidator;

@Controller
@RequestMapping("/order")
public class OrderController {

	@Resource
	OrderDao orderDao;
	
	@Autowired
	private FormValidator validator;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) 
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
	@RequestMapping ( value="/{order_id}", method = RequestMethod.GET )
	public ModelAndView getOrder ( @PathVariable String order_id ){
		ModelAndView mav = new ModelAndView ( "showOrder" );
		Order order = orderDao.get ( new Long ( order_id ) );
		mav.addObject ( "ORDER_RESULT_KEY", order );
		return mav;
	}
}
