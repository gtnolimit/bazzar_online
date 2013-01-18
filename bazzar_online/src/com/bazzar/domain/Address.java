package com.bazzar.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.bazzar.domain.lookup.AddressTypeLookup;
import com.bazzar.domain.lookup.StateTypeLookup;

@Entity
@Table(name="Address")
@Where(clause="status<>0")
public class Address extends DBBase {

	private static final long serialVersionUID = 1L;
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="ID")
	private long id;
	@ManyToOne
	@JoinColumn (name="type")
	private AddressTypeLookup addressType;
	@Column(name="Address_1")
	private String addressLine1;
	@Column(name="Address_2")
	private String addressLine2;
	@Column(name="Address_3")
	private String addressLine3;
	@Column(name="City")
	private String city;
	@ManyToOne
    @JoinColumn(name = "State")
	private StateTypeLookup state;
	@Column(name="Zip")
	private String zip;
	@Column(name="Zip4")
	private String zip4;
	@Column(name="Status")
	private Boolean isActive;
	
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public AddressTypeLookup getAddressType() {
		return addressType;
	}
	public void setAddressType(AddressTypeLookup addressType) {
		this.addressType = addressType;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getAddressLine1() {
		return addressLine1;
	}
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}	
	public String getAddressLine2() {
		return addressLine2;
	}
	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}	
	public String getAddressLine3() {
		return addressLine3;
	}
	public void setAddressLine3(String addressLine3) {
		this.addressLine3 = addressLine3;
	}	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public StateTypeLookup getState() {
		return state;
	}
	public void setState(StateTypeLookup stateTypeLookup) {
		this.state = stateTypeLookup;
	}	
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}	
	public String getZip4() {
		return zip4;
	}
	public void setZip4(String zip4) {
		this.zip4 = zip4;
	}

}
