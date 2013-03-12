package com.bazzar.base.job.batch;

/* ......................................................................................... */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ParseDate;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.dozer.CsvDozerBeanReader;
import org.supercsv.io.dozer.ICsvDozerBeanReader;
import org.supercsv.prefs.CsvPreference;

import com.bazzar.base.dao.ItemBatchDao;
import com.bazzar.base.dao.ItemDao;
import com.bazzar.base.dao.MenuDao;
import com.bazzar.base.domain.item.Item;
import com.bazzar.base.domain.menu.Category;
import com.bazzar.base.domain.menu.Product;
import com.bazzar.base.domain.menu.SubCategory;

/*
 * ..............................................................................
 * ...........
 */

class Document2 {
	private final List<String> lines;

	Document2(List<String> lines) {
		this.lines = lines;
	}

	List<String> getLines() {
		return this.lines;
	}

	static Document2 fromFile(Path file) throws IOException {
		List<String> lines = new ArrayList<>();
		Charset charset = Charset.forName("UTF-8");
		try (BufferedReader reader = Files.newBufferedReader(file, charset)) {
			String line = null;
			while ((line = reader.readLine()) != null) {
				lines.add(line);
			}
		}
		return new Document2(lines);
	}
}

/*
 * ..............................................................................
 * ...........
 */

public class BuildItemMap {

	ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
	        new String[] { "bazzar_base-context.xml", "applicationContext.xml" });

	/*
	 * ...........................................................................
	 * ..............
	 */

	Map<String, List<Item>> occurrencesCount(Path file) throws IOException {
		Map<String, List<Item>> items = new HashMap<>();
		// ignore age, and question/answer 3
		final String[] partialFieldMapping = new String[] { "id", "category",
		        "subCategory", "product", "subject", "description",
		        "specialOfferPrice", "specialPriceStart", "specialPriceEnd",
		        "specialPriceActive", "salePrice", "listedPrice", "quantity",
		        "rebate", "inStock", "manufactureModelNumber", "barCode",
		        "pageLocator", "active" };

		// set processors for ignored columns to null for efficiency (could have
		// used full array if we wanted them to execute anyway)
		final CellProcessor[] processors = new CellProcessor[] {
		        new Optional(), new Optional(), new Optional(), new Optional(),
		        new Optional(), new Optional(), new Optional(),
		        new Optional(new ParseDate("MM/dd/yyyy")),
		        new Optional(new ParseDate("MM/dd/yyyy")), new Optional(),
		        new Optional(), new Optional(), new Optional(), new Optional(),
		        new Optional(), new Optional(), new Optional(), new Optional(),
		        new Optional() };

		try (ICsvDozerBeanReader beanReader = new CsvDozerBeanReader(
		        new FileReader(file.toFile()),
		        CsvPreference.STANDARD_PREFERENCE)) {
			beanReader.getHeader(true); // ignore the header
			beanReader.configureBeanMapping(Item.class, partialFieldMapping);

			Item item = null;
			while ((item = beanReader.read(Item.class, processors)) != null) {
				if (items.containsKey(item.getCategory()
				        + item.getSubCategory() + item.getProduct())) {
					items.get(
					        item.getCategory() + item.getSubCategory()
					                + item.getProduct()).add(item);
				} else {
					List<Item> itemList = new ArrayList<Item>();
					itemList.add(item);
					items.put(
					        item.getCategory() + item.getSubCategory()
					                + item.getProduct(), itemList);
				}
			}
			ItemBatchDao itemBatchDao = (ItemBatchDao) ctx
			        .getBean(ItemDao.class);
			for (List<Item> listItems : items.values()) {
				itemBatchDao.batchInsert(listItems, null);
			}
		}

		return items;
	}

	/*
	 * ...........................................................................
	 * ..............
	 */

	Map<String, List<Item>> countOccurrencesOnSingleThread(Path file)
	        throws IOException {
		return occurrencesCount(file);
	}

	/*
	 * ...........................................................................
	 * ..............
	 */

	class ItemSearchTask2 extends RecursiveTask<Map<String, List<Item>>> {
		private final Path file;
		private final long start;
		private final long offset;
		private final long size;
		private long paddedStartOffset = 0;
		private final Map<String, List<Item>> items = new HashMap<>();

		ItemSearchTask2(Path file, long start, long offset, long size) {
			super();
			this.file = file;
			this.start = start;
			this.offset = offset;
			this.size = size;
		}

		@Override
		protected Map<String, List<Item>> compute() {
			MappedByteBuffer buffer = null;

			if (start > 0) {
				paddedStartOffset = start - Math.round(start * .05);
			}
			try (FileChannel fileChannel = (FileChannel.open(file,
			        EnumSet.of(StandardOpenOption.READ)))) {
				// if (start > 0) {
				// MappedByteBuffer readBehindBuffer = fileChannel.map(
				// FileChannel.MapMode.READ_ONLY,
				// start - Math.round(start * .02), start);
				// for (long i = readBehindBuffer.limit(); i >= 0; i--) {
				// if (readBehindBuffer.getChar((int) i) == '\n') {
				// start = i;
				// }
				// }
				// start -= Math.round(start * .02);
				// }
				buffer = fileChannel
				        .map(FileChannel.MapMode.READ_ONLY,
				                paddedStartOffset,
				                ((start - paddedStartOffset) + offset)
				                        + paddedStartOffset < size ? ((start - paddedStartOffset) + offset)
				                        : size - paddedStartOffset);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			String content = "";
			if (buffer != null) {
				try {
					Charset charset = Charset.forName("UTF-8");
					CharsetDecoder decoder = charset.newDecoder();
					CharBuffer charBuffer = decoder.decode(buffer);
					content = charBuffer.toString();
					long paddedStartOffsetIndex = 0;
					if (start > 0) {
						String beginingPad = content.substring(0,
						        (int) (start - paddedStartOffset));
						paddedStartOffsetIndex = beginingPad.lastIndexOf('\n') > beginingPad
						        .lastIndexOf('\r') ? beginingPad
						        .lastIndexOf('\n') : beginingPad
						        .lastIndexOf('\r');
					}
					long end = content.lastIndexOf('\n') > content
					        .lastIndexOf('\r') ? content.lastIndexOf('\n')
					        : content.lastIndexOf('\r');
					content = content.substring((int) paddedStartOffsetIndex,
					        (int) (end > 0 ? end : content.length()));
				} catch (CharacterCodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					buffer.clear();
				}
			}
			// ignore age, and question/answer 3
			final String[] partialFieldMapping = new String[] { "id",
			        "category", "subCategory", "product", "subject",
			        "description", "specialOfferPrice", "specialPriceStart",
			        "specialPriceEnd", "specialPriceActive", "salePrice",
			        "listedPrice", "quantity", "rebate", "inStock",
			        "manufactureModelNumber", "barCode", "pageLocator",
			        "active" };

			// set processors for ignored columns to null for efficiency (could
			// have
			// used full array if we wanted them to execute anyway)
			final CellProcessor[] processors = new CellProcessor[] {
			        new Optional(), new Optional(), new Optional(),
			        new Optional(), new Optional(), new Optional(),
			        new Optional(), new Optional(new ParseDate("MM/dd/yyyy")),
			        new Optional(new ParseDate("MM/dd/yyyy")), new Optional(),
			        new Optional(), new Optional(), new Optional(),
			        new Optional(), new Optional(), new Optional(),
			        new Optional(), new Optional(), new Optional() };

			try (ICsvDozerBeanReader beanReader = new CsvDozerBeanReader(
			        new StringReader(content),
			        CsvPreference.STANDARD_PREFERENCE)) {

				if (start == 0) {
					beanReader.getHeader(true); // ignore the header
				}
				beanReader
				        .configureBeanMapping(Item.class, partialFieldMapping);

				Item item;
				while ((item = beanReader.read(Item.class, processors)) != null) {
					if (items.containsKey(item.getCategory()
					        + item.getSubCategory() + item.getProduct())) {
						items.get(
						        item.getCategory() + item.getSubCategory()
						                + item.getProduct()).add(item);
					} else {
						List<Item> itemList = new ArrayList<Item>();
						itemList.add(item);
						items.put(item.getCategory() + item.getSubCategory()
						        + item.getProduct(), itemList);
					}
				}
				ItemBatchDao itemBatchDao = (ItemBatchDao) ctx
				        .getBean(ItemDao.class);
				for (List<Item> listItems : items.values()) {
					itemBatchDao.batchInsert(listItems, null);
				}
			} catch (IOException e) {
				throw new RuntimeException(e.getMessage(), e.getCause());
			}

			return items;
		}
	}

	/*
	 * ...........................................................................
	 * ..............
	 */

	class ItemSearchTask extends RecursiveTask<Map<String, List<Item>>> {
		private final Map<String, List<Item>> items = new HashMap<>();
		private final List<String> lines;

		ItemSearchTask(List<String> lines) {
			super();
			this.lines = lines;
		}

		@Override
		protected Map<String, List<Item>> compute() {
			// ignore age, and question/answer 3
			final String[] partialFieldMapping = new String[] { "id",
			        "category", "subCategory", "product", "subject",
			        "description", "specialOfferPrice", "specialPriceStart",
			        "specialPriceEnd", "specialPriceActive", "salePrice",
			        "listedPrice", "quantity", "rebate", "inStock",
			        "manufactureModelNumber", "barCode", "pageLocator",
			        "active" };

			// set processors for ignored columns to null for efficiency (could
			// have
			// used full array if we wanted them to execute anyway)
			final CellProcessor[] processors = new CellProcessor[] {
			        new Optional(), new Optional(), new Optional(),
			        new Optional(), new Optional(), new Optional(),
			        new Optional(), new Optional(new ParseDate("MM/dd/yyyy")),
			        new Optional(new ParseDate("MM/dd/yyyy")), new Optional(),
			        new Optional(), new Optional(), new Optional(),
			        new Optional(), new Optional(), new Optional(),
			        new Optional(), new Optional(), new Optional() };

			try (ICsvDozerBeanReader beanReader = new CsvDozerBeanReader(
			        new StringReader(StringUtils.join(lines, "\r")),
			        CsvPreference.STANDARD_PREFERENCE)) {

				// beanReader.getHeader(true); // ignore the header
				beanReader
				        .configureBeanMapping(Item.class, partialFieldMapping);

				Item item;
				int i = beanReader.getRowNumber();
				while ((item = beanReader.read(Item.class, processors)) != null) {
					if (items.containsKey(item.getCategory() + ","
					        + item.getSubCategory() + "," + item.getProduct())) {
						items.get(
						        item.getCategory() + ","
						                + item.getSubCategory() + ","
						                + item.getProduct()).add(item);
					} else {
						List<Item> itemList = new ArrayList<Item>();
						itemList.add(item);
						items.put(
						        item.getCategory() + ","
						                + item.getSubCategory() + ","
						                + item.getProduct(), itemList);
					}
					i++;
				}
				ItemBatchDao itemBatchDao = (ItemBatchDao) ctx
				        .getBean(ItemBatchDao.class);
				MenuDao menuDao = (MenuDao) ctx.getBean(MenuDao.class);
				for (Entry<String, List<Item>> entry : items.entrySet()) {
					List<String> keys = Arrays
					        .asList(entry.getKey().split(","));
					if (keys.size() > 2) {
						Product product = null;
						Category category = menuDao.getCategoryByAttribute(keys
						        .get(0));
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
							SubCategory subCategory = menuDao
							        .findSubCategoryChild(keys.get(0),
							                keys.get(1));
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

						itemBatchDao.batchInsert(entry.getValue(), product);
					}
				}
			} catch (IOException e) {
				throw new RuntimeException(e.getMessage(), e.getCause());
			}

			return items;
		}
	}

	/*
	 * ...........................................................................
	 * ..............
	 */

	class DocumentSearchTask2 extends RecursiveTask<Map<String, List<Item>>> {
		private final Map<String, List<Item>> items = new HashMap<>();
		private final Path file;

		DocumentSearchTask2(Path file) {
			super();
			this.file = file;
		}

		@Override
		protected Map<String, List<Item>> compute() {
			List<RecursiveTask<Map<String, List<Item>>>> forks = new LinkedList<>();
			long itemSize = 0;
			try {
				itemSize = Files.size(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for (long start = 0, offset = Math.round(itemSize / 4d); start < itemSize; start += offset) {
				ItemSearchTask2 task = new ItemSearchTask2(file, start, offset,
				        itemSize);
				forks.add(task);
				task.fork();
			}
			for (RecursiveTask<Map<String, List<Item>>> task : forks) {
				for (Entry<String, List<Item>> entry : task.join().entrySet()) {
					if (items.containsKey(entry.getKey())) {
						items.get(entry.getKey()).addAll(entry.getValue());
					} else {
						items.put(entry.getKey(), entry.getValue());
					}
				}
			}
			return items;
		}
	}

	/*
	 * ...........................................................................
	 * ..............
	 */

	class DocumentSearchTask extends RecursiveTask<Map<String, List<Item>>> {
		private final Map<String, List<Item>> items = new HashMap<>();
		private final Document2 document;

		DocumentSearchTask(Document2 document) {
			super();
			this.document = document;
		}

		@Override
		protected Map<String, List<Item>> compute() {
			List<RecursiveTask<Map<String, List<Item>>>> forks = new LinkedList<>();
			int itemSize = document.getLines().size();
			for (int startIndex = 1, offset = 51000; startIndex < itemSize; startIndex += offset) {
				ItemSearchTask task = new ItemSearchTask(
				        document.getLines()
				                .subList(
				                        startIndex,
				                        (startIndex + offset) <= itemSize ? (startIndex + offset)
				                                : itemSize));
				forks.add(task);
				task.fork();
			}
			for (RecursiveTask<Map<String, List<Item>>> task : forks) {
				for (Entry<String, List<Item>> entry : task.join().entrySet()) {
					if (items.containsKey(entry.getKey())) {
						items.get(entry.getKey()).addAll(entry.getValue());
					} else {
						items.put(entry.getKey(), entry.getValue());
					}
				}
			}
			return items;
		}
	}

	/*
	 * ...........................................................................
	 * ..............
	 */

	private final ForkJoinPool forkJoinPool = new ForkJoinPool();

	Map<String, List<Item>> countOccurrencesInParallel(Document2 document) {
		return forkJoinPool.invoke(new DocumentSearchTask(document));
	}

	private final ForkJoinPool forkJoinPool2 = new ForkJoinPool();

	Map<String, List<Item>> countOccurrencesInParallel2(Path file) {
		return forkJoinPool.invoke(new DocumentSearchTask2(file));
	}

	/*
	 * ...........................................................................
	 * ..............
	 */

	public static void main(String[] args) throws IOException {
		BuildItemMap buildItemMap = new BuildItemMap();
		Path file = Paths.get(args[0]);

		Map<String, List<Item>> items;
		final int repeatCount = Integer.decode(args[1]);
		long counts = 0;
		long startTime;
		long stopTime;

		long[] singleThreadTimes = new long[repeatCount];
		long[] forkedThreadTimes = new long[repeatCount];
		long[] forkedChannelThreadTimes = new long[repeatCount];

		/*
		 * for (int i = 0; i < repeatCount; i++) { startTime =
		 * System.currentTimeMillis(); items =
		 * buildItemMap.countOccurrencesOnSingleThread(file); stopTime =
		 * System.currentTimeMillis(); singleThreadTimes[i] = (stopTime -
		 * startTime); for (List<Item> itemList : items.values()) { counts +=
		 * itemList.size(); } System.out.println(counts +
		 * " , single thread search took " + singleThreadTimes[i] + "ms");
		 * counts = 0;
		 * 
		 * }
		 */

		for (int i = 0; i < repeatCount; i++) {
			startTime = System.currentTimeMillis();
			items = buildItemMap.countOccurrencesInParallel(Document2
			        .fromFile(file));
			stopTime = System.currentTimeMillis();
			forkedThreadTimes[i] = (stopTime - startTime);
			for (List<Item> itemList : items.values()) {
				counts += itemList.size();
			}
			System.out.println(counts + " , fork / join search took "
			        + forkedThreadTimes[i] + "ms");
			counts = 0;

		}

		/*
		 * for (int i = 0; i < repeatCount; i++) { startTime =
		 * System.currentTimeMillis(); items =
		 * buildItemMap.countOccurrencesInParallel2(file); stopTime =
		 * System.currentTimeMillis(); forkedChannelThreadTimes[i] = (stopTime -
		 * startTime); for (List<Item> itemList : items.values()) { counts +=
		 * itemList.size(); } System.out.println(counts +
		 * " , fork / join search took " + forkedChannelThreadTimes[i] + "ms");
		 * counts = 0;
		 * 
		 * }
		 */

		System.out.println("\nCSV Output:\n");
		System.out.println("Single thread,Fork/Join");
		for (int i = 0; i < repeatCount; i++) {
			System.out.println(singleThreadTimes[i] + ","
			        + forkedThreadTimes[i] + "," + forkedChannelThreadTimes[i]);
		}
		System.out.println();
		System.exit(0);
	}
}