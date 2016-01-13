package order;

import java.util.ArrayList;
import java.util.Calendar;

public class InsertIO {
  public InsertIO() {
    SelectProcess select;
    Calendar cal = Calendar.getInstance();
    
    // 受注年月日の入力と例外処理
    try {
      System.out.println("受注年を入力してください");
      System.out.print("年:");
      // 年の入力
      int year = Input.convertToInteger();
      System.out.println("受注月を入力してください");
      System.out.print("月:");
      // 月の入力
      int month = Input.convertToInteger();
      System.out.println("受注日を入力してください");
      System.out.print("日:");
      // 日の入力
      int day = Input.convertToInteger();

      cal.setLenient(false);
      cal.set(Calendar.YEAR, year);
      cal.set(Calendar.MONTH, month-1);
      cal.set(Calendar.DATE, day);
      cal.getTime();
      System.out.println();

      System.out.println("顧客を選択してください");
      // 顧客の一覧表示
      select = new SelectProcess();
      select.selectCustomListAll();
      ArrayList<SelectCustomResult> customList = select.getCustomResultList();
      for (int i=0; i<customList.size(); i++) {
        System.out.printf("%-2d ", i+1);
        System.out.printf("%s", customList.get(i).getCustomName());
        System.out.printf("(%s)", customList.get(i).getCustomCode());
        System.out.println();
      }
      // ここまで
      System.out.print("番号:");
      int indexCustom = Input.convertToInteger();
      int customnum = customList.get(indexCustom-1).getCustomCode();
  
      System.out.println("担当者を選択してください");
      // 担当者の一覧表示
      select = new SelectProcess();
      select.selectSalesListAll();
      ArrayList<SelectSalesResult> salesList = select.getSalesResultList();
      for (int i=0; i<salesList.size(); i++) {
        System.out.printf("%-2d ", i+1);
        System.out.printf("%s", salesList.get(i).getSalesName());
        System.out.printf("(%s)", salesList.get(i).getSalesCode());
        System.out.println();
      }
      // ここまで
      System.out.print("番号:");
      int indexSales = Input.convertToInteger();
      String salesnum = salesList.get(indexSales-1).getSalesCode();
      
      System.out.println("商品を選択してください");
      // 商品一覧表示
      select = new SelectProcess();
      select.selectItemListAll();
      ArrayList<SelectItemResult> itemList = select.getItemResultList();
      for (int i=0; i<itemList.size(); i++) {
        String name = itemList.get(i).getItemName();
        for (int j=0; j<15; j++) {
          name += "　";
        }
        System.out.printf("%3d ", i+1);
        System.out.printf("%.14s", name);
        System.out.printf("(%3d)", itemList.get(i).getItemCode());
        if (i%2 == 0) {
          System.out.print("\t");
        } else {
          System.out.println();
        }
      }
      // ここまで
  
      ArrayList<Integer> itemnum = new ArrayList<Integer>();
      ArrayList<Integer> quantity = new ArrayList<Integer>();
      boolean flag = true;
      int num, num2;
      double total = 0.0, tax = 0.0, bill = 0.0;
  
      while (flag) {
        System.out.print("商品の一連番号（0の入力で選択終了):");
        num = Input.convertToInteger();
        if (num == 0) {
          break;
        }
        int itemCode = itemList.get(num-1).getItemCode();
        itemnum.add(itemCode);
        System.out.print("数量:");
        num2 = Input.convertToInteger();
        quantity.add(num2);
  
        int price = itemList.get(num-1).getItemPrice();
        total += price * num2;
      }
  
      tax = Math.floor(total * 0.08);
      bill = total + tax;
  
      InsertProcess insert = new InsertProcess();
      insert.insertOrder(cal, customnum, salesnum, itemnum,
                         quantity, total, tax, bill);
      
      System.out.println();
    } catch (IllegalArgumentException e) {
      Alert.incorrectNumber();
      System.out.println();
    } catch (IndexOutOfBoundsException e) {
      Alert.incorrectNumber();
      System.out.println();
    }
  }
}
