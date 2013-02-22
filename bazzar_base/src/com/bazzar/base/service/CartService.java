package com.bazzar.base.service;

import com.bazzar.base.domain.order.Cart;

public interface CartService {

	public Long create ( Cart cart );
	public void delete ( Cart cart );
	public void delete ( Long cartId );
	public void edit ( Cart cart );
	public Cart get ( Long cartId );
	
	public Cart findCartByCustomerId ( Long customerId );
	public Cart findCartBySession ( String session );
	public Cart findCartByIp ( String ip );

}
