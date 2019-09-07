package controllers.checks;

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
import utils.DBUtil;

/**
 * Servlet implementation class ChecksIndexServlet
 */
@WebServlet("/checks/index")
public class ChecksIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChecksIndexServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        int page;
        try {
            page = Integer.parseInt(request.getParameter("page"));

        } catch(Exception e) {
            page = 1;
        }

        List<Check> checklist = em.createNamedQuery("getMyChecks", Check.class)
                .setParameter("employee", request.getSession().getAttribute("login_employee"))
                .setFirstResult(15 * (page -1))
                .setMaxResults(15)
                .getResultList();

        long checks_count = (long)em.createNamedQuery("getMyChecksCount", Long.class)
                    .setParameter("employee", request.getSession().getAttribute("login_employee"))
                    .getSingleResult();


        em.close();

        request.setAttribute("checklist", checklist);
        request.setAttribute("checks_count", checks_count);
        request.setAttribute("page", page);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/checks/index.jsp");
        rd.forward(request, response);
    }

}
