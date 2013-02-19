package com.bazzar.base.test;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.bazzar.base.domain.Picture;
import com.bazzar.base.domain.Review;
import com.bazzar.base.domain.item.Accessories;
import com.bazzar.base.domain.item.Color;
import com.bazzar.base.domain.item.Demensions;
import com.bazzar.base.domain.item.Detail;
import com.bazzar.base.domain.item.Features;
import com.bazzar.base.domain.item.Item;
import com.bazzar.base.domain.item.Manufacture;
import com.bazzar.base.domain.item.Rating;
import com.bazzar.base.domain.item.ShippingDemensions;
import com.bazzar.base.domain.item.Specification;
import com.bazzar.base.domain.item.Warranty;
import com.bazzar.base.domain.item.Weight;
import com.bazzar.base.domain.qa.Answer;
import com.bazzar.base.domain.qa.Question;

public class CreateItemTest {
	
	public CreateItemTest (){}
	public Item setMicrovave (){
		Item item = this.setItem("GE Spacemaker Over-The-Range White Microwave Oven", 
				"1.5 Cu. Ft. Capacity/ 950 Watts/ Convenience Controls/ Auto And Time Defrost/ Two-Speed 300-CFM Venting System/ Removable Oven Rack/ White Finish", 
				170.00, 
				new Date(), 
				new Date(), 
				false, 
				188.00, 
				209.00, 
				-1, 
				false, 
				true, 
				"JVM1540DNWW", 
				"JVM1540DNWW", 
				null);
		// set Color
		String [] col = {"Black","White","Dark Grey","Almond","Steal"};
		item.setColor(this.setColor(col));
		// set Review
		String [] rev = {"EASY TO USE...JUST USE FOR VEGGIES, DEFROSTING, POPCORN, SOME TO WARM UP DISHES.",
				"VERY HELPFUL OVER THE PHONE...MAN WHO INSTALLED WAS GREAT...VWRY GOOD SERVICE."};
		item.setReview(this.setReviews(rev));
		// set Manufacture
		String [][] man = 	{{"GE", "JVM1540WH", "/images/items/xlarge0-27716.jpg"},
							{"General Electric",  "JVM1540WH", "/images/items/xlarge0-27716.jpg"}};
		item.setManufacture(this.setManufactures(man));
		// set Rating
		int [] rating = {1,1,1,1,1,1};
		item.setRating(setRatings(rating));
		// set Weight
		String [][] wet = {{"Unit","51 lbs"},{"Shipping","57 lbs"}};
		item.setWieght(this.setWeights(wet));
		// set Warranty
		String [][]warranty = {{"Manufacturer Warranty (authorized online retailer)",null},
				{"Parts Warranty","Limited 1-year entire appliance"},
				{"Labor Warranty","Limited 1-year entire appliance"}};
		item.setWarranty(this.setWaranties(warranty));
		// set Demensions
		String [][] dem = {{"Depth","15-1/4\""},{"Height","16-3/4\""},{"Width","29-7/8\""}};
		item.setDemensions(this.setDemensions(dem));
		// set Shipping Demensions
		String [][] shipDem = {{"Depth","14\""},{"Height","9-1/4\""},{"Width","18-13/16\""}};
		item.setShippingDemensions(this.setShippingDemensions(shipDem));
		// set Features
		String [][]features = {{"1.5 cu. ft. capacity","950 watts (IEC-705 test procedure)"},
				{"Convenience Controls","Allow cooking and reheating at the touch of a button"},
				{"Auto and Time Defrost","Automatically defrosts for a specified amount of time"},
				{"Turntable On/Off","Ideal for oversized, oblong-shaped dishes"},
				{"Two-Speed, 300-CFM Venting System","Quickly removes smoke and steam from the cooktop"},
				{"Removable Oven Rack","Makes multi-dish cooking possible"}};
		item.setFeatures(this.setFeatures(features));
		// set Specifications
		String [][]specification = {{"Type","Under Cabinet"},
				{"CFM","300"},
				{"Venting Type","Ducked"},
				{"Fan Speeds","2"},
				{"Halogen Lighting","Yes"},
				{"Type","Over-the-Range"},
				{"Capacity","1.5 Cu. Ft."},
				{"Cooking Power","950 W"},
				{"Height","16.75\""},
				{"Width","30\""},
				{"Number of Power Levels","10"},
				{"Convection","No"},
				{"Sensor Cooking","No"},
				{"Interior Light","Yes"},
				{"Quick Minute","Yes"},
				{"Timer","Yes"}};
		item.setSpecification(this.setSpecification(specification));
		// set Details
		String [][]detail = {{"GE Spacemaker JVM1540DNWW Over-The-Range White Microwave Oven, Model JVM1540WH",null}};
		item.setDetail(this.setDetails(detail));
		// set Pictures
		String [] pics = {"/images/items/xlarge0-27716.jpg","/images/items/LaundryAccessories.jpg"};
		item.setPictureLocation(this.setPictures(pics));
		// set Accessories
		return item;
	}
	private Set <Color> setColor ( String [] colors){
		Set <Color> color = new HashSet <Color> ();
		for ( int i = 0; i < colors.length; i++){
			Color col = new Color ();
			col.setColor(colors[i]);
			col.setCPD(new Date ());
			col.setUPD(new Date ());
			color.add(col);
		}
		return color;
	}
	private Item setItem (String subgect, String description, double specialOfferPrice, 
			Date specialPriceStart, Date specialPriceEnd, boolean spesialPriceActive, 
			double salePrice, double listedPrice, int quantity,  boolean rebate, 
			boolean inStock, String manufactureModelNumber, String barCode, String pageLocator ){
		Item it = new Item ();
		it.setUPD(new Date());
		it.setCPD(new Date());
		it.setActive(true);
		it.setSubgect(subgect);
		it.setDescription(description);
		it.setSpecialOfferPrice(specialOfferPrice);
		it.setSpecialPriceStart(specialPriceStart);
		it.setSpecialPriceEnd(specialPriceEnd);
		it.setSpesialPriceActive(spesialPriceActive);
		it.setSalePrice(salePrice);
		it.setListedPrice(listedPrice);
		it.setQuantity(quantity);
		it.setRebate(rebate);
		it.setInStock(inStock);
		it.setManufactureModelNumber(manufactureModelNumber);
		it.setBarCode(barCode);
		it.setPageLocator(pageLocator);
		return it;
	}
	@SuppressWarnings("unused")
	private Answer setAnswer (String answer, Long customerId){
		Answer an = new Answer();
		an.setActive(true);
		an.setCustomerId(customerId);
		an.setAnswer(answer);
		an.setCPD(new Date());
		an.setUPD(new Date());
		return an;
	}
	@SuppressWarnings("unused")
	private Question setQuestion (String question, Long customerId){
		Question qa = new Question ();
		qa.setActive(true);
		qa.setCustomerId(customerId);
		qa.setQa(question);
		qa.setCPD(new Date());
		qa.setUPD(new Date());
		return qa;
	}
 	@SuppressWarnings({ "unchecked" })
	private Set <Review> setReviews (String [] reviews){
 		@SuppressWarnings("rawtypes")
		Set <Review> rev = new HashSet ();
 		for ( int i=0; i < reviews.length; i++){
 			rev.add(setReview ( reviews[i], new Date()));
 		}
 		return rev;
 	}
	private Review setReview (String review, Date setDate){
		Review rev = new Review();
		rev.setActive(true);
		rev.setCPD(new Date());
		rev.setReview(review);
		rev.setReviewDate(setDate);
		rev.setUPD(new Date ());
		return rev;
	}
	@SuppressWarnings("unchecked")
	private Set <Manufacture> setManufactures ( String [] [] man){
		@SuppressWarnings("rawtypes")
		Set <Manufacture> manuf = new HashSet ();
		for ( int rowIndex = 0; rowIndex < man.length; rowIndex++){
			String [] row = man[rowIndex];
			String name = null;
			String number = null;
			String pic = null;
			for ( int i=0; i< row.length; i++){
				name = row[i];
				number = row[i];
				pic = row[i];
			}
			manuf.add(setManufacture(name,number,pic));	
		}
		return manuf;
	}
	private Manufacture setManufacture (String name, String number, String pic ){
		Manufacture man = new Manufacture ();
		man.setActive(true);
		man.setAuthorizePicture(true);
		man.setCPD(new Date());
		man.setUPD(new Date());
		man.setName(name);
		man.setNumber(number);
		man.setPicture(setPicture(pic));
		return man;
	}
	@SuppressWarnings("unchecked")
	private Set<Rating> setRatings ( int [] rat){
		@SuppressWarnings("rawtypes")
		Set <Rating> rating = new HashSet ();
		for ( int i=0; i < rat.length; i++){
			rating.add(setRating(rat[i]));
		}
		return rating;
	}
	private Rating setRating ( int rating){
		Rating rat = new Rating ();
		rat.setActive(true);
		rat.setRating(rating);
		rat.setCPD(new Date());
		rat.setUPD(new Date());
		return rat;
	}
 	private Set <Weight> setWeights ( String [][] weig){
 		Set <Weight> weights = new HashSet <Weight> ();
 		for ( int index=0; index < weig.length; index ++){
 			String [] row = weig[index];
 			String att = null;
 			String value = null;
 			for ( int i=0; i<row.length; i++){
 				att = row[i];
 				value = row[i];
 			}
			weights.add(this.setWeight(att,value));
 		}
 		return weights;
 	}
	private Weight setWeight ( String att, String value){
		Weight dem = new Weight ();
		dem.setActive(true);
		dem.setAttribute(att);
		dem.setValue(value);
		dem.setCPD(new Date());
		dem.setUPD(new Date());
		return dem;
	}
	private Set <Warranty> setWaranties ( String [][] object){
		Set <Warranty> setObj = new HashSet <Warranty> ();
 		for ( int index=0; index < object.length; index ++){
 			String [] row = object[index];
 			String att = null;
 			String value = null;
 			for ( int i=0; i<row.length; i++){
 				att = row[i];
 				value = row[i];
 			}
 			setObj.add(this.setWarranty(att,value));
 		}
 		return setObj;
 	
	}
	private Warranty setWarranty ( String att, String value){
		Warranty dem = new Warranty ();
		dem.setActive(true);
		dem.setAttribute(att);
		dem.setValue(value);
		dem.setCPD(new Date());
		dem.setUPD(new Date());
		return dem;
	}
	private Set <Specification> setSpecification ( String [][] object){
		Set <Specification> setObj = new HashSet <Specification> ();
 		for ( int index=0; index < object.length; index ++){
 			String [] row = object[index];
 			String att = null;
 			String value = null;
 			for ( int i=0; i<row.length; i++){
 				att = row[i];
 				value = row[i];
 			}
 			setObj.add(this.setSpecification(att,value));
 		}
 		return setObj;
	}
	private Specification setSpecification ( String att, String value){
		Specification dem = new Specification ();
		dem.setActive(true);
		dem.setAttribute(att);
		dem.setValue(value);
		dem.setCPD(new Date());
		dem.setUPD(new Date());
		return dem;
	}
	private Set <Features> setFeatures ( String [][] object){
		Set <Features> setObj = new HashSet <Features> ();
 		for ( int index=0; index < object.length; index ++){
 			String [] row = object[index];
 			String att = null;
 			String value = null;
 			for ( int i=0; i<row.length; i++){
 				att = row[i];
 				value = row[i];
 			}
 			setObj.add(this.setFeatures(att,value));
 		}
 		return setObj;
	}
	private Features setFeatures ( String att,  String value){
		Features dem = new Features ();
		dem.setActive(true);
		dem.setAttribute(att);
		dem.setValue(value);
		dem.setCPD(new Date());
		dem.setUPD(new Date());
		return dem;
	}
	private Set <Detail> setDetails ( String [][] object){
		Set <Detail> setObj = new HashSet <Detail> ();
 		for ( int index=0; index < object.length; index ++){
 			String [] row = object[index];
 			String att = null;
 			String value = null;
 			for ( int i=0; i<row.length; i++){
 				att = row[i];
 				value = row[i];
 			}
 			setObj.add(this.setDetail(att,value));
 		}
 		return setObj;
 	
	}
	private Detail setDetail ( String att,  String value){
		Detail dem = new Detail ();
		dem.setActive(true);
		dem.setAttribute(att);
		dem.setValue(value);
		dem.setCPD(new Date());
		dem.setUPD(new Date());
		return dem;
	}
	private Set <Demensions> setDemensions ( String [][] object){
		Set <Demensions> setObj = new HashSet <Demensions> ();
 		for ( int index=0; index < object.length; index ++){
 			String [] row = object[index];
 			String att = null;
 			String value = null;
 			for ( int i=0; i<row.length; i++){
 				att = row[i];
 				value = row[i];
 			}
 			setObj.add(this.setDemensions(att,value));
 		}
 		return setObj;
 	
	}
	private Demensions setDemensions ( String att, String value){
		Demensions dem = new Demensions ();
		dem.setActive(true);
		dem.setAttribute(att);
		dem.setValue(value);
		dem.setCPD(new Date());
		dem.setUPD(new Date());
		return dem;
	}
	private Set <ShippingDemensions> setShippingDemensions ( String [][] object){
		Set <ShippingDemensions> setObj = new HashSet <ShippingDemensions> ();
 		for ( int index=0; index < object.length; index ++){
 			String [] row = object[index];
 			String att = null;
 			String value = null;
 			for ( int i=0; i<row.length; i++){
 				att = row[i];
 				value = row[i];
 			}
 			setObj.add(this.setShippingDemensions(att,value));
 		}
 		return setObj;
	}
	private ShippingDemensions setShippingDemensions ( String att, String value){
		ShippingDemensions dem = new ShippingDemensions ();
		dem.setActive(true);
		dem.setAttribute(att);
		dem.setValue(value);
		dem.setCPD(new Date());
		dem.setUPD(new Date());
		return dem;
	}
	private Set <Picture> setPictures ( String [] pics){
		Set <Picture> pic = new HashSet <Picture> ();
		for ( int row=0; row < pics.length; row++){
			pic.add(this.setPicture(pics[row]));
		}
		return pic;
	}
	private Picture setPicture ( String location ){
		Picture pic = new Picture ();
		pic.setPictureLocation(location);
		pic.setDescription(location);
		pic.setActive(true);
		pic.setCPD(new Date ());
		pic.setUPD(new Date ());
		return pic;
	}
	private Set <Accessories> setAccessories ( Long [] ids){
		Set <Accessories> acc = new HashSet <Accessories> ();
		for ( int i=0; i< ids.length; i++){
			acc.add(setAccessories(ids[i]));
		}
		return acc;
	}
	private Accessories setAccessories (Long itemId){
		Accessories acce = new Accessories ();
		acce.setActive(true);
		acce.setCPD(new Date());
		acce.setUPD(new Date ());
		acce.setItem(itemId);
		return acce;
	}
}
