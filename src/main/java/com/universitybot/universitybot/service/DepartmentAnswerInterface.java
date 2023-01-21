package com.universitybot.universitybot.service;

import java.sql.SQLException;

public interface DepartmentAnswerInterface {
    String getHeadOfDepartment(String departmentName) throws SQLException;
    String getDepartmentStatistics(String departmentName) throws SQLException;
    String getAverageSalaryByDepartment(String departmentName) throws SQLException;
    String getCountOfEmployee(String departmentName) throws SQLException;
    String getMatches(String match) throws SQLException;
}
