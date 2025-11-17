package com.example.demo.Models;

import com.example.demo.Controllers.Employee.Employee;
import com.example.demo.Database.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Users {

    public static String username;

    // This method is used to check if the Admin username exists in the database
    static boolean checkAdminUserName(String username) {
        try {
            Connection connection = DbConnection.getInstance().getConnection();
            String sql = "SELECT * FROM admin WHERE name=?";
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, username);
            return pstm.executeQuery().next();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //This method is used to check if the Admin username and password match
    public static int checkAdminLogin(String username, String password) {
        try {
            if (checkAdminUserName(username)) {
                Connection connection = DbConnection.getInstance().getConnection();
                String sql = "SELECT * FROM admin WHERE name=? AND password=?";
                PreparedStatement pstm = connection.prepareStatement(sql);
                pstm.setString(1, username);
                pstm.setString(2, password);
                return pstm.executeQuery().next() ? 1 : 2;
            } else {
                return 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //This method is used to check if the Employee username exists in the database
    static boolean checkEmployeeUserName(String username) {
        try {
            Connection connection = DbConnection.getInstance().getConnection();
            String sql = "SELECT * FROM employees WHERE name=?";
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, username);
            return pstm.executeQuery().next();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //This method is used to check if the Employee username and password match
    public static int checkEmployeeLogin(String username, String password) {
        try {
            if (checkEmployeeUserName(username)) {
                Connection connection = DbConnection.getInstance().getConnection();
                String sql = "SELECT * FROM employees WHERE name=? AND password=?";
                PreparedStatement pstm = connection.prepareStatement(sql);
                pstm.setString(1, username);
                pstm.setString(2, password);
                return pstm.executeQuery().next() ? 1 : 2;
            } else {
                return 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Employee> getAllEmployees() throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM employees";

        PreparedStatement pstm = connection.prepareStatement(sql);

        List<Employee> dtoList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()) {

            String empId = resultSet.getString(1);
            String firstName = resultSet.getString(4);
            String lastName = resultSet.getString(5);
            String userName = resultSet.getString(2);
            String password = resultSet.getString(3);
            String contact_Number = resultSet.getString(6);
            Employee employee = new Employee(empId, userName, password, firstName, lastName, contact_Number);
            dtoList.add(employee);
        }
        return dtoList;
    }

    public static boolean deleteEmployee(String empId) {
        try {
            Connection connection = DbConnection.getInstance().getConnection();
            String sql = "DELETE FROM employees WHERE emp_Id = ?";
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, empId);
            return pstm.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean updateEmployee(Employee employee) {
        try {
            Connection connection = DbConnection.getInstance().getConnection();
            String sql = "UPDATE employees SET name=?, password=?, first_name=?, last_name=?, contact_number=? WHERE emp_id=?";
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, employee.getUserName());
            pstm.setString(2, employee.getPassword());
            pstm.setString(3, employee.getFirstName());
            pstm.setString(4, employee.getLastName());
            pstm.setString(5, employee.getContact_Number());
            pstm.setString(6, employee.getEmpId());
            return pstm.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            System.out.println("Employee Updated");
        }
    }

    public static Employee searchEmployee(String empId) {
        try {
            Connection connection = DbConnection.getInstance().getConnection();
            String sql = "SELECT * FROM employees WHERE emp_Id=?";
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, empId);
            ResultSet resultSet = pstm.executeQuery();
            if (resultSet.next()) {
                String emp_Id = resultSet.getString(1);
                String userName = resultSet.getString(2);
                String password = resultSet.getString(3);
                String firstName = resultSet.getString(4);
                String lastName = resultSet.getString(5);
                String contact_Number = resultSet.getString(6);
                return new Employee(emp_Id, firstName, lastName, userName, password, contact_Number);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean addEmployee(Employee employee) {
        try {
            Connection connection = DbConnection.getInstance().getConnection();
            String sql = "INSERT INTO employees VALUES(?,?,?,?,?,?)";
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, employee.getEmpId());
            pstm.setString(2, employee.getUserName());
            pstm.setString(3, employee.getPassword());
            pstm.setString(4, employee.getFirstName());
            pstm.setString(5, employee.getLastName());
            pstm.setString(6, employee.getContact_Number());
            return pstm.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
        public static String generateNextEmployeeId() {
        try {
            Connection connection = DbConnection.getInstance().getConnection();
            String sql = "SELECT emp_id FROM employees ORDER BY emp_id DESC LIMIT 1";
            PreparedStatement pstm = connection.prepareStatement(sql);
            ResultSet resultSet = pstm.executeQuery();
            if (resultSet.next()) {
                String lastEmployeeId = resultSet.getString(1);
                int newEmployeeId = Integer.parseInt(lastEmployeeId.substring(1)) + 1;
                if (newEmployeeId < 10) {
                    return "E00" + newEmployeeId;
                } else if (newEmployeeId < 100) {
                    return "E0" + newEmployeeId;
                } else {
                    return "E" + newEmployeeId;
                }
            } else {
                return "E001";
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}