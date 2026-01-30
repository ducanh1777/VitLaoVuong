package com.vitlaovuong.controller;

import com.vitlaovuong.dal.ReportDAO;
import com.vitlaovuong.model.User;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "AdminReportServlet", urlPatterns = { "/admin/report" })
public class AdminReportServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("account");

        if (user == null || !"ADMIN".equals(user.getRole())) {
            response.sendRedirect("../login");
            return;
        }

        ReportDAO dao = new ReportDAO();
        List<ReportDAO.ReportItem> daily = dao.getDailyReport();
        List<ReportDAO.ReportItem> monthly = dao.getMonthlyReport();
        List<ReportDAO.ReportItem> quarterly = dao.getQuarterlyReport();
        List<ReportDAO.ReportItem> yearly = dao.getYearlyReport();

        request.setAttribute("daily", daily);
        request.setAttribute("monthly", monthly);
        request.setAttribute("quarterly", quarterly);
        request.setAttribute("yearly", yearly);

        request.getRequestDispatcher("/admin_report.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
