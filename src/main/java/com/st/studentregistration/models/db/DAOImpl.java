package com.st.studentregistration.models.db;

import com.st.studentregistration.models.constants.UserCredentialFlags;
import com.st.studentregistration.models.users.Admin;
import com.st.studentregistration.models.users.Student;

import java.sql.*;
import java.util.ArrayList;

public class DAOImpl implements DAO {
    private Connection conn;
    private Statement stmnt;

    @Override
    public void connectDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/student_registration", "root", "Deepak.k.1");
            stmnt = conn.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int registerStudent(Student student, String password) {
        int rowsChanged;
        try {
            rowsChanged = stmnt.executeUpdate(String.format("insert into student (name,city,email,phone,password) values ('%s','%s','%s','%s','%s')", student.getName(), student.getCity(), student.getEmail(), student.getPhone(), password));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rowsChanged;
    }

    @Override
    public int verifyLogin(String email, String userInputPassword) {
        int flag;
        try {
            ResultSet rs = stmnt.executeQuery(String.format("select * from admin where email ='%s'", email));
            if (rs.next()) {
                String password = rs.getString("password");
                if (userInputPassword.equals(password)) {
                    flag = UserCredentialFlags.USER_CREDENTIAL_MATCHED;
                } else {
                    flag = UserCredentialFlags.WRONG_PASSWORD;
                }
            } else {
                flag = UserCredentialFlags.USER_NOT_EXISTS;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            flag = UserCredentialFlags.UNEXPECTED_ERROR;
        }

        return flag;
    }

    @Override
    public int deleteRegistration(String email) {
        int rowsChanged =0;
        try {
            rowsChanged = stmnt.executeUpdate(String.format("delete from student where email = '%s'",email));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsChanged;

    }

    @Override
    public int updateData(Student student, String currentMail) {
        int rowsChanged = 0;
        try {
            rowsChanged = stmnt.executeUpdate(String.format("update student set name ='%s' ,city = '%s',email = '%s',phone = '%s' where email = '%s'",
                    student.getName(), student.getCity(), student.getEmail(), student.getPhone(), currentMail));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rowsChanged;
    }

    @Override
    public Admin getAdmin(String email) {
        try {
            Admin admin = null;
            ResultSet rs = stmnt.executeQuery(String.format("select * from Admin where email = '%s'", email));
            while (rs.next()) {
                admin = new Admin(
                        rs.getString("name"),
                        rs.getString("city"),
                        rs.getString("email"),
                        rs.getString("phone")
                );
            }
            return admin;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ArrayList<Student> getAllStudentData() {
        ArrayList<Student> students = new ArrayList<>();
        try {
            ResultSet rs = stmnt.executeQuery("select * from student");
            while (rs.next()) {
                students.add(new Student(
                        rs.getString("name"),
                        rs.getString("city"),
                        rs.getString("email"),
                        rs.getString("phone")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;

    }


    @Override
    public void closeConnection() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int isEmailAlreadyRegistered(String email) {
        int flag = 0;
        try {
            ResultSet rs = stmnt.executeQuery(String.format("Select * from student where email = '%s'",email));
            if(rs.next()){
                flag = UserCredentialFlags.EMAIL_ALREADY_REGISTERED;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }
}
