package order;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name="Register", urlPatterns={"/Register"})
public class InsertController extends HttpServlet {
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
      response.sendRedirect("/index.html");
    }
    
    // requestから値取得
    int year = Integer.parseInt(request.getParameter("year"));
    int month = Integer.parseInt(request.getParameter("month"));
    int day = Integer.parseInt(request.getParameter("day"));
    int customNumber = Integer.parseInt(request.getParameter("custom"));
    String salesNumber = request.getParameter("sales");
    double total = Double.parseDouble(request.getParameter("total"));
    double tax = Double.parseDouble(request.getParameter("tax"));
    double bill = Double.parseDouble(request.getParameter("bill"));
    
    Calendar cal = Calendar.getInstance();
    cal.set(Calendar.YEAR, year);
    cal.set(Calendar.MONTH, month-1);
    cal.set(Calendar.DATE, day);
    
    InsertProcess ip = new InsertProcess();
    ip.insertOrder(cal, customNumber, salesNumber, list, total, tax, bill);
    
    response.sendRedirect("./registered.html");
  }
}
