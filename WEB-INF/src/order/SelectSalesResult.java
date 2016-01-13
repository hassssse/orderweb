package order;

public class SelectSalesResult {
  private String salesCode, salesName;

  public SelectSalesResult() {
    this.salesCode = "00";
    this.salesName = "noname";
  }

  public SelectSalesResult(String salesCode, String salesName) {
    this();
    this.salesCode = salesCode;
    this.salesName = salesName;
  }

  public String getSalesCode() {
    return salesCode;
  }

  public String getSalesName() {
    return salesName;
  }
}