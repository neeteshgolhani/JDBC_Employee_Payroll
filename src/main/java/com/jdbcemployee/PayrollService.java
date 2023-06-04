package com.jdbcemployee;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
public class PayrollService {
    String url = "jdbc:mysql://localhost:3306/payroll_service";
    String username = "root";
    String password = "Neetesh@007";

    public List<EmployeePayroll> getEmployeePayrollData() throws EmployeePayrollException {
        // Create an empty list to hold EmployeePayroll objects
        List<EmployeePayroll> employeePayrollList = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            // Establish a connection to the database using the URL, username, and password
            // The URL specifies the database type (MySQL), host (localhost), port (3306), and database name (payroll_service)

            String query = "SELECT * FROM employee_payroll";
            // The SQL query to select all columns from the employee_payroll table

            Statement statement = connection.createStatement();
            // Create a Statement object to execute the query

            ResultSet resultSet = statement.executeQuery(query);
            // Execute the query and obtain the result set

            while (resultSet.next()) {
                // Iterate over each row in the result set

                int id = resultSet.getInt("id");
                // Retrieve the value of the "id" column for the current row

                String name = resultSet.getString("name");
                // Retrieve the value of the "name" column for the current row

                double salary = resultSet.getDouble("salary");
                // Retrieve the value of the "salary" column for the current row

                EmployeePayroll employeePayroll = new EmployeePayroll(id, name, salary);
                // Create an EmployeePayroll object using the retrieved data

                employeePayrollList.add(employeePayroll);
                // Add the EmployeePayroll object to the list
            }
        } catch (SQLException e) {
            throw new EmployeePayrollException("Error retrieving employee payroll data");
            // In case of any SQLException, throw a custom EmployeePayrollException with an appropriate error message
        }

        return employeePayrollList;
        // Return the list of EmployeePayroll objects retrieved from the database
    }
    public void analyzeEmployeeDataByGender() throws EmployeePayrollException {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT gender, SUM(salary), AVG(salary), MIN(salary), MAX(salary), COUNT(*) " +
                    "FROM employee_payroll " +
                    "GROUP BY gender";

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String gender = resultSet.getString(1);
                double sumSalary = resultSet.getDouble(2);
                double avgSalary = resultSet.getDouble(3);
                double minSalary = resultSet.getDouble(4);
                double maxSalary = resultSet.getDouble(5);
                int count = resultSet.getInt(6);

                System.out.println("Gender: " + gender);
                System.out.println("Sum Salary: " + sumSalary);
                System.out.println("Average Salary: " + avgSalary);
                System.out.println("Minimum Salary: " + minSalary);
                System.out.println("Maximum Salary: " + maxSalary);
                System.out.println("Count: " + count);
                System.out.println("--------------------");
            }
        } catch (SQLException e) {
            throw new EmployeePayrollException("Error analyzing employee data by gender");
        }
    }

    public class EmployeePayroll {
        private int id;
        private String name;
        private double salary;

        public EmployeePayroll(int id, String name, double salary) {
            // Constructor to initialize the EmployeePayroll object with the provided id, name, and salary
            this.id = id;
            this.name = name;
            this.salary = salary;
        }

        public int getId() {
            // Getter method to retrieve the id of the EmployeePayroll object
            return id;
        }

        public String getName() {
            // Getter method to retrieve the name of the EmployeePayroll object
            return name;
        }

        public double getSalary() {
            // Getter method to retrieve the salary of the EmployeePayroll object
            return salary;
        }

        public void setSalary(double salary) {
            // Setter method to update the salary of the EmployeePayroll object
            this.salary = salary;
        }

        @Override
        public String toString() {
            // toString method to return a string representation of the EmployeePayroll object
            return "EmployeePayroll [id=" + id + ", name=" + name + ", salary=" + salary + "]";
        }
    }

    public class EmployeePayrollException extends Exception {
        public EmployeePayrollException(String message) {
            // Constructor to create an EmployeePayrollException object with the provided error message
            super(message);
        }
    }
}