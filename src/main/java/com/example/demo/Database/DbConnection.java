package com.example.demo.Database;

import javafx.scene.image.Image;

import java.io.InputStream;
import java.sql.*;

public class DbConnection {

        private static DbConnection dbConnection;

        private final Connection connection;

        private DbConnection() throws SQLException {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cafe","root","");
        }

        public static DbConnection getInstance() throws SQLException {
            return (null == dbConnection) ? dbConnection = new DbConnection() : dbConnection;
        }

        public Connection getConnection(){
            return connection;
        }


}
