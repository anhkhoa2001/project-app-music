package Controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Entity.Cadres;
import Model.DAO;
import Model.ModelCadres;
import Model.json.JSONObject;

// thực hiện việc xác thực quá trình đăng nhập
@WebServlet(urlPatterns = {"/authorication"})
public class authoricationController extends HttpServlet {
	private DAO dao = new DAO();
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html; charset=UTF-8");
		PrintWriter out = resp.getWriter();
		//B1: Kiểm tra hợp thức dữ liệu đầu vào
		JSONObject obj = dao.convertDataBodyToJSON(req.getReader());
		//B2: Đọc dữ liệu đầu vào và chuyển thành output
		if(obj!=null) {
			String username = obj.getString("username");
			String password = obj.getString("password");
			Cadres cadres = dao.getCadresByUsername(username);
			HttpSession session = req.getSession();
			boolean isno = cadres !=null && ModelCadres.hashPassword(password).equals(cadres.getPassword());

			// B3: tạo và trả response
			if(isno) {
				session.setAttribute("account", cadres);
				session.setMaxInactiveInterval(1000);
				if(cadres.getRank().equals("B2")) {
					System.out.println("B2");
					out.print("/citizen_war_exploded/homeb2");
				} else {
					System.out.println("B1");
					out.print("/citizen_war_exploded/home");
				}
			} else {
				out.print("error");
			}
		} else {
			out.print("error");
		}
	}

}
