package controllers.reports;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Check;
import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class ReportsShowServlet
 */
@WebServlet("/reports/show")
public class ReportsShowServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportsShowServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        Report r = em.find(Report.class, Integer.parseInt(request.getParameter("id")));

        List<Check> checks = em.createNamedQuery("getChecksOnReport", Check.class)
                .setParameter("report", r)
                .getResultList();

        List<Check> check_flag = em.createNamedQuery("getMyCheckOnReport", Check.class)
                .setParameter("report", r)
                .setParameter("employee", request.getSession().getAttribute("login_employee"))
                .getResultList();

        int check_flag_count = check_flag.size();


        long checks_count = (long)em.createNamedQuery("getReportChecksCount", Long.class)
                    .setParameter("report", r)
                    .getSingleResult();

        em.close();

        request.setAttribute("report", r);
        request.setAttribute("checks", checks);
        request.setAttribute("checks_count", checks_count);
        request.setAttribute("check_flag_count", check_flag_count);

        request.setAttribute("_token", request.getSession().getId());

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reports/show.jsp");
        rd.forward(request, response);
    }

}
