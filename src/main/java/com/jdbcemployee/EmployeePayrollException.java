package com.jdbcemployee;

import java.sql.SQLException;

public class EmployeePayrollException extends Exception {
    public EmployeePayrollException(String message, SQLException e) {
        super(message);
    }
}
