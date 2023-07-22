package ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.services.impl.ConsoleUserService;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.services.impl.PathStorageImpl;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.services.impl.ResourceLoaderImpl;

class ConsoleUserServiceTest {

	@Test
	void testSayHello() {
		ByteArrayOutputStream consoleOut = new ByteArrayOutputStream();
		System.setOut(new PrintStream(consoleOut));

		new ConsoleUserService(null,
				new ResourceLoaderImpl(new PathStorageImpl(Paths.get("src", "main", "resourses"))).getMessageStorage())
				.sayHello();

		String actual = consoleOut.toString();

		String expected = "\t"
				+ "Hello! This application will help you conduct research within our school's information." + "\n";
		assertEquals(expected, actual);
	}

	@Test
	void testPrintMenu() {
		ByteArrayOutputStream consoleOut = new ByteArrayOutputStream();
		System.setOut(new PrintStream(consoleOut));

		new ConsoleUserService(null,
				new ResourceLoaderImpl(new PathStorageImpl(Paths.get("src", "main", "resourses"))).getMessageStorage())
				.printMenu();

		String actual = consoleOut.toString();

		String expected = "\t"
				+ "Please choose a command from the options below by entering the corresponding number:\n" + "\t"
				+ "\"1\" to Find all groups with less or equal students’ number\n" + "\t"
				+ "\"2\" to Find all students related to the course with the given name\n" + "\t"
				+ "\"3\" to Add a new student\n" + "\t" + "\"4\" to Delete a student by the student_id\n" + "\t"
				+ "\"5\" to Add a student to the course (from a list)\n" + "\t"
				+ "\"6\" to Remove the student from one of their courses.\n" + "\t" + "\"7\" to See that menu again.\n"
				+ "\t" + "\"8\" to Exit" + "\n";
		assertEquals(expected, actual);
	}

	@Test
	void testSayByeBye() {

		ByteArrayOutputStream consoleOut = new ByteArrayOutputStream();
		System.setOut(new PrintStream(consoleOut));

		new ConsoleUserService(null,
				new ResourceLoaderImpl(new PathStorageImpl(Paths.get("src", "main", "resourses"))).getMessageStorage())
				.sayByeBye();

		String actual = consoleOut.toString();

		String expected = "\t" + "Thank you for using our application. Have a great day!" + "\n";
		assertEquals(expected, actual);
	}

	@Test
	void testGetCommand() {

		ByteArrayOutputStream consoleOut = new ByteArrayOutputStream();
		System.setOut(new PrintStream(consoleOut));

		ByteArrayInputStream consoleIn = new ByteArrayInputStream("EXIT".getBytes());
		System.setIn(consoleIn);

		String actual = new ConsoleUserService(new Scanner(System.in),
				new ResourceLoaderImpl(new PathStorageImpl(Paths.get("src", "main", "resourses"))).getMessageStorage())
				.getCommand();

		String expected = "EXIT";

		assertEquals(expected, actual);
	}

	@Test
	void testGetCommand_empty() {

		ByteArrayOutputStream consoleOut = new ByteArrayOutputStream();
		System.setOut(new PrintStream(consoleOut));

		ByteArrayInputStream consoleIn = new ByteArrayInputStream(" \nEXIT".getBytes());
		System.setIn(consoleIn);

		new ConsoleUserService(new Scanner(System.in),
				new ResourceLoaderImpl(new PathStorageImpl(Paths.get("src", "main", "resourses"))).getMessageStorage())
				.getCommand();

		String actual = consoleOut.toString();

		String expected = "\t" + "Oops! Something went wrong. Please check your input and try again." + "\n";

		assertEquals(expected, actual);
	}

	@Test
	void testPrint() {
		ByteArrayOutputStream consoleOut = new ByteArrayOutputStream();
		System.setOut(new PrintStream(consoleOut));

		new ConsoleUserService(null,
				new ResourceLoaderImpl(new PathStorageImpl(Paths.get("src", "main", "resourses"))).getMessageStorage())
				.print("test\ntest");

		String actual = consoleOut.toString();

		String expected = "\t" + "test" + "\n" + "\t" + "test" + "\n";
		assertEquals(expected, actual);
	}

	@Test
	void testPrintWrongCommand() {
		ByteArrayOutputStream consoleOut = new ByteArrayOutputStream();
		System.setOut(new PrintStream(consoleOut));

		new ConsoleUserService(null,
				new ResourceLoaderImpl(new PathStorageImpl(Paths.get("src", "main", "resourses"))).getMessageStorage())
				.printWrongCommand();

		String actual = consoleOut.toString();

		String expected = "\t" + "Oops! Something went wrong. Please check your input and try again." + "\n";
		assertEquals(expected, actual);
	}

	@Test
	void testPrintAskCommand() {
		ByteArrayOutputStream consoleOut = new ByteArrayOutputStream();
		System.setOut(new PrintStream(consoleOut));

		new ConsoleUserService(null,
				new ResourceLoaderImpl(new PathStorageImpl(Paths.get("src", "main", "resourses"))).getMessageStorage())
				.printAskCommand();

		String actual = consoleOut.toString();

		String expected = "\t" + "Please enter your selection:" + "\n";
		assertEquals(expected, actual);
	}

	@Test
	void testGetNumber() {
		ByteArrayOutputStream consoleOut = new ByteArrayOutputStream();
		System.setOut(new PrintStream(consoleOut));

		ByteArrayInputStream consoleIn = new ByteArrayInputStream("15".getBytes());
		System.setIn(consoleIn);

		Integer actual = new ConsoleUserService(new Scanner(System.in),
				new ResourceLoaderImpl(new PathStorageImpl(Paths.get("src", "main", "resourses"))).getMessageStorage())
				.getNumber();

		Integer expected = 15;
		assertEquals(expected, actual);
	}

	@Test
	void testGetNumber_letter() {

		ByteArrayOutputStream consoleOut = new ByteArrayOutputStream();
		System.setOut(new PrintStream(consoleOut));

		ByteArrayInputStream consoleIn = new ByteArrayInputStream("sm\n14".getBytes());
		System.setIn(consoleIn);

		new ConsoleUserService(new Scanner(System.in),
				new ResourceLoaderImpl(new PathStorageImpl(Paths.get("src", "main", "resourses"))).getMessageStorage())
				.getNumber();

		String actual = consoleOut.toString();

		String expected = "\t" + "Oops! Something went wrong. Please check your input and try again." + "\n";
		assertEquals(expected, actual);
	}

	@Test
	void testGetNumber_null() {

		ByteArrayOutputStream consoleOut = new ByteArrayOutputStream();
		System.setOut(new PrintStream(consoleOut));

		ByteArrayInputStream consoleIn = new ByteArrayInputStream(" \n15".getBytes());
		System.setIn(consoleIn);

		new ConsoleUserService(new Scanner(System.in),
				new ResourceLoaderImpl(new PathStorageImpl(Paths.get("src", "main", "resourses"))).getMessageStorage())
				.getNumber();

		String actual = consoleOut.toString();

		try {
			consoleIn.read("15".getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}

		String expected = "\t" + "Oops! Something went wrong. Please check your input and try again." + "\n";
		assertEquals(expected, actual);
	}

	@Test
	void testIsNumber_false() {
		boolean actual = new ConsoleUserService(null, null).isNumber("25o36");

		assertFalse(actual);
	}

	@Test
	void testIsNumber() {
		boolean actual = new ConsoleUserService(null, null).isNumber("0152");

		assertTrue(actual);
	}

	@Test
	void testAddedMessage() {
		ByteArrayOutputStream consoleOut = new ByteArrayOutputStream();
		System.setOut(new PrintStream(consoleOut));

		new ConsoleUserService(null,
				new ResourceLoaderImpl(new PathStorageImpl(Paths.get("src", "main", "resourses"))).getMessageStorage())
				.addedMessage();

		String actual = consoleOut.toString();

		String expected = "\t" + "Successfully added." + "\n";
		assertEquals(expected, actual);
	}

	@Test
	void testDeletedMessage() {
		ByteArrayOutputStream consoleOut = new ByteArrayOutputStream();
		System.setOut(new PrintStream(consoleOut));

		new ConsoleUserService(null,
				new ResourceLoaderImpl(new PathStorageImpl(Paths.get("src", "main", "resourses"))).getMessageStorage())
				.deletedMessage();

		String actual = consoleOut.toString();

		String expected = "\t" + "Successfully deleted." + "\n";
		assertEquals(expected, actual);
	}

	@Test
	void testRemovedMessage() {
		ByteArrayOutputStream consoleOut = new ByteArrayOutputStream();
		System.setOut(new PrintStream(consoleOut));

		new ConsoleUserService(null,
				new ResourceLoaderImpl(new PathStorageImpl(Paths.get("src", "main", "resourses"))).getMessageStorage())
				.removedMessage();

		String actual = consoleOut.toString();

		String expected = "\t" + "Successfully removed." + "\n";
		assertEquals(expected, actual);
	}

	@Test
	void testAskWhatCourse() {
		ByteArrayOutputStream consoleOut = new ByteArrayOutputStream();
		System.setOut(new PrintStream(consoleOut));

		ByteArrayInputStream consoleIn = new ByteArrayInputStream("15".getBytes());
		System.setIn(consoleIn);

		List<String[]> previous = new ArrayList<>();
		previous.add(new String[] { "previous" });

		new ConsoleUserService(new Scanner(System.in),
				new ResourceLoaderImpl(new PathStorageImpl(Paths.get("src", "main", "resourses"))).getMessageStorage())
				.askWhatCourse(previous);

		String actual = consoleOut.toString();

		String expected = "\t" + "Please select a course number from the list below:" + "\n" + "\t" + "previous" + "\n";
		assertEquals(expected, actual);
	}

	@Test
	void testGetStudentsCount() {
		ByteArrayOutputStream consoleOut = new ByteArrayOutputStream();
		System.setOut(new PrintStream(consoleOut));

		ByteArrayInputStream consoleIn = new ByteArrayInputStream("15".getBytes());
		System.setIn(consoleIn);

		Integer actual = new ConsoleUserService(new Scanner(System.in),
				new ResourceLoaderImpl(new PathStorageImpl(Paths.get("src", "main", "resourses"))).getMessageStorage())
				.getStudentsCount();

		Integer expected = 15;
		assertEquals(expected, actual);
	}

	@Test
	void testPrintTable() {
		ByteArrayOutputStream consoleOut = new ByteArrayOutputStream();
		System.setOut(new PrintStream(consoleOut));

		List<String[]> table = new ArrayList<>();
		table.add(new String[] { "student_id", "first_name" });
		table.add(new String[] { "1", "petr" });
		table.add(new String[] { "null", "Taras" });
		table.add(new String[] { "3", "null" });
		new ConsoleUserService(null,
				new ResourceLoaderImpl(new PathStorageImpl(Paths.get("src", "main", "resourses"))).getMessageStorage())
				.printTable(table);

		String actual = consoleOut.toString();

		String expected = "\t" + "student_id	first_name\n" + "\t" + "1         	petr      \n" + "\t"
				+ "null      	Taras     \n" + "\t" + "3         	null      \n";
		assertEquals(expected, actual);
	}

	@Test
	void testGetCourseName() {
		ByteArrayOutputStream consoleOut = new ByteArrayOutputStream();
		System.setOut(new PrintStream(consoleOut));

		ByteArrayInputStream consoleIn = new ByteArrayInputStream("biology".getBytes());
		System.setIn(consoleIn);

		String actual = new ConsoleUserService(new Scanner(System.in),
				new ResourceLoaderImpl(new PathStorageImpl(Paths.get("src", "main", "resourses"))).getMessageStorage())
				.getCourseName();

		String expected = "biology";
		assertEquals(expected, actual);
	}

	@Test
	void testGetFirstName() {
		ByteArrayOutputStream consoleOut = new ByteArrayOutputStream();
		System.setOut(new PrintStream(consoleOut));

		ByteArrayInputStream consoleIn = new ByteArrayInputStream("Taras".getBytes());
		System.setIn(consoleIn);

		String actual = new ConsoleUserService(new Scanner(System.in),
				new ResourceLoaderImpl(new PathStorageImpl(Paths.get("src", "main", "resourses"))).getMessageStorage())
				.getFirstName();

		String expected = "Taras";
		assertEquals(expected, actual);
	}

	@Test
	void testGetLastName() {
		ByteArrayOutputStream consoleOut = new ByteArrayOutputStream();
		System.setOut(new PrintStream(consoleOut));

		ByteArrayInputStream consoleIn = new ByteArrayInputStream("Stepanko".getBytes());
		System.setIn(consoleIn);

		String actual = new ConsoleUserService(new Scanner(System.in),
				new ResourceLoaderImpl(new PathStorageImpl(Paths.get("src", "main", "resourses"))).getMessageStorage())
				.getLastName();

		String expected = "Stepanko";
		assertEquals(expected, actual);
	}

	@Test
	void testGetGroupId() {
		ByteArrayOutputStream consoleOut = new ByteArrayOutputStream();
		System.setOut(new PrintStream(consoleOut));

		ByteArrayInputStream consoleIn = new ByteArrayInputStream("15".getBytes());
		System.setIn(consoleIn);

		Integer actual = new ConsoleUserService(new Scanner(System.in),
				new ResourceLoaderImpl(new PathStorageImpl(Paths.get("src", "main", "resourses"))).getMessageStorage())
				.getGroupId();

		Integer expected = 15;
		assertEquals(expected, actual);
	}

	@Test
	void testGetStudentId() {
		ByteArrayOutputStream consoleOut = new ByteArrayOutputStream();
		System.setOut(new PrintStream(consoleOut));

		ByteArrayInputStream consoleIn = new ByteArrayInputStream("15".getBytes());
		System.setIn(consoleIn);

		Integer actual = new ConsoleUserService(new Scanner(System.in),
				new ResourceLoaderImpl(new PathStorageImpl(Paths.get("src", "main", "resourses"))).getMessageStorage())
				.getStudentId();

		Integer expected = 15;
		assertEquals(expected, actual);
	}

}
