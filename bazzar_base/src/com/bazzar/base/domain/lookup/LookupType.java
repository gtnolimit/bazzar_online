package com.bazzar.base.domain.lookup;


public interface LookupType {

   long getId();
   void setId(long id);
   String  getCode();
   void    setCode(String value);
   String getDescription();
   void setDescription(String description);
}
