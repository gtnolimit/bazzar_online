package com.bazzar.base.service.impl;

import java.util.Iterator;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bazzar.base.dao.CustomerDao;
import com.bazzar.base.dao.HomeDao;
import com.bazzar.base.domain.Address;
import com.bazzar.base.domain.Home;
import com.bazzar.base.domain.customer.Customer;
import com.bazzar.base.domain.lookup.AddressTypeLookup;
import com.bazzar.base.domain.order.Order;
import com.bazzar.base.domain.order.OrderDetail;
import com.bazzar.base.service.CustomerService;
import com.bazzar.base.service.HomeService;

@Service
public class CalculateOrder {
	
	@Autowired
	HomeService homeService;
	@Autowired
	CustomerService customerService;
	
	public CalculateOrder (){}
	
	public Order calculateOrder ( Order order, Home home, Customer customer ){
		System.out.println("inside calc service");
		order = calculateSubTotal ( order );
		System.out.println("Subtotal");
		order = calculateTax ( order, home, customer );
		System.out.println("Tax");
		//order = calculateShipping( order );
		System.out.println("Shipping");
		order = calculateTotal ( order );
		System.out.println("Total");
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
	
	public Order calculateTax ( Order order, Home home, Customer customer){
		System.out.println("inside Calc Tax");
		//Home company = homeService.get((long) 1);
		System.out.println("got home : " + home.getCompanyName());
		//Customer customer = customerService.get(order.getCustomer_id());
		System.out.println("got customer : " + customer.getFirstName());
		Set <Address> address = customer.getAddress();
		System.out.println("setting address");
		Iterator <Address> it = address.iterator();
		while ( it.hasNext () ){
			Address add = it.next();
			AddressTypeLookup addressType = add.getAddressType();
			System.out.println("State : " + add.getState().getCode() );
			if ( addressType.getCode ().equals ( "B" ) && add.getState ().getCode().equals (home.getHomeState()) )
				order.setOrderTax( order.getTotalBeforeTax() * home.getStateTax() / 100 );
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
