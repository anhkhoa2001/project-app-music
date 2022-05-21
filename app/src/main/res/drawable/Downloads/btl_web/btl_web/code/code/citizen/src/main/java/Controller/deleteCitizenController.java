package Controller;

import Entity.Cadres;
import Model.DAO;
import Model.ModelCadres;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(urlPatterns = {"/home/remove/citizen"})
public class deleteCitizenController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        DAO dao = new DAO();
        HttpSession session = req.getSession();
        PrintWriter out = resp.getWriter();
        //B1: Kiểm tra quyền truy cập
        Cadres cadres = (Cadres) session.getAttribute("account");
        if(cadres != null && (ModelCadres.checkAccessTimeCadres(cadres.getBeginTime(), cadres.getEndTime()))) {
            //B2: Kiểm tra đầu vào dữ liệu
            if(req.getParameter("ordinal") != null) {
                int ordinal = Integer.parseInt(req.getParameter("ordinal"));
                int remove = dao.removeCitizenByOrdinal(ordinal);
                //B4: trả về resp
                if(remove == 1) {
                    out.print("okbro!");
                } else {
                    out.print("error");
                }
            } else {
                out.print("error");
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
