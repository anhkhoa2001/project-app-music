package Controller;

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

import Entity.*;
import Model.DAO;
import Model.ModelCadres;
import Model.json.JSONObject;

//Trả về tên quận huyện theo ID của thành phố
@WebServlet(urlPatterns = {"/home/APIcity"})
public class homeCityController extends HttpServlet {
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
			//B2: Kiểm tra kiểu dữ liệu đầu vào
			if(obj!=null) {
				String type = obj.getString("type");
				String[] IDs = new String[obj.getJSONArray("id").length()];
				for(int i=0; i< obj.getJSONArray("id").length(); i++) {
					IDs[i] = obj.getJSONArray("id").getString(i);
				}
				//B3: Nhận đầu vào và xử lí ra output
				switch (type) {
					case "cityID":
						int indexCity = 0;
						List<CityA2> listCity = dao.getAllCity();
						String[] dataJSONCity = new String[listCity.size()];
						for (CityA2 c : listCity) {
							dataJSONCity[indexCity] = c.toJSON();
							indexCity++;
						}
						//B4: Trả về dữ liệu
						out.print(Arrays.toString(dataJSONCity));
						break;
					case "districtID":
						int indexDistrict = 0;
						List<DistrictA3> listDistrict = dao.getAllDistrictByTypeAndListID(IDs, "cityID");
						String[] dataJSONDistrict = new String[listDistrict.size()];
						for (DistrictA3 c : listDistrict) {
							dataJSONDistrict[indexDistrict] = c.toJSON();
							indexDistrict++;
						}
						//B4: Trả về dữ liệu
						out.print(Arrays.toString(dataJSONDistrict));
						break;
					case "communeID":
						int indexCommune = 0;
						List<CommuneB1> listCommune = dao.getAllCommuneByTypeAndListID(IDs, "districtID");
						String[] dataJSONCommune = new String[listCommune.size()];
						for (DistrictA3 c : listCommune) {
							dataJSONCommune[indexCommune] = c.toJSON();
							indexCommune++;
						}
						//B4: Trả về dữ liệu
						out.print(Arrays.toString(dataJSONCommune));
						break;
					case "villageID":
						int indexVillage = 0;
						List<VillageB2> listVillage = dao.getAllVillageByTypeAndListID(IDs, "communeID");
						String[] dataJSONVillage = new String[listVillage.size()];
						for (DistrictA3 c : listVillage) {
							dataJSONVillage[indexVillage] = c.toJSON();
							indexVillage++;
						}
						//B4: Trả về dữ liệu
						out.print(Arrays.toString(dataJSONVillage));
						break;
				}
			} else {
				out.print("error");
			}
		} else {
			out.print("error");
		}
		
	}

}
