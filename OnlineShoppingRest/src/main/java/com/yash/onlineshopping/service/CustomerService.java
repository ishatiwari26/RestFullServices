package com.yash.onlineshopping.service;

import java.util.List;

import com.yash.onlineshopping.model.CustomerModel;

public interface CustomerService {

	CustomerModel findById(int id);

	void createCustomer(CustomerModel customerModel);

	List<CustomerModel> getCustomers();

	CustomerModel updateCustomer(CustomerModel customerModel, Integer customerId);

	void deleteCustomer(Integer customerId);

}
