package com.st.studentregistration.servlets;

import com.st.studentregistration.models.auth.Auth;
import com.st.studentregistration.models.constants.UserCredentialFlags;
import com.st.studentregistration.models.db.DAOImpl;
import com.st.studentregistration.models.message.Message;
import com.st.studentregistration.models.users.Student;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "Registration", value = "/auth/registration")
public class Registration extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (Auth.getInstance().isUserLoggedIn(request)) {
            RequestDispatcher rd = request.getRequestDispatcher("/views/student_registration.jsp");
            rd.forward(request, response);
        } else {
            request.setAttribute("msg", new Message("You have logged out!", "please login first.", "danger"));
            request.getRequestDispatcher("/auth/login").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String city = request.getParameter("city");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");
        if (Auth.getInstance().isUserLoggedIn(request)) {
            DAOImpl dao = new DAOImpl();
            dao.connectDatabase();
            if(dao.isEmailAlreadyRegistered(email)!= UserCredentialFlags.EMAIL_ALREADY_REGISTERED){
                int rowsChanged = dao.registerStudent(new Student(name, city, email, phone), password);
                dao.closeConnection();
                if (rowsChanged > 0) {
                    request.setAttribute("msg", new Message("Registration Success!", "", "success"));
                } else {
                    request.setAttribute("msg", new Message("Registration Failed!", "An error Occurred while registering user", "danger"));
                }
            }else {
                request.setAttribute("msg", new Message("Email Already registered!", "please use another email", "danger"));

            }
            RequestDispatcher rd = request.getRequestDispatcher("/views/student_registration.jsp");
            rd.forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/auth/login");
        }
    }
}
