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

@WebServlet(urlPatterns = {"/home/edit/citizen"})
public class editCitizenController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        DAO dao = new DAO();
        HttpSession session = req.getSession();
        PrintWriter out = resp.getWriter();
        //B1: Kiểm tra quyền truy cập
        Cadres cadres = (Cadres) session.getAttribute("account");
        if(cadres != null && (ModelCadres.checkAccessTimeCadres(cadres.getBeginTime(), cadres.getEndTime()))) {
            JSONObject obj = dao.convertDataBodyToJSON(req.getReader());
            //B2: Kiểm tra đầu vào dữ liệu
            if(obj != null) {
                Citizen citizen = ModelCitizen.handleDataJsonToCitizen(obj, "edit");
                int ordinal = obj.getString("ordinal") != null ? Integer.parseInt(obj.getString("ordinal")) : 0;
                //B3: đọc input và tạo output
                int i = dao.editCitizenToSQL(citizen, ordinal);
                //B4: trả về resp
                if(i == 1) {
                    out.print("okbro!");
                } else {
                    out.print("error");
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
