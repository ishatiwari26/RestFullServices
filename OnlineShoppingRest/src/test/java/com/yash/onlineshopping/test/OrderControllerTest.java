package com.yash.onlineshopping.test;

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

		/*
		 * Response response = givenAuth().get(URL_PREFIX + "/api/foos/ccc");
		 * RecordNotFoundException error =
		 * response.as(RecordNotFoundException.class);
		 */

		// exception.expect(RecordNotFoundException.class);
		// Mockito.when(orderService.findById(Mockito.anyInt())).thenReturn(null);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/order/10").accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println(result.getResponse());
		// String expected =
		// "{orderId:1,paymentMethod:Cash,paidAmount:12000,orderQuantity:1}";

		// JSONAssert.assertEquals(RecordNotFoundException.class,
		// HttpStatus.NOT_FOUND);
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
