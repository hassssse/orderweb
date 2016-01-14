package order;

import java.util.ArrayList;

public class SelectOrderResult {
  private int orderNumber, customCode;
  private String orderDate, customName, salesCode, salesName;
  private Double totalAmount,salesTax,bill;
  private ArrayList<SelectOrderItemResult> itemList;

  public SelectOrderResult() {
    this.orderNumber = 0;
    this.customCode = 0;
    this.orderDate = "0000-00-00";
    this.customName = "noname";
    this.salesCode = "00";
    this.salesName = "noname";
    itemList = new ArrayList<SelectOrderItemResult>();
  }

  public SelectOrderResult(int orderNumber, int customCode, String orderDate,
  String customName, String salesCode, String salesName) {
    this();
    this.orderNumber = orderNumber;
    this.customCode = customCode;
    this.orderDate = orderDate;
    this.customName = customName;
    this.salesCode = salesCode;
    this.salesName = salesName;
  }

  public SelectOrderResult(int orderNumber, int customCode, String orderDate,
  String customName, String salesCode, String salesName,Double totalAmount,Double salesTax,Double bill) {
    this();
    this.orderNumber = orderNumber;
    this.customCode = customCode;
    this.orderDate = orderDate;
    this.customName = customName;
    this.salesCode = salesCode;
    this.salesName = salesName;
    this.totalAmount = totalAmount;
    this.salesTax = salesTax;
    this.bill = bill;
  }

  public int getOrderNumber() {
    return orderNumber;
  }

  public int getCustomCode() {
    return customCode;
  }

  public String getOrderDate() {
    return orderDate;
  }

  public String getCustomName() {
    return customName;
  }

  public String getSalesCode() {
    return salesCode;
  }

  public String getSalesName() {
    return salesName;
  }

  public Double getTotalAmount() {
  return totalAmount;
  }

  public Double getSalesTax() {
  return salesTax;
  }

  public Double getBill() {
    return bill;
  }

  public ArrayList<SelectOrderItemResult> getOrderItemList() {
    return itemList;
  }

  public void addOrderItemResult(SelectOrderItemResult itemResult) {
    itemList.add(itemResult);
  }

  public SelectOrderItemResult getOrderItemValue(int index) {
    return itemList.get(index);
  }
}