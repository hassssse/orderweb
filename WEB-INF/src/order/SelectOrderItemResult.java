package order;

public class SelectOrderItemResult {
  private int itemCode,quantity;
  private String itemName;
  private Double price;

  public SelectOrderItemResult(int itemCode, String itemName, Double price,
      int quantity) {
    this.itemCode = itemCode;
    this.itemName = itemName;
    this.price = price;
    this.quantity = quantity;
  }
  public SelectOrderItemResult(String itemName, int itemCode, int quantity) {
    this.itemName = itemName;
    this.itemCode = itemCode;
    this.quantity = quantity;
  }

  public int getItemCode(){
    return itemCode;
  }
  public String getItemName(){
    return itemName;
  }
  public Double getPrice(){
    return price;
  }
  public int getQuantity(){
    return quantity;
  }

}