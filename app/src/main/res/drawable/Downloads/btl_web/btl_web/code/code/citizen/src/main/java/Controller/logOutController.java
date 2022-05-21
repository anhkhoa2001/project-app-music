package Controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

// thực hiện việc đăng xuất tài khoản cán bộ
@WebServlet(urlPatterns = {"/logout"})
public class logOutController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html; charset=UTF-8");
		HttpSession session = req.getSession();
		//xóa session rồi trả về trang login
		session.removeAttribute("account");
		resp.sendRedirect("/citizen_war_exploded");
	}
}
