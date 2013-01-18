package com.bazzar.ui.model;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.persistence.Transient;

import org.springframework.context.ApplicationContext;

public class AjaxErrorsBean {

    @Transient
    private ApplicationContext context;
    
    private Map<String, List<String>> errors = new HashMap<String, List<String>>();
    
    public AjaxErrorsBean(ApplicationContext context) {
        this.context = context;
    }
    
    public void rejectValue(String field, String error) {
        List<String> msg = errors.get(field);
        if (msg==null) {
            msg = new LinkedList<String>();
            errors.put(field, msg);
        }
        msg.add(context.getMessage(error, null,  Locale.getDefault()));
    }

    
    /**
     * @return the errors
     */
    public Map<String, List<String>> getErrors() {
        return errors;
    }

    /**
     * @param errors the errors to set
     */
    public void setErrors(Map<String, List<String>> errors) {
        this.errors = errors;
    }

    
}
