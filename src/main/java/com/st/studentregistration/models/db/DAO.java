package com.st.studentregistration.models.db;

import com.st.studentregistration.models.users.Admin;
import com.st.studentregistration.models.users.Student;
import java.util.ArrayList;

public interface DAO {
    void connectDatabase();
    int registerStudent(Student student, String password);
    int verifyLogin(String email,String password);
    int deleteRegistration(String email);
    int updateData(Student student,String currentMail);
    Admin getAdmin(String email);
    ArrayList<Student> getAllStudentData();
    void closeConnection();
    int isEmailAlreadyRegistered(String email);
}
