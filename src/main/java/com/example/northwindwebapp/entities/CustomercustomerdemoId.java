package com.example.northwindwebapp.entities;

import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CustomercustomerdemoId implements Serializable {
	private static final long serialVersionUID = -2032640227301585090L;
	@Column(name = "CustomerID", nullable = false, length = 5)
	private String customerID;

	@Column(name = "CustomerTypeID", nullable = false, length = 10)
	private String customerTypeID;

	public String getCustomerID() {
		return customerID;
	}

	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}

	public String getCustomerTypeID() {
		return customerTypeID;
	}

	public void setCustomerTypeID(String customerTypeID) {
		this.customerTypeID = customerTypeID;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
		CustomercustomerdemoId entity = (CustomercustomerdemoId) o;
		return Objects.equals(this.customerTypeID, entity.customerTypeID) &&
				Objects.equals(this.customerID, entity.customerID);
	}

	@Override
	public int hashCode() {
		return Objects.hash(customerTypeID, customerID);
	}

}