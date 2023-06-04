package com.jdbcemployee;
import java.time.LocalDate;
import java.util.List;
public class EmployeeMain {
    public static void main(String[] args) {
        PayrollService service = new PayrollService();
        // Create an instance of PayrollService
        try {
            service.analyzeEmployeeDataByGender();
        } catch (PayrollService.EmployeePayrollException e) {
            e.printStackTrace();
        }
    }
}