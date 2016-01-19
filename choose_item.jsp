<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="order.*"%>
<%@page import="java.util.*"%>
<%@page import="org.apache.commons.lang3.*" %>

<%
  // 選択した商品を表示するための準備
  ChooseItemList list = (ChooseItemList) session.getAttribute("list");

  // 商品一覧を表示するための準備
  SelectProcess select = new SelectProcess();
  select.selectItemListAll();
  ArrayList<SelectItemResult> itemList = select.getItemResultList();

  // 合計金額
  Double total = (Double) request.getAttribute("total");
  if (total == null) {
    total = 0.0;
  }

  // 商品税額
  Double tax = (Double) request.getAttribute("tax");
  if (tax == null) {
    tax = 0.0;
  }

  // 請求金額
  Double bill = (Double) request.getAttribute("bill");
  if (bill == null) {
    bill = 0.0;
  }
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>受注登録</title>
</head>
<body>

<h1>受注商品選択</h1>
<form action="./ChooseItem" method="post">
  <input type="hidden" name="reset" value="0" />
  <p>
    商品コード
    <input type="text" name="code" />
    数量
    <input type="text" name="quantity" />
    <input type="submit" value="商品選択" />
  </p>
</form>
<form action="./choose_date.jsp" method="post">
  <input type="hidden" name="total" value="<%= total  %>" />
  <input type="hidden" name="tax" value="<%= tax %>" />
  <input type="hidden" name="bill" value="<%= bill %>" />
  <p><input type="submit" value="商品選択確定" /></p>
</form>
<form action="./ChooseItem" method="post">
  <input type="hidden" name="reset" value="1" />
  <p><input type="submit" value="リセット" /></p>
</form>
<input type="button" value="戻る" onclick="location.href='./index.html'" />


<!-- 受注商品 -->
<table border="1">
  <caption>受注商品一覧</caption>
  <tr>
    <th>商品コード</th>
    <th>商品名</th>
    <th>単価</th>
    <th>数量</th>
    <th>金額</th>
    <th>税込金額</th>
  </tr>
  <%
    // 選択した商品を表示
    if (list != null) {
      ArrayList<ChooseItem> ciList = list.getList();
      for (int i=0; i<ciList.size(); i++) {
        ChooseItem now = ciList.get(i);
        int code = now.getItemCode();
        String name = now.getItemName();
        int price = now.getItemPrice();
        int quantity = now.getQuantity();
        double amount = now.getAmount();
        double inTax = now.getInTax();

        out.println(String.format(
          "<tr><td>%s</td><td>%s</td><td>%s</td><td>%s</td>"
          + "<td>%s</td><td>%s</td></tr>",
          code, name, price, quantity, amount, inTax));
      }
    }
  %>
</table>
<p>
  合計金額 : <%= total %>
  商品税額 : <%= tax %>
  請求金額 : <%= bill %>
</p>


<hr />


<!-- 商品一覧 -->
<table border="1">
  <caption>商品一覧</caption>
  <tr>
    <th>商品コード</th>
    <th>商品名</th>
    <th>単価</th>
  </tr>
  <%
    // 商品一覧を表示
    for (int i=0; i<itemList.size(); i++) {
      out.println(String.format(
        "<tr><td>%s</td><td>%s</td><td>%s</td></tr>",
        itemList.get(i).getItemCode(),
        itemList.get(i).getItemName(),
        (double)itemList.get(i).getItemPrice()));
    }
  %>
</table>

</body>
</html>