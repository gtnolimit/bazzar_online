package com.bazzar.base.service;

import com.bazzar.base.domain.order.Order;

public interface ProcessOrderService {

	public Order processOrderComplete ( Long orderId );
}
