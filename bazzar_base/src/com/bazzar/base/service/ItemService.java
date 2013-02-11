package com.bazzar.base.service;

import java.util.List;

import com.bazzar.base.domain.item.Item;

public interface ItemService {

	public List <Item> getAllItems ( );
	public Item getItemQuestions ( Long id );
	public Item getItemReviews ( Long id );
	public Item getItemAccessories ( Long id );
	public Item getItem ( Long id );
	
	public List <Item> findItemsByName ( String itemName );
	public List <Item> findItemsByManufactureNumber ( String manufactureNumber );
	public List <Item> findItemsByManufacture ( String manufacture );
	public List <Item> findItemsByDescription ( String description );
 	
	public void editItem ( Item item );
	public int addItem ( Item item );
	public void delete ( Long id );
	public void delete ( Item item );
}
