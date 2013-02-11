package com.bazzar.base.service;

import com.bazzar.base.domain.order.Order;

public interface CalculateOrder {
	
	public Order calculateOrder (Order order);
	//public Order calculateSubTotal (Order order);
	public Order calculateTax ( Order order);
	public Order calculateShipping ( Order order );
	public Order calculateTotal ( Order order );

}
