package Controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Entity.Cadres;

// trả về giao diện đăng nhập rồi hay chưa
@WebServlet(urlPatterns = {""})
public class loginController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html; charset=UTF-8");
		HttpSession session = req.getSession();
		//B1: kiểm tra quyền truy cập
		Cadres cadres = (Cadres) session.getAttribute("account");

		//B2 tạo response
		// tạo response
		// cadres = null -> chưa đăng nhập ->  trả về Login.jsp
		// cadres != null -> trả về trang home
		if(cadres == null) {
			req.getRequestDispatcher("/Login.jsp").forward(req, resp);
		} else {
			if(cadres.getRank().equals("B2")) {
				resp.sendRedirect("/citizen_war_exploded/homeb2");
			} else {
				resp.sendRedirect("/citizen_war_exploded/home");
			}
		}
	}
	
}
