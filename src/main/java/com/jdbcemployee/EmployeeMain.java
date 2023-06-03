package com.jdbcemployee;

import java.util.List;

public class EmployeeMain {
    public static void main(String[] args) {
        PayrollService service = new PayrollService();
        try {
            List<EmployeePayroll> payrollData = service.getEmployeePayrollData();
            for (EmployeePayroll payroll : payrollData) {
                System.out.println(payroll);
            }
        } catch (EmployeePayrollException e) {
            e.printStackTrace();
        }
    }
}
