package com.bazzar.dao;

import com.bazzar.domain.order.Order;

public interface OrderDao {
	
	public void add ( Order order );
	public Order get ( Long id );
	public void edit ( Order order );
	public void delete ( Order order );

}
