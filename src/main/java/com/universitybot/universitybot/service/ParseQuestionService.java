package com.universitybot.universitybot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.SQLException;

@Service
public class ParseQuestionService implements ParseQuestionInterface {
    AnswerBuilderService answerBuilderService;

    @Autowired
    public ParseQuestionService (AnswerBuilderService answerBuilderService) {
        this.answerBuilderService = answerBuilderService;
    }

    public Integer checkQuestion(String question) {
        int questionTypeNumber = 0;

        if (question.matches("(^Who is head of department)\\s\\w+$")) {
            questionTypeNumber =  1;
        }
        if (question.matches("^(Show)\\s\\S+\\s(statistics)$")) {
            questionTypeNumber = 2;
        }
        if (question.matches("^(Show the average salary for the department)\\s\\S+$")) {
            questionTypeNumber = 3;
        }
        if (question.matches("^(Show count of employees for )\\S+$")) {
            questionTypeNumber = 4;
        }
        if (question.matches("^(Global search by )\\S+$")) {
            questionTypeNumber = 5;
        }

        return questionTypeNumber;
    }

    public String getAnswer(String input) throws SQLException {
        input = input.trim().replaceAll("[.?]$", "");
        Integer numberOfQuestion = checkQuestion(input);
        String answer;
        switch (numberOfQuestion) {
            case 1 -> {
                String department_name = input.substring(input.lastIndexOf(" ") + 1);
                answer = answerBuilderService.getHeadOfDepartment(department_name);
            }
            case 2 -> {
                String department_name = input.split(" ")[1];
                answer = answerBuilderService.getDepartmentStatistics(department_name);
            }
            case 3 -> {
                String department_name = input.substring(input.lastIndexOf(" ") + 1);
                answer = answerBuilderService.getAverageSalaryByDepartment(department_name);
            }
            case 4 -> {
                String department_name = input.substring(input.lastIndexOf(" ") + 1);
                answer = answerBuilderService.getCountOfEmployee(department_name);
            }
            case 5 -> {
                String match = input.substring(input.lastIndexOf(" ") + 1);
                answer = answerBuilderService.getMatches(match);
            }
            default -> answer = "Bad request";
        }


        return answer;

    }
}
