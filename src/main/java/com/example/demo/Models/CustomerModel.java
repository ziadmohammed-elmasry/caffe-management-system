package com.example.demo.Models;

import com.example.demo.Controllers.Customer.Customer;
import com.example.demo.Database.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.example.demo.Models.Users.checkEmployeeUserName;

public class CustomerModel {

    static boolean checkCustomerUserName(String username) {
        try {
            Connection connection = DbConnection.getInstance().getConnection();
            String sql = "SELECT * FROM customers WHERE name=?";
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, username);
            return pstm.executeQuery().next();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //This method is used to check if the Customer username and password match
    public static int checkCustomerLogin(String username, String password) {
        try {
            if (checkEmployeeUserName(username)) {
                Connection connection = DbConnection.getInstance().getConnection();
                String sql = "SELECT * FROM customers WHERE name=? AND password=?";
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

    public static List<com.example.demo.Controllers.Customer.Customer> getAllCustomers() throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM customers";

        PreparedStatement pstm = connection.prepareStatement(sql);

        List<com.example.demo.Controllers.Customer.Customer> dtoList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()) {

            String cus_id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String address = resultSet.getString(3);
            String contact_number = resultSet.getString(4);
            com.example.demo.Controllers.Customer.Customer customer = new com.example.demo.Controllers.Customer.Customer(cus_id, name, address, contact_number);
            dtoList.add(customer);
        }
        return dtoList;
    }

    public static boolean deleteCustomer(String cus_id) {
        try {
            Connection connection = DbConnection.getInstance().getConnection();
            String sql = "DELETE FROM customers WHERE cus_id = ?";
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, cus_id);
            return pstm.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean updateCustomer(com.example.demo.Controllers.Customer.Customer customer) {
        try {
            Connection connection = DbConnection.getInstance().getConnection();
            System.out.println (customer);
            String sql = "UPDATE customers SET name=?, address=?, contact_no=? WHERE cus_id=?";
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, customer.getName());
            pstm.setString(2, customer.getAddress());
            pstm.setString(3, customer.getContact_Number());
            pstm.setString(4, customer.getCusId());
            return pstm.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            System.out.println("Customer Updated");
        }
    }

    public static Customer searchCustomer(String cusId) {
        try {
            Connection connection = DbConnection.getInstance().getConnection();
            String sql = "SELECT * FROM customers WHERE cus_id=?";
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, cusId);
            ResultSet resultSet = pstm.executeQuery();
            if (resultSet.next()) {
                String cus_id = resultSet.getString(1);
                String name = resultSet.getString(2);
                String address = resultSet.getString(3);
                String contact_number = resultSet.getString(4);
                System.out.println("Customer Found");
                return new Customer(cus_id, name, address, contact_number);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean addCustomer(com.example.demo.Controllers.Customer.Customer customer) {
        try {
            Connection connection = DbConnection.getInstance().getConnection();
            String sql = "INSERT INTO customers VALUES(?,?,?,?)";
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, customer.getCusId());
            pstm.setString(2, customer.getName());
            pstm.setString(3, customer.getAddress());
            pstm.setString(4, customer.getContact_Number());
            return pstm.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static String generateNextCustomerId() {
        try {
            Connection connection = DbConnection.getInstance().getConnection();
            String sql = "SELECT cus_id FROM customers ORDER BY cus_id DESC LIMIT 1";
            PreparedStatement pstm = connection.prepareStatement(sql);
            ResultSet resultSet = pstm.executeQuery();
            if (resultSet.next()) {
                String lastCustomerId = resultSet.getString(1);
                int newCustomerId = Integer.parseInt(lastCustomerId.substring(1)) + 1;
                if (newCustomerId < 10) {
                    return "C000" + newCustomerId;
                } else if (newCustomerId < 1000) {
                    return "C00" + newCustomerId;
                } else {
                    return "E" + newCustomerId;
                }
            } else {
                return "C0001";
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    //search customer by contact no
    public static Customer searchCustomerByContactNo(String contactNo) {
        try {
            Connection connection = DbConnection.getInstance().getConnection();
            String sql = "SELECT * FROM customers WHERE contact_no=?";
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, contactNo);
            ResultSet resultSet = pstm.executeQuery();
            if (resultSet.next()) {
                String cus_id = resultSet.getString(1);
                String name = resultSet.getString(2);
                String address = resultSet.getString(3);
                String contact_number = resultSet.getString(4);
                System.out.println("Customer Found");
                return new Customer(cus_id, name, address, contact_number);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
