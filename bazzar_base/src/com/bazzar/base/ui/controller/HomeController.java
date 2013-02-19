package com.bazzar.base.ui.controller;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import com.bazzar.base.dao.ItemDao;
import com.bazzar.base.dao.MenuDao;
import com.bazzar.base.dao.SearchDao;
import com.bazzar.base.domain.item.Item;
import com.bazzar.base.domain.menu.Product;
import com.bazzar.base.test.CreateItemTest;
import com.bazzar.base.test.CreateMenuTest;

@Controller
public class HomeController {

	@Autowired
	private View jsonView_i;
	@Autowired 
	MenuDao menuDao;
	@Autowired 
	ItemDao itemDao;
	@Autowired 
	SearchDao searchDao;
	
	private static final String HOME_FIELD = "home";
	private static final String ITEM_FIELD = "item";
	private static final String ERROR_FIELD = "error";
	
	@RequestMapping(value = "/createMenuTest/", method = RequestMethod.GET)
	public ModelAndView createMenu() {
		CreateMenuTest cm = new CreateMenuTest ();
		menuDao.add( cm.setAppliances() );
		menuDao.add( cm.setAudio() );
		menuDao.add( cm.setCamerasCamcorders() );
		menuDao.add( cm.setComputers() );
		menuDao.add( cm.setPortableElectronics() );
		return new ModelAndView(jsonView_i, HOME_FIELD, null);
	}
	@RequestMapping(value = "/createItemTest/", method = RequestMethod.GET)
	public ModelAndView createItem() {
		Set <Item> items = new HashSet <Item> ();
		CreateItemTest cit = new CreateItemTest();
		Item micr = cit.setMicrovave();
		Long itemMicro = itemDao.addItem(micr);
		System.out.println("Item Out will be this : " + itemMicro);
		items.add(micr);
		List <Product> microwaves = searchDao.findProductByName("Microwave Ovens");
		Iterator<Product> itM = microwaves.iterator();
		while ( itM.hasNext() ){
			Product pr = (Product) itM.next();
			System.out.println("ProductFound : " + pr.getAttribute());
			pr.setItem(items);
			System.out.println("Item added to Product: ");
			menuDao.edit(pr);
		}
		System.out.println("product saved");
		return new ModelAndView(jsonView_i, ITEM_FIELD, null);
	}
	@RequestMapping(value = { "/addItemCart/{itemId}" }, method = { RequestMethod.GET })
	public ModelAndView addItemCart(@PathVariable("itemId") String itemId,
				   HttpServletResponse httpResponse_p) {
		Item item;
		try {
			Long id = Long.parseLong(itemId);
			//item = itemService_i.getItem( id );
		} catch (Exception e) {
			String sMessage = "Error finding product. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		httpResponse_p.setStatus(HttpStatus.OK.value());
		return new ModelAndView(jsonView_i, ITEM_FIELD, null ); //item);
	}
	@RequestMapping(value = { "/createOrder/{cartId}" }, method = { RequestMethod.GET })
	public ModelAndView createOrder(@PathVariable("cartId") String cartId,
				   HttpServletResponse httpResponse_p) {
		Item item;
		try {
			Long id = Long.parseLong(cartId);
			//item = itemService_i.getItem( id );
		} catch (Exception e) {
			String sMessage = "Error finding product. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		httpResponse_p.setStatus(HttpStatus.OK.value());
		return new ModelAndView(jsonView_i, ITEM_FIELD, null ); //item);
	}
	public void setJsonView(View view) {
		jsonView_i = view;
	}
	private ModelAndView createErrorResponse(String sMessage) {
		return new ModelAndView(jsonView_i, ERROR_FIELD, sMessage);
	}
}
