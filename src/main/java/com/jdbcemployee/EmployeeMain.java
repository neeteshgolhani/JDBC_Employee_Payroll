package com.jdbcemployee;
import java.time.LocalDate;
import java.util.List;
public class EmployeeMain {
    public static void main(String[] args) {
        PayrollService service = new PayrollService();
        // Create an instance of PayrollService
        try {
            LocalDate startDate = LocalDate.of(2010, 1, 1);
            LocalDate endDate = LocalDate.of(2023, 12, 31);

            // Retrieve the list of employees who joined in the specified date range
            List<PayrollService.EmployeePayroll> employeesInRange = service.getEmployeesJoinedInRange(startDate, endDate);

            if (employeesInRange.isEmpty()) {
                // Print a message if no employees were found in the date range
                System.out.println("No employees found in the specified date range.");
            } else {
                // Print the details of employees who joined in the date range
                System.out.println("Employees joined in the date range:");
                for (PayrollService.EmployeePayroll employee : employeesInRange) {
                    System.out.println(employee);
                }
            }
        } catch (PayrollService.EmployeePayrollException e) {
            // Print the stack trace if an exception occurs
            e.printStackTrace();
        }
    }
}