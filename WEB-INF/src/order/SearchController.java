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
// �ȉ����C���|�[�g����i�T�j�^�C�W���O�̂��߁j


@WebServlet(name = "Search", urlPatterns = {"/Search"})
public class SearchController extends HttpServlet {
	private Connection con;
	private PreparedStatement stmt;
	private ResultSet rslt;
	private ArrayList<SelectOrderResult> orderResultList = new ArrayList<SelectOrderResult>();
	private ArrayList<SelectOrderItemResult> orderItemResultList = new ArrayList<SelectOrderItemResult>();


	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		response.setContentType("text/html; charset=Windows-31J");

		// �p�����[�^�i���̓f�[�^�j�擾
		String searchcustomName = request.getParameter("name");
		searchcustomName = new String(searchcustomName.getBytes("8859_1"), "Windows-31J");

		//�ڋq���̈ꕔ���猟��
		//SQL�����s
		SearchModel.DoSql(searchcustomName);


		request.setAttribute("orderResultList", orderResultList);
		request.getRequestDispatcher("/SearchResults.jsp").forward(request, response);

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
