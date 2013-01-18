package com.bazzar.dao;

import java.util.Set;

import com.bazzar.domain.item.Item;
import com.bazzar.domain.item.Manufacture;

public interface ItemDao {
	
	//Item
	public void add ( Item item );
	public Item getItem ( Long id );
	public void deleteItem ( Long id );
	public void delete ( Item item );
	public void edit ( Item item );
	
	public Set <Item> getAllReviewsForItem ( Long id );
	public Set <Item> getAllQuestionsForItem ( Long id );
	
	//Manufacture
	public void add ( Manufacture manufacture );
	public Manufacture getManufacture ( Long id );
	public void deleteManufacture ( Long id );
	public void delete ( Manufacture manufacture );
	public void edit ( Manufacture manufacture );
	
	
	
}
