package com.bazzar.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

//import com.bazzar.domain.item.Item;
import com.bazzar.domain.item.Manufacture;

@Entity
@Table(name = "PICTURES")
@Where(clause="status<>1")
public class Picture  extends DBBase {

	private static final long serialVersionUID = -5527566248002296042L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id; 
	@Column(name = "PICTURE_LOCATION")
	private String pictureLocation;
	@Column(name = "DESCRIPTION")
	private String description;
	@Column(name = "ALT")
	private String alt;
	@Column(name="Status")
	private boolean isActive;
	
	//@ManyToMany(fetch = FetchType.LAZY, mappedBy = "picture")
	//private Set<Item> item = new HashSet<Item>(0);
	
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "picture")
	private Set<Manufacture> manufacture = new HashSet<Manufacture>(0);
	
	public Set<Manufacture> getManufacture() {
		return manufacture;
	}
	public void setManufacture(Set<Manufacture> manufacture) {
		this.manufacture = manufacture;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPictureLocation() {
		return pictureLocation;
	}
	public void setPictureLocation(String pictureLocation) {
		this.pictureLocation = pictureLocation;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAlt() {
		return alt;
	}
	public void setAlt(String alt) {
		this.alt = alt;
	}
		
}
