package order;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteModel {
	static Connection con = null;
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

	public static void DoDelete(int orderNumber) {
		con = connectDatabase();
		String sql1,sql2;
		sql1 = "DELETE FROM order_detail WHERE  order_no = ?";
		sql2 = "DELETE FROM order_title WHERE order_no = ?";
		try{
			PreparedStatement stmt = con.prepareStatement(sql1);
			stmt.setInt(1, orderNumber);
			stmt.executeUpdate();
			stmt = con.prepareStatement(sql2);
			stmt.setInt(1, orderNumber);
			stmt.executeUpdate();
		}catch(SQLException e){
		}finally{
			close();
		}
	}

	private static void close() {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
