package Controller;

import Entity.Cadres;
import Model.DAO;
import Model.ModelCadres;
import Model.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(urlPatterns = {"/home/grant/cadres"})
public class grantCadresController extends HttpServlet {

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
                String username = obj.getString("username");
                String begin = obj.getJSONObject("time").getString("begin");
                String end = obj.getJSONObject("time").getString("end");//yyyy-mm-dd
                //B3: đọc input và tạo output
                List<Cadres> listLowerCadres = dao.getAllCadresLikeUsername(username);
                int i = dao.editGrantCadres(begin, end, username);
                //B4: trả về resp
                if(i == listLowerCadres.size()) {
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
