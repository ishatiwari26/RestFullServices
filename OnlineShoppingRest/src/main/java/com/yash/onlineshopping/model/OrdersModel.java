package com.yash.onlineshopping.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "orders")
public class OrdersModel implements Serializable {

	private static final long serialVersionUID = 1L;

	@GenericGenerator(name = "g1", strategy = "increment")
	@Id
	@GeneratedValue(generator = "g1")
	private Integer orderId;
	private String paymentMethod;
	private Double paidAmount;
	private Integer orderQuantity;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "customerIdFk", referencedColumnName = "customerId")
	private CustomerModel customerModel;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "productIdFk", referencedColumnName = "productId")
	private ProductModel productModel;

	@Transient
	private Integer productId;

	@Transient
	private Integer customerId;

	private Date createdDate = new Date();

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public Double getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(Double paidAmount) {
		this.paidAmount = paidAmount;
	}

	public CustomerModel getCustomerModel() {
		return customerModel;
	}

	public void setCustomerModel(CustomerModel customerModel) {
		this.customerModel = customerModel;
	}

	public ProductModel getProductModel() {
		return productModel;
	}

	public void setProductModel(ProductModel productModel) {
		this.productModel = productModel;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Integer getOrderQuantity() {
		return orderQuantity;
	}

	public void setOrderQuantity(Integer orderQuantity) {
		this.orderQuantity = orderQuantity;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	@Override
	public String toString() {
		return "OrdersModel [orderId=" + orderId + ", paymentMethod=" + paymentMethod + ", paidAmount=" + paidAmount
				+ ", orderQuantity=" + orderQuantity + ", customerModel=" + customerModel + ", productModel="
				+ productModel + ", productId=" + productId + ", customerId=" + customerId + ", createdDate="
				+ createdDate + "]";
	}
}
