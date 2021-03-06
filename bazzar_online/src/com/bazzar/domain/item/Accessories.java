package com.bazzar.domain.item;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.bazzar.domain.DBBase;

@Entity
@Table(name = "ACCESSORIES")
@Where(clause="status=1")
public class Accessories extends DBBase {

	private static final long serialVersionUID = -5527566248002296042L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@Column(name = "ITEM_ID")
	private Item Item;
	@Column(name="Status")
	private boolean isActive;
	
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
	
	public Item getItem() {
		return Item;
	}

	public void setItem(Item item) {
		Item = item;
	}


	
}
