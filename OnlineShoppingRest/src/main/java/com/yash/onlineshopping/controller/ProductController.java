package com.yash.onlineshopping.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.yash.onlineshopping.model.ProductModel;
import com.yash.onlineshopping.service.ProductService;
import com.yash.onlineshopping.util.RecordNotFoundException;

@RestController
@RequestMapping(value = { "/product" })
public class ProductController {
	private static final Logger log = LogManager.getLogger(ProductController.class);

	@Autowired
	private ProductService productService;

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProductModel> getProductById(@PathVariable("id") int id) {
		log.info("Fetching CustomerModel with id " + id);
		ProductModel productModel = productService.findById(id);
		if (productModel == null) {
			throw new RecordNotFoundException("id-" + id);
		}
		return new ResponseEntity<ProductModel>(productModel, HttpStatus.OK);
	}

	@PostMapping(value = "/create", headers = "Accept=application/json")
	public ResponseEntity<ProductModel> addNewProduct(@RequestBody ProductModel productModel,
			UriComponentsBuilder ucBuilder) {
		log.info("Creating Customer :: Name :: " + productModel.getProductName());

		int productId = productService.createProduct(productModel);
		productModel = productService.findById(productId);
		return new ResponseEntity<ProductModel>(productModel, HttpStatus.CREATED);
	}

	@GetMapping(value = "/customers", headers = "Accept=application/json")
	public ResponseEntity<List<ProductModel>> getAllCustomers() {
		return new ResponseEntity<List<ProductModel>>(productService.getProducts(), HttpStatus.OK);

	}

	@PutMapping(value = "/update", headers = "Accept=application/json")
	public ResponseEntity<ProductModel> updateProduct(@RequestBody ProductModel currentProduct) {

		log.info("Updating Customer :: Customer Id :: " + currentProduct.getProductId());
		ProductModel productModel = productService.findById(currentProduct.getProductId());
		if (productModel == null) {
			return new ResponseEntity<ProductModel>(HttpStatus.NOT_FOUND);
		}
		currentProduct = productService.updateCustomer(currentProduct, productModel.getProductId());
		return new ResponseEntity<ProductModel>(currentProduct, HttpStatus.OK);
	}

	@DeleteMapping(value = "/delete/{id}", headers = "Accept=application/json")
	public ResponseEntity<String> deleteProduct(@PathVariable("id") int id) {

		ProductModel productModel = productService.findById(id);
		if (productModel == null) {
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
		productService.deleteCustomer(productModel.getProductId());
		return new ResponseEntity<String>(HttpStatus.OK);
	}

}
