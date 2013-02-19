package com.bazzar.base.dao;

import com.bazzar.base.domain.Home;

public interface HomeDao {

	public int create ( Home cart );
	public void edit ( Home cart );
	public Home get ( Long homeId );
}
