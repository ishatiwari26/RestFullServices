package com.yash.onlineshopping.service;

import java.util.List;

import com.yash.onlineshopping.model.ProductModel;

public interface ProductService {

	ProductModel findById(int id);

	int createProduct(ProductModel productModel);

	List<ProductModel> getProducts();

	ProductModel updateCustomer(ProductModel currentProduct, Integer productId);

	void deleteCustomer(Integer productId);

}
