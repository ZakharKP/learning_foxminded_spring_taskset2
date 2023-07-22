package ua.foxminded.foxstudent104788.javaspring.task2.task2_5_school_console_app_hibernate.services.viewservices;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import ua.foxminded.foxstudent104788.javaspring.task2.task2_5_school_console_app_hibernate.models.MessageStorage;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_5_school_console_app_hibernate.services.impl.ResourceLoaderImpl;

class ResourceLoaderImplTest {

	@Test
	void testGetMessageStorage_menu() {

		MessageStorage storage = new ResourceLoaderImpl().getMessageStorage();

		List<String> actual = storage.getMenu();

		List<String> expected = new ArrayList<>();
		expected.add("\"1\" to Find all groups with less or equal students’ number");
		expected.add("\"2\" to Find all students related to the course with the given name");
		expected.add("\"3\" to Add a new student");
		expected.add("\"4\" to Delete a student by the student_id");
		expected.add("\"5\" to Add a student to the course (from a list)");
		expected.add("\"6\" to Remove the student from one of their courses.");
		expected.add("\"7\" to See that menu again.");
		expected.add("\"8\" to Exit");

		assertEquals(expected, actual);
	}

	@Test
	void testGetMessageStorage_mesages() {

		MessageStorage settings = new ResourceLoaderImpl().getMessageStorage();

		Map<String, String> actual = settings.getMessages();

		Map<String, String> expected = new HashMap<>();
		expected.put("hello",
				"Hello! This application will help you conduct research within our school's information.");
		expected.put("menu", "Please choose a command from the options below by entering the corresponding number:");
		expected.put("bye", "Thank you for using our application. Have a great day!");
		expected.put("wrongCommand", "Oops! Something went wrong. Please check your input and try again.");
		expected.put("askCommand", "Please enter your selection:");
		expected.put("clarifyCourse", "Please select a course number from the list below:");
		expected.put("added", "Successfully added.");
		expected.put("deleted", "Successfully deleted.");
		expected.put("removed", "Successfully removed.");
		expected.put("ask_students_count", "Please enter the maximum number of students.");
		expected.put("ask_course_name", "Please enter the course name.");
		expected.put("ask_first_name", "Please enter the first name.");
		expected.put("ask_last_name", "Please enter the last name.");
		expected.put("ask_course_id", "Please enter the course ID.");
		expected.put("ask_student_id", "Please enter the student ID.");
		expected.put("ask_group_id", "Please enter the group ID.");

		assertEquals(expected, actual);
	}

	@Test
	void testGetMapFromFile() {

		Map<String, String> actual = new ResourceLoaderImpl()
				.getMapFromFile(Paths.get("src", "main", "resources", "settings", "messages.properties"));

		Map<String, String> expected = new HashMap<>();
		expected.put("hello",
				"Hello! This application will help you conduct research within our school's information.");
		expected.put("menu", "Please choose a command from the options below by entering the corresponding number:");
		expected.put("bye", "Thank you for using our application. Have a great day!");
		expected.put("wrongCommand", "Oops! Something went wrong. Please check your input and try again.");
		expected.put("askCommand", "Please enter your selection:");
		expected.put("clarifyCourse", "Please select a course number from the list below:");
		expected.put("added", "Successfully added.");
		expected.put("deleted", "Successfully deleted.");
		expected.put("removed", "Successfully removed.");
		expected.put("ask_students_count", "Please enter the maximum number of students.");
		expected.put("ask_course_name", "Please enter the course name.");
		expected.put("ask_first_name", "Please enter the first name.");
		expected.put("ask_last_name", "Please enter the last name.");
		expected.put("ask_course_id", "Please enter the course ID.");
		expected.put("ask_student_id", "Please enter the student ID.");
		expected.put("ask_group_id", "Please enter the group ID.");
		assertEquals(expected, actual);

	}

}
