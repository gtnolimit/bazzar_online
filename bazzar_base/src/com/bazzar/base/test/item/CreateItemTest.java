package com.bazzar.base.test.item;

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
import com.bazzar.base.domain.item.Specification;
import com.bazzar.base.domain.item.Warranty;
import com.bazzar.base.domain.item.Weight;
import com.bazzar.base.domain.qa.Answer;
import com.bazzar.base.domain.qa.Question;

public class CreateItemTest {
	
	CreateItemTest (){
		
	}
	@SuppressWarnings("unused")
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
	@SuppressWarnings("unused")
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
 	@SuppressWarnings("unused")
	private Review setReview (String review, Date setDate){
		Review rev = new Review();
		rev.setActive(true);
		rev.setCPD(new Date());
		rev.setReview(review);
		rev.setReviewDate(setDate);
		rev.setUPD(new Date ());
		return rev;
	}
	@SuppressWarnings("unused")
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
	@SuppressWarnings("unused")
	private Rating setRating ( int rating){
		Rating rat = new Rating ();
		rat.setActive(true);
		rat.setRating(rating);
		rat.setCPD(new Date());
		rat.setUPD(new Date());
		return rat;
	}
 	@SuppressWarnings("unused")
	private Weight setWeight ( String att, Long group, String category, String value){
		Weight dem = new Weight ();
		dem.setActive(true);
		dem.setAttribute(att);
		dem.setCategory(category);
		dem.setCategoryGroup(group);
		dem.setValue(value);
		dem.setCPD(new Date());
		dem.setUPD(new Date());
		return dem;
	}
	@SuppressWarnings("unused")
	private Warranty setWarranty ( String att, Long group, String category, String value){
		Warranty dem = new Warranty ();
		dem.setActive(true);
		dem.setAttribute(att);
		dem.setCategory(category);
		dem.setCategoryGroup(group);
		dem.setValue(value);
		dem.setCPD(new Date());
		dem.setUPD(new Date());
		return dem;
	}
	@SuppressWarnings("unused")
	private Specification setSpecification ( String att,  String category, String value){
		Specification dem = new Specification ();
		dem.setActive(true);
		dem.setAttribute(att);
		dem.setCategory(category);
		dem.setValue(value);
		dem.setCPD(new Date());
		dem.setUPD(new Date());
		return dem;
	}
	@SuppressWarnings("unused")
	private Features setFeatures ( String att,  String category, String value){
		Features dem = new Features ();
		dem.setActive(true);
		dem.setAttribute(att);
		dem.setCategory(category);
		dem.setValue(value);
		dem.setCPD(new Date());
		dem.setUPD(new Date());
		return dem;
	}
	@SuppressWarnings("unused")
	private Detail setDetail ( String att,  String category, String value){
		Detail dem = new Detail ();
		dem.setActive(true);
		dem.setAttribute(att);
		dem.setCategory(category);
		dem.setValue(value);
		dem.setCPD(new Date());
		dem.setUPD(new Date());
		return dem;
	}
	@SuppressWarnings("unused")
	private Demensions setDemensions ( String att, Long group, String category, String value){
		Demensions dem = new Demensions ();
		dem.setActive(true);
		dem.setAttribute(att);
		dem.setCategory(category);
		dem.setCategoryGroup(group);
		dem.setValue(value);
		dem.setCPD(new Date());
		dem.setUPD(new Date());
		return dem;
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
	@SuppressWarnings("unused")
	private Accessories setAccessories (Long itemId){
		Accessories acce = new Accessories ();
		acce.setActive(true);
		acce.setCPD(new Date());
		acce.setUPD(new Date ());
		acce.setItem(itemId);
		return acce;
	}
}
