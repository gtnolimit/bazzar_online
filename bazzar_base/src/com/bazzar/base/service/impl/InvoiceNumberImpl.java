package com.bazzar.base.service.impl;

import java.util.concurrent.atomic.AtomicInteger;

import com.bazzar.base.service.InvoiceNumber;

public class InvoiceNumberImpl implements InvoiceNumber {
	
	private AtomicInteger atomicInteger;  
    private static InvoiceNumberImpl obj = null; 
    
    private InvoiceNumberImpl(int initialValue){  
        this.atomicInteger = new AtomicInteger(initialValue);   
    }  
      
    public static InvoiceNumberImpl getInstance(){  
        if(obj == null){  
            obj = new InvoiceNumberImpl(100);  
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
