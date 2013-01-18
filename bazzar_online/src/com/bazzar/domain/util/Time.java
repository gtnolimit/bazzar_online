package com.bazzar.domain.util;

import java.util.Date;

public class Time extends Date {

	private static final long serialVersionUID = 1165302700266344158L;
	
	public Time() {
		super();
	}
	
	public Time(Date date) {
		super(date.getTime());
	}

}
