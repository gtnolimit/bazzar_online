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
import com.bazzar.base.dao.CustomerDao;
import com.bazzar.base.dao.HomeDao;
import com.bazzar.base.dao.ItemDao;
import com.bazzar.base.dao.MenuDao;
import com.bazzar.base.dao.OrderDao;
import com.bazzar.base.dao.SearchDao;
import com.bazzar.base.domain.Home;
import com.bazzar.base.domain.customer.Customer;
import com.bazzar.base.domain.item.Item;
import com.bazzar.base.domain.menu.Product;
import com.bazzar.base.domain.order.Cart;
import com.bazzar.base.domain.order.Order;
import com.bazzar.base.service.CartService;
import com.bazzar.base.service.CustomerService;
import com.bazzar.base.service.ItemService;
import com.bazzar.base.service.MenuService;
import com.bazzar.base.service.OrderService;
import com.bazzar.base.service.SearchService;
import com.bazzar.base.test.CreateCartTest;
import com.bazzar.base.test.CreateItemTest;
import com.bazzar.base.test.CreateMenuTest;
import com.bazzar.base.test.CreateOrderTest;

@Controller
public class HomeController {

	@Autowired
	private View jsonView_i;
	@Autowired 
	MenuService menuDao;
	@Autowired 
	ItemService itemDao;
	@Autowired 
	SearchService searchDao;
	@Autowired
	CartService cartDao;
	@Autowired
	OrderService orderDao;
	@Autowired
	CustomerService custDao;
	@Autowired
	HomeDao homeDao;
	
	private static final String HOME_FIELD = "home";
	private static final String ITEM_FIELD = "item";
	private static final String ERROR_FIELD = "error";
	private static final String CART_FIELD = "cart";
	private static final String ORDER_FIELD = "order";
	
	//private static final String ip = "10.10.120.122";
	private static final String session = "3344556677-456";

	
	@RequestMapping(value = "/createMenuTest/", method = RequestMethod.GET)
	public ModelAndView createMenu() {
		CreateMenuTest cm = new CreateMenuTest ();
		menuDao.create( cm.setAppliances() );
		menuDao.create( cm.setAudio() );
		menuDao.create( cm.setCamerasCamcorders() );
		menuDao.create( cm.setComputers() );
		menuDao.create( cm.setPortableElectronics() );
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
		System.out.println("cart id : " + cart.getId());
		return new ModelAndView(jsonView_i, CART_FIELD, cart);
	}
	@RequestMapping(value = "/createItemTest/", method = RequestMethod.GET)
	public ModelAndView createItem() {
		Set <Item> items = new HashSet <Item> ();
		CreateItemTest cit = new CreateItemTest();
		Item item = null;
		Iterator<Product> it = null;
		Long id = (long) 0;
		List <Product> products = new ArrayList <Product> ();
		Product product = null;
		item = cit.setMicrovave();
		items.add(item);
		products = searchDao.findProdactByName("Microwave Ovens");
		it = products.iterator();
		while ( it.hasNext() ){
			product = (Product) it.next();
			product.setItem(items);
			menuDao.update(product);
		}
		items.clear();
		item = cit.setIPodShuff();
		items.add(item);
		products = searchDao.findProdactByName("iPods & MP3 Players");
		it = products.iterator();
		while ( it.hasNext() ){
			product = (Product) it.next();
			product.setItem(items);
			menuDao.update(product);
		}
		items.clear();
		return new ModelAndView(jsonView_i, ITEM_FIELD, null);
	}
	@RequestMapping(value = { "/createOrder/{cartId}" }, method = { RequestMethod.GET })
	public ModelAndView createOrder(@PathVariable("cartId") String cartId,
				   HttpServletResponse httpResponse_p) {
		Order order = null;
		try {
			Long id = Long.parseLong(cartId);
			Cart cart = cartDao.get(id);
			System.out.println("got cart");
			CreateOrderTest cot = new CreateOrderTest ();
			order = cot.createOrder(cart);
			System.out.println("order returned");
			Long orderId = orderDao.createOrder(order);
			//cartDao.delete(id);
			System.out.println("order saved id : " + orderId);
		} catch (Exception e) {
			String sMessage = "Error finding product. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		httpResponse_p.setStatus(HttpStatus.OK.value());
		return new ModelAndView(jsonView_i, ORDER_FIELD, order ); 
	}
	@RequestMapping(value = { "/personOrder/{orderId}" }, method = { RequestMethod.GET })
	public ModelAndView personOrder(@PathVariable("orderId") String orderId,
				   HttpServletResponse httpResponse_p) {
		Order order = null;
		try {
			Long id = Long.parseLong(orderId);
			order = orderDao.getOrder(id);
			CreateOrderTest cot = new CreateOrderTest();
			Customer cust = cot.createCastomer( order.getSessionNumber(), order.getIp());
			order.setCustomer_id(custDao.create(cust));
			orderDao.editOrder(order);
		} catch (Exception e) {
			String sMessage = "Error finding product. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}
		
		httpResponse_p.setStatus(HttpStatus.OK.value());
		return new ModelAndView(jsonView_i, ORDER_FIELD, order ); 
	}
	@RequestMapping(value = { "/calcOrder/{orderId}" }, method = { RequestMethod.GET })
	public ModelAndView calcOrder(@PathVariable("orderId") String orderId,
				   HttpServletResponse httpResponse_p) {
		Order order = null;
		try {
			Long id = Long.parseLong(orderId);
			order = orderDao.getOrder(id);
			CreateOrderTest cot = new CreateOrderTest();
			Home home = homeDao.get( (long) 1);
			System.out.println("customer id : " + order.getCustomer_id());
			Customer customer = custDao.get(order.getCustomer_id());
			order = cot.calcOrder(order, home, customer);
			orderDao.editOrder(order);
		} catch (Exception e) {
			String sMessage = "Error finding product. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}
		
		httpResponse_p.setStatus(HttpStatus.OK.value());
		return new ModelAndView(jsonView_i, ORDER_FIELD, order ); 
	}
	public void setJsonView(View view) {
		jsonView_i = view;
	}
	private ModelAndView createErrorResponse(String sMessage) {
		return new ModelAndView(jsonView_i, ERROR_FIELD, sMessage);
	}
}
