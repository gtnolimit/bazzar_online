package com.bazzar.base.ui.controller;

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

import com.bazzar.base.domain.menu.Category;
import com.bazzar.base.service.impl.MenuServiceImpl;

@Controller
public class MenuController {
	
	@Autowired
	private MenuServiceImpl menuService_i;
	
	@Autowired
	private View jsonView_i;
	
	private static final String CATEGORY_FIELD = "category";
	private static final String SUBCATEGORY_FIELD = "subcategory";
	private static final String PRODUCT_FIELD = "product";
	private static final String ERROR_FIELD = "error";
	
	@RequestMapping ( value = "/category/{categoryId}", method = RequestMethod.GET )
	public ModelAndView getCategory ( @PathVariable ( "categoryId" ) String categoryId ) {
		Long _id = (long) 0;
		try {
			_id = Long.parseLong ( categoryId );
		} catch (Exception e) {
			String sMessage = "Error converting ID into numeric value";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}
		return new ModelAndView ( jsonView_i, CATEGORY_FIELD, menuService_i.getCategory ( _id ) );
	}

	@RequestMapping ( value = "/subCategory/{subCategoryId}", method = RequestMethod.GET )
	public ModelAndView getSubCategory ( @PathVariable ( "subCategoryId" ) String subCategoryId ) {
		Long _id = (long) 0;
		try {
			_id = Long.parseLong ( subCategoryId );
		} catch (Exception e) {
			String sMessage = "Error converting ID into numeric value";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}
		return new ModelAndView ( jsonView_i, SUBCATEGORY_FIELD, menuService_i.getSubCategory ( _id ) );
	}

	@RequestMapping ( value = "/product/{productId}", method = RequestMethod.GET )
	public ModelAndView getProduct ( @PathVariable ( "productId" ) String productId ) {
		Long _id = (long) 0;
		try {
			_id = Long.parseLong ( productId );
		} catch (Exception e) {
			String sMessage = "Error converting ID into numeric value";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}
		return new ModelAndView ( jsonView_i, PRODUCT_FIELD, menuService_i.getProduct ( _id ) );
	}

	@RequestMapping(value = "/categories/", method = RequestMethod.GET)
	public ModelAndView getCategories() {
		return new ModelAndView(jsonView_i, CATEGORY_FIELD, menuService_i.getAllCategories());
	}

	@RequestMapping(value = "/subCategories/", method = RequestMethod.GET)
	public ModelAndView getSubCategories() {
		return new ModelAndView(jsonView_i, SUBCATEGORY_FIELD, menuService_i.getAllSubCategories());
	}

	@RequestMapping(value = "/products/", method = RequestMethod.GET)
	public ModelAndView getProducts() {
			return new ModelAndView(jsonView_i, PRODUCT_FIELD, menuService_i.getAllProducts());
	}
	
	@RequestMapping(value = { "/menu/category/" }, method = { RequestMethod.POST })
	public ModelAndView createCategory(@RequestBody Category category_p,
			HttpServletResponse httpResponse_p, WebRequest request_p) {

		Long createCategoryId;
		try {
			createCategoryId = menuService_i.create ( category_p );
			category_p.setId( createCategoryId );
		} catch (Exception e) {
			String sMessage = "Error creating new category. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}
		/* set HTTP response code */
		httpResponse_p.setStatus(HttpStatus.CREATED.value());
		/* set location of created resource */
		httpResponse_p.setHeader("caregory", request_p.getContextPath() + "/menu/category/" + createCategoryId);
		return new ModelAndView(jsonView_i, CATEGORY_FIELD, category_p );
	}
	
	@RequestMapping(value = { "/menu/category/{categoryId}" }, method = { RequestMethod.PUT })
	public ModelAndView updateFund(@RequestBody Category category_p, @PathVariable("categoryId") String categoryId_p,
								   HttpServletResponse httpResponse_p) {

		/* validate fund Id parameter */
		if (isEmpty(categoryId_p) || categoryId_p.length() < 50) {
			String sMessage = "Error updating fund - Invalid fund Id parameter";
			return createErrorResponse(sMessage);
		}
		try {
			menuService_i.edit(category_p);
		} catch (Exception e) {
			String sMessage = "Error updating category. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		httpResponse_p.setStatus(HttpStatus.OK.value());
		return new ModelAndView(jsonView_i, CATEGORY_FIELD, null);
	}

	@RequestMapping(value = "/menu/category/{categoryId}", method = RequestMethod.DELETE)
	public ModelAndView removeFund(@PathVariable("categoryId") String categoryId_p,
								   HttpServletResponse httpResponse_p) {

		try {
			Long id = Long.parseLong(categoryId_p);
			menuService_i.deleteCategoryById( id);
		} catch (Exception e) {
			String sMessage = "Error invoking getFunds. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		httpResponse_p.setStatus(HttpStatus.OK.value());
		return new ModelAndView(jsonView_i, CATEGORY_FIELD, null);
	}
	
	public void setJsonView(View view) {
		jsonView_i = view;
	}
	
	private ModelAndView createErrorResponse(String sMessage) {
		return new ModelAndView(jsonView_i, ERROR_FIELD, sMessage);
	}

	public static boolean isEmpty(String s_p) {
		return (null == s_p) || s_p.trim().length() == 0;
	}


}
