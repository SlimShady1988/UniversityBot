package com.universitybot.universitybot;

import com.universitybot.universitybot.service.AnswerBuilderService;
import com.universitybot.universitybot.service.ParseQuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.io.*;
import java.sql.SQLException;

@SpringBootApplication
public class UniversityBotApplication implements CommandLineRunner {

	@Autowired
	private ApplicationContext context;
	private static Logger LOG = LoggerFactory
			.getLogger(UniversityBotApplication.class);

	public static void main(String[] args) {
		LOG.info("STARTING THE APPLICATION");
		SpringApplication.run(UniversityBotApplication.class, args);
		LOG.info("APPLICATION FINISHED");
	}

	@Override
	public void run(String... args) {
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
			ParseQuestionService parseQuestionService = (ParseQuestionService) context.getBean("parseQuestionService");

			String input = reader.readLine();
			String answer = parseQuestionService.getAnswer(input);

		} catch (IOException | SQLException exception) {
			exception.printStackTrace();
		}
	}

	@Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}
