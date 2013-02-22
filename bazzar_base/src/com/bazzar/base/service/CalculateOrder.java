package com.bazzar.base.service;

import java.util.Iterator;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bazzar.base.dao.HomeDao;
import com.bazzar.base.domain.Address;
import com.bazzar.base.domain.Home;
import com.bazzar.base.domain.lookup.AddressTypeLookup;
import com.bazzar.base.domain.order.Order;
import com.bazzar.base.domain.order.OrderDetail;

public class CalculateOrder {
	
	@Autowired
	HomeDao homeDao;
	
	public Order calculateOrder ( Order order ){
		order = calculateSubTotal ( order );
		order = calculateTax ( order );
		order = calculateShipping( order );
		order = calculateTotal ( order );
		return order; 
	}
	
	public Order calculateSubTotal ( Order order ){
		double subTotal = 0.00;
		Set <OrderDetail> orderDetails = order.getDetail();
		if ( !orderDetails.isEmpty ( ) ){
			Iterator <OrderDetail> odIter = orderDetails.iterator ( );
			while ( odIter.hasNext ( ) ){
				OrderDetail od = odIter.next();
				subTotal += od.getPrice ( ) * od.getQty ( );
			}
		}
		order.setTotalBeforeTax(subTotal);
		return order;
	}
	
	public Order calculateTax ( Order order){
		Home company = homeDao.get((long) 1);
		Set <Address> address = order.getAddress();
		Iterator <Address> addressIt = address.iterator();
		while ( addressIt.hasNext () ){
			Address add = addressIt.next();
			AddressTypeLookup addressType = add.getAddressType();
			if ( addressType.getCode ().equals ( "B" ) && add.getState ().equals ("IL") )
				order.setOrderTax( order.getTotalBeforeTax() * company.getStateTax() / 100 );
		}
		return order;
	}
	
	//TODO make calculations for shipping
	public Order calculateShipping ( Order order ){
		double shippingAndHandling = 0.00;
		order.setShippingHandling ( shippingAndHandling );
		return order;
	}
	
	public Order calculateTotal ( Order order ){
		order.setOrderTotal ( order.getTotalBeforeTax() + 
				order.getOrderTax() + order.getShippingHandling() );
		return order;
	}
}
