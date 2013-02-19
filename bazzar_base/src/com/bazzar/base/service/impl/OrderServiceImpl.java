package com.bazzar.base.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bazzar.base.dao.OrderDao;
import com.bazzar.base.domain.order.Order;
import com.bazzar.base.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	OrderDao orderDao;
	
	public List<Order> getOrders() {
		return orderDao.getOrders();
	}
	public List<Order> getOrdersForCustomer(Long customerId) {
		return orderDao.getOrdersForCustomer(customerId);
	}
	public Order getOrder(Long orderId) {
		return orderDao.getOrder(orderId);
	}
	public Long createOrder(Order order) {
		return orderDao.createOrder(order);
	}
	public void editOrder(Order order) {
		orderDao.editOrder(order);
	}
	public Order getOrderByInvoice(String invoiceNumber) {
		return orderDao.getOrderByInvoice(invoiceNumber);
	}
	public void delete ( Long orderId ){
		orderDao.delete ( orderId );
	}
}
