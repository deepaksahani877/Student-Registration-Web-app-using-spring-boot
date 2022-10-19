package com.st.studentregistration.servlets;

import com.st.studentregistration.models.db.DAOImpl;
import com.st.studentregistration.models.message.Message;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "DeleteData", value = "/delete")
public class DeleteData extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        DAOImpl dao = new DAOImpl();
        dao.connectDatabase();
        int rowsChanged = dao.deleteRegistration(email);
        dao.closeConnection();
        HttpSession session = request.getSession(false);
        if(rowsChanged>0){

            session.setAttribute("msg",new Message("Data Successfully deleted.","","danger"));
        }else{
            request.setAttribute("msg",new Message("Failed to delete data.","","danger"));
        }
        response.sendRedirect(request.getContextPath()+"/");
    }
}
