package order;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "Advances", urlPatterns = {"/Advances"})


public class AdvancesController extends HttpServlet {
	private Connection con;
	private PreparedStatement stmt;
	private ResultSet rslt;
	private ArrayList<SelectOrderItemResult>orderItemResultList = new ArrayList<SelectOrderItemResult>();

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		response.setContentType("text/html; charset=Windows-31J");

		// パラメータ（入力データ）取得
		String no = request.getParameter("No");
		no = new String(no.getBytes("8859_1"), "Windows-31J");

		int noint = Integer.parseInt(no);

		//顧客名の一部から検索
		//SQLを実行
		AdvancesModel.DoSql(noint);


		request.setAttribute("orderItemResultList", orderItemResultList);
		request.setAttribute("noint", noint);
		request.getRequestDispatcher("/AdvancesResults.jsp").forward(request, response);

		close();
	}

	private void close() {
		try {
			if (rslt != null) {
				rslt.close();
			}
		} catch (SQLException e) {
		}
		try {
			if (stmt != null) {
				stmt.close();
			}
		} catch (SQLException e) {
		}
		try {
			if (con != null) {
				con.close();
			}
		} catch (SQLException e) {
		}
	}
}

