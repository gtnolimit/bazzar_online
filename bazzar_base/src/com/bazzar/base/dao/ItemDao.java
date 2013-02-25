package com.bazzar.base.dao;

import java.util.List;

import com.bazzar.base.domain.item.Item;
import com.bazzar.base.domain.menu.Category;
import com.bazzar.base.domain.menu.Product;

public interface ItemDao {

	public Item getItem(Long id);

	public Item getItemQuestions(Long id);

	public Item getItemReview(Long id);

	public Item getItemAccessories(Long id);

	public List<Item> getAllItems();

	public List<Item> findItemsByName(String itemName);

	public List<Item> findItemsByManufactureNumber(String manufactureNumber);

	public List<Item> findItemsByManufacture(String manufacture);

	public List<Item> findItemsByDescription(String description);

	public void editItem(Item item);

	public int addItem(Item item);

	public void delete(Long id);

	public void delete(Item item);

	public void batchInsert(List<Item> items, Product product);

	public List<Category> findItemParent(List<String> categories);

	public void createCategory(Category category);

}
