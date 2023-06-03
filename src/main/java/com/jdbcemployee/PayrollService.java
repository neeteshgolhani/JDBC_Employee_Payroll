package com.jdbcemployee;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PayrollService {
    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/payroll_service";
        String username = "root";
        String password = "Neetesh@007";

        try {
            // Establish a database connection
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("Database connection established!");
            // Close the connection when done
            connection.close();
        } catch (SQLException e) {
            System.out.println("Failed to connect to the database!");
            e.printStackTrace();
        }
    }
}
