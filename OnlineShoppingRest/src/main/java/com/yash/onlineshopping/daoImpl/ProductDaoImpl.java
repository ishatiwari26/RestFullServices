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
import com.yash.onlineshopping.dao.ProductDao;
import com.yash.onlineshopping.model.ProductModel;

@Repository
public class ProductDaoImpl implements ProductDao {

	@Autowired
	private JDBCConnection jdbcConnection;

	@Override
	public ProductModel getProductById(int id) {
		ProductModel model = null;
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			con = jdbcConnection.getConnection();
			stmt = con.createStatement();

			String sql = "select * FROM product_master WHERE productId =" + id;
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				model = new ProductModel(rs.getInt("productId"), rs.getString("productName"),
						rs.getString("productDesc"), rs.getInt("availableQuantity"), rs.getDouble("price"));

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
	public int addNewProduct(ProductModel productModel) {
		Connection conn = null;
		Statement st = null;
		int result = -1;
		try {
			conn = jdbcConnection.getConnection();
			st = conn.createStatement();
			String sql = "INSERT INTO product_master(productId,availableQuantity,price,productDesc,productName)"
					+ "VALUES(null, " + productModel.getAvailableQuantity() + "," + productModel.getPrice() + " , '"
					+ productModel.getProductDesc() + "' , '" + productModel.getProductName() + "')";
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
		return result;
	}

	@Override
	public List<ProductModel> getAllProducts() {
		List<ProductModel> list = new ArrayList<ProductModel>();
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			con = jdbcConnection.getConnection();
			stmt = con.createStatement();

			String sql = "select * FROM product_master";
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				ProductModel model = new ProductModel(rs.getInt("productId"), rs.getString("productName"),
						rs.getString("productDesc"), rs.getInt("availableQuantity"), rs.getDouble("price"));
				list.add(model);
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
	public void deleteProduct(Integer productId) {
		Connection con = null;
		Statement stmt = null;
		try {
			con = jdbcConnection.getConnection();
			stmt = con.createStatement();
			int result = stmt.executeUpdate("DELETE FROM product_master WHERE productId =" + productId);
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

	@Override
	public void updateProductDetails(ProductModel currentProduct, Integer productId) {
		ProductModel product = getProductById(productId);
		if (product.getAvailableQuantity() != null)
			product.setAvailableQuantity(currentProduct.getAvailableQuantity());
		if (product.getPrice() != null)
			product.setPrice(currentProduct.getPrice());
		if (product.getProductDesc() != null)
			product.setProductDesc(currentProduct.getProductDesc());
		if (product.getProductName() != null)
			product.setProductName(currentProduct.getProductName());

		Connection conn = null;
		Statement st = null;
		try {
			conn = jdbcConnection.getConnection();
			st = conn.createStatement();
			String sql = "update  product_master set availableQuantity='" + product.getAvailableQuantity() + "',price="
					+ product.getPrice() + ",productDesc='" + product.getProductDesc() + "',productName='"
					+ product.getProductName() + "' where productId=" + product.getProductId();

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
	}

}
