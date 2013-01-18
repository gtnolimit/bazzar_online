package com.bazzar.dao;

import com.bazzar.domain.order.ShoppingCart;

public interface ShoppingCartDao {
	
	public void add ( ShoppingCart shoppingCart );
	public ShoppingCart get ( Long id );
	public void edit ( ShoppingCart shoppingCart );
	public void delete ( ShoppingCart shoppingCart );
	
	//public ShoppingCart getShoppingCartByUserId ( Long userId );
	

}
