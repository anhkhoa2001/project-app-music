package Controller;

import Entity.Cadres;
import Entity.Citizen;
import Model.DAO;
import Model.ModelCadres;
import Model.ModelCitizen;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(urlPatterns = {"/homeb2"})
public class homeB2Controller extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter out = resp.getWriter();
        HttpSession session = req.getSession();
        DAO dao = new DAO();
        int count = 25;
        //B1: Kiểm tra quyền truy cập
        Cadres cadres = (Cadres) session.getAttribute("account");
        boolean isno = cadres != null;
        //B4: Tạo và trả resp
        if(isno) {


            req.setAttribute("listvillage", ModelCadres.getAllVillageByCadres(cadres, "villageID"));
            req.getRequestDispatcher("HomeB2.jsp").forward(req, resp);
        } else {
            session.removeAttribute("account");
            out.println("<script type=\"text/javascript\">");
            out.println("alert('Vui lòng đăng nhập');");
            out.println("location='/citizen_war_exploded';");
            out.println("</script>");
        }
    }
}
