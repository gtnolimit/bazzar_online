package com.bazzar.base.dao;

import java.util.List;

import com.bazzar.base.domain.item.Item;

public interface SearchDao {

	public List <Item> findItemsByName ( String itemName );
	public List <Item> findItemsByManufactureNumber ( String manufactureNumber );
	public List <Item> findItemsByManufacture ( String manufacture );
	public List <Item> findItemsByDescription ( String description );
	public List <Item> findItemsByBarCode ( String barCode );
 
}
