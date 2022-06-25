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
import Entity.Citizen;
import Model.DAO;
import Model.ModelCadres;
import Model.ModelCitizen;
import Model.json.JSONObject;

//có chức năg thêm người dân
@WebServlet(urlPatterns = {"/add/citizen"})
public class addCitizenController extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html; charset=UTF-8");
		DAO dao = new DAO();
		HttpSession session = req.getSession();
		PrintWriter out = resp.getWriter();
		Cadres cadres = (Cadres) session.getAttribute("account");
		if(cadres != null && (ModelCadres.checkAccessTimeCadres(cadres.getBeginTime(), cadres.getEndTime()))) {
			JSONObject obj = dao.convertDataBodyToJSON(req.getReader());
			//B2: Kiểm tra đầu vào dữ liệu
			if(obj != null) {
				//B3: Đọc dữ liệu và tạo output
				System.out.println(obj.toString());
				Citizen citizen = ModelCitizen.handleDataJsonToCitizen(obj, "add");
				//B4: Trả về response
				if(citizen!=null) {
					int i = dao.addCitizenToSQL(citizen);
					if(i==1) {
						out.print("okbro!");
					} else {
						out.print("error1");
					}
				} else {
					out.print("error2");
				}
			} else {
				out.print("error-data");
			}
		} else {
			if(cadres == null) {
				out.print("error-login");
			} else {
				out.print("error-grant");
			}
		}
	}
	
}
