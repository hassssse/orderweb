package order;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "Delete", urlPatterns = {"/Delete"})
public class DeleteController extends HttpServlet {
	protected void doPost(HttpServletRequest request,HttpServletResponse response)
	throws ServletException, IOException {
	  response.setContentType("text/html; charset=UTF-8");
		String no = request.getParameter("No");
		no = new String(no.getBytes("8859_1"), "UTF-8");

		int noint = Integer.parseInt(no);

		//削除を実行
		DeleteModel.DoDelete(noint);

		response.sendRedirect("./deleted.html");
	}
}
