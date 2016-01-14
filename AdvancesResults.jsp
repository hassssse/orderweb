<%@page contentType="text/html" pageEncoding ="UTF-8" %>
<%@page import="java.sql.*"%>
<%@page import="java.util.ArrayList" %>
<%@page import="order.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>受注詳細情報</title>
	</head>
	<body>
		<%
		ArrayList<SelectOrderResult> ResultList = SearchModel.getOrderResultList();
		ArrayList<SelectOrderItemResult> ItemList = AdvancesModel.getOrderItemResultList();

		int num = (int)request.getAttribute("noint");
		%>

		<h1>受注詳細情報</h1>
		<input type="button" value="戻る" onclick="location.href='./index.html'" />

		<table border=1>
			<tr><th>受注No</th><th>受注日付</th><th>顧客コード</th><th>顧客名</th><th>担当者コード</th><th>担当者名</th><th>合計金額</th><th>消費税額</th><th>請求金額</th></tr>
			<%
			out.println(
				"<tr><td>"+ResultList.get(num).getOrderNumber()+
				"</td><td>"+ResultList.get(num).getOrderDate()+
				"</td><td>"+ResultList.get(num).getCustomCode()+
				"</td><td>"+ResultList.get(num).getCustomName()+
				"</td><td>"+ResultList.get(num).getSalesCode()+
				"</td><td>"+ResultList.get(num).getSalesName()+
				"</td><td>"+ResultList.get(num).getTotalAmount()+
				"</td><td>"+ResultList.get(num).getSalesTax()+
				"</td><td>"+ResultList.get(num).getBill()+
				"</td></tr>"
			);
			%>
		</table>

	</body>
</html>

