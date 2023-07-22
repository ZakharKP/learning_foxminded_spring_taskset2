package ua.foxminded.foxstudent104788.javaspring.task2.task2_2_school_console_app_Spring_Boot_JDBC_Api.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import ua.foxminded.foxstudent104788.javaspring.task2.task2_2_school_console_app_Spring_Boot_JDBC_Api.services.QueryBuilder;

class QueryBuilderTest {

	@Test
	void testGetInsertQuery() {
		QueryBuilder queryBuilder = new QueryBuilder("students");
		List<String> columnNames = new ArrayList<>();
		columnNames.add("first_name");
		columnNames.add("last_name");

		String actual = queryBuilder.getInsertQuery(columnNames);
		String expected = "INSERT INTO students (first_name, last_name) VALUES (?, ?)";

		assertEquals(expected, actual);
	}

	@Test
	void testGetSelectAllQuery() {
		QueryBuilder queryBuilder = new QueryBuilder("students");

		String actual = queryBuilder.getSelectAllQuery();
		String expected = "SELECT * FROM students ";

		assertEquals(expected, actual);
	}

	@Test
	void testGetSelectColumnsQuery() {
		QueryBuilder queryBuilder = new QueryBuilder("students");
		List<String> columnNames = new ArrayList<>();
		columnNames.add("first_name");
		columnNames.add("last_name");

		String actual = queryBuilder.getSelectColumnsQuery(columnNames);
		String expected = "SELECT first_name, last_name FROM students ";

		assertEquals(expected, actual);
	}

	@Test
	void testGetDeleteQuery() {
		QueryBuilder queryBuilder = new QueryBuilder("students");

		String actual = queryBuilder.getDeleteQuery();
		String expected = "DELETE FROM students ";

		assertEquals(expected, actual);
	}

	@Test
	void testGetWhereListOfString() {
		QueryBuilder queryBuilder = new QueryBuilder("students");
		List<String> columnNames = new ArrayList<>();
		columnNames.add("first_name");
		columnNames.add("last_name");

		String actual = queryBuilder.getWhere(columnNames);
		String expected = "WHERE first_name = ? AND last_name = ?";

		assertEquals(expected, actual);
	}

	@Test
	void testGetWhereString() {
		QueryBuilder queryBuilder = new QueryBuilder("students");

		String actual = queryBuilder.getWhere("first_name");
		String expected = "WHERE first_name = ?";

		assertEquals(expected, actual);
	}

	@Test
	void testGetCountByQuery() {
		QueryBuilder queryBuilder = new QueryBuilder("students");

		String actual = queryBuilder.getCountByQuery("first_name", "<=");
		String expected = "GROUP BY first_name HAVING COUNT(first_name) <= ?";

		assertEquals(expected, actual);
	}

	
}
