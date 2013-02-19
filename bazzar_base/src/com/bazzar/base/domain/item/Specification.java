package com.bazzar.base.domain.item;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.bazzar.base.domain.DBBase;

@Entity
@Table(name = "SPECIFICATIONS")
@Where(clause="STATUS=1")
public class Specification extends DBBase{

	private static final long serialVersionUID = -5527566248002296042L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@Column(name = "CATEGORY")
	private String category;
	@Column(name = "ATTRIBUTE")
	private String attribute;
	@Column(name = "VALUE")
	private String value;
	@Column(name = "DISPLAY_OPTION")
	private String displayOption;
	@Column(name="STATUS")
	private boolean isActive;
	
 	
	
	public String getAttribute() {
		return attribute;
	}
	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
		public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getDisplayOption() {
		return displayOption;
	}
	public void setDisplayOption(String displayOption) {
		this.displayOption = displayOption;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	
}
