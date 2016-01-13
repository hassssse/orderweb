package order;

public class SelectCustomResult {
  private int customCode;
  private String customName;

  public SelectCustomResult() {
    this.customCode = 0;
    this.customName = "noname";
  }

  public SelectCustomResult(int customCode, String customName) {
    this();
    this.customCode = customCode;
    this.customName = customName;
  }

  public int getCustomCode() {
    return customCode;
  }

  public String getCustomName() {
    return customName;
  }
}