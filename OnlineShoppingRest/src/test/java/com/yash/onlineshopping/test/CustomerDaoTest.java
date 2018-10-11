package com.yash.onlineshopping.test;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.yash.onlineshopping.configuration.JDBCConnection;
import com.yash.onlineshopping.daoImpl.CustomerDaoImpl;
import com.yash.onlineshopping.model.CustomerModel;

@RunWith(MockitoJUnitRunner.class)
public class CustomerDaoTest {
	@InjectMocks
	private CustomerDaoImpl customerDaoImpl;
	@Mock
	private JDBCConnection jdbcConnection;
	@Mock
	private Connection conn;
	@Mock
	private PreparedStatement stmt;
	@Mock
	private ResultSet rs;

	@Rule
	public ExpectedException exception = ExpectedException.none();

	CustomerModel customer;

	@Before
	public void setUp() throws Exception {
		assertNotNull(jdbcConnection);
		when(conn.prepareStatement(any(String.class))).thenReturn(stmt);
		when(jdbcConnection.getConnection()).thenReturn(conn);
		customer = new CustomerModel();
		customer.setCustomerId(1);
		customer.setCustomerName("Isha Tiwari");
		customer.setCustomerContactNo("9370244805");
		customer.setCreatedDate(new Date());
		customer.setCustomerEmail("isha.tiwari@yash.com");
		customer.setIsActive("Y");
		when(rs.first()).thenReturn(true);
		when(rs.getInt(1)).thenReturn(1);
		when(rs.getString(2)).thenReturn(customer.getCustomerName());
		when(rs.getString(3)).thenReturn(customer.getCustomerContactNo());
		when(stmt.executeQuery()).thenReturn(rs);
	}

	@Test
	public void shouldGiveCustomerDetailsWhenPassCustomerId() throws SQLException {
		customerDaoImpl.createCustomer(customer);
	}

}
