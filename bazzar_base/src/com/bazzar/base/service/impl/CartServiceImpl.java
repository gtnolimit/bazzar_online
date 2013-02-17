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
	
	//@Autowired
	//CustomerSequenceIdDao csIdDao;

	public int create(Cart cart) {
	
		/*
		if ( cart.getCustomer_id() == null ){
			CustomerSequenceId csi = new CustomerSequenceId ();
			Long defaultId = (long) csIdDao.create ( csi );
			cart.setCustomer_id("DEFAULT_" + defaultId);
		}
		*/
		return cartDao.create(cart);
	}

	public void delete(Cart cart) {
		cartDao.delete(cart);
	}

	public void delete(Long cartId) {
		cartDao.delete(cartId);
	}

	public void edit(Cart cart) {
		//if ( cart.getCustomer_id().startsWith("DAFAULT_"))
		cartDao.edit(cart);
	}

	public Cart get(Long cartId) {
		return cartDao.get(cartId);
	}

	public Cart findCartByCustomerId(Long customerId) {
		return cartDao.findCartByCustomerId(customerId);
	}

}
