package com.bazzar.base.service;

import java.util.List;

import com.bazzar.base.domain.item.Item;

public interface ItemService {

	public List <Item> getAllItems ( );
	public Item getItemQuestions ( Long id );
	public Item getItemReviews ( Long id );
	public Item getItemAccessories ( Long id );
	public Item getItem ( Long id );
}
