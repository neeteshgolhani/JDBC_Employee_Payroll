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

    public void updateEmployeeSalary(String name, double newSalary) throws EmployeePayrollException {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            // Establish a connection to the database using the provided URL, username, and password

            String query = "UPDATE employee_payroll SET salary = " + newSalary + " WHERE name = '" + name + "'";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            //create instance of preparedststement

            // Define the SQL query to update the salary of an employee in the employee_payroll table
            // The salary is updated to the newSalary value for the employee with the specified name

            // Set the parameters for the PreparedStatement
            preparedStatement.setDouble(1, newSalary);
            preparedStatement.setString(2, name);

            int rowsUpdated = preparedStatement.executeUpdate();
            // Execute the SQL query and get the number of rows updated
            if (rowsUpdated == 0) {
                // If no rows were updated, it means no employee was found with the specified name
                throw new EmployeePayrollException("No employee found with the name: " + name);
            }
            // Update the Employee Payroll Object with the updated salary
            List<EmployeePayroll> employeePayrollList = getEmployeePayrollData();
            for (EmployeePayroll employeePayroll : employeePayrollList) {
                if (employeePayroll.getName().equals(name)) {
                    employeePayroll.setSalary(newSalary);
                    break;
                }
            }
        } catch (SQLException e) {
            // In case of an SQLException, throw a custom EmployeePayrollException with an appropriate error message
            throw new EmployeePayrollException("Error updating employee salary");
        }
    }

    public void updateEmployeePayrollObject(String name, double newSalary) throws EmployeePayrollException {
        List<EmployeePayroll> employeePayrollList = getEmployeePayrollData();
        // Retrieve the employee payroll data from the database and store it in a list

        for (EmployeePayroll employeePayroll : employeePayrollList) {
            // Iterate through each EmployeePayroll object in the list

            if (employeePayroll.getName().equals(name)) {
                // Check if the name of the current EmployeePayroll object matches the specified name

                employeePayroll.setSalary(newSalary);
                // If there is a match, update the salary of the EmployeePayroll object to the newSalary value

                break;
                // Exit the loop since the desired EmployeePayroll object has been found and updated
            }
        }
    }

    public boolean compareEmployeePayrollWithDatabase(String name) throws EmployeePayrollException {
        List<EmployeePayroll> employeePayrollList = getEmployeePayrollData();
        // Retrieve the employee payroll data from the database and store it in a list

        for (EmployeePayroll employeePayroll : employeePayrollList) {
            // Iterate through each EmployeePayroll object in the list

            if (employeePayroll.getName().equals(name)) {
                // Check if the name of the current EmployeePayroll object matches the specified name

                double salaryFromDB = getSalaryFromDatabase(name);
                // Get the salary of the employee from the database based on the specified name

                double salaryFromObject = employeePayroll.getSalary();
                // Get the salary of the employee from the EmployeePayroll object

                return salaryFromDB == salaryFromObject;
                // Compare the salaries from the database and the EmployeePayroll object and return the result
            }
        }

        throw new EmployeePayrollException("No employee found with the name: " + name);
        // If no employee is found with the specified name, throw a custom EmployeePayrollException
    }

    double getSalaryFromDatabase(String name) throws EmployeePayrollException {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            // Establish a connection to the database using the provided URL, username, and password

            String query = "SELECT salary FROM employee_payroll WHERE name = '" + name + "'";
            // Define the SQL query to retrieve the salary of an employee from the employee_payroll table
            // The query selects the salary column where the name matches the specified name

            Statement statement = connection.createStatement();
            // Create a Statement object to execute the SQL query

            ResultSet resultSet = statement.executeQuery(query);
            // Execute the SQL query and get the result set

            if (resultSet.next()) {
                // If the result set has a next row, retrieve the salary from the "salary" column and return it
                return resultSet.getDouble("salary");
            }
        } catch (SQLException e) {
            // In case of an SQLException, throw a custom EmployeePayrollException with an appropriate error message
            throw new EmployeePayrollException("Error retrieving employee salary");
        }

        // If no employee is found with the specified name, throw a custom EmployeePayrollException
        throw new EmployeePayrollException("No employee found with the name: " + name);
    }

    public List<EmployeePayroll> getEmployeesJoinedInRange(LocalDate startDate, LocalDate endDate) throws EmployeePayrollException {
        List<EmployeePayroll> employeesInRange = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            // Prepare the SQL query to select employees joined within the date range
            String query = "SELECT * FROM employee_payroll WHERE join_date >= '" + startDate + "' AND join_date <= '" + endDate + "'";
            Statement statement = connection.createStatement();

            // Execute the SQL query and obtain the result set
            ResultSet resultSet = statement.executeQuery(query);

            // Iterate over the result set and create EmployeePayroll objects
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double salary = resultSet.getDouble("salary");

                // Create an EmployeePayroll object
                EmployeePayroll employeePayroll = new EmployeePayroll(id, name, salary);

                // Add the EmployeePayroll object to the list
                employeesInRange.add(employeePayroll);
            }
        } catch (SQLException e) {
            // Throw a custom exception if there is an error retrieving the employees
            throw new EmployeePayrollException("Error retrieving employees joined in the specified date range");
        }

        // Return the list of employees in the specified date range
        return employeesInRange;
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