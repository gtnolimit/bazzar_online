package com.bazzar.base.service.impl;

import com.bazzar.base.domain.order.Cart;
import com.bazzar.base.service.CalculateCart;

public class CalculateCartImpl implements CalculateCart{

	public Cart calculateSubTotal (Cart shippingCart){
/*
		double subTotal = 0.00;
		Set <Item> items = shippingCart.getItem();
		Set <OrderDetail> quantities = shippingCart.getDetail();
		if ( !items.isEmpty () ){
			Iterator <Item> itemsIt = items.iterator();
			while ( itemsIt.hasNext() ){
				Item item = ( Item ) itemsIt.next();
				Iterator<OrderDetail> quantitiesIt = quantities.iterator();
				while ( quantitiesIt.hasNext () ){
					OrderDetail quantity = ( OrderDetail ) quantitiesIt.next();
					if ( item.getId() == quantity.getItemId () )
						subTotal += item.getSalePrice() * quantity.getQty();		
				}
			}
		}
		shippingCart.setShoppingCartSubTotal ( subTotal );
		return shippingCart;
*/
		return null;
	}
}
