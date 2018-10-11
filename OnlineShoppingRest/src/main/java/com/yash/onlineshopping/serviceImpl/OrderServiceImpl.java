package com.yash.onlineshopping.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yash.onlineshopping.dao.CustomerDao;
import com.yash.onlineshopping.dao.OrderDao;
import com.yash.onlineshopping.dao.ProductDao;
import com.yash.onlineshopping.model.OrdersModel;
import com.yash.onlineshopping.model.ProductModel;
import com.yash.onlineshopping.service.OrderService;

@Transactional
@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderDao orderDao;

	@Autowired
	private ProductDao productDao;

	@Autowired
	private CustomerDao customerDao;

	@Override
	public OrdersModel findById(Integer orderId) {
		OrdersModel ordersModel = orderDao.getOrderDetailsById(orderId);

		ordersModel.setCustomerModel(customerDao.getCustomerById(ordersModel.getCustomerId()));
		ordersModel.setProductModel(productDao.getProductById(ordersModel.getProductId()));
		return ordersModel;
	}

	@Override
	public List<OrdersModel> bookOrder(List<OrdersModel> orders, int customerId) {
		List<OrdersModel> list = new ArrayList<OrdersModel>();
		if (customerDao.getCustomerById(customerId) != null) {
			for (OrdersModel ordersModel : orders) {
				OrdersModel newOrdersModel = new OrdersModel();
				newOrdersModel.setCustomerId(customerId);
				newOrdersModel.setProductId(ordersModel.getProductId());
				newOrdersModel.setOrderQuantity(ordersModel.getOrderQuantity());
				if (Double.compare(getActualAmountOfOrderedWithQuantity(ordersModel.getProductId(),
						ordersModel.getOrderQuantity()), ordersModel.getPaidAmount()) == 0) {
					newOrdersModel.setPaidAmount(ordersModel.getPaidAmount());
				} else {
					return null;
				}
				newOrdersModel.setPaymentMethod(ordersModel.getPaymentMethod());
				list.add(orderDao.newOrderCreate(newOrdersModel));
			}
		}
		return list;
	}

	public Double getActualAmountOfOrderedWithQuantity(Integer productId, Integer quantity) {
		ProductModel product = null;
		Double amount = null;
		try {
			product = productDao.getProductById(productId);
			if (product.getAvailableQuantity() < quantity)
				return amount;
			amount = product.getPrice() * quantity;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return amount;
	}

}
