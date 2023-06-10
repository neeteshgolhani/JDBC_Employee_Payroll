package com.jdbcemployee;
import java.time.LocalDate;
import java.util.List;

import static java.util.Set.of;

public class EmployeeMain {
    public static void main(String[] args) {
        PayrollService service = new PayrollService();

        try {
            // Get the employee payroll data
            List<PayrollService.EmployeePayroll> payrollData = service.getEmployeePayrollData();
            System.out.println("Employee Payroll Data:");
            for (PayrollService.EmployeePayroll payroll : payrollData) {
                System.out.println(payroll);
            }

            // Remove an employee from the payroll
            int employeeIdToRemove = 1; // Specify the employee ID to remove
            service.removeEmployeeFromPayroll(employeeIdToRemove);
            System.out.println("Employee with ID " + employeeIdToRemove + " has been removed from the payroll.");

            // Verify the updated employee payroll data
            payrollData = service.getEmployeePayrollData();
            System.out.println("Updated Employee Payroll Data:");
            for (PayrollService.EmployeePayroll payroll : payrollData) {
                System.out.println(payroll);
            }
        } catch (EmployeePayrollException e) {
            e.printStackTrace();
        }
    }
}
