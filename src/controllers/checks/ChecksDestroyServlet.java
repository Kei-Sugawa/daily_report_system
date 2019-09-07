package controllers.checks;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Check;
import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class ChecksDestroyServlet
 */
@WebServlet("/checks/destroy")
public class ChecksDestroyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChecksDestroyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _token = (String)request.getParameter("_token");
        if(_token != null && _token.equals(request.getSession().getId())) {
            EntityManager em = DBUtil.createEntityManager();

            Report r = em.find(Report.class, Integer.parseInt(request.getParameter("id")));
            Check c = em.createNamedQuery("getMyCheckOnReport", Check.class)
                    .setParameter("report", r)
                    .setParameter("employee", request.getSession().getAttribute("login_employee"))
                    .setMaxResults(1)
                    .getSingleResult();

            em.getTransaction().begin();
            em.remove(c);
            em.getTransaction().commit();
            em.close();

            response.sendRedirect(request.getContextPath() + "/reports/show?id=" + r.getId());

        }
    }

}
