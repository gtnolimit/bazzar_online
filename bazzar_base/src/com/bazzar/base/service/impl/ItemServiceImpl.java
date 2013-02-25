package com.bazzar.base.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.bazzar.base.dao.ItemDao;
import com.bazzar.base.domain.item.Item;
import com.bazzar.base.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	ItemDao itemDao;

	@Autowired
	private RedisTemplate<String, Object> template;

	public List<Item> getAllItems() {
		return itemDao.getAllItems();
	}

	public Item getItemQuestions(Long id) {
		return itemDao.getItemQuestions(id);
	}

	public Item getItemReviews(Long id) {
		return itemDao.getItemReview(id);
	}

	public Item getItemAccessories(Long id) {
		return itemDao.getItemAccessories(id);
	}

	public Item getItem(Long id) {
		return itemDao.getItem(id);
	}

	public List<Item> findItemsByName(String itemName) {
		return itemDao.findItemsByName(itemName);
	}

	public List<Item> findItemsByManufactureNumber(String manufactureNumber) {
		return itemDao.findItemsByManufactureNumber(manufactureNumber);
	}

	public List<Item> findItemsByManufacture(String manufacture) {
		return itemDao.findItemsByManufacture(manufacture);
	}

	public List<Item> findItemsByDescription(String description) {
		return itemDao.findItemsByDescription(description);
	}

	public void editItem(Item item) {
		itemDao.editItem(item);
	}

	public int addItem(Item item) {
		return itemDao.addItem(item);
	}

	public void delete(Long id) {
		itemDao.delete(id);
	}

	public void delete(Item item) {
		itemDao.delete(item);
	}

	public Long importItems(JSONObject json) {
		Long jobId = addJob(json);
		template.convertAndSend("pubsub:queue", jobId.toString());
		return jobId;
	}

	public JSONObject validateImportItemRequest(String json) {
		return JSONObject.fromObject(json);
	}

	private Long addJob(JSONObject json) {
		final Map<String, Object> jobParams = new HashMap<String, Object>();

		jobParams.put("id", 1);
		jobParams.put("params", json.toString());
		jobParams.put("status", "pending");
		template.opsForHash().putAll("1", jobParams);

		return 1l;
	}
}
