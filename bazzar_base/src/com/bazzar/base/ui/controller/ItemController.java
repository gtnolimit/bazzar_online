package com.bazzar.base.ui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import com.bazzar.base.service.impl.ItemServiceImpl;

@Controller
public class ItemController {

	@Autowired
	private ItemServiceImpl itemService_i;
	
	@Autowired
	private View jsonView_i;
	
	private static final String ITEM_FIELD = "item";
	private static final String ITEMS_FIELD = "items";
	private static final String ERROR_FIELD = "error";
	
	@RequestMapping(value = "/items/", method = RequestMethod.GET)
	public ModelAndView getCategories() {
		return new ModelAndView(jsonView_i, ITEMS_FIELD, itemService_i.getAllItems());
	}
	
	@RequestMapping ( value = "/item/{itemId}", method = RequestMethod.GET )
	public ModelAndView getItem ( @PathVariable ( "itemId" ) String itemId ) {
		Long _id = (long) 0;
		try {
			_id = Long.parseLong ( itemId );
		} catch (Exception e) {
			String sMessage = "Error converting ID into numeric value";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}
		return new ModelAndView ( jsonView_i, ITEM_FIELD, itemService_i.getItem ( _id ) );
	}

	@RequestMapping ( value = "/itemQuestions/{itemId}", method = RequestMethod.GET )
	public ModelAndView getQuestions ( @PathVariable ( "itemId" ) String itemId ) {
		Long _id = (long) 0;
		try {
			_id = Long.parseLong ( itemId );
		} catch (Exception e) {
			String sMessage = "Error converting ID into numeric value";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}
		return new ModelAndView ( jsonView_i, ITEM_FIELD, itemService_i.getItemQuestions ( _id ) );
	}
	
	@RequestMapping ( value = "/itemReviews/{itemId}", method = RequestMethod.GET )
	public ModelAndView getReviews ( @PathVariable ( "itemId" ) String itemId ) {
		Long _id = (long) 0;
		try {
			_id = Long.parseLong ( itemId );
		} catch (Exception e) {
			String sMessage = "Error converting ID into numeric value";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}
		return new ModelAndView ( jsonView_i, ITEM_FIELD, itemService_i.getItemReviews ( _id ) );
	}
	
	@RequestMapping ( value = "/itemAccessories/{itemId}", method = RequestMethod.GET )
	public ModelAndView getAccessories ( @PathVariable ( "itemId" ) String itemId ) {
		Long _id = (long) 0;
		try {
			_id = Long.parseLong ( itemId );
		} catch (Exception e) {
			String sMessage = "Error converting ID into numeric value";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}
		return new ModelAndView ( jsonView_i, ITEM_FIELD, itemService_i.getItemAccessories ( _id ) );
	}
	
	public void setJsonView(View view) {
		jsonView_i = view;
	}
	
	private ModelAndView createErrorResponse(String sMessage) {
		return new ModelAndView(jsonView_i, ERROR_FIELD, sMessage);
	}

}
