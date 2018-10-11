package com.yash.onlineshopping.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "customer_master")
public class CustomerModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@GenericGenerator(name = "g1", strategy = "increment")
	@Id
	@GeneratedValue(generator = "g1")
	private Integer customerId;

	private String customerName, customerContactNo;

	@Column(unique = true)
	private String customerEmail;
	@Column(name = "isActive", nullable = false, columnDefinition = "VARCHAR(5) default 'N'")
	private String isActive;

	private Date createdDate = new Date();

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerContactNo() {
		return customerContactNo;
	}

	public void setCustomerContactNo(String customerContactNo) {
		this.customerContactNo = customerContactNo;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public CustomerModel() {

	}

	public CustomerModel(Integer customerId, String customerName, String customerContactNo, String customerEmail,
			String isActive, Date createdDate) {
		super();
		this.customerId = customerId;
		this.customerName = customerName;
		this.customerContactNo = customerContactNo;
		this.customerEmail = customerEmail;
		this.isActive = isActive;
		this.createdDate = createdDate;
	}

	@Override
	public String toString() {
		return "CustomerModel [customerId=" + customerId + ", customerName=" + customerName + ", customerContactNo="
				+ customerContactNo + ", customerEmail=" + customerEmail + ", isActive=" + isActive + ", createdDate="
				+ createdDate + "]";
	}
}
