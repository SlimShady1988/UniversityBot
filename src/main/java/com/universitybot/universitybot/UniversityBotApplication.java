package com.universitybot.universitybot;

import com.universitybot.universitybot.service.ParseQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import java.io.*;
import java.sql.SQLException;



/*
*  This Bot has answer for next question:
*
*  Who is head of department {department_name}
*  Show {department_name} statistics.
*  Show the average salary for the department {department_name}.
*  Show count of employees for {department_name}.
*  Global search by {template}.
*
*  In 4-th question I added “s” to word “employees”.
*
*  I don't know if ".” should be at the end of
*  sentences or not, or somewhere should be “?”, that's why I included all the variants.
*/

@SpringBootApplication
public class UniversityBotApplication implements CommandLineRunner {
	private final ApplicationContext context;
	@Autowired
	public UniversityBotApplication (ApplicationContext context) {
		this.context = context;
	}

	public static void main(String[] args) {
		SpringApplication.run(UniversityBotApplication.class, args);
	}
	@Override
	public void run(String... args) {
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
			ParseQuestionService parseQuestionService = (ParseQuestionService) context.getBean("parseQuestionService");
			var readyToCLose = false;
			while(true) {
				String input = reader.readLine();
				if (!readyToCLose) {
				String answer = parseQuestionService.getAnswer(input);
				System.out.println(answer);

				if (answer.equals("Bad request")) {
					System.out.println("Do you wanna try again? ( y / n )");
					readyToCLose = true;
				}

				} else {
					if (input.equals("n")) {
						System.out.println("Bye...!");
						break;
					}
					if (input.equals("y")) {
						readyToCLose = false;
						System.out.println("What is your question?");
					}
				}
			}
		} catch (IOException | SQLException exception) {
			exception.printStackTrace();
		}

	}
}
