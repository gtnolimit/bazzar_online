package com.bazzar.test;

import static org.junit.Assert.assertNotNull;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.bazzar.dao.ItemDao;
import com.bazzar.dao.MenuDao;
//import com.bazzar.domain.item.Accessories;
import com.bazzar.domain.item.Demensions;
import com.bazzar.domain.item.Detail;
import com.bazzar.domain.item.Features;
import com.bazzar.domain.item.Item;
import com.bazzar.domain.item.Specification;
import com.bazzar.domain.item.Warranty;
import com.bazzar.domain.item.Weight;
import com.bazzar.domain.menu.Product;

public class ItemTest {

	@Test
	public void createItem( ItemDao itemDao, MenuDao menuDao ) {

		Item item = getItem();
		itemDao.add( item );
		
		Long itemId = item.getId();
		System.out.println("itemId="+itemId);
		
		assertNotNull ( itemDao.getItem ( itemId ) );
		
		Product product = menuDao.findProductByName("Refrigerators & Freezers");
		System.out.println( " product ID :" + product.getId());
		Set <Item> itemSet = new HashSet <Item> ();
		itemSet.add(item);
		product.setItem(itemSet);
		menuDao.edit(product);
	}
	
	private Item getItem (){
		Item item = new Item ();
		
		item.setBarCode("22554488665658");
		item.setInStock(true);
		item.setListedPrice(3580.00);
		item.setManufactureModelNumber("B22CS30SNS");
		item.setQuantity(25);
		item.setRebate(false);
		item.setSalePrice(3400.00);
		item.setSpecialOfferPrice(3400.00);
		item.setSpesialPriceActive(false);
		item.setSubgect("Bosch Linea 300 Series Stainless Steel Side-By-Side Refrigerator - B22CS30SNS");
		item.setActive(true);
		item.setCPD ( new Date () );
		item.setUPD ( new Date () );
		
		Set < Demensions > dem = new HashSet <Demensions> ();
		dem.add(setDemensions("Approximate Dimensions", (long) 1, "Height", "69\""));
		dem.add(setDemensions("Approximate Dimensions", (long) 1, "Width", "36\""));
		dem.add(setDemensions("Approximate Dimensions", (long) 1, "Depth", "30\""));
		dem.add(setDemensions("Approximate Dimensions", (long) 1, "Product packaging dimensions (HxWxD) (in)", "74.80 x 37.79 x 30.70 \""));
		item.setDemensions(dem);
		
		Set <Weight> wieght = new HashSet <Weight> ();
		wieght.add(setWieght("Approximate Weight", (long) 1, "Unit", "253 lbs"));
		wieght.add(setWieght("Approximate Weight", (long) 1, "Shipping", "280 lbs"));
		item.setWieght(wieght);
		
		Set <Warranty> warranty = new HashSet <Warranty> ();
		warranty.add(setWarranty("Warranty Information", (long) 1, "Manufacturer Warranty (authorized online retailer)", "Full One Year Warranty"));
		item.setWarranty(warranty);
		
		Set <Specification> specification = new HashSet <Specification> ();
		specification.add(setSpecification("Specifications", "Panel ready", ""));
		specification.add(setSpecification("Specifications", "Watts (W)", "300 W"));
		specification.add(setSpecification("Specifications", "Current (A)", "10 A"));
		specification.add(setSpecification("Specifications", "Volts (V)", "115 V"));
		specification.add(setSpecification("Specifications", "Frequency (Hz)", "60 Hz"));
		specification.add(setSpecification("Specifications", "Approval certificates", "NOM, UL "));
		specification.add(setSpecification("Specifications", "Total unit gross capacity (cu. ft.) - AHAM", "22 Cu Ft "));
		specification.add(setSpecification("Specifications", "Capacity in 0.75l (bordeaux bottles)", "0 No."));
		specification.add(setSpecification("Specifications", "Energy consumption (kWh/yr)", "546.04 kWh"));
		specification.add(setSpecification("Specifications", "Silence level (dBA)", "44 dB"));
		specification.add(setSpecification("Specifications", "SUPERCOOL", "Yes"));
		specification.add(setSpecification("Specifications", "SUPERFREEZE", "Yes"));
		item.setSpecification(specification);
		
		Set <Features> features = new HashSet <Features> ();
		features.add(setFeatures("Features", "Convenience", "Counter Depth"));
		features.add(setFeatures("Features", "Convenience", "Frameless Exterior Ice and Water Dispenser with LED illumination serves filtered water and both crushed and cubed Ice"));
		features.add(setFeatures("Features", "Convenience", "Spill proof glass shelves"));
		features.add(setFeatures("Features", "Convenience", "Fully extendable freezer drawers at an 90 degree door opening angle"));
		features.add(setFeatures("Features", "Performance", "Dual Evaporator keeps the air-flows and aromas separate between the fridge and the freezer"));
		features.add(setFeatures("Features", "Performance", "Multi-Flow Shower Cooling System™ - For optimum, uniform cold air circulation"));
		features.add(setFeatures("Features", "Performance", "Humidity controlled crisper for any type of food: Fish, meat, fruit and dairy"));
		features.add(setFeatures("Features", "Performance", "AirFresh carbon filter reduces unwanted odors"));
		features.add(setFeatures("Features", "Performance", "AntiBacterial wall linings provide a hygienic atmosphere"));
		features.add(setFeatures("Features", "Safety", "Door Open Alarm - This alarm gently notifies you when the door is left ajar for over a minute"));
		features.add(setFeatures("Features", "Safety", "Dispenser Control Lock - Prevents small children from operating or making adjustments without adult supervision"));
		features.add(setFeatures("Features", "Efficiency", "ENERGY STAR® Qualified"));
		item.setFeatures(features);
		
		Set <Detail> detail = new HashSet <Detail> ();
		detail.add(setDetail("Product Details", "Name", "Bosch B22CS30SNS Linea 300 Series Stainless Steel Side-By-Side Refrigerator - B22CS30SNSS"));
		item.setDetail(detail);
			
		return item;
	}
	
	private Detail  setDetail ( String category, String attribute, String value){
		Detail detail = new Detail ();
		detail.setAttribute(attribute);
		detail.setCategory(category);
		detail.setValue(value);
		detail.setActive ( true );
		detail.setCPD ( new Date () );
		detail.setUPD ( new Date () );
		return detail;
	}
	private Features  setFeatures ( String category, String attribute, String value){
		Features features = new Features ();
		features.setAttribute(attribute);
		features.setCategory(category);
		features.setValue(value);
		features.setActive ( true );
		features.setCPD ( new Date () );
		features.setUPD ( new Date () );
		return features;
	}
	private Specification  setSpecification ( String category, String attribute, String value){
		Specification specification = new Specification ();
		specification.setAttribute(attribute);
		specification.setCategory(category);
		specification.setValue(value);
		specification.setActive ( true );
		specification.setCPD ( new Date () );
		specification.setUPD ( new Date () );
		return specification;
	}
	private Warranty  setWarranty (
			String category, Long categoryGroup, String attribute, String value){
		Warranty warranty = new Warranty ();
		warranty.setAttribute(attribute);
		warranty.setCategory(category);
		warranty.setCategoryGroup(categoryGroup);
		warranty.setValue(value);
		warranty.setActive ( true );
		warranty.setCPD ( new Date () );
		warranty.setUPD ( new Date () );
		return warranty;
	}
	private Weight setWieght (
			String category, Long categoryGroup, String attribute, String value){
		Weight wieght = new Weight ();
		wieght.setAttribute(attribute);
		wieght.setCategory(category);
		wieght.setCategoryGroup(categoryGroup);
		wieght.setValue(value);
		wieght.setActive ( true );
		wieght.setCPD ( new Date () );
		wieght.setUPD ( new Date () );
		return wieght;
	}
	private Demensions setDemensions (
			String category, Long categoryGroup, String attribute, String value){
		Demensions dem = new Demensions ();
		dem.setAttribute(attribute);
		dem.setCategory(category);
		dem.setCategoryGroup(categoryGroup);
		dem.setValue(value);
		dem.setActive ( true );
		dem.setCPD ( new Date () );
		dem.setUPD ( new Date () );
		return dem;
	}
}
