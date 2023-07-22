package ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.services;

import java.util.List;

/**
 * The UserService interface provides methods for interacting with the user.
 */
public interface UserService {

	/**
	 * Prints a greeting message.
	 */
	void sayHello();

	/**
	 * Prints the menu options.
	 */
	void printMenu();

	/**
	 * Prints a farewell message.
	 */
	void sayByeBye();

	/**
	 * Prints a message asking for a command.
	 */
	void printAskCommand();

	/**
	 * Retrieves the user's command input.
	 *
	 * @return The user's command input.
	 */
	String getCommand();

	/**
	 * Prints a message for an invalid command.
	 */
	void printWrongCommand();

	/**
	 * Prints a message.
	 *
	 * @param result The message to print.
	 */
	void print(String result);

	/**
	 * Prints a message for a successful addition.
	 */
	void addedMessage();

	/**
	 * Prints a message for a successful deletion.
	 */
	void deletedMessage();

	/**
	 * Prints a message for a successful removal.
	 */
	void removedMessage();

	/**
	 * Asks the user for a course selection based on a previous message.
	 *
	 * @param coursesTable The table of courses to display for selection.
	 * @return The selected course.
	 */
	Integer askWhatCourse(List<String[]> coursesTable);

	/**
	 * Retrieves a number input from the user.
	 *
	 * @return The number input from the user.
	 */
	Integer getNumber();

	/**
	 * Checks if the given string is a valid number.
	 *
	 * @param number The string to check.
	 * @return True if the string is a valid number, false otherwise.
	 */
	boolean isNumber(String number);

	/**
	 * Retrieves the count of students from the user.
	 *
	 * @return The count of students.
	 */
	Integer getStudentsCount();

	/**
	 * Prints a table.
	 *
	 * @param answerTable The table to print.
	 */
	void printTable(List<String[]> answerTable);

	/**
	 * Retrieves the course name from the user.
	 *
	 * @return The course name.
	 */
	String getCourseName();

	/**
	 * Retrieves the first name from the user.
	 *
	 * @return The first name.
	 */
	String getFirstName();

	/**
	 * Retrieves the last name from the user.
	 *
	 * @return The last name.
	 */
	String getLastName();

	/**
	 * Retrieves the group ID from the user.
	 *
	 * @return The group ID.
	 */
	Integer getGroupId();

	/**
	 * Retrieves the student ID from the user.
	 *
	 * @return The student ID.
	 */
	Integer getStudentId();

	/**
	 * Retrieves the course ID from the user.
	 *
	 * @return The course ID.
	 */
	Integer getCourseId();
}