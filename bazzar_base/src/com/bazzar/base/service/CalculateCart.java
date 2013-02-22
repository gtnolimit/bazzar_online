package com.bazzar.base.service;


import java.util.Iterator;
import java.util.Set;

import com.bazzar.base.domain.order.Cart;
import com.bazzar.base.domain.order.CartDetail;

public class CalculateCart{

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
