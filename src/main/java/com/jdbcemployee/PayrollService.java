package com.jdbcemployee;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
public class PayrollService {
    private String url = "jdbc:mysql://localhost:3306/payroll_service";
    private String username = "root";
    private String password = "Neetesh@007";
    public List<EmployeePayroll> getEmployeePayrollData() throws EmployeePayrollException {
        List<EmployeePayroll> employeePayrollList = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT * FROM employee_payroll WHERE is_active = true";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double salary = resultSet.getDouble("salary");
                boolean isActive = resultSet.getBoolean("is_active");

                // Create an EmployeePayroll object
                EmployeePayroll employeePayroll = new EmployeePayroll(id, name, salary, isActive);

                employeePayrollList.add(employeePayroll);
            }
        } catch (SQLException e) {
            throw new EmployeePayrollException("Error retrieving employee payroll data", e);
        }

        return employeePayrollList;
    }

    public void updateEmployeeStatus(int employeeId, boolean isActive) throws EmployeePayrollException {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "UPDATE employee_payroll SET is_active = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setBoolean(1, isActive);
            statement.setInt(2, employeeId);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                SQLException e;
                throw new EmployeePayrollException("No employee found with ID: " + employeeId, e);
            }
        } catch (SQLException e) {
            throw new EmployeePayrollException("Error updating employee status", e);
        }
    }
    public void removeEmployeeFromPayroll(int employeeId) throws EmployeePayrollException {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String updateQuery = "UPDATE employee_payroll SET is_active = false WHERE id = ?";
            PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
            updateStatement.setInt(1, employeeId);

            int rowsUpdated = updateStatement.executeUpdate();

            if (rowsUpdated == 0) {
                SQLException e;
                throw new EmployeePayrollException("No employee found with ID: " + employeeId, e);
            }

            // Remove the employee from the list
            employeePayrollList.removeIf(employee -> employee.getId() == employeeId);
        } catch (SQLException e) {
            throw new EmployeePayrollException("Error removing employee from the payroll", e);
        }
    }

    static class EmployeePayroll {
        private int id;
        private String name;
        private double salary;
        private Gender gender;
        private LocalDate startDate;

        public EmployeePayroll(int id, String name, double salary, Gender gender, LocalDate startDate) {
            this.id = id;
            this.name = name;
            this.salary = salary;
            this.gender = gender;
            this.startDate = startDate;
        }

        public EmployeePayroll(int id, String name, double salary, boolean isActive) {
            
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

        public Gender getGender() {
            return gender;
        }

        public LocalDate getStartDate() {
            return startDate;
        }
    }

    enum Gender {
        MALE, FEMALE
    }
}