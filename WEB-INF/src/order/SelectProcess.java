package order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SelectProcess {
  private Connection conn;
  private PreparedStatement stmt;
  private ResultSet rslt;
  private ArrayList<SelectOrderResult> orderResultList
    = new ArrayList<SelectOrderResult>();
  private ArrayList<SelectOrderItemResult> orderItemResultList
    = new ArrayList<SelectOrderItemResult>();
  private ArrayList<SelectCustomResult> customResultList
    = new ArrayList<SelectCustomResult>();
  private ArrayList<SelectSalesResult> salesResultList
    = new ArrayList<SelectSalesResult>();
  private ArrayList<SelectItemResult> itemResultList
    = new ArrayList<SelectItemResult>();
  private ArrayList<ChooseItem> chooseItemList
    = new ArrayList<ChooseItem>();
      

  public SelectProcess() {
    init();
    conn = ConnectDB.connectDatabase();
  }

  // getter
  public ArrayList<SelectOrderResult> getOrderResultList() {
    return orderResultList;
  }

  public ArrayList<SelectOrderItemResult> getOrderItemResultList() {
    return orderItemResultList;
  }

  public ArrayList<SelectCustomResult> getCustomResultList() {
    return customResultList;
  }

  public ArrayList<SelectSalesResult> getSalesResultList() {
    return salesResultList;
  }

  public ArrayList<SelectItemResult> getItemResultList() {
    return itemResultList;
  }
  
  public ArrayList<ChooseItem> getChooseItemList() {
    return chooseItemList;
  }

  // 初期化
  public void init() {
    conn = null;
    stmt = null;
    rslt = null;
  }

  // クローズ
  public void close() {
    try {
      if (rslt != null) {
        rslt.close();
      }
    } catch (SQLException e) {
      sqlError(e);;
    }
    try {
      if (stmt != null) {
        stmt.close();
      }
    } catch (SQLException e) {
      sqlError(e);
    }
    try {
      if (conn != null) {
        conn.close();
      }
    } catch (SQLException e) {
      sqlError(e);
    }
  }

  // sqlの例外処理
  public void sqlError(SQLException e) {
    Alert.sqlError(e);
  }

  //検索のメソッド(受注検索用)
  //顧客名(の一部)から検索
  public void selectOrderListByCustomName(String searchCustomName) {
    String sql;
    sql = "SELECT order_no, order_date, custom.custom_c, custom_nam,"
        + " sales.sales_c, sales_nam"
        + " FROM order_title, custom, sales"
        + " WHERE custom.custom_c = order_title.custom_c"
        + " AND sales.sales_c = order_title.sales_c"
        + " AND custom_nam LIKE ?"
        + " ORDER BY order_no";
    try {
      stmt = conn.prepareStatement(sql);
      stmt.setString(1, "%"+searchCustomName+"%");
      rslt = stmt.executeQuery();
      while (rslt.next()) {
        int orderNumber = rslt.getInt("order_no");
        String orderDate = rslt.getString("order_date");
        int customCode = rslt.getInt("custom_c");
        String customName = rslt.getString("custom_nam");
        String salesCode = rslt.getString("sales_c");
        String salesName = rslt.getString("sales_nam");
        SelectOrderResult orderResult
          = new SelectOrderResult(orderNumber, customCode, orderDate,
                                  customName, salesCode, salesName);
        orderResultList.add(orderResult);
      }
    } catch(SQLException e) {
      sqlError(e);
    } finally {
      close();
    }
  }

  //受注No．から検索(受注商品一覧)
  public void selectItemListByOrderNumber(SelectOrderResult orderResult) {
    String sql;
    sql = "SELECT item.item_c, item_nam, quantity"
        + " FROM item, order_detail"
        + " WHERE item.item_c = order_detail.item_c"
        + " AND order_no = ?";
    try {
      stmt = conn.prepareStatement(sql);
      stmt.setInt(1, orderResult.getOrderNumber());
      rslt = stmt.executeQuery();
      while (rslt.next()) {
        String itemName = rslt.getString("item_nam");
        int itemCode = rslt.getInt("item_c");
        int itemQuantity = rslt.getInt("quantity");
        SelectOrderItemResult itemResult
          = new SelectOrderItemResult(itemName, itemCode, itemQuantity);
        orderResult.addOrderItemResult(itemResult);;
      }
    } catch(SQLException e) {
      sqlError(e);
    } finally {
      close();
    }
  }

  public void selectItemListByOrderNumber(int orderNumber) {
    String sql;
    sql = "SELECT item.item_c, item_nam, quantity"
        + " FROM item, order_detail"
        + " WHERE item.item_c = order_detail.item_c"
        + " AND order_no = ?";
    try {
      stmt = conn.prepareStatement(sql);
      stmt.setInt(1, orderNumber);
      rslt = stmt.executeQuery();
      while (rslt.next()) {
        String itemName = rslt.getString("item_nam");
        int itemCode = rslt.getInt("item_c");
        int itemQuantity = rslt.getInt("quantity");
        SelectOrderItemResult itemResult
          = new SelectOrderItemResult(itemName, itemCode, itemQuantity);
        //orderResult.addOrderItemResult(itemResult);;
        orderItemResultList.add(itemResult);
      }
    } catch(SQLException e) {
      sqlError(e);
    } finally {
      close();
    }
  }

  //検索のメソッド（受注登録用）
  //顧客の一覧
  public void selectCustomListAll() {
    String sql;
    sql = "SELECT custom_c, custom_nam"
        + " FROM custom";
    try {
      stmt = conn.prepareStatement(sql);
      rslt = stmt.executeQuery();
      while (rslt.next()) {
        int customCode = rslt.getInt("custom_c");
        String customName = rslt.getString("custom_nam");
        SelectCustomResult customResult
          = new SelectCustomResult(customCode, customName);
        customResultList.add(customResult);
      }
    } catch(SQLException e) {
      sqlError(e);
    } finally {
      close();
    }
  }

  //担当者の一覧
  public void selectSalesListAll() {
    String sql;
    sql = "SELECT sales_c, sales_nam"
        + " FROM sales";
    try {
      stmt = conn.prepareStatement(sql);
      rslt = stmt.executeQuery();
      while (rslt.next()) {
        String salesCode = rslt.getString("sales_c");
        String salesName = rslt.getString("sales_nam");
        SelectSalesResult salesResult
        = new SelectSalesResult(salesCode, salesName);
        salesResultList.add(salesResult);
      }
    } catch(SQLException e) {
      sqlError(e);
    } finally {
      close();
    }
  }

  //商品の一覧
  public void selectItemListAll() {
    String sql;
    sql = "SELECT item_c, item_nam, price"
        + " FROM item ORDER BY item_nam";
    try {
      stmt = conn.prepareStatement(sql);
      rslt = stmt.executeQuery();
      while (rslt.next()) {
        int itemCode = rslt.getInt("item_c");
        String itemName = rslt.getString("item_nam");
        int itemPrice = rslt.getInt("price");
        SelectItemResult itemResult
          = new SelectItemResult(itemCode, itemName, itemPrice);
        itemResultList.add(itemResult);
      }
    } catch(SQLException e) {
      sqlError(e);
    } finally {
      close();
    }
  }
  
  // web用に追加した
  // 商品コードから商品を検索
  public void selectItemByItemCode(int searchCode) {
    String sql;
    sql = "SELECT item_c, item_nam, price"
        + " FROM item WHERE item_c = ?";
    try {
      stmt = conn.prepareStatement(sql);
      stmt.setInt(1, searchCode);
      rslt = stmt.executeQuery();
      SelectItemResult itemResult = null;
      while (rslt.next()) {
        int itemCode = rslt.getInt("item_c");
        String itemName = rslt.getString("item_nam");
        int itemPrice = rslt.getInt("price");
        itemResult = new SelectItemResult(itemCode, itemName, itemPrice);
        itemResultList.add(itemResult);
      }
      ChooseItem chooseItem = new ChooseItem(itemResult);
      chooseItemList.add(chooseItem);
    } catch (SQLException e) {
      sqlError(e);
    } finally {
      close();
    }
  }

  // 検索のメソッド（受注削除用）
  // select文を実行して、検索された件数を数値で返す
  public int countOrderByOrderNumber(int orderNumber) {
    int count=0;
    String sql;
    sql = "SELECT order_no"
        + " FROM order_title"
        + " WHERE order_no = ?";
    try {
      stmt = conn.prepareStatement(sql);
      stmt.setInt(1, orderNumber);
      rslt = stmt.executeQuery();
      while (rslt.next()) {
        count++;
      }
    } catch(SQLException e) {
      sqlError(e);
    } finally {
      close();
    }
    return count;
  }
}