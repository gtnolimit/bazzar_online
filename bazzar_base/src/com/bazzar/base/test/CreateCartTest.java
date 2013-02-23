package com.bazzar.base.test;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.bazzar.base.domain.item.Item;
import com.bazzar.base.domain.order.Cart;
import com.bazzar.base.domain.order.CartDetail;
import com.bazzar.base.service.impl.CalculateCart;

public class CreateCartTest {
	
	public CreateCartTest (){}
	
	public Cart updateCart ( Cart cart ){
		CalculateCart cartCalcImpl = new CalculateCart ();
		cart = cartCalcImpl.calculateSubTotal(cart);
		
		return cart;
	}
	
	public Cart addCart ( Cart cart, Item item, int qty, String session, String ip ){
		
		Cart cart_t;
		Set <CartDetail> detail = new HashSet <CartDetail> ();
		
		if ( cart == null ){
			System.out.println("creating cart - was empty");
			cart_t = new Cart ();
			cart_t.setCPD(new Date ());
			cart_t.setIp(ip);
			cart_t.setSessionNumber(session);
			System.out.println ("Cart Created");
		}
		else{
			cart_t = cart;
			detail = cart.getDetail();
		}
			
		cart_t.setUPD( new Date());
		System.out.println ("getting cart details");
		
		detail.add(this.setCartDetail(item, qty));
		
		cart_t.setItemCount(detail.size());
		cart_t.setDetail(detail);
		System.out.println ("about to calc");
		CalculateCart cartCalcImpl = new CalculateCart ();
		cart_t = cartCalcImpl.calculateSubTotal(cart_t);
		System.out.println ("About to return");
		return cart_t;
	}

	private CartDetail setCartDetail ( Item item, int qty ){
		CartDetail cd = new CartDetail ( );
		cd.setCPD ( new Date ( ) );
		cd.setUPD ( new Date ( ) );
		cd.setItemId ( item.getId ( ) );
		cd.setPrice ( item.getSalePrice ( ) );
		cd.setQty ( qty );
		return cd;
	}

}
