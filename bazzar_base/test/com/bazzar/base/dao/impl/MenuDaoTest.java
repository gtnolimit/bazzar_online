package com.bazzar.base.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.bazzar.base.dao.ItemBatchDao;
import com.bazzar.base.dao.MenuDao;
import com.bazzar.base.domain.item.Item;
import com.bazzar.base.domain.menu.Category;
import com.bazzar.base.domain.menu.Product;
import com.bazzar.base.domain.menu.SubCategory;
import com.bazzar.base.job.batch.file.Document;
import com.bazzar.base.job.batch.parallel.CsvToItem;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/bazzar_base-context.xml",
        "/applicationContext.xml" })
@TransactionConfiguration(transactionManager = "transactionManager")
@Transactional
public class MenuDaoTest {

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	MenuDao menuDao;

	@Autowired
	ItemBatchDao itemBatchDao;

	@Before
	public void setUp() throws IOException {
	}

	@After
	public void tearDown() {
	}

	@Test
	public void testFindItemParent() throws IOException, URISyntaxException {
		Document document = Document
		        .fromFile("file:////Users/dben-avraham/projects/bazzar/bazzar_online_not_mavenized/bazzar_base/build/classes/com/bazzar/base/dao/impl/items-sample.csv");
		CsvToItem csvToItem = new CsvToItem(document.getLines().subList(1,
		        document.getLines().size()));
		Map<String, List<Item>> items = csvToItem.getItems();
		for (Entry<String, List<Item>> entry : items.entrySet()) {
			List<String> keys = Arrays.asList(entry.getKey().split(","));
			if (keys.size() > 2) {
				Product product = null;
				Category category = menuDao.getCategoryByAttribute(keys.get(0));
				if (category == null) {
					product = new Product();
					product.setAttribute(keys.get(2));
					product.setActive(true);
					SubCategory subCategory = new SubCategory();
					subCategory.setAttribute(keys.get(1));
					subCategory.setActive(true);
					subCategory.getProduct().add(product);
					category = new Category();
					category.setAttribute(keys.get(0));
					category.setActive(true);
					category.getSubCategory().add(subCategory);
					menuDao.add(category);
				} else {
					SubCategory subCategory = menuDao.findSubCategoryChild(
					        keys.get(0), keys.get(1));
					if (subCategory == null) {
						product = new Product();
						product.setAttribute(keys.get(2));
						product.setActive(true);
						subCategory = new SubCategory();
						subCategory.setAttribute(keys.get(1));
						subCategory.setActive(true);
						subCategory.getProduct().add(product);
						category.getSubCategory().add(subCategory);
						menuDao.edit(category);
					} else {
						product = menuDao.findProductChild(keys.get(0),
						        keys.get(1), keys.get(2));
						if (product == null) {
							product = new Product();
							product.setAttribute(keys.get(2));
							product.setActive(true);
							subCategory.getProduct().add(product);
							menuDao.edit(subCategory);
						}
					}
				}

				// To avoid false positives.
				sessionFactory.getCurrentSession().flush();

				product = menuDao.findProductChild(keys.get(0), keys.get(1),
				        keys.get(2));
				itemBatchDao.batchInsert(entry.getValue(), product);
			}
		}

		// To avoid false positives.
		sessionFactory.getCurrentSession().flush();

		List<Category> categories = menuDao.getAllCategories();
		assertNotNull("Categories should not be null", categories);
		assertEquals("There should be one category", 6, categories.size());

		List<SubCategory> subCategories = menuDao.getAllSubCategories();
		assertNotNull("Sub categories should not be null", subCategories);
		assertEquals("There should be six subcategory", 11,
		        subCategories.size());
	}
}
