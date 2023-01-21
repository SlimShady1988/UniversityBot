package com.universitybot.universitybot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnswerBuilderService implements DepartmentAnswerInterface {
    private final DataSource dataSource;

    @Autowired
    public AnswerBuilderService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public String getHeadOfDepartment(String departmentName) throws SQLException {
        String firstname = null;
        String lastname = null;
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT DISTINCT firstname, lastname FROM lectors JOIN departments ON lectors.head_of_department " +
                            "=(SELECT id FROM departments WHERE departments.name = ?);")) {
                statement.setString(1, departmentName);
                var result = statement.executeQuery();

                while (result.next()) {
                    firstname = result.getString("firstname");
                    lastname = result.getString("lastname");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        if (firstname != null && lastname != null) {
            return "Head of " + departmentName + " department is " + firstname + " " + lastname;
        } else {
            return "Wrong department name, or hasn't a head of department";
        }
    }

    public String getDepartmentStatistics(String departmentName) throws SQLException {
        int assistants = 0;
        int associateProfessors = 0;
        int professors = 0;
        Integer departmentId = null;

        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement departmentIdStatement = connection.prepareStatement(
                    "SELECT id FROM departments WHERE name = ?")
            ) {
                departmentIdStatement.setString(1, departmentName);
                var departmentStatementIdResult = departmentIdStatement.executeQuery();

                while (departmentStatementIdResult.next()) {
                    departmentId = Integer.valueOf(departmentStatementIdResult.getString("id"));
                }

                if (departmentId == null) {
                    return "Wrong department name";
                }
            }
            try (
                    PreparedStatement assistantStatement = connection.prepareStatement(
                            """ 
                                    SELECT count(*) FROM lectors JOIN lectors_departments ld
                                    ON lectors.id = ld.lector_id
                                    AND department_id = ?
                                    AND degree_id = (SELECT id FROM degree WHERE name = 'assistant');""");
                    PreparedStatement associateProfessorStatement = connection.prepareStatement(
                            """
                                    SELECT count(*) FROM lectors JOIN lectors_departments ld
                                    ON lectors.id = ld.lector_id
                                    AND department_id = ?
                                    AND degree_id = (SELECT id FROM degree WHERE name = 'associate professor');""");
                    PreparedStatement professorStatement = connection.prepareStatement(
                            """
                                    SELECT count(*) FROM lectors JOIN lectors_departments ld
                                    ON lectors.id = ld.lector_id
                                    AND department_id = ?
                                    AND degree_id = (SELECT id FROM degree WHERE name = 'professor');""")
            ) {
                assistantStatement.setInt(1, departmentId);
                associateProfessorStatement.setInt(1, departmentId);
                professorStatement.setInt(1, departmentId);

                var assistantResult = assistantStatement.executeQuery();
                var associateProfessorResult = associateProfessorStatement.executeQuery();
                var professorResult = professorStatement.executeQuery();

                while (assistantResult.next()) {
                    assistants = assistantResult.getInt(1);
                }

                while (associateProfessorResult.next()) {
                    associateProfessors = associateProfessorResult.getInt(1);
                }

                while (professorResult.next()) {
                    professors = professorResult.getInt(1);
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if (assistants != 0 || associateProfessors != 0 || professors != 0) {
            return " assistants - " + assistants + ". associate professors - "
                    + associateProfessors + ". professors - " + professors +".";
        } else {
            return "Nobody works in this department";
        }
    }

    public String getAverageSalaryByDepartment(String departmentName) throws SQLException {
        Integer averageSalary = null;
        try (Connection connection = dataSource.getConnection()) {
            try (
                    PreparedStatement averageSalaryStatement = connection.prepareStatement(
                            """ 
                                    SELECT  avg(payment)FROM lectors
                                    JOIN lectors_departments ld ON lectors.id = ld.lector_id
                                    AND department_id =(SELECT id FROM departments WHERE departments.name = ?);""");
            ) {
                averageSalaryStatement.setString(1, departmentName);
                var averageSalaryResult = averageSalaryStatement.executeQuery();

                while (averageSalaryResult.next()) {
                    averageSalary = averageSalaryResult.getInt(1);
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        if (averageSalary != null) {
            return "The average salary of " + departmentName + " is " + averageSalary;
        } else {
            return "Wrong department name";
        }
    }

    public String getCountOfEmployee(String departmentName) throws SQLException {
        Integer countEmployees = null;

        try (Connection connection = dataSource.getConnection()) {
            try (
                    PreparedStatement countEmployeesStatement = connection.prepareStatement(
                            """ 
                                    SELECT count(firstname)FROM lectors JOIN lectors_departments ld
                                        ON lectors.id = ld.lector_id
                                               AND department_id =(
                                               SELECT id FROM departments WHERE departments.name = ?);""");
            ) {
                countEmployeesStatement.setString(1, departmentName);
                var countEmployeesResult = countEmployeesStatement.executeQuery();

                while (countEmployeesResult.next()) {
                    countEmployees = countEmployeesResult.getInt(1);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        if (countEmployees != null) {
            return String.valueOf(countEmployees);
        } else {
            return "Nobody works in this department";
        }
    }

    public String getMatches(String match) throws SQLException {
        List<String> matchList = new ArrayList<>();
        String result;

        System.out.println(match);

        try (Connection connection = dataSource.getConnection()) {
            try (
                    PreparedStatement matchesStatement = connection.prepareStatement(
                            """ 
                                    SELECT firstname, lastname
                                        FROM lectors
                                    WHERE lectors.firstname LIKE ? OR lectors.lastname LIKE ?;""");
            ) {
                matchesStatement.setString(1, "%" + match + "%");
                matchesStatement.setString(2, "%" + match + "%");
                var matchesResult = matchesStatement.executeQuery();
                while (matchesResult.next()) {
                    matchList.add(matchesResult.getString("firstname") + " " +
                            matchesResult.getString("lastname"));
                }

                result = matchList.stream().map(Object::toString)
                        .collect(Collectors.joining(", "));

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        if (!matchList.isEmpty()) {
            return result;
        } else {
            return "No one match was founded";
        }
    }
}
