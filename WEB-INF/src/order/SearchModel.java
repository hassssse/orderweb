package order;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SearchModel {
	static Connection con = null;
	private static ArrayList<SelectOrderResult> orderResultList= new ArrayList<SelectOrderResult>();
	public static Connection connectDatabase() {
		//データベースアクセス
		con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(
				"jdbc:mysql://127.0.0.1/orderdb?"
				+ "useUnicode=true&characterEncoding=WINDOWS-31J",
				"webdb", "webdb");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return con;
	}

	public static void DoSql(String searchcustomName) {
		con = connectDatabase();
		orderResultList= new ArrayList<SelectOrderResult>();
		String sql;
		sql = "SELECT order_no, order_date, custom.custom_c, custom_nam,"
			+ " sales.sales_c, sales_nam,total_amount,sales_tax,bill"
			+ " FROM order_title, custom, sales"
			+ " WHERE custom.custom_c = order_title.custom_c"
			+ " AND sales.sales_c = order_title.sales_c"
			+ " AND custom_nam LIKE ?"
			+ " ORDER BY order_no";
		try{
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, "%"+searchcustomName+"%");
			ResultSet rslt = stmt.executeQuery();
			while (rslt.next()) {
				int orderNumber = rslt.getInt("order_no");
				String orderDate = rslt.getString("order_date");
				int customCode = rslt.getInt("custom_c");
				String customName = rslt.getString("custom_nam");
				String salesCode = rslt.getString("sales_c");
				String salesName = rslt.getString("sales_nam");
				Double totalAmount = rslt.getDouble("total_amount");
				Double salesTax = rslt.getDouble("sales_tax");
				Double bill = rslt.getDouble("bill");

				SelectOrderResult orderResult
				= new SelectOrderResult(orderNumber, customCode, orderDate,
						customName, salesCode, salesName,totalAmount,salesTax,bill);
				orderResultList.add(orderResult);
			}
		} catch(SQLException e) {
		} finally {
			close();
		}

	}

	public static ArrayList<SelectOrderResult> getOrderResultList() {
		return orderResultList;
	}

	private static void close() {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
