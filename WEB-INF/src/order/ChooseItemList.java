package order;

import java.util.ArrayList;

public class ChooseItemList extends ChooseItem {
  private ArrayList<ChooseItem> list
    = new ArrayList<ChooseItem>();
  
  public ArrayList<ChooseItem> getList() {
    return list;
  }
  
  public void addList(ChooseItem ci) {
    list.add(ci);
  }
  
  public void clearList() {
    list.clear();
  }
}
