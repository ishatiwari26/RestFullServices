package com.yash.onlineshopping.dao;

import com.yash.onlineshopping.model.OrdersModel;

public interface OrderDao {

	OrdersModel getOrderDetailsById(Integer orderId);

	OrdersModel newOrderCreate(OrdersModel newOrdersModel);

}
