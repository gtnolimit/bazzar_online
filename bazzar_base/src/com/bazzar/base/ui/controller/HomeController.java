package com.bazzar.base.ui.controller;

import java.util.ArrayList;
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

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.bazzar.base.dao.CartDao;
import com.bazzar.base.dao.ItemDao;
import com.bazzar.base.dao.MenuDao;
import com.bazzar.base.dao.SearchDao;
import com.bazzar.base.domain.item.Item;
import com.bazzar.base.domain.menu.Product;
import com.bazzar.base.domain.order.Cart;
import com.bazzar.base.test.CreateCartTest;
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
	@Autowired
	CartDao cartDao;
	
	private static final String HOME_FIELD = "home";
	private static final String ITEM_FIELD = "item";
	private static final String ERROR_FIELD = "error";
	private static final String CART_FIELD = "cart";
	
	//private static final String ip = "10.10.120.122";
	private static final String session = "3344556677-456";

	
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
	@RequestMapping(value = "/createCartTest/", method = RequestMethod.GET)
	public ModelAndView createCart() {
		// TODO delete after testing
		String ip = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes())
		           .getRequest().getRemoteAddr();
		CreateCartTest cct = new CreateCartTest ();
		Item item = itemDao.getItem( (long) 1 );
		Cart cart = cartDao.findCartByIp(ip);
		cart = cct.addCart(cart, item, 10, session, ip);
		cart = cartDao.edit(cart);
		return new ModelAndView(jsonView_i, CART_FIELD, cart);
	}
	
	@RequestMapping(value = "/updateCartTest/", method = RequestMethod.GET)
	public ModelAndView updateCart() {
		// TODO delete after testing
		String ip = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes())
		           .getRequest().getRemoteAddr();
		Cart cart = cartDao.findCartByIp(ip);
		CreateCartTest cct = new CreateCartTest ();
		Item item = itemDao.getItem( (long) 2 );
		cart = cct.addCart(cart, item, 5, session, ip);
		cart = cartDao.edit(cart);
		return new ModelAndView(jsonView_i, CART_FIELD, cart);
	}
	
	@RequestMapping(value = "/createItemTest/", method = RequestMethod.GET)
	public ModelAndView createItem() {
		Set <Item> items = new HashSet <Item> ();
		CreateItemTest cit = new CreateItemTest();
		Item item = null;
		Iterator it = null;
		Long id = (long) 0;
		List <Product> products = new ArrayList <Product> ();
		Product product = null;
		
		// set microwave
		item = cit.setMicrovave();
		//id = itemDao.addItem(item);
		items.add(item);
		products = searchDao.findProductByName("Microwave Ovens");
		it = products.iterator();
		while ( it.hasNext() ){
			product = (Product) it.next();
			product.setItem(items);
			menuDao.edit(product);
			System.out.println("Looping");
		}
		System.out.println("Item added to Product: ");
		System.out.println("ProductFound : " + product.getAttribute());
		System.out.println("Item Out will be this : " + id);
		items.clear();
		
		// set ipod
		item = cit.setIPodShuff();
		//id = itemDao.addItem(item);
		items.add(item);
		products = searchDao.findProductByName("iPods & MP3 Players");
		it = products.iterator();
		while ( it.hasNext() ){
			product = (Product) it.next();
			product.setItem(items);
			menuDao.edit(product);
		}
		System.out.println("Item added to Product: ");
		System.out.println("ProductFound : " + product.getAttribute());
		System.out.println("Item Out will be this : " + id);
		items.clear();
		
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
