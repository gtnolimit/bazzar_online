package com.bazzar.base.domain.order;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.bazzar.base.domain.DBBase;
import com.bazzar.base.domain.Note;
import com.bazzar.base.domain.lookup.ShoppingCartTypeLookup;

@Entity
@Table(name = "CART")
public class Cart extends DBBase implements Serializable{

	private static final long serialVersionUID = -5527566248002296042L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@Column(name="SESSION_NUMBER")
	private String sessionNumber;
	@Column ( name = "CUSTOMER_ID")
	private String customer_id;
	@Column(name="CART_CREATED")
	private Date shoppingCartCreated;
	@Column(name="CART_SEND")
	private Date shoppingCartSendToOrder;
	@Column(name="CART_CANCELED")
	private Date shoppingCartCanceled;
	@Column(name="CART_SAVED")
	private Date shoppingCartSaved;
	@Column(name="CART_SUB_TOTAL")
	private double shoppingCartSubTotal;
	@ManyToOne
	@JoinColumn(name="CART_STATUS")
	private ShoppingCartTypeLookup shoppingCartStatus;
 
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinTable(
	     name="CART_DETAILS",
	     joinColumns = @JoinColumn( name="CART_ID"),
	     inverseJoinColumns = @JoinColumn( name="DETAIL_ID")
	)private Set <CartDetail> detail = new HashSet <CartDetail> ();
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinTable(
	     name="CART_NOTE",
	     joinColumns = @JoinColumn( name="CART_ID"),
	     inverseJoinColumns = @JoinColumn( name="NOTE_ID")
	)
	private Set <Note> note = new HashSet <Note> ();
	
	public String getSessionNumber() {
		return sessionNumber;
	}
	public void setSessionNumber(String sessionNumber) {
		this.sessionNumber = sessionNumber;
	}
	public String getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}
	public Set<CartDetail> getDetail() {
		return detail;
	}
	public void setDetail(Set<CartDetail> detail) {
		this.detail = detail;
	}
	
	public double getShoppingCartSubTotal() {
		return shoppingCartSubTotal;
	}
	public void setShoppingCartSubTotal(double shoppingCartSubTotal) {
		this.shoppingCartSubTotal = shoppingCartSubTotal;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getShoppingCartCreated() {
		return shoppingCartCreated;
	}
	public void setShoppingCartCreated(Date shoppingCartCreated) {
		this.shoppingCartCreated = shoppingCartCreated;
	}
	public Date getShoppingCartSendToOrder() {
		return shoppingCartSendToOrder;
	}
	public void setShoppingCartSendToOrder(Date shoppingCartSendToOrder) {
		this.shoppingCartSendToOrder = shoppingCartSendToOrder;
	}
	public Date getShoppingCartCanceled() {
		return shoppingCartCanceled;
	}
	public void setShoppingCartCanceled(Date shoppingCartCanceled) {
		this.shoppingCartCanceled = shoppingCartCanceled;
	}
	public Date getShoppingCartSaved() {
		return shoppingCartSaved;
	}
	public void setShoppingCartSaved(Date shoppingCartSaved) {
		this.shoppingCartSaved = shoppingCartSaved;
	}
	public ShoppingCartTypeLookup getShoppingCartStatus() {
		return shoppingCartStatus;
	}
	public void setShoppingCartStatus(ShoppingCartTypeLookup shoppingCartStatus) {
		this.shoppingCartStatus = shoppingCartStatus;
	}
	public Set<Note> getNote() {
		return note;
	}
	public void setNote(Set<Note> note) {
		this.note = note;
	}	
}
