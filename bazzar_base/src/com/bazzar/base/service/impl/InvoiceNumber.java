package com.bazzar.base.service.impl;

import java.util.concurrent.atomic.AtomicInteger;

public class InvoiceNumber {
	
	private AtomicInteger atomicInteger;  
    private static InvoiceNumber obj = null; 
    private static int initialValue = 1000;
    
    private InvoiceNumber(){
    	
        this.atomicInteger = new AtomicInteger(initialValue);   
    }  
      
    public static InvoiceNumber getInstance(){  
        if(obj == null){  
            obj = new InvoiceNumber();  
        }  
        return obj;  
    }  
  
    public int getCounter() {  
        return atomicInteger.getAndIncrement();  
    }  
    
    public String getInvoiceNumber (){
    	return "BAZ_" + getCounter();
    }
}
