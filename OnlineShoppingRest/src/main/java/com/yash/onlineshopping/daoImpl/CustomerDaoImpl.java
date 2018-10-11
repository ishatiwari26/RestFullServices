package com.yash.onlineshopping.daoImpl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yash.onlineshopping.configuration.JDBCConnection;
import com.yash.onlineshopping.dao.CustomerDao;
import com.yash.onlineshopping.model.CustomerModel;

@Repository
public class CustomerDaoImpl implements CustomerDao {

	@Autowired
	private JDBCConnection jdbcConnection;

	@Override
	public CustomerModel getCustomerById(int id) {
		CustomerModel customer = null;
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			con = jdbcConnection.getConnection();
			stmt = con.createStatement();

			String sql = "select * FROM customer_master c WHERE c.customerId =" + id;
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				customer = new CustomerModel(rs.getInt("customerId"), rs.getString("customerName"),
						rs.getString("customerContactNo"), rs.getString("customerEmail"), rs.getString("isActive"),
						rs.getDate("createdDate"));

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
		return customer;
	}

	@Override
	public void createCustomer(CustomerModel customerModel) {
		/*
		 * Session session =
		 * entityManagerFactory.unwrap(SessionFactory.class).openSession(); try
		 * { session.beginTransaction(); session.saveOrUpdate(customerModel);
		 * session.flush(); session.clear(); session.getTransaction().commit();
		 * 
		 * } catch (Exception e) { e.printStackTrace(); } finally {
		 * session.close(); }
		 */
		Connection conn = null;
		Statement st = null;
		try {
			conn = jdbcConnection.getConnection();
			st = conn.createStatement();
			String sql = "INSERT INTO customer_master(customerId,createdDate,customerContactNo,customerEmail,customerName,isActive)"
					+ "VALUES(null, CURRENT_TIMESTAMP(),'" + customerModel.getCustomerContactNo() + "' , '"
					+ customerModel.getCustomerEmail() + "' , '" + customerModel.getCustomerName() + "' , '"
					+ customerModel.getIsActive() + "')";
			int result = st.executeUpdate(sql);
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
	}

	public List<CustomerModel> getCustomers() {
		// Session session =
		// entityManagerFactory.unwrap(SessionFactory.class).openSession();
		// return session.createCriteria(CustomerModel.class).list();

		List<CustomerModel> list = new ArrayList<CustomerModel>();
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			con = jdbcConnection.getConnection();
			stmt = con.createStatement();

			String sql = "select * FROM customer_master";
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				CustomerModel customer = new CustomerModel(rs.getInt("customerId"), rs.getString("customerName"),
						rs.getString("customerContactNo"), rs.getString("customerEmail"), rs.getString("isActive"),
						rs.getDate("createdDate"));
				list.add(customer);
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

		return list;

	}

	@Override
	public CustomerModel updateCustomerDetails(CustomerModel customerModel, Integer customerId) {
		CustomerModel customer = getCustomerById(customerId);
		if (customerModel.getCustomerContactNo() != null)
			customer.setCustomerContactNo(customerModel.getCustomerContactNo());
		if (customerModel.getCustomerEmail() != null)
			customer.setCustomerEmail(customerModel.getCustomerEmail());
		if (customerModel.getCustomerName() != null)
			customer.setCustomerName(customerModel.getCustomerName());
		if (customerModel.getIsActive() != null)
			customer.setIsActive(customerModel.getIsActive());

		Connection conn = null;
		Statement st = null;
		try {
			conn = jdbcConnection.getConnection();
			st = conn.createStatement();
			String sql = "update  customer_master set customerContactNo='" + customerModel.getCustomerContactNo()
					+ "',customerEmail='" + customerModel.getCustomerEmail() + "',customerName='"
					+ customerModel.getCustomerName() + "',isActive='" + customerModel.getIsActive()
					+ "' where customerId=" + customer.getCustomerId();

			int result = st.executeUpdate(sql);
			System.out.println("Update ::" + result);

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

		createCustomer(customer);
		return customer;
	}

	@Override
	public void deleteCustomer(Integer customerId) {
		/*
		 * EntityManager em = entityManagerFactory.createEntityManager();
		 * em.getTransaction().begin(); Query query = em.
		 * createQuery("DELETE FROM CustomerModel c WHERE c.customerId = :cid "
		 * ); query.setParameter("cid", customerId); int rowsDeleted =
		 * query.executeUpdate(); System.out.println("Entities Deleted: " +
		 * rowsDeleted); em.getTransaction().commit(); em.close();
		 */

		Connection con = null;
		Statement stmt = null;
		try {
			con = jdbcConnection.getConnection();
			stmt = con.createStatement();
			int result = stmt.executeUpdate("DELETE FROM customer_master where customerId=" + customerId);
			System.out.println("Deleted ::" + result);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
