//package com.jdbcemployee;
//import org.junit.Assert;
//import org.junit.Test;
//
//import java.util.List;
//
//public class EmployeePayrollTest {
//    @Test
//    public void testCompareEmployeePayrollWithDatabase() throws PayrollService.EmployeePayrollException {
//        PayrollService service = new PayrollService();
//
//        // Retrieve the employee payroll data from the database
//        List<PayrollService.EmployeePayroll> payrollData = service.getEmployeePayrollData();
//
//        for (PayrollService.EmployeePayroll payroll : payrollData) {
//            String name = payroll.getName();
//
//            // Compare the salary from the EmployeePayroll object with the salary from the database
//            boolean isSynced = service.compareEmployeePayrollWithDatabase(name);
//
//            // Assert that the salaries are equal
//            Assert.assertTrue("EmployeePayroll object is not synced with the database for employee: " + name, isSynced);
//        }
//    }
//}