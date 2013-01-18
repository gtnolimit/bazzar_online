package com.bazzar.service.order;

import java.util.Iterator;
import java.util.Set;

import com.bazzar.domain.item.Item;
import com.bazzar.domain.order.OrderDetail;
import com.bazzar.domain.order.ShoppingCart;

public class CalculateShippingCart {

	public ShoppingCart calculateSubTotal (ShoppingCart shippingCart){
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
	}
}
