package com.st.studentregistration.servlets;

import com.st.studentregistration.models.constants.UserCredentialFlags;
import com.st.studentregistration.models.db.DAOImpl;
import com.st.studentregistration.models.message.Message;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "Login", value = "/auth/login")
public class Login extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("/views/login.jsp");
        rd.forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        DAOImpl dao = new DAOImpl();
        dao.connectDatabase();
        int res = dao.verifyLogin(email, password);
        RequestDispatcher rd = request.getRequestDispatcher("/views/login.jsp");
        if (res == UserCredentialFlags.USER_CREDENTIAL_MATCHED) {
            HttpSession session = request.getSession(true);
            session.setAttribute("currentUser", dao.getAdmin(email));
            Cookie cookie = new Cookie("JSESSIONID", session.getId());
            cookie.setMaxAge(Integer.MAX_VALUE);
            cookie.setPath(request.getContextPath()+"/");
            response.addCookie(cookie);
            dao.closeConnection();
            response.sendRedirect("/");
        } else if (res == UserCredentialFlags.WRONG_PASSWORD) {
            dao.connectDatabase();
            request.setAttribute("msg", new Message("Invalid Password!","please check your password.","danger"));
            rd.forward(request, response);
        } else if (res == UserCredentialFlags.USER_NOT_EXISTS) {
            dao.closeConnection();
            request.setAttribute("msg", new Message("Email not registered!","please register first.","warning"));
            rd.forward(request, response);
        } else {
            dao.closeConnection();
            request.setAttribute("msg", new Message("Unexpected Error","Something goes wrong.","danger"));
            rd.forward(request, response);
        }
    }
}
