package com.bazzar.base.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bazzar.base.dao.CartDao;
//import com.bazzar.base.dao.CustomerSequenceIdDao;
import com.bazzar.base.domain.order.Cart;
//import com.bazzar.base.domain.order.CustomerSequenceId;
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
	public void edit(Cart cart) {
		cartDao.edit(cart);
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
}
