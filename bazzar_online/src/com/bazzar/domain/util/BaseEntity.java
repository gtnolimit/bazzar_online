package com.bazzar.domain.util;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

//import com.bazzar.domain.customer.User;

@MappedSuperclass
public class BaseEntity {

	private Date lastUpdate;
	private Date created;
	//private User user;
	
	@Basic
    @Temporal(TemporalType.TIMESTAMP)
	public Date getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate() {
		this.lastUpdate = new Date();
	}
	
	@Basic
    @Temporal(TemporalType.TIMESTAMP)
	public Date getCreated() {
		return created;
	}
	public void setCreated() {
		this.created = new Date();
	}
	//public User getUser() {
	//	return user;
	//}
	//public void setUser(User user) {
	//	this.user = user;
	//}
}
