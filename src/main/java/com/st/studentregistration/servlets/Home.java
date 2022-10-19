package com.st.studentregistration.servlets;

import com.st.studentregistration.models.auth.Auth;
import com.st.studentregistration.models.db.DAOImpl;
import com.st.studentregistration.models.message.Message;
import com.st.studentregistration.models.users.Student;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;


@WebServlet(name = "Home", value = "")
public class Home extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Auth auth = Auth.getInstance();
        if(auth.isUserLoggedIn(request)){
            RequestDispatcher rd = request.getRequestDispatcher("/views/home.jsp");
            DAOImpl dao = new DAOImpl();
            dao.connectDatabase();
            ArrayList<Student> studentList = dao.getAllStudentData();
            dao.closeConnection();
            request.setAttribute("studentList",studentList);
            HttpSession session = request.getSession(false);
            try{
                Message msg = (Message) session.getAttribute("msg");
                request.setAttribute("msg",msg);
                session.removeAttribute("msg");
            }catch (NullPointerException e){
                e.printStackTrace();
            }
            rd.forward(request,response);
        }else{
            response.sendRedirect(request.getContextPath()+"/auth/login");
        }
    }
}
