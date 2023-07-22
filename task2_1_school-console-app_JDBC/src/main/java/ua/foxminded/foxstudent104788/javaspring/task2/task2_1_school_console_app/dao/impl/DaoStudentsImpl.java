package ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.dao.DaoConnectionHolder;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.dao.DaoStudents;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.models.Student;

@AllArgsConstructor
public class DaoStudentsImpl implements DaoStudents {

	DaoConnectionHolder connection;

	/**
	 * Adds a student to the database.
	 *
	 * @param student The student to be added.
	 * @return The number of rows affected (usually 1) if the student was added
	 *         successfully, or -1 if an error occurred.
	 */
	@Override
	public int addStudents(List<Student> students) {
		Map<Object[], String> statement = new HashMap<>();
		String query = null;
		Object[] parametres = null;
		List<String> columnNames;
		for (Student student : students) {
			columnNames = getColumnNames(student);
			if (columnNames.isEmpty()) {
				return -1;
			}

			query = getInsertQuery(columnNames);
			parametres = getParametres(columnNames.size(), student);
			statement.put(parametres, query);
		}

		return connection.updateExecute(statement);
	}

	/**
	 * Adds multiple students to the database.
	 *
	 * @param students The list of students to be added.
	 * @return The number of rows affected if the students were added successfully,
	 *         or -1 if an error occurred.
	 */
	@Override
	public int addStudent(Student student) {

		String query = null;
		Object[] parametres = null;
		List<String> columnNames = getColumnNames(student);

		if (columnNames.isEmpty()) {
			return -1;
		}

		query = getInsertQuery(columnNames);
		parametres = getParametres(columnNames.size(), student);

		return connection.updateExecute(query, parametres);
	}

	/**
	 * Retrieves students by their IDs.
	 *
	 * @param studentsId The list of student IDs.
	 * @return A list of students associated with the specified IDs.
	 */
	@Override
	public List<Student> getById(List<Integer> studentsIds) {

		StringBuilder query = new StringBuilder();

		Student student;
		List<String> columnNames = new ArrayList<>();

		List<Object[]> parametres = new ArrayList<>();

		for (Integer studentId : studentsIds) {
			student = new Student(studentId, null, null, null);
			if (columnNames.isEmpty()) {
				columnNames.addAll(getColumnNames(student));
			}
			parametres.add(getParametres(columnNames.size(), student));
		}

		query.append(getSelectAllQuery());
		query.append(getWhere(columnNames));

		List<Map<String, String>> studentsData = connection.selectExecute(query.toString(), parametres);
		List<Student> students = getStudents(studentsData);

		return students;

	}

	/**
	 * Retrieves a table representation of the students.
	 *
	 * @param students The list of students.
	 * @return A list of string arrays representing the table, with each string
	 *         array representing a row of the table.
	 */
	@Override
	public List<String[]> getTable(List<Student> students) {
		List<String[]> table = new ArrayList<>();
		table.add(new String[] { "student_id", "first_name", "last_name", "group_id" });
		for (Student student : students) {
			table.add(new String[] { String.valueOf(student.getId()), String.valueOf(student.getFirstName()),
					String.valueOf(student.getLastName()), String.valueOf(student.getGroupId()) });
		}

		return table;
	}

	/**
	 * Deletes a student from the database.
	 *
	 * @param student The student to be deleted.
	 * @return The number of rows affected (usually 1) if the student was deleted
	 *         successfully, or -1 if an error occurred.
	 */
	@Override
	public int delete(Student student) {
		StringBuilder query = new StringBuilder();
		Object[] parametres = null;
		List<String> columnNames = getColumnNames(student);

		if (columnNames.isEmpty()) {
			return -1;
		}

		query.append(getDeleteQuery());
		query.append(getWhere(columnNames));

		parametres = getParametres(columnNames.size(), student);

		return connection.updateExecute(query.toString(), parametres);
	}

	/**
	 * Retrieves the group IDs based on the count of students in each group.
	 *
	 * @param studentsCount The count of students in a group.
	 * @return A list of students associated with the specified group count.
	 */
	@Override
	public List<Student> getGroupIdByGroupCount(Integer studentsCount) {
		StringBuilder query = new StringBuilder();

		List<String> columnNames = new ArrayList<>();
		columnNames.add("group_id");

		query.append(getSelectColumnsQuery(columnNames));
		query.append(getCountByQuery("group_id", "<="));

		List<Map<String, String>> studentsData = connection.selectExecute(query.toString(), studentsCount);
		List<Student> students = getStudents(studentsData);

		return students;

	}

	private String getInsertQuery(List<String> columnNames) {
		StringBuilder query = new StringBuilder("INSERT INTO students (");
		StringBuilder values = new StringBuilder(" VALUES (");
		for (String columnName : columnNames) {
			query.append(columnName);
			query.append(", ");
			values.append("?");
			values.append(", ");
		}
		deleteLastComa(query);
		deleteLastComa(values);
		query.append(")");
		values.append(")");
		query.append(values.toString());
		return query.toString();
	}

	private List<Student> getStudents(List<Map<String, String>> studentsData) {
		List<Student> students = new ArrayList<>();
		Integer id;
		String firstName;
		String lastName;
		Integer groupId;

		if (!studentsData.get(0).get("table_name").equals("students")) {
			return students;
		}

		for (int i = 1; i < studentsData.size(); i++) {
			id = studentsData.get(i).get("student_id") == null ? null
					: Integer.parseInt(studentsData.get(i).get("student_id"));
			firstName = studentsData.get(i).get("first_name") == null ? null : studentsData.get(i).get("first_name");
			lastName = studentsData.get(i).get("last_name") == null ? null : studentsData.get(i).get("last_name");
			groupId = studentsData.get(i).get("group_id") == null ? null
					: Integer.parseInt(studentsData.get(i).get("group_id"));

			students.add(new Student(id, firstName, lastName, groupId));
		}

		return students;
	}

	private String getWhere(List<String> columnNames) {
		StringBuilder query = new StringBuilder("WHERE ");

		for (String columnName : columnNames) {
			query.append(columnName);
			query.append(" = ");
			query.append("?");
			query.append(" AND ");

		}
		deleteLastAnd(query);

		return query.toString();
	}

	private String getSelectAllQuery() {
		return "SELECT * FROM students ";
	}

	private String getSelectColumnsQuery(List<String> columnNames) {
		StringBuilder query = new StringBuilder("SELECT ");
		for (String columnName : columnNames) {
			query.append(columnName);
			query.append(", ");
		}

		deleteLastComa(query);
		query.append(" FROM students ");
		return query.toString();
	}

	private String getDeleteQuery() {
		return "DELETE FROM students ";
	}

	private Object getCountByQuery(String columnCount, String lessOrMore) {

		return String.format("GROUP BY %s HAVING COUNT(*) %s ?", columnCount, lessOrMore);
	}

	private List<String> getColumnNames(Student student) {
		List<String> columnNames = new ArrayList<>();
		if (student.getId() != null) {
			columnNames.add("student_id");
		}
		if (student.getFirstName() != null) {
			columnNames.add("first_name");
		}
		if (student.getLastName() != null) {
			columnNames.add("last_name");
		}
		if (student.getGroupId() != null) {
			columnNames.add("group_id");
		}
		return columnNames;
	}

	private Object[] getParametres(int size, Student student) {
		Object[] parametres = new Object[size];
		int i = 0;
		if (student.getId() != null) {
			parametres[i] = student.getId();
			i++;
		}
		if (student.getFirstName() != null) {
			parametres[i] = student.getFirstName();
			i++;
		}
		if (student.getLastName() != null) {
			parametres[i] = student.getLastName();
			i++;
		}
		if (student.getGroupId() != null) {
			parametres[i] = student.getGroupId();
		}

		return parametres;
	}

	private void deleteLastAnd(StringBuilder query) {
		query.delete(query.length() - 5, query.length());

	}

	private void deleteLastComa(StringBuilder query) {
		query.delete(query.length() - 2, query.length());
	}
}
