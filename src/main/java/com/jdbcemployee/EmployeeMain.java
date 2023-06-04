package com.jdbcemployee;

import java.util.List;

public class EmployeeMain {
    public static void main(String[] args) {
        PayrollService service = new PayrollService();
        // Create an instance of PayrollService
        try {
            // Get the employee payroll data
            List<PayrollService.EmployeePayroll> payrollData = service.getEmployeePayrollData();
            System.out.println("Employee Payroll Data:");
            for (PayrollService.EmployeePayroll payroll : payrollData) {
                System.out.println(payroll);
                // Print each employee payroll data
            }

            // Update the salary of an employee
            String employeeName = "Terissa";
            double newSalary = 3000000.00;
            service.updateEmployeeSalary(employeeName, newSalary);
            System.out.println("Salary updated for employee: " + employeeName);

            // Update the EmployeePayroll object with the updated salary
            service.updateEmployeePayrollObject(employeeName, newSalary);
            System.out.println("EmployeePayroll object updated for employee: " + employeeName);

            // Compare the EmployeePayroll object with the database
            boolean isSynced = service.compareEmployeePayrollWithDatabase(employeeName);
            System.out.println("Is EmployeePayroll object synced with the database? " + isSynced);
        } catch (PayrollService.EmployeePayrollException e) {
            e.printStackTrace();
        }
    }
}
