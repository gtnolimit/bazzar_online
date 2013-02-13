package com.bazzar.base.dao;

import com.bazzar.base.domain.customer.Home;

public interface HomeDao {
	
	public int add ( Home company );
	public void edit ( Home comoany );
	public Home get ( Long id );

}
