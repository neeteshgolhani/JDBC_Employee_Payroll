package com.jdbcemployee;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
public class PayrollService {
    String url = "jdbc:mysql://localhost:3306/payroll_service";
    String username = "root";
    String password = "Neetesh@007";
    private List<EmployeePayroll> employeePayrollList; // Declare the variable at the class level


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
                String gender = resultSet.getString("gender");
                LocalDate startDate = resultSet.getDate("start_date").toLocalDate();
                LocalDate endDate = resultSet.getDate("end_date").toLocalDate();

                EmployeePayroll employeePayroll = new EmployeePayroll(id, name, salary, gender, startDate);

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

    public void addEmployeeToPayroll (EmployeePayroll employee) throws EmployeePayrollException {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "INSERT INTO employee_payroll (name, salary, gender, start_date) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, employee.getName()); // Set employee name parameter
            preparedStatement.setDouble(2, employee.getSalary()); // Set employee salary parameter
            preparedStatement.setString(3, employee.getGender()); // Set employee gender parameter
            preparedStatement.setDate(4, java.sql.Date.valueOf(employee.getStartDate())); // Set employee start date parameter

            int rowsInserted = preparedStatement.executeUpdate(); // Execute the INSERT statement

            if (rowsInserted == 0) {
                throw new EmployeePayrollException("Failed to add employee to the database");
            }

            // Add the employee to the list only if the database operation is successful
            employeePayrollList.add(employee);
        } catch (SQLException e) {
            throw new EmployeePayrollException("Error adding employee to the payroll");
        }
    }

    public static class EmployeePayroll {

        private int id;
        private String name;
        private double salary;
        private String gender;
        private LocalDate startDate;
        private LocalDate endDate;
        // Constructor to initialize the EmployeePayroll object with the provided id, name, and salary

        public EmployeePayroll(int id, String name, double salary, String gender, LocalDate startDate) {
            this.id = id;
            this.name = name;
            this.salary = salary;
            this.gender = gender;
            this.startDate = startDate;
            this.endDate = endDate;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public double getSalary() {
            return salary;
        }

        public String getGender() {
            return gender;
        }

        public LocalDate getStartDate() {
            return startDate;
        }

        public LocalDate getEndDate() {
            return endDate;
        }

        @Override
        public String toString() {
            return "EmployeePayroll [id=" + id + ", name=" + name + ", salary=" + salary + ", gender=" + gender + "]";
        }
    }
    public class EmployeePayrollException extends Exception {
        public EmployeePayrollException(String message) {
            super(message);
        }
    }
}