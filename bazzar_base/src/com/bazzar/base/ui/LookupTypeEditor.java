package com.bazzar.base.ui;

import java.beans.PropertyEditorSupport;

import com.bazzar.base.domain.lookup.LookupType;
import com.bazzar.base.service.impl.LookupTypeServiceImpl;

/**
 * Provides mapping between Reference Types by ID/Value and the Reference Domain Objects.
 */
public class LookupTypeEditor  extends PropertyEditorSupport  {
    
    Class<? extends LookupType>      specificType;
    LookupTypeServiceImpl lookupTypeService;

    public LookupTypeEditor(Class<? extends LookupType> specificType, LookupTypeServiceImpl lookupTypeService) {
        this.specificType = specificType;
        this.lookupTypeService = lookupTypeService;
    }
    
    public Class<? extends LookupType> getSpecificType() {
        return specificType;
    }

    public void setSpecificType(Class<? extends LookupType> specificType) {
        this.specificType = specificType;
    }

    @Override
    public String getAsText() {
        return super.getAsText();
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        LookupType val = lookupTypeService.getLookupTypeByCode(text, specificType);
        
        if(val == null || val.getCode() == null || val.getCode().length() == 0) {
            setValue(null);
        } else {
            setValue(val);
        }
    }
    
}

