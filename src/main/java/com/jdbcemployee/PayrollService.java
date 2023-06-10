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
                String query = "SELECT * FROM employee_payroll";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    double salary = resultSet.getDouble("salary");

                    EmployeePayroll employeePayroll = new EmployeePayroll(id, name, salary, LocalDate.of(2023, 6, 1));
                    employeePayrollList.add(employeePayroll);
                }
            } catch (SQLException e) {
                throw new EmployeePayrollException("Error retrieving employee payroll data");
            }

            return employeePayrollList;
        }

        public void addEmployeeToPayroll(EmployeePayroll employee) throws EmployeePayrollException {
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                connection.setAutoCommit(false);

                String insertEmployeeQuery = "INSERT INTO employee_payroll (id, name, salary) VALUES (?, ?, ?)";
                PreparedStatement employeeStatement = connection.prepareStatement(insertEmployeeQuery);
                employeeStatement.setInt(1, employee.getId());
                employeeStatement.setString(2, employee.getName());
                employeeStatement.setDouble(3, employee.getSalary());
                int employeeRows = employeeStatement.executeUpdate();

                String insertPayrollQuery = "INSERT INTO payroll_details (employee_id, deductions, taxable_pay, tax, net_pay) " +
                        "VALUES (?, ?, ?, ?, ?)";
                PreparedStatement payrollStatement = connection.prepareStatement(insertPayrollQuery);
                payrollStatement.setInt(1, employee.getId());
                double deductions = employee.getSalary() * 0.2;
                double taxablePay = employee.getSalary() - deductions;
                double tax = taxablePay * 0.1;
                double netPay = employee.getSalary() - tax;
                payrollStatement.setDouble(2, deductions);
                payrollStatement.setDouble(3, taxablePay);
                payrollStatement.setDouble(4, tax);
                payrollStatement.setDouble(5, netPay);
                int payrollRows = payrollStatement.executeUpdate();

                if (employeeRows == 1 && payrollRows == 1) {
                    connection.commit();
                    employee.setDeductions(deductions);
                    employee.setTaxablePay(taxablePay);
                    employee.setTax(tax);
                    employee.setNetPay(netPay);
                } else {
                    connection.rollback();
                    throw new EmployeePayrollException("Error adding employee to the payroll");
                }
            } catch (SQLException e) {
                throw new EmployeePayrollException("Error adding employee to the payroll");
            }
        }

        public static class EmployeePayroll {
            private int id;
            private String name;
            private double salary;
            private double deductions;
            private double taxablePay;
            private double tax;
            private double netPay;

            public EmployeePayroll(int id, String name, double salary, double deductions, double taxablePay, double tax, double netPay) {
                this.id = id;
                this.name = name;
                this.salary = salary;
                this.deductions = deductions;
                this.taxablePay = taxablePay;
                this.tax = tax;
                this.netPay = netPay;
            }

            public EmployeePayroll(int id, String name, double salary, LocalDate of) {
                this.id=id;
                this.name=name;
                this.salary=salary;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public double getSalary() {
                return salary;
            }

            public void setSalary(double salary) {
                this.salary = salary;
            }

            public double getDeductions() {
                return deductions;
            }

            public void setDeductions(double deductions) {
                this.deductions = deductions;
            }

            public double getTaxablePay() {
                return taxablePay;
            }

            public void setTaxablePay(double taxablePay) {
                this.taxablePay = taxablePay;
            }

            public double getTax() {
                return tax;
            }

            public void setTax(double tax) {
                this.tax = tax;
            }

            public double getNetPay() {
                return netPay;
            }

            public void setNetPay(double netPay) {
                this.netPay = netPay;
            }

            @Override
            public String toString() {
                return "EmployeePayroll{" +
                        "id=" + id +
                        ", name='" + name + '\'' +
                        ", salary=" + salary +
                        ", deductions=" + deductions +
                        ", taxablePay=" + taxablePay +
                        ", tax=" + tax +
                        ", netPay=" + netPay +
                        '}';
            }
        }
    }
