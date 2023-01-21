package com.universitybot.universitybot.service;

import java.sql.SQLException;

public interface ParseQuestionInterface {
    Integer checkQuestion(String question);
    String getAnswer(String input) throws SQLException;
}
