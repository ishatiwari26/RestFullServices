package com.yash.onlineshopping.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.yash.onlineshopping.model.CustomerModel;
import com.yash.onlineshopping.service.CustomerService;
import com.yash.onlineshopping.util.RecordNotFoundException;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = { "/customer" })
public class CustomerController {

	private static final Logger log = LogManager.getLogger(CustomerController.class);

	@Autowired
	private CustomerService customerService;

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CustomerModel> getUserById(@PathVariable("id") int id) {
		log.info("Fetching CustomerModel with id " + id);
		CustomerModel customerModel = customerService.findById(id);
		if (customerModel == null) {
			throw new RecordNotFoundException("id-" + id);
		}
		return new ResponseEntity<CustomerModel>(customerModel, HttpStatus.OK);
	}

	@PostMapping(value = "/create", headers = "Accept=application/json")
	public ResponseEntity<Void> createCustomer(@RequestBody CustomerModel customerModel,
			UriComponentsBuilder ucBuilder) {
		log.info("Creating Customer :: Name :: " + customerModel.getCustomerName());

		customerService.createCustomer(customerModel);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/user/{id}").buildAndExpand(customerModel.getCustomerId()).toUri());

		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	@GetMapping(value = "/customers", headers = "Accept=application/json")
	public ResponseEntity<List<CustomerModel>> getAllCustomers() {
		return new ResponseEntity<List<CustomerModel>>(customerService.getCustomers(), HttpStatus.OK);

	}

	@PutMapping(value = "/update", headers = "Accept=application/json")
	public ResponseEntity<String> updateCustomer(@RequestBody CustomerModel currentCustomer) {

		log.info("Updating Customer :: Customer Id :: " + currentCustomer.getCustomerId());
		CustomerModel customerModel = customerService.findById(currentCustomer.getCustomerId());
		if (customerModel == null) {
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
		customerService.updateCustomer(currentCustomer, customerModel.getCustomerId());
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@DeleteMapping(value = "/delete/{id}", headers = "Accept=application/json")
	public ResponseEntity<String> deleteCustomer(@PathVariable("id") int id) {

		CustomerModel customerModel = customerService.findById(id);
		if (customerModel == null) {
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
		customerService.deleteCustomer(customerModel.getCustomerId());
		return new ResponseEntity<String>(HttpStatus.OK);
	}

}
