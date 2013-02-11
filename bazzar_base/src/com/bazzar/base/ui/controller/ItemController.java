package com.bazzar.base.ui.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import com.bazzar.base.domain.item.Item;
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
	
	@RequestMapping(value = { "/item/add/" }, method = { RequestMethod.POST })
	public ModelAndView createCategory(@RequestBody Item item_p,
			HttpServletResponse httpResponse_p, WebRequest request_p) {

		Long createItemId;
		try {
			createItemId = (long) itemService_i.addItem ( item_p );
			item_p.setId( createItemId );
		} catch (Exception e) {
			String sMessage = "Error creating new category. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}
		httpResponse_p.setStatus(HttpStatus.CREATED.value());
		httpResponse_p.setHeader("caregory", request_p.getContextPath() + "/item/" + createItemId);
		return new ModelAndView(jsonView_i, ITEM_FIELD, item_p );
	}

	@RequestMapping(value = { "/item/update/{itemId}" }, method = { RequestMethod.PUT })
	public ModelAndView updateCategory(@RequestBody Item item_p, @PathVariable("itemId") String itemId_p,
								   HttpServletResponse httpResponse_p) {
		try {
			itemService_i.editItem(item_p);
		} catch (Exception e) {
			String sMessage = "Error updating category. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		httpResponse_p.setStatus(HttpStatus.OK.value());
		return new ModelAndView(jsonView_i, ITEM_FIELD, null);
	}

	@RequestMapping(value = "/item/delete/{itemId}", method = RequestMethod.DELETE)
	public ModelAndView removeCategory(@PathVariable("itemId") String itemId_p,
								   HttpServletResponse httpResponse_p) {

		try {
			Long id = Long.parseLong(itemId_p);
			itemService_i.delete( id);
		} catch (Exception e) {
			String sMessage = "Error invoking getFunds. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		httpResponse_p.setStatus(HttpStatus.OK.value());
		return new ModelAndView(jsonView_i, ITEM_FIELD, null);
	}	


	@RequestMapping(value = { "/item/find/id/{itemId}" }, method = { RequestMethod.GET })
	public ModelAndView findItemById(@PathVariable("itemId") String itemId,
				   HttpServletResponse httpResponse_p) {
		Item item;
		try {
			Long id = Long.parseLong(itemId);
			item = itemService_i.getItem( id );
		} catch (Exception e) {
			String sMessage = "Error finding product. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		httpResponse_p.setStatus(HttpStatus.OK.value());
		return new ModelAndView(jsonView_i, ITEM_FIELD, item);
	}

	@RequestMapping(value = { "/item/find/name/{itemName}" }, method = { RequestMethod.GET })
	public ModelAndView findItemByName(@PathVariable("itemName") String itemName,
				   HttpServletResponse httpResponse_p) {
		List <Item> item;
		try {
			item = itemService_i.findItemsByName( itemName );
		} catch (Exception e) {
			String sMessage = "Error finding product. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		httpResponse_p.setStatus(HttpStatus.OK.value());
		return new ModelAndView(jsonView_i, ITEM_FIELD, item);
	}

	@RequestMapping(value = { "/item/find/manufactureNumber/{manufactureNumber}" }, method = { RequestMethod.GET })
	public ModelAndView findItemByManufactureName(@PathVariable("manufactureNumber") String manufactureNumber,
				   HttpServletResponse httpResponse_p) {
		List <Item> item;
		try {
			item = itemService_i.findItemsByManufactureNumber( manufactureNumber );
		} catch (Exception e) {
			String sMessage = "Error finding product. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		httpResponse_p.setStatus(HttpStatus.OK.value());
		return new ModelAndView(jsonView_i, ITEM_FIELD, item);
	}

	@RequestMapping(value = { "/item/find/manufacture/{manufacture}" }, method = { RequestMethod.GET })
	public ModelAndView findItemsByManufacture(@PathVariable("manufacture") String manufacture,
				   HttpServletResponse httpResponse_p) {
		List <Item> item;
		try {
			item = itemService_i.findItemsByManufacture( manufacture );
		} catch (Exception e) {
			String sMessage = "Error finding product. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		httpResponse_p.setStatus(HttpStatus.OK.value());
		return new ModelAndView(jsonView_i, ITEM_FIELD, item);
	}

	@RequestMapping(value = { "/item/find/description/{description}" }, method = { RequestMethod.GET })
	public ModelAndView findItemsByDescription(@PathVariable("description") String description,
				   HttpServletResponse httpResponse_p) {
		List <Item> item;
		try {
			item = itemService_i.findItemsByDescription( description );
		} catch (Exception e) {
			String sMessage = "Error finding product. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		httpResponse_p.setStatus(HttpStatus.OK.value());
		return new ModelAndView(jsonView_i, ITEM_FIELD, item);
	}

	
	public void setJsonView(View view) {
		jsonView_i = view;
	}
	
	private ModelAndView createErrorResponse(String sMessage) {
		return new ModelAndView(jsonView_i, ERROR_FIELD, sMessage);
	}

}
