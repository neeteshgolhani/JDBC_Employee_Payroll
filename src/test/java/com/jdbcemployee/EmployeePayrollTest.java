package com.jdbcemployee;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

public class EmployeePayrollTest {
    @Test
    public void testAddEmployeeToPayroll() {
        PayrollService service = new PayrollService();
        PayrollService.EmployeePayroll employee = new PayrollService.EmployeePayroll(10, "John Doe", 5000.0, "M", LocalDate.of(2023, 6, 1));
        try {
            service.addEmployeeToPayroll(employee);

            // Get the employee payroll data from the database
            List<PayrollService.EmployeePayroll> payrollData = service.getEmployeePayrollData();

            // Check if the added employee exists in the payroll data
            boolean isEmployeeAdded = false;
            for (PayrollService.EmployeePayroll emp : payrollData) {
                if (emp.getName().equals(employee.getName())) {
                    isEmployeeAdded = true;
                    break;
                }
            }

            Assert.assertTrue("Employee was not added to the payroll", isEmployeeAdded);
        } catch (PayrollService.EmployeePayrollException e) {
            e.printStackTrace();
            Assert.fail("Failed to add employee to the payroll");
        }
    }
}