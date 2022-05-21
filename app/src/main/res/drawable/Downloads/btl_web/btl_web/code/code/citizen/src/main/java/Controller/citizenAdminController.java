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

//thực hiện việc trả về danh sách người dân trên cả nước theo yêu cầu
@WebServlet(urlPatterns = {"/home/citizenadmin"})
public class citizenAdminController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=UTF-8");
        DAO dao = new DAO();
        HttpSession session = req.getSession();
        PrintWriter out = resp.getWriter();
        int i = 0;
        //B1: Kiểm tra quyền truy cập
        Cadres cadres = (Cadres) session.getAttribute("account");
        boolean isno = cadres != null;
        if(isno) {
            List<Citizen> list = new ArrayList<>();
            list = ModelCitizen.filterCitizenByCadres(new String[] {cadres.getNumberID()});
            //B2: Kiểm tra dữ liệu đầu vào
            int count = Integer.parseInt(req.getParameter("count"));
            int page = Integer.parseInt(req.getParameter("page"));
            String[] IDs = req.getParameterValues("ID");
            String search = req.getParameter("search");

            //Trả về response
            String data = ModelCitizen.filterGeneralCitizen(IDs, search, count, page, list);
            out.print(data);
        } else {
            out.print("error");
        }
    }
}
