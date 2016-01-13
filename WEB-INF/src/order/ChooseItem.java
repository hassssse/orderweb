package order;

public class ChooseItem extends SelectItemResult{
  private int quantity;
  private double amount, inTax;
  private final double TAX = 0.08; 
  
  public ChooseItem() {
    super();
    this.quantity = 0;
    this.amount = 0.0;
    this.inTax = 0.0;
  }
  
  public ChooseItem(SelectItemResult itemResult) {
    super(itemResult.getItemCode(), itemResult.getItemName(),
        itemResult.getItemPrice());
  }
    
  public ChooseItem(int itemCode, String itemName,
    int itemPrice, int quantity) {
    super(itemCode, itemName, itemPrice);
    setQuantity(quantity);
  }
  
  public void setItemCode(int itemCode) {
    super.itemCode = itemCode;
    SelectProcess sp = new SelectProcess();
    sp.selectItemByItemCode(itemCode);
    super.itemName = sp.getChooseItemList().get(0).getItemName();
    super.itemPrice = sp.getChooseItemList().get(0).getItemPrice();
  }

  public int getQuantity() {
    return quantity;
  }
  
  public void setQuantity(int quantity) {
    this.quantity = quantity;
    this.amount = itemPrice * quantity;
    this.inTax = Math.floor(amount + amount * TAX);
  }

  public double getAmount() {
    return amount;
  }

  public double getInTax() {
    return inTax;
  }
}
