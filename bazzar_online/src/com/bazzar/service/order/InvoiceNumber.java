package com.bazzar.service.order;

import java.util.concurrent.atomic.AtomicInteger;

public class InvoiceNumber  {
	
	private AtomicInteger atomicInteger;  
    private static InvoiceNumber obj = null;  
    private InvoiceNumber(int initialValue){  
        this.atomicInteger = new AtomicInteger(initialValue);   
    }  
      
    public static InvoiceNumber getInstance(){  
        if(obj == null){  
            obj = new InvoiceNumber(100);  
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
