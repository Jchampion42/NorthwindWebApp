package com.example.northwindwebapp.entities;

import javax.persistence.*;

@Entity
@Table(name = "customercustomerdemo")
public class Customercustomerdemo {
	@EmbeddedId
	private CustomercustomerdemoId id;

	@MapsId("customerID")
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "CustomerID", nullable = false)
	private Customer customerID;

	@MapsId("customerTypeID")
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "CustomerTypeID", nullable = false)
	private Customerdemographic customerTypeID;

	public CustomercustomerdemoId getId() {
		return id;
	}

	public void setId(CustomercustomerdemoId id) {
		this.id = id;
	}

	public Customer getCustomerID() {
		return customerID;
	}

	public void setCustomerID(Customer customerID) {
		this.customerID = customerID;
	}

	public Customerdemographic getCustomerTypeID() {
		return customerTypeID;
	}

	public void setCustomerTypeID(Customerdemographic customerTypeID) {
		this.customerTypeID = customerTypeID;
	}

}