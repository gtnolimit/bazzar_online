package com.bazzar.base.dao;

import com.bazzar.base.domain.order.Cart;

public interface CartDao {
	
	public Long create ( Cart cart );
	public void delete ( Cart cart );
	public void delete ( Long cartId );
	public void edit ( Cart cart );
	public Cart get ( Long cartId );
	
	public Cart findCartByCustomerId ( Long customerId );

}
