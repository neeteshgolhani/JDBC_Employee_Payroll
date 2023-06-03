package com.jdbcemployee;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

public class PayrollService {
    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/payroll_service";
        String username = "root";
        String password = "Neetesh@007";
        // Check for the JDBC driver class availability
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver class found!");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver class not found!");
            e.printStackTrace();
            return; // Exit the program if the driver class is not found
        }
            listDriver();
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
    public static void listDriver(){
        Enumeration<Driver>driverList = DriverManager.getDrivers();
        while (driverList.hasMoreElements()){
            Driver driverClass = driverList.nextElement();
            System.out.println("   "+ driverClass.getClass().getName());

        }
    }
}
