package com.jdbcemployee;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class PayrollService {
    String url = "jdbc:mysql://localhost:3306/payroll_service";
    String username = "root";
    String password = "Neetesh@007";

    public List<EmployeePayroll> getEmployeePayrollData() throws EmployeePayrollException {
        List<EmployeePayroll> employeePayrollList = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT * FROM employee_payroll";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                // Retrieve data from each row
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double salary = resultSet.getDouble("salary");

                // Create an EmployeePayroll object
                EmployeePayroll employeePayroll = new EmployeePayroll(id, name, salary);

                // Add the EmployeePayroll object to the list
                employeePayrollList.add(employeePayroll);
            }
        } catch (SQLException e) {
            throw new EmployeePayrollException("Error retrieving employee payroll data", e);
        }

        return employeePayrollList;
    }
}