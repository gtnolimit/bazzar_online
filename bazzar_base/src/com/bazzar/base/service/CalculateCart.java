package com.bazzar.base.service;

import com.bazzar.base.domain.order.Cart;

public interface CalculateCart {

	public Cart calculateSubTotal (Cart shippingCart);
}
