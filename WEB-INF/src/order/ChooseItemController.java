package order;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringEscapeUtils;

@WebServlet(name="ChooseItem", urlPatterns={"/ChooseItem"})
public class ChooseItemController extends HttpServlet {
  protected void doPost(HttpServletRequest request,
    HttpServletResponse response)
    throws ServletException, IOException {
    response.setContentType("text/html; charset=UTF-8");
    
    // セッションを利用して選択した商品を管理
    // セッションの生成
    HttpSession session = request.getSession();

    // セッション属性の取得
    String key = "list";
    ChooseItemList list
      = (ChooseItemList) session.getAttribute(key);
    if (list == null) {
      list = new ChooseItemList();
    }

    // requestから値の取得
    String tempC = request.getParameter("code");
    tempC = StringEscapeUtils.escapeXml(tempC);
    String tempQ = request.getParameter("quantity");
    tempQ = StringEscapeUtils.escapeXml(tempQ);

    // リセットボタンが押されたとき、
    // index.html から移動してきたとき、
    // どちらかのときにsessionを再生成する
    String reset = request.getParameter("reset");
    if (reset.equals("1")) {
      session.invalidate();
      session = request.getSession();
      list.clearList();
    }

    // 変数宣言
    int code = -1;
    int quantity = -1;
    ChooseItem chooseItem = new ChooseItem();

    // 取得した値が数値であればintへ変換
    if (isNumber(tempC)) {
      code = Integer.parseInt(tempC);
    }
    if (isNumber(tempQ)) {
      quantity = Integer.parseInt(tempQ);
    }

    // コードと数量が数値のとき、[ChooseItem]のsetterを呼び出す
    // 情報をjspファイルへ送り返す
    if (code != -1 && quantity != -1) {
      chooseItem.setItemCode(code);
      chooseItem.setQuantity(quantity);

      list.addList(chooseItem);
      request.setAttribute("chooseItem", chooseItem);
    }

    // 合計、税、請求
    double total=0.0, tax=0.0, bill=0.0;
    for (int i=0; i<list.getList().size(); i++) {
      total += list.getList().get(i).getAmount();
    }
    tax = Math.floor(total * 0.08);
    bill = total + tax;
    request.setAttribute("total", total);
    request.setAttribute("tax", tax);
    request.setAttribute("bill", bill);


    session.setAttribute(key, list);
    getServletContext().getRequestDispatcher("/choose_item.jsp")
      .forward(request, response);
  }

  private static boolean isNumber(String value) {
    try {
      Integer.parseInt(value);
      return true;
    } catch (NumberFormatException nfex) {
      return false;
    }
  }
}
