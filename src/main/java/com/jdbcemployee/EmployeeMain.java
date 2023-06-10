package com.jdbcemployee;
import java.time.LocalDate;
import java.util.List;

import static java.util.Set.of;

public class EmployeeMain {
    public static void main(String[] args) {
            PayrollService service = new PayrollService();
            PayrollService.EmployeePayroll employee = new PayrollService.EmployeePayroll(11,"aruf",500660,PayrollService.Gender.MALE, LocalDate.now());
            try {
                service.addEmployeeToPayroll(employee);
                System.out.println("Employee added to the payroll successfully");
            } catch (EmployeePayrollException e) {
                e.printStackTrace();
            }
        }
}