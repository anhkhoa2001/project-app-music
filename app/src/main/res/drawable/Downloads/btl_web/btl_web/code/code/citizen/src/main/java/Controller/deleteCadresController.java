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

//thực hiện chức năng xóa tài khoản cán bộ
@WebServlet(urlPatterns = {"/home/remove/cadres"})
public class deleteCadresController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        DAO dao = new DAO();
        HttpSession session = req.getSession();
        PrintWriter out = resp.getWriter();
        //B1: Kiểm tra quyền truy cập
        Cadres cadres = (Cadres) session.getAttribute("account");
        if(cadres != null && (ModelCadres.checkAccessTimeCadres(cadres.getBeginTime(), cadres.getEndTime()))) {
            String username = req.getParameter("username");
            //B2: Kiểm tra đầu vào dữ liệu
            if(username != null) {
                List<Cadres> listLowerCadres = dao.getAllCadresLikeUsername(username);
                int remove = dao.removeCadresToSQL(username);
                //B4: trả về resp
                if(remove == listLowerCadres.size()) {
                    out.print("okbro!");
                } else {
                    out.print("error");
                }
            } else {
                out.print("error-data");
            }
        } else {
            out.print("error-login");
        }
    }
}
