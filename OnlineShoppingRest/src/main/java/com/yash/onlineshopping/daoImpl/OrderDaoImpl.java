package com.yash.onlineshopping.daoImpl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yash.onlineshopping.configuration.JDBCConnection;
import com.yash.onlineshopping.dao.CustomerDao;
import com.yash.onlineshopping.dao.OrderDao;
import com.yash.onlineshopping.dao.ProductDao;
import com.yash.onlineshopping.model.OrdersModel;

@Repository
public class OrderDaoImpl implements OrderDao {
	@Autowired
	private JDBCConnection jdbcConnection;

	@Autowired
	private ProductDao productDao;

	@Autowired
	private CustomerDao customerDao;

	@Override
	public OrdersModel getOrderDetailsById(Integer orderId) {
		OrdersModel model = null;
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			con = jdbcConnection.getConnection();
			stmt = con.createStatement();

			String sql = "select * FROM orders WHERE orderId =" + orderId;
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				model = new OrdersModel();
				model.setOrderId(rs.getInt("orderId"));
				model.setOrderQuantity(rs.getInt("orderQuantity"));
				model.setPaidAmount(rs.getDouble("paidAmount"));
				model.setPaymentMethod(rs.getString("paymentMethod"));
				model.setCreatedDate(rs.getDate("createdDate"));
				model.setCustomerId(rs.getInt("customerIdFk"));
				model.setProductId(rs.getInt("productIdFk"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return model;
	}

	@Override
	public OrdersModel newOrderCreate(OrdersModel newOrdersModel) {

		Connection conn = null;
		Statement st = null;
		int result = -1;
		try {
			conn = jdbcConnection.getConnection();
			st = conn.createStatement();
			String sql = "INSERT INTO orders (orderId,paymentMethod,paidAmount,createdDate,customerIdFk,productIdFk,orderQuantity)"
					+ "VALUES(null, '" + newOrdersModel.getPaymentMethod() + "'," + newOrdersModel.getPaidAmount()
					+ ", CURRENT_TIMESTAMP() , " + newOrdersModel.getCustomerId() + " , "
					+ newOrdersModel.getProductId() + "," + newOrdersModel.getOrderQuantity() + ")";
			System.out.println(" sql >>> " + sql);
			result = st.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
			try (ResultSet rs = st.getGeneratedKeys()) {
				if (rs.next()) {
					result = rs.getInt(1);

				}
				rs.close();
			} catch (SQLException s) {
				s.printStackTrace();
			}
			System.out.println("Inserted ::" + result);

		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (st != null)
					conn.close();
			} catch (SQLException se) {
			}
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		return getOrderDetailsById(result);
	}

}
