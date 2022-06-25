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

//thực hiện việc thêm cán bộ
@WebServlet(urlPatterns = {"/home/add/cadres"})
public class addCadresController extends HttpServlet {
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
                System.out.println(obj.toString());
                String user = obj.getString("user");
                String pass = obj.getString("pass");
                String repass = obj.getString("repass");
                String managearea = obj.getString("managearea");
                String postcode = obj.getString("postcode");
                String begin = obj.getJSONObject("date").getString("begin");
                String end = obj.getJSONObject("date").getString("end");//yyyy-mm-dd hh:mm:ss
                Cadres cadres1 = new Cadres(user, pass, postcode, begin, end);
                cadres1.setRank(ModelCadres.searchLowerCadres(cadres));

                if(ModelCadres.checkInfoCadres(cadres1, cadres, managearea, repass)) {
                    //B3: Nhận dữ liệu và tạo output
                    cadres1.setRank(ModelCadres.searchLowerCadres(cadres));
                    cadres1.setPassword(ModelCadres.hashPassword(pass));
                    cadres1.setCreateTime(DAO.formatter.format(DAO.currenTime));
                    boolean grant = ModelCadres.checkAccessTimeCadres(begin, end);
                    cadres1.setGrant(grant);
                    int put = dao.putCadresToSQL(cadres1);
                    if(put == 1) {
                        out.print("okbro!");
                    } else {
                        out.print("error");
                    }
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
