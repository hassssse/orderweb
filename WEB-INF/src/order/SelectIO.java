package order;

import java.util.ArrayList;

public class SelectIO {
  public SelectIO() {
    System.out.println("顧客名（の一部）を入力してください");
    System.out.print("顧客名:");
    String customName = Input.characterString();
    System.out.println();
    
    SelectProcess select = new SelectProcess();
    select.selectOrderListByCustomName(customName);
    ArrayList<SelectOrderResult> orderList = select.getOrderResultList();
    
    if (orderList.size()!=0) {
      for (SelectOrderResult orderResult : orderList) {
        System.out.println("受注No.:" + orderResult.getOrderNumber());
        System.out.println("受注日付:" + orderResult.getOrderDate());
        System.out.println("顧客名:" + orderResult.getCustomName()
          + "(" + orderResult.getCustomCode() + ")");
        System.out.println("担当者名:"+orderResult.getSalesName()
          + "(" + orderResult.getSalesCode() + ")");
        
        System.out.println("受注商品一覧");
        select = new SelectProcess();
        select.selectItemListByOrderNumber(orderResult.getOrderNumber());
        ArrayList<SelectOrderItemResult> itemList
          = select.getOrderItemResultList();
        for (SelectOrderItemResult itemResult : itemList) {
          System.out.println(itemResult.getItemCode()
            + "\t" + itemResult.getItemName()
            + " : " + itemResult.getItemQuantity() + "個");
        }
        System.out.println();
      }
    } else {
      System.out.println("該当する顧客の受注は存在しません");
      System.out.println();
    }
  }
}