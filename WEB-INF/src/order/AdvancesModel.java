package order;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AdvancesModel {
	static Connection con = null;
	private static ArrayList<SelectOrderItemResult>orderItemResultList = new ArrayList<SelectOrderItemResult>();
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

	public static void DoSql(int noint) {
		con = connectDatabase();
		orderItemResultList= new ArrayList<SelectOrderItemResult>();
		String sql;
		sql = "SELECT item.item_c,item.item_num,item.price,order_detail.quantity"
			+ " FROM order_detail, item"
			+ " WHERE order_detail.item_c = item.item_c"
			+ " AND order_no = ?"
			+ " ORDER BY item.item_c";
		try{
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, noint);
			ResultSet rslt = stmt.executeQuery();
			while (rslt.next()) {
				int itemCode = rslt.getInt("item_c");
				String itemName = rslt.getString("iten_nam");
				Double Price = rslt.getDouble("price");
				int Quantity = rslt.getInt("quantity");;

				SelectOrderItemResult orderResult
				= new SelectOrderItemResult(itemCode,itemName,Price,Quantity);
				orderItemResultList.add(orderResult);
			}
		} catch(SQLException e) {
		} finally {
			close();
		}

	}

	public static ArrayList<SelectOrderItemResult> getOrderItemResultList() {
		return orderItemResultList;
	}

	private static void close() {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
