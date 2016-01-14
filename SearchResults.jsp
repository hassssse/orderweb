<%@page import="order.SelectOrderResult"%>
<%@page contentType="text/html" pageEncoding ="UTF-8" %>
<%@page import="java.sql.*"%>
<%@page import="java.util.ArrayList" %>
<%@page import="order.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>検索結果</title>
	</head>
	<body>
		<%
		ArrayList<SelectOrderResult> ResultList = SearchModel.getOrderResultList();
		%>

		<h1>検索結果</h1>
		<input type="button" value="戻る" onclick="location.href='./index.html'" />
		<form action="./Advances" method="post">
		受注No.
		<select name="No">
			<%
			for(int i=0;i<ResultList.size();i++){
				out.println(
					"<option value="+i+">"+
				ResultList.get(i).getOrderNumber()+"</option>"
				);
			}
			%>
		</select>
		<input type="submit" value="詳細表示" />
		</form>


		<table border=1>
			<tr><th>受注No</th><th>受注日付</th><th>顧客コード</th><th>顧客名</th><th>担当者コード</th><th>担当者名</th><th>合計金額</th><th>消費税額</th><th>請求金額</th></tr>
			<%
			for(int i=0;i<ResultList.size();i++){
				out.println(
					"<tr><td>"+ResultList.get(i).getOrderNumber()+
					"</td><td>"+ResultList.get(i).getOrderDate()+
					"</td><td>"+ResultList.get(i).getCustomCode()+
					"</td><td>"+ResultList.get(i).getCustomName()+
					"</td><td>"+ResultList.get(i).getSalesCode()+
					"</td><td>"+ResultList.get(i).getSalesName()+
					"</td><td>"+ResultList.get(i).getTotalAmount()+
					"</td><td>"+ResultList.get(i).getSalesTax()+
					"</td><td>"+ResultList.get(i).getBill()+
					"</td></tr>"
				);
			}
			%>
		</table>


		<!--テーブルを作って商品一覧を表示することで完成する  -->

	</body>
</html>

