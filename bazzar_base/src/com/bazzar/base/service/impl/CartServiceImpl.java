package com.bazzar.base.service.impl;

import java.util.Iterator;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bazzar.base.dao.CartDao;
import com.bazzar.base.domain.order.Cart;
import com.bazzar.base.domain.order.CartDetail;
import com.bazzar.base.service.CartService;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	CartDao cartDao;
	
	public Long create(Cart cart) {
		return cartDao.create(cart);
	}
	public void delete(Cart cart) {
		cartDao.delete(cart);
	}
	public void delete(Long cartId) {
		cartDao.delete(cartId);
	}
	public Cart edit(Cart cart) {
		return cartDao.edit(cart);
	}
	public Cart get(Long cartId) {
		return cartDao.get(cartId);
	}
	public Cart findCartByCustomerId(Long customerId) {
		return cartDao.findCartByCustomerId(customerId);
	}
	public Cart findCartBySession(String session) {
		return cartDao.findCartBySession(session);
	}
	public Cart findCartByIp(String ip) {
		return cartDao.findCartByIp(ip);
	}
	public Cart calculateSubTotal ( Cart cart ){
		double subTotal = 0.00;
		Set <CartDetail> detail = cart.getDetail ( );
		Iterator <CartDetail> it = detail.iterator ( );
		while ( it.hasNext ( ) ){
			CartDetail cd = ( CartDetail ) it.next ( );
			subTotal += cd.getPrice ( ) * cd.getQty ( );		
		}
		cart.setShoppingCartSubTotal ( subTotal );
		return cart;
	}
}
