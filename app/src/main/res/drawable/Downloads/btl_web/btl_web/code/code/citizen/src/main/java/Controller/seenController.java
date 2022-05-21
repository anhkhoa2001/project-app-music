package Controller;

import Entity.Cadres;
import Entity.Notification;
import Model.DAO;
import Model.ModelCadres;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(urlPatterns = {"/home/noti/seen"})
public class seenController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        DAO dao = new DAO();
        HttpSession session = req.getSession();
        PrintWriter out = resp.getWriter();
        Cadres cadres = (Cadres) session.getAttribute("account");
        if(cadres != null && (ModelCadres.checkAccessTimeCadres(cadres.getBeginTime(), cadres.getEndTime()))) {
            String ID = req.getParameter("id");
            //B2: Kiểm tra đầu vào dữ liệu
            if(ID != null) {
                //B3: Đọc dữ liệu và tạo output
                List<Notification> list = dao.getAllNotificationByID(ID);
                int rs = 0;
                for(Notification notification : list) {
                    if(!notification.isStatus()) {
                        rs += dao.updateNotification(notification.getOrdinal());
                    }
                }
                System.out.println(rs);
                //B4: Trả về response
                if(rs == list.size()) {
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
