package com.yash.onlineshopping.service;

import java.util.List;

import com.yash.onlineshopping.model.OrdersModel;

public interface OrderService {

	OrdersModel findById(Integer orderId);

	List<OrdersModel> bookOrder(List<OrdersModel> orders, int customerId);

}
