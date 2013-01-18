package com.bazzar.service.order;

import java.util.Iterator;
import java.util.Set;

import com.bazzar.domain.Address;
import com.bazzar.domain.item.Item;
import com.bazzar.domain.lookup.AddressTypeLookup;
import com.bazzar.domain.order.Order;
import com.bazzar.domain.order.OrderDetail;

public class CalculateOrder {
	
	public Order calculateOrder (Order order){
		order = calculateSubTotal ( order );
		order = calculateTax ( order );
		order = calculateShipping( order );
		order = calculateTotal ( order );
		return order; 
	}
	
	public Order calculateSubTotal (Order order){
		double subTotal = 0.00;
		Set <Item> items = order.getItem();
		Set <OrderDetail> quantities = order.getDetail();
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
		order.setTotalBeforeTax(subTotal);
		return order;
	}
	// TODO create a tax class and table for Home state
	public Order calculateTax ( Order order){
		Set <Address> address = order.getAddress();
		Iterator <Address> addressIt = address.iterator();
		while ( addressIt.hasNext () ){
			Address add = addressIt.next();
			AddressTypeLookup addressType = add.getAddressType();
			if ( addressType.getCode ().equals ( "B" ) && add.getState ().equals ("IL") )
				order.setOrderTax( order.getTotalBeforeTax() * 9.75 / 100 );
		}
		return order;
	}
	public Order calculateShipping ( Order order ){
		double shippingAndHandling = 0.00;
		//TODO make calculations for shipping
		order.setShippingHandling ( shippingAndHandling );
		return order;
	}
	public Order calculateTotal ( Order order ){
		order.setOrderTotal ( order.getTotalBeforeTax() + 
				order.getOrderTax() + order.getShippingHandling() );
		return order;
	}
}
