package com.yash.onlineshopping.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.yash.onlineshopping.controller.CustomerController;
import com.yash.onlineshopping.model.CustomerModel;
import com.yash.onlineshopping.service.CustomerService;

public class CustomerControllerTest {
	private MockMvc mockMvc;

	@Mock
	private CustomerService customerService;

	@InjectMocks
	private CustomerController customerController;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
	}

	@Test
	public void getCustomerByIdTest() throws Exception {
		CustomerModel model = new CustomerModel(1, "Anish Singh", "9661188225", "anish.singh@yash.com", "Y", new Date(),
				"Male");

		Mockito.when(customerService.findById(1)).thenReturn(model);

		mockMvc.perform(get("/customer/{id}", 1)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
		// .andExpect(jsonPath("$.id").value(1));

		Mockito.verify(customerService, Mockito.times(1)).findById(1);
		Mockito.verifyNoMoreInteractions(customerService);
	}
}
