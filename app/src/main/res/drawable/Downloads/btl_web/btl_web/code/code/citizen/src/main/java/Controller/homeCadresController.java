package Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

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

//nhận username của cán bộ
//trả về thông tin chi tiết của cán bộ
@WebServlet(urlPatterns = {"/home/APIcadres"})
public class homeCadresController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private int defaultCount = 25;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json; charset=UTF-8");
		DAO dao = new DAO();
		HttpSession session = req.getSession();
		PrintWriter out = resp.getWriter();
		//B1: Kiểm tra quyền truy cập
		Cadres cadres = (Cadres) session.getAttribute("account");
		boolean isno = cadres != null;
		if(isno) {
			//B2: kiểm tra dữ liệu
			JSONObject obj = dao.convertDataBodyToJSON(req.getReader());
			if(obj!=null) {
				String username = obj.getString("username");
				//B3: Nhận input, chuyển thành output
				Cadres cadres1 = dao.getCadresByUsername(username);
				boolean checkRank = cadres1.getRank().equals(ModelCadres.searchLowerCadres(cadres));
				//B4: trả response
				if(checkRank) {
					out.print(cadres1.toJSON());
				} else {
					out.print("error");
				}
			} else {
				out.println("error-data");
			}
		} else {
			out.println("error-login");
		}
	}

}
