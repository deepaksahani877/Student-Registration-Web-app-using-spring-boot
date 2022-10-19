package com.st.studentregistration.servlets;

import com.st.studentregistration.models.constants.UserCredentialFlags;
import com.st.studentregistration.models.db.DAOImpl;
import com.st.studentregistration.models.message.Message;
import com.st.studentregistration.models.users.Student;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;


@WebServlet(name = "UpdateData",value = "/update")
public class UpdateData extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String city = request.getParameter("city");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String currentEmail = request.getParameter("currentEmail");

        DAOImpl dao = new DAOImpl();
        dao.connectDatabase();
        HttpSession session= request.getSession(false);
        if(dao.isEmailAlreadyRegistered(email)!= UserCredentialFlags.EMAIL_ALREADY_REGISTERED||currentEmail.equals(email)){
            int rowsChanged = dao.updateData(new Student(name,city,email,phone),currentEmail);
            if(rowsChanged>0){
                session.setAttribute("msg", new Message("Updated Successfully!", "", "success"));
            }
            else{
                session.setAttribute("msg", new Message("Updated Failed!", "Unexpected Error occurred while updating.", "danger"));
            }
        }
        else{
            session.setAttribute("msg", new Message("Update Failed!", "New Email already exists.", "danger"));
        }
        dao.closeConnection();
        response.sendRedirect(request.getContextPath()+"/");
    }
}
