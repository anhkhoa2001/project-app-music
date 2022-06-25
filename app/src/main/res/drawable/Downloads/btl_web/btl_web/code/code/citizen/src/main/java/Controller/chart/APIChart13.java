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

@WebServlet(urlPatterns = {"/home/chart/onethree"})
public class APIChart13 extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=UTF-8");
        PrintWriter out = resp.getWriter();
        HttpSession session = req.getSession();
        DAO dao = new DAO();

        Cadres cadres = (Cadres) session.getAttribute("account");
        Map<Integer, Integer> map = ModelCitizen.getCountCitizenInTime(new String[]{cadres.getNumberID()});

        int[] labels = new int[map.size()];
        int[] datas = new int[map.size()];
        int index = 0;
        for(int s:map.keySet()) {
            labels[index] = s;
            datas[index] = map.get(s);
            index++;
        }
        String data = "\"chart\": {\n";
        data += "\t\"chartlabel\": " + Arrays.toString(labels) + ",\n";
        data += "\t\"chartdata\": " + Arrays.toString(datas) + "\n}";

        out.print("{"+ data +"}");
    }
}
