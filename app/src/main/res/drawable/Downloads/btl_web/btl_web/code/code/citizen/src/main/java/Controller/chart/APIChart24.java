package Controller.chart;

import Entity.Cadres;
import Model.DAO;
import Model.ModelCitizen;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Map;

@WebServlet(urlPatterns = {"/home/chart/twofour"})
public class APIChart24 extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=UTF-8");
        PrintWriter out = resp.getWriter();
        HttpSession session = req.getSession();
        DAO dao = new DAO();

        Cadres cadres = (Cadres) session.getAttribute("account");
        Map<String, Integer> map = ModelCitizen.getRadioCitizenByArea(new String[]{cadres.getNumberID()});


        String data = ModelCitizen.convertMapToJson(map, "chart");
        out.print("{"+ data +"}");
    }
}