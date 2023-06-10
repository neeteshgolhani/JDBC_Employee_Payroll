package com.jdbcemployee;
import java.time.LocalDate;
import java.util.List;

import static java.util.Set.of;

public class EmployeeMain {
    public static void main(String[] args) {
        PayrollService service = new PayrollService();
        try {
            // Add a new employee to the payroll
            PayrollService.EmployeePayroll newEmployee = new PayrollService.EmployeePayroll(10,"Emma Watson", 5000.00,LocalDate.of(2023, 6, 1));
            service.addEmployeeToPayroll(newEmployee);

            // Get the updated employee payroll data
            List<PayrollService.EmployeePayroll> payrollData = service.getEmployeePayrollData();
            System.out.println("Employee Payroll Data:");
            for (PayrollService.EmployeePayroll payroll : payrollData) {
                System.out.println(payroll);
            }
        } catch (EmployeePayrollException e) {
            e.printStackTrace();
        }
    }
}
