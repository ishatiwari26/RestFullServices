package com.yash.onlineshopping.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yash.onlineshopping.dao.ProductDao;
import com.yash.onlineshopping.model.ProductModel;
import com.yash.onlineshopping.service.ProductService;

@Transactional
@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDao productDao;

	@Override
	public ProductModel findById(int id) {
		return productDao.getProductById(id);
	}

	@Override
	public int createProduct(ProductModel productModel) {
		return productDao.addNewProduct(productModel);
	}

	@Override
	public List<ProductModel> getProducts() {
		return productDao.getAllProducts();
	}

	@Override
	public ProductModel updateCustomer(ProductModel currentProduct, Integer productId) {
		productDao.updateProductDetails(currentProduct, productId);
		return productDao.getProductById(productId);
	}

	@Override
	public void deleteCustomer(Integer productId) {
		productDao.deleteProduct(productId);
	}

}
