package order;

import java.sql.SQLException;

public class Alert {
  public static void incorrectNumber() {
    System.out.println("入力データが不正です");
  }
  
  public static void sqlError(SQLException e) {
    System.out.println("エラーコード:" + e.getErrorCode());
    System.out.println("SQL状態:" + e.getSQLState());
    e.printStackTrace();
  }
}