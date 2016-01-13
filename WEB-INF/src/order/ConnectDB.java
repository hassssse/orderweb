package order;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {
  public static Connection connectDatabase() {
    Connection con = null;
    try {
      Class.forName("com.mysql.jdbc.Driver");
      con = DriverManager.getConnection(
        "jdbc:mysql://127.0.0.1/orderdb?"
        + "useUnicode=true&characterEncoding=WINDOWS-31J",
        "webdb", "webdb");
    } catch (SQLException ex) {
      System.out.println("エラーコード:" + ex.getErrorCode());
      System.out.println("SQL状態:" + ex.getSQLState());
      ex.printStackTrace();
    } catch (ClassNotFoundException ex) {
      ex.printStackTrace();
    }
    return con;
  }
}