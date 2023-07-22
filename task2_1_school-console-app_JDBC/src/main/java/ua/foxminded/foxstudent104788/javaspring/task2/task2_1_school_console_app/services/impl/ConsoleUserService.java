package ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.models.MessageStorage;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.services.UserService;

/**
 * Service to work with console - get input command and print output information
 */
@AllArgsConstructor
@Getter
public class ConsoleUserService implements UserService {

	Scanner reader;
	MessageStorage storage;

	@Override
	public void sayHello() {
		print(storage.getMessages().get("hello"));

	}

	@Override
	public void printMenu() {

		print(storage.getMessages().get("menu"));
		storage.getMenu().forEach(row -> print(row));
	}

	@Override
	public void sayByeBye() {

		print(storage.getMessages().get("bye"));

	}

	@Override
	public String getCommand() {

		String command = reader.nextLine().trim();
		while (StringUtils.isBlank(command)) {
			printWrongCommand();
			command = reader.nextLine().trim();
		}

		return command;
	}

	private String prepareSqlOut(String sql) {
		List<String[]> answerRows = getAnswerRows(sql);
		StringBuilder answer = new StringBuilder();
		fillInWithSpases(answerRows);

		for (String[] row : answerRows) {
			answer.append(String.join("\t", row));
			answer.append("\n");
		}

		return answer.toString();
	}

	private void fillInWithSpases(List<String[]> answerRows) {
		int[] maxSymbolsAmount = getMaxSymbolsAmount(answerRows);
		String[] spases;
		for (String[] row : answerRows) {
			spases = getSpaces(maxSymbolsAmount, row);
			for (int i = 0; i < row.length; i++) {
				row[i] = row[i] + spases[i];
			}
		}

	}

	private List<String[]> getAnswerRows(String table) {
		List<String[]> tableRows = new ArrayList<>();
		for (String row : table.split("\n")) {
			tableRows.add(row.split("\t"));
		}
		return tableRows;
	}

	private int[] getMaxSymbolsAmount(List<String[]> resultList) {
		int[] symbolsAmount = new int[resultList.get(0).length];

		for (String[] line : resultList) {
			for (int i = 0; i < line.length; i++) {
				symbolsAmount[i] = symbolsAmount[i] < line[i].length() ? line[i].length() : symbolsAmount[i];
			}

		}

		return symbolsAmount;
	}

	private String[] getSpaces(int[] symbolsAmount, String[] resultLine) {
		int spacesAmount = 0;
		String[] spaces = new String[resultLine.length];
		for (int i = 0; i < resultLine.length; i++) {
			spacesAmount = symbolsAmount[i] - resultLine[i].length();
			spaces[i] = String.valueOf(new char[spacesAmount]).replaceAll("\0", " ");
		}

		return spaces;
	}

	@Override
	public void print(String outPutData) {
		for (String line : outPutData.split("\n")) {
			System.out.println("\t" + line);
		}
	}

	@Override
	public void printWrongCommand() {
		print(storage.getMessages().get("wrongCommand"));

	}

	@Override
	public void printAskCommand() {
		print(storage.getMessages().get("askCommand"));

	}

	@Override
	public Integer getNumber() {
		String number = reader.nextLine().trim();
		while (StringUtils.isBlank(number) || !isNumber(number)) {
			printWrongCommand();
			number = reader.nextLine().trim();
		}

		return Integer.parseInt(number);
	}

	public boolean isNumber(String number) {

		return number.matches("^[0-9]+$");
	}

	@Override
	public void addedMessage() {
		print(storage.getMessages().get("added"));

	}

	@Override
	public void deletedMessage() {
		print(storage.getMessages().get("deleted"));

	}

	@Override
	public void removedMessage() {
		print(storage.getMessages().get("removed"));

	}

	@Override
	public Integer askWhatCourse(List<String[]> coursesTable) {
		print(storage.getMessages().get("clarifyCourse"));
		printTable(coursesTable);

		return getNumber();

	}

	@Override
	public Integer getStudentsCount() {
		print(storage.getMessages().get("ask_students_count"));
		return getNumber();
	}

	@Override
	public void printTable(List<String[]> answerTable) {
		StringBuilder answer = new StringBuilder();

		fillInWithSpases(answerTable);

		for (String[] row : answerTable) {
			answer.append(String.join("\t", row));
			answer.append("\n");
		}

		print(answer.toString());

	}

	@Override
	public String getCourseName() {
		print(storage.getMessages().get("ask_course_name"));
		return getCommand();
	}

	@Override
	public String getFirstName() {
		print(storage.getMessages().get("ask_first_name"));
		return getCommand();
	}

	@Override
	public String getLastName() {
		print(storage.getMessages().get("ask_last_name"));
		return getCommand();
	}

	@Override
	public Integer getGroupId() {
		print(storage.getMessages().get("ask_group_id"));
		return getNumber();
	}

	@Override
	public Integer getStudentId() {
		print(storage.getMessages().get("ask_student_id"));
		return getNumber();
	}

	@Override
	public Integer getCourseId() {
		print(storage.getMessages().get("ask_course_id"));
		return getNumber();
	}

}
