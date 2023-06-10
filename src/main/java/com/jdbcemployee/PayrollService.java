package com.jdbcemployee;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
public class PayrollService {
    private String url = "jdbc:mysql://localhost:3306/payroll_service";
    private String username = "root";
    private String password = "Neetesh@007";

    public void addEmployeeToPayroll(EmployeePayroll employee) throws EmployeePayrollException {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, username, password);
            connection.setAutoCommit(false); // Start the transaction

            // Insert into employee_payroll table
            String insertEmployeeQuery = "INSERT INTO employee_payroll (id, name, salary, gender, start_date) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement insertEmployeeStmt = connection.prepareStatement(insertEmployeeQuery);
            insertEmployeeStmt.setInt(1, employee.getId());
            insertEmployeeStmt.setString(2, employee.getName());
            insertEmployeeStmt.setDouble(3, employee.getSalary());
            insertEmployeeStmt.setString(4, employee.getGender().toString());
            insertEmployeeStmt.setDate(5, java.sql.Date.valueOf(employee.getStartDate()));
            int rowsAffected = insertEmployeeStmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new EmployeePayrollException("Failed to add employee to payroll");
            }

            // Insert into payroll_details table
            String insertPayrollQuery = "INSERT INTO payroll_details (employee_id, deductions, taxable_pay, tax, net_pay) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement insertPayrollStmt = connection.prepareStatement(insertPayrollQuery);
            insertPayrollStmt.setInt(1, employee.getId());
            double deductions = employee.getSalary() * 0.2; // Assuming deductions are 20% of salary
            double taxablePay = employee.getSalary() - deductions;
            double tax = taxablePay * 0.1; // Assuming tax is 10% of taxable pay
            double netPay = employee.getSalary() - tax;
            insertPayrollStmt.setDouble(2, deductions);
            insertPayrollStmt.setDouble(3, taxablePay);
            insertPayrollStmt.setDouble(4, tax);
            insertPayrollStmt.setDouble(5, netPay);
            rowsAffected = insertPayrollStmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new EmployeePayrollException("Failed to add employee to payroll");
            }

            connection.commit(); // Commit the transaction
        } catch (SQLException e) {
            try {
                if (connection != null) {
                    connection.rollback(); // Rollback the transaction in case of an error
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            throw new EmployeePayrollException("Error adding employee to the payroll");
        } finally {
            try {
                if (connection != null) {
                    connection.setAutoCommit(true); // Reset auto-commit to true
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
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