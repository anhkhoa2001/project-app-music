package Controller;

import Entity.Cadres;
import Entity.Citizen;
import Model.DAO;
import Model.ModelCadres;
import Model.ModelCitizen;
import Model.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

//thực hiện việc trả về danh sách cán bộ theo yêu cầu
//yc: số lượng, số trang, từ khóa tìm kiếm
@WebServlet(urlPatterns = {"/home/cadresadmin"})
public class cadresAdminController extends HttpServlet {

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
            List<Cadres> list = new ArrayList<>();
            list = ModelCadres.getAllLowerCadresByCadres(cadres);
            //B2: Kiểm tra dữ liệu đầu vào
            JSONObject obj = dao.convertDataBodyToJSON(req.getReader());
            int count = Integer.parseInt(obj.getString("count"));
            int page = Integer.parseInt(obj.getString("page"));
            String search = obj.getString("search");

            list = (!search.equals("")) ? ModelCadres.filterSearchCadresByUsernameAndManageArea(list, search) : list;
            int size = list.size();
            int countPage = list.size()/count + 1;
            int countleft = 1;
            int countright = count;
            if(page < countPage) {
                countleft = (page-1)*count;
                countright = page*count;
            } else if(page == countPage) {
                countleft = (page-1)*count ;
                countright = list.size();
            }
            list = ModelCadres.filterCadresByPage(countleft, countright, list);
            String data = "{\n"
                    + "\t\"countPage\": " + countPage + ",\n"
                    + "\t\"countleft\": " + countleft + ",\n"
                    + "\t\"countright\": " + countright + ",\n"
                    + "\t\"size\": " + size + ",\n"
                    + "\t\"dataresponse\": " + ModelCadres.convertListToJSON(list) + "\n"
                    + "}";
            out.print(data);

        } else {
            out.print("error");
        }
    }
}
