package com.bazzar.base.service;

import com.bazzar.base.domain.order.Cart;

public interface CartService {

	public int create ( Cart cart );
	public void delete ( Cart cart );
	public void delete ( Long cartId );
	public void edit ( Cart cart );
	public Cart get ( Long cartId );
	
	public Cart findCartByCustomerId ( Long customerId );

}
