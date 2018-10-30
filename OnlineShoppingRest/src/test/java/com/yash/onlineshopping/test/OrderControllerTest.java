package com.yash.onlineshopping.test;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.yash.onlineshopping.controller.OrderController;
import com.yash.onlineshopping.model.CustomerModel;
import com.yash.onlineshopping.model.OrdersModel;
import com.yash.onlineshopping.model.ProductModel;
import com.yash.onlineshopping.serviceImpl.OrderServiceImpl;
import com.yash.onlineshopping.util.RecordNotFoundException;

@RunWith(SpringRunner.class)
@WebMvcTest(value = OrderController.class, secure = false)
public class OrderControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private OrderServiceImpl orderServiceimpl;

	@Rule
	public ExpectedException exception = ExpectedException.none();

	OrdersModel order = new OrdersModel();
	CustomerModel customer = new CustomerModel();
	ProductModel product = new ProductModel();
	private String orderJSON;

	@Test
	public void shouldGetOrderByOrderId() throws Exception {
		setOrderModel();

		Mockito.when(orderServiceimpl.findById(Mockito.anyInt())).thenReturn(order);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/order/1").accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println(result.getResponse());
		String expected = "{orderId:1,paymentMethod:Cash,paidAmount:12000,orderQuantity:1}";

		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}

	@Test
	public void shouldGetUserById() throws Exception {
		List<OrdersModel> orders = new ArrayList<>();
		setOrderModel();
		String ss = "[" + "{" + "\"paymentMethod\":\"Cash\"," + "\"paidAmount\":\"12000\"," + "\"orderQuantity\":\"1\","
				+ "\"productId\":\"1\"" + "}]";
		order.setPaymentMethod("Cash");
		order.setPaidAmount(12000.00);
		order.setOrderQuantity(1);
		order.setProductId(1);
		order.setCustomerId(2);

		orders.add(order);
		Mockito.when(orderServiceimpl.bookOrder(Mockito.anyObject(), Mockito.anyInt())).thenReturn(orders);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/orderplace/2").accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println("orders >>>>>>>>>>>>>>>>>>>" + orders.get(0).toString());
		String expected = "[" + "{" + " \"orderId\": \"4\"," + " \"paymentMethod\": \"Cash\","
				+ " \"paidAmount\": \"12000\"," + " \"orderQuantity\": \"1\"," + " \"customerModel\": \"null\","
				+ " \"productModel\": \"null\"," + " \"productId\": \"1\"," + " \"customerId\": \"2\","
				+ " \"createdDate\": \"2018-10-11\"" + "}" + "]";

		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);

	}

	@Test
	public void shouldReturnRecordNotFoundWhenOrderIsNull() throws Exception {

		when(orderServiceimpl.findById(10)).thenThrow(new RecordNotFoundException("Resource not found!"));

		// If I replace the above line with Mockito.any, the testcase passes
		// Mockito.when(customerService.insert(Mockito.any(Customer.class))
		// .thenThrow(new BusinessException("CustomerName", "CustomerName
		// already in use"));
		mockMvc.perform(get("/10")).andExpect(status().isOk()).andExpect(content().contentType(orderJSON))
				.andDo(print());

		/*
		 * mockMvc.perform(get("/10").contentType(MediaType.APPLICATION_JSON).
		 * content(orderJSON)
		 * .accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest(
		 * ));
		 */
	}

	private void setOrderModel() {
		order.setCreatedDate(new Date());
		order.setPaidAmount(12000.00);
		order.setPaymentMethod("Cash");
		order.setOrderQuantity(1);
		order.setProductId(1);
		order.setCustomerId(1);
		customer.setCreatedDate(new Date());
		customer.setCustomerContactNo("123456");
		customer.setCustomerEmail("isha.tiwari@yash.com");
		customer.setCustomerId(1);
		customer.setCustomerName("isha");
		customer.setIsActive("Y");
		order.setCustomerModel(customer);
		order.setOrderId(1);
		product.setAvailableQuantity(1);
		product.setPrice(12000.00);
		product.setProductDesc("iPhone");
		product.setProductId(1);
		product.setProductName("iPhone");
		order.setProductModel(product);
	}

}
