package com.jdbcemployee;
import java.time.LocalDate;

public class EmployeeMain {
    public static void main(String[] args) throws PayrollService.EmployeePayrollException {
        PayrollService service = new PayrollService();
        try {
            PayrollService.EmployeePayroll newEmployee = new PayrollService.EmployeePayroll(10, "Jony", 5000.0, "M",
                    LocalDate.of(2023, 1, 1));
            service.addEmployeeToPayroll(newEmployee);
            System.out.println("Employee added successfully: " + newEmployee);
        } catch (PayrollService.EmployeePayrollException e) {
            e.printStackTrace();
        }
    }
}