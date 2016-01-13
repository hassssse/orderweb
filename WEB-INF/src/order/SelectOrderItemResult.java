package order;

public class SelectOrderItemResult {
  private String itemName;
  private int itemCode, itemQuantity;
  
  public SelectOrderItemResult() {
    this.itemName = "noname";
    this.itemCode = 0;
    this.itemQuantity = 0;
  }
  
  public SelectOrderItemResult(String itemName, int itemCode,
    int itemQuantity) {
    this();
    this.itemName = itemName;
    this.itemCode = itemCode;
    this.itemQuantity = itemQuantity;
  }
  
  public String getItemName() {
    return itemName;
  }
  
  public int getItemCode() {
    return itemCode;
  }
  
  public int getItemQuantity() {
    return itemQuantity;
  }
}