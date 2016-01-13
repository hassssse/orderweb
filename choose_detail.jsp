<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="order.*"%>
<%@page import="java.util.*"%>
<%@page import="org.apache.commons.lang3.*" %>

<%
  /* formで以下の値を飛ばす
   * sessionで保持されているlist
   * year, month, day (calenderで利用する)
   * custom, sales それぞれのコード
   * total, tax, bill
   */


  // 選択した商品を表示するための準備
  ChooseItemList list = (ChooseItemList) session.getAttribute("list");

  // 合計金額
  double total = Double.parseDouble(request.getParameter("total"));

  // 商品税額
  double tax = Double.parseDouble(request.getParameter("tax"));

  // 請求金額
  double bill = Double.parseDouble(request.getParameter("bill"));

  // 顧客の一覧
  SelectProcess select = new SelectProcess();
  select.selectCustomListAll();
  ArrayList<SelectCustomResult> customList = select.getCustomResultList();

  // 担当者の一覧
  select = new SelectProcess();
  select.selectSalesListAll();
  ArrayList<SelectSalesResult> salesList = select.getSalesResultList();
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<script type="text/javascript">
<!--
// 年月に応じて日付を変更する関数setDateForm
function setDateForm(selectYear,selectMonth,selectDay){
  year = document.getElementById(selectYear).value;
  month = parseInt(document.getElementById(selectMonth).value);
  
  selMonth = document.getElementById(selectMonth);
  selDay = document.getElementById(selectDay);
  
  // 日付optionの初期化
  if(selDay.options[28] == null){
    selDay.appendChild(document.createElement("option"));
    selDay.options[28].value = "29";
    selDay.options[28].text = "29";
  }
  if(selDay.options[29] == null){
    selDay.appendChild(document.createElement("option"));
    selDay.options[29].value = "30";
    selDay.options[29].text = "30";
  }
  if(selDay.options[30] == null){
    selDay.appendChild(document.createElement("option"));
    selDay.options[30].value = "31";
    selDay.options[30].text = "31";
  }
  
  // 月に応じて日付optionのノードを削除
  if(month == 2){
    selDay.removeChild(selDay.options[30]);
    selDay.removeChild(selDay.options[29]);
    // 閏年ではない場合
    if((year % 400 == 0 || (year % 100 != 0 && year % 4 == 0))){
      selDay.removeChild(selDay.options[28]);
    }
  }else if(month == 4 || month == 6 || month == 9 || month == 11){
    selDay.removeChild(selDay.options[30]);
  }
}
-->
</script>
<title>受注登録</title>
</head>
<body>

<h1>受注登録</h1>
受注年月日
<form action="./Register" method="post">
  <input type="hidden" name="total" value="<%= total  %>" />
  <input type="hidden" name="tax" value="<%= tax %>" />
  <input type="hidden" name="bill" value="<%= bill %>" />
  <select name="year" id="year" onchange="setDateForm('year','month','day');">
  <%
    for (int i=2000; i<=2016; i++) {
      out.println("<option value=\"" + i + "\">" +  i + "</option>");
    }
  %>
  </select>
  年

  <select name="month" id="month" onchange="setDateForm('year','month','day');">
  <%
    for (int i=1; i<=12; i++) {
      out.println("<option value=\"" + i + "\">" +  i + "</option>");
    }
  %>
  </select>
  月

  <select name="day" id="day">
  <%
    for (int i=1; i<=31; i++) {
      out.println("<option value=\"" + i + "\">" +  i + "</option>");
    }
  %>
  </select>
  日

  　顧客
  <select name="custom">
  <%
    for (int i=0; i<customList.size(); i++) {
      SelectCustomResult scr = customList.get(i);
      out.println("<option value=\"" + scr.getCustomCode() + "\">"
        + scr.getCustomName() + "(" + scr.getCustomCode() + ")</option>");
    }
  %>
  </select>

  　担当者
  <select name="sales">
  <%
    for (int i=0; i<salesList.size(); i++) {
      SelectSalesResult ssr = salesList.get(i);
      out.println("<option value=\"" + ssr.getSalesCode() + "\">"
        + ssr.getSalesName() + "(" + ssr.getSalesCode() + ")</option>");
    }
  %>
  </select>
  <p><input type="submit" value="登録" /></p>
</form>


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

<input type="button" value="戻る" onclick="location.href='./index.html'" />

</body>
</html>