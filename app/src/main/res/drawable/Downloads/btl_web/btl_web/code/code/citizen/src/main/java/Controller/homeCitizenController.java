package Controller;

import Entity.Cadres;
import Entity.Citizen;
import Model.DAO;
import Model.ModelCadres;
import Model.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

// nhận số cmnd của người dân
//trả về thông tin đầy đủ của người dân đó
@WebServlet(urlPatterns = {"/home/APIcitizen"})
public class homeCitizenController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=UTF-8");
        DAO dao = new DAO();
        HttpSession session = req.getSession();
        PrintWriter out = resp.getWriter();
        //B1: Kiểm tra quyền truy cập
        Cadres cadres = (Cadres) session.getAttribute("account");
        boolean isno = cadres != null;
        if(isno) {
            //B2: kiểm tra dữ liệu
            if(req.getParameter("ordinal")!=null) {
                int ordinal = Integer.parseInt(req.getParameter("ordinal"));
                //B3: Nhận input, chuyển thành output
                Citizen citizen = dao.getCitizenByOdinal(ordinal);
                //B4: trả response
                out.print(citizen.toJSON());
            } else {
                out.print("error-data");
            }
        } else {
            out.print("error-login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
