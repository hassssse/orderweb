package order;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;

public class InsertProcess {
  private Connection conn;
  private PreparedStatement stmt;
  private ResultSet rslt;

  public InsertProcess(){
    init();
    conn = ConnectDB.connectDatabase();
  }

  //初期化
  public void init(){
    conn = null;
    stmt = null;
    rslt = null;
  }

  public void close(){
    try{
      if(rslt != null){
        rslt.close();
      }
    }catch(SQLException e){
      sqlError(e);
    }
    try {
      if(stmt != null){
        stmt.close();
      }
    }catch(SQLException e){
      sqlError(e);
    }
    try{
      if(conn != null){
        conn.close();
      }
    }catch(SQLException e){
      sqlError(e);
    }
  }

  public void sqlError(SQLException e){
    Alert.sqlError(e);
  }

  public void insertOrder(Calendar cal, int customnum, String salesnum,
    ArrayList<Integer> itemnum, ArrayList<Integer> quantity,
    double totalamount, double salestax, double bill){
    String sql1,sql2;
    sql1 = "INSERT INTO order_title"
         + " (order_date,custom_c,sales_c,total_amount,sales_tax,bill)"
         + " values(?,?,?,?,?,?)";
    sql2 = "INSERT INTO order_detail (order_no,item_c,quantity) values(?,?,?)";
    try{
      stmt = conn.prepareStatement(sql1, stmt.RETURN_GENERATED_KEYS);
      Date datecal = new java.sql.Date(cal.getTime().getTime());
      //stmt.setInt(1, 0);
      stmt.setDate(1, datecal);
      stmt.setInt(2, customnum);
      stmt.setString(3, salesnum);
      stmt.setDouble(4, totalamount);
      stmt.setDouble(5, salestax);
      stmt.setDouble(6, bill);
      stmt.execute();
      //stmt = conn.prepareStatement("SELECT MAX(order_no) FROM order_title;");
      
      ResultSet orderno = stmt.getGeneratedKeys();  // 最新のオーダーナンバーの値
      orderno.next();
      stmt = conn.prepareStatement(sql2);
      for(int i=0; i<itemnum.size(); i++){
        stmt.setInt(1, orderno.getInt(1));
        stmt.setInt(2, itemnum.get(i));
        stmt.setInt(3, quantity.get(i));
        stmt.executeUpdate();
      }
    }catch(SQLException e){
      sqlError(e);
    }finally{
      close();
    }
  }
  
  public void insertOrder(Calendar cal, int customNumber, String salesNumber,
    ChooseItemList list, double total, double tax, double bill){
    ArrayList<Integer> itemCodeList = new ArrayList<Integer>();
    ArrayList<Integer> quantityList = new ArrayList<Integer>();
    for (int i=0; i<list.getList().size(); i++) {
      itemCodeList.add(list.getList().get(i).getItemCode());
      quantityList.add(list.getList().get(i).getQuantity());
    }
    
    insertOrder(cal, customNumber, salesNumber, itemCodeList, quantityList,
      total, tax, bill);
  }
}