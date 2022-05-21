package Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Entity.Cadres;
import Entity.CommuneB1;
import Model.DAO;
import Model.ModelCadres;
import Model.json.JSONObject;

//Trả về tên xã, phường theo ID của quận huyện
@WebServlet(urlPatterns = {"/home/APIdistrict"})
public class homeDistrictController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json; charset=UTF-8");
		HttpSession session = req.getSession();
		PrintWriter out = resp.getWriter();
		DAO dao = new DAO();
		Cadres cadres = (Cadres) session.getAttribute("account");
		//B1: Kiểm tra quyền truy cập
		if(cadres != null) {
			JSONObject obj = dao.convertDataBodyToJSON(req.getReader());
			//B2: Kiểm tra dữ liệu đầu vào
			if(obj!=null) {
				//B3: Xử lí input và tạo output
				String districtID = obj.getString("districtID");
				int index = 0;
				List<CommuneB1> list = dao.getAllCommuneByType(districtID, "districtID");
				String[] dataJSON = new String[list.size()];
				for (CommuneB1 d : list) {
					dataJSON[index] = d.toJSON();
					index++;
				}
				//B4: Trả về dữ liệu
				out.print(Arrays.toString(dataJSON));
			} else {
				out.print("error");
			}
		} else {
			out.print("error");
		}
	}

}
