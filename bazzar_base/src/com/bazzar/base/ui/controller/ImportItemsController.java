package com.bazzar.base.ui.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import com.bazzar.base.service.impl.ItemServiceImpl;

@Controller
public class ImportItemsController {

	@Autowired
	private ItemServiceImpl itemService_i;

	@Autowired
	private View jsonView_i;

	@Autowired
	private RedisTemplate<String, Object> template;

	private static final String JOB_FIELD = "job";
	private static final String ERROR_FIELD = "error";

	@RequestMapping(value = { "/item/import/job" }, method = { RequestMethod.POST })
	public ModelAndView importItems(@RequestBody String json,
	        HttpServletResponse httpResponse_p, WebRequest request_p) {
		Long jobId = null;
		try {
			jobId = itemService_i.importItems(itemService_i
			        .validateImportItemRequest(json));
		} catch (Exception e) {
			String sMessage = "Error importing items. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		httpResponse_p.setStatus(HttpStatus.OK.value());
		return new ModelAndView(jsonView_i, JOB_FIELD, template.opsForHash()
		        .entries(jobId.toString()));
	}

	@RequestMapping(value = { "/item/import/jobStatus/{jobId}" }, method = { RequestMethod.GET })
	public ModelAndView importItemsStatus(@PathVariable("jobId") String jobId,
	        HttpServletResponse httpResponse_p) {
		return new ModelAndView(jsonView_i, JOB_FIELD, template.opsForHash()
		        .entries(jobId));
	}

	public void setJsonView(View view) {
		jsonView_i = view;
	}

	private ModelAndView createErrorResponse(String sMessage) {
		return new ModelAndView(jsonView_i, ERROR_FIELD, sMessage);
	}

}
