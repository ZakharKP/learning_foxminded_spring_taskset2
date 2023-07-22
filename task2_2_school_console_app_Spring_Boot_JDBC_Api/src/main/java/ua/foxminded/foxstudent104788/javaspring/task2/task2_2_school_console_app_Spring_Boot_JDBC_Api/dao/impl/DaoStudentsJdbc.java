package ua.foxminded.foxstudent104788.javaspring.task2.task2_2_school_console_app_Spring_Boot_JDBC_Api.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import lombok.AllArgsConstructor;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_2_school_console_app_Spring_Boot_JDBC_Api.dao.DaoStudents;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_2_school_console_app_Spring_Boot_JDBC_Api.dao.impl.rowmap.StudentRowMapper;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_2_school_console_app_Spring_Boot_JDBC_Api.models.Student;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_2_school_console_app_Spring_Boot_JDBC_Api.services.QueryBuilder;

/**
 * Implementation of the {@link DaoStudents} interface using JDBC and Spring
 * Boot.
 */
@AllArgsConstructor
@Repository
public class DaoStudentsJdbc implements DaoStudents {

	private static final Logger logger = LogManager.getLogger(DaoStudentsJdbc.class);

	private final JdbcTemplate jdbcTemplate;

	private final QueryBuilder queryBuilder = new QueryBuilder("students");

	private final RowMapper<Student> rowMapper = new StudentRowMapper();

	/**
	 * Adds multiple students to the database.
	 *
	 * @param students The list of students to be added.
	 * @return The number of rows affected if the students were added successfully,
	 *         or -1 if an error occurred.
	 */
	@Override
	public int save(Student student) {

		String query = null;
		Object[] parametres = null;
		List<String> columnNames = getColumnNames(student);

		if (columnNames.isEmpty()) {
			return -1;
		}

		query = queryBuilder.getInsertQuery(columnNames);
		parametres = getParametres(columnNames.size(), student);

		return jdbcTemplate.update(query, parametres);
	}

	

	/**
	 * Retrieves a student from the database based on the given ID.
	 *
	 * @param id The ID of the student to retrieve.
	 * @return An {@link Optional} containing the student if found, or an empty
	 *         {@link Optional} if the student doesn't exist.
	 */
	@Override
	public Optional<Student> get(Integer id) {
		StringBuilder query = new StringBuilder(queryBuilder.getSelectAllQuery());
		query.append(queryBuilder.getWhere("student_id"));

		try {
			return Optional.of(jdbcTemplate.queryForObject(query.toString(), rowMapper, id));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	/**
	 * Retrieves students by their IDs.
	 *
	 * @param studentsId The list of student IDs.
	 * @return A list of students associated with the specified IDs.
	 */
	@Override
	public List<Student> get(List<Integer> studentsIds) {

		List<Student> students = new ArrayList<>();
		Student student;

		for (Integer studentId : studentsIds) {
			students.add(get(studentId).get());
		}

		return students;

	}

	/**
	 * Retrieves all students from the database.
	 *
	 * @return A list of all students in the database.
	 */
	@Override
	public List<Student> getAll() {

		List<Map<String, Object>> studentsData = jdbcTemplate.queryForList(queryBuilder.getSelectAllQuery());

		return getStudents(studentsData);
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
		parametres = getParametres(columnNames.size(), student);

		if (columnNames.isEmpty()) {
			return -1;
		}

		query.append(queryBuilder.getDeleteQuery());
		query.append(queryBuilder.getWhere(columnNames));

		return jdbcTemplate.update(query.toString(), parametres);
	}

	/**
	 * Retrieves the group IDs based on the count of students in each group.
	 *
	 * @param groupsCount The count of students in a group.
	 * @return A list of students associated with the specified group count.
	 */
	@Override
	public List<Integer> getGroupSIdCount(Integer groupsCount) {

		List<Integer> groupIds = new ArrayList<>();

		StringBuilder query = new StringBuilder();
		List<String> columnNames = getColumnNames(new Student(null, null, null, groupsCount));

		query.append(queryBuilder.getSelectColumnsQuery(columnNames));
		query.append(queryBuilder.getCountByQuery("group_id", "<="));

		List<Map<String, Object>> studentsData = jdbcTemplate.queryForList(query.toString(), groupsCount);

		for (Student student : getStudents(studentsData)) {
			if (!groupIds.contains(student.getGroupId()) && student.getGroupId() != null) {
				groupIds.add(student.getGroupId());
			}
		}

		return groupIds;

	}

	/**
	 * Retrieves a table representation of the students.
	 *
	 * @param students The list of students.
	 * @return A list of string arrays representing the table, with each string
	 *         array representing a row of the table.
	 */
	@Override
	public List<String[]> representAsTable(List<Student> students) {
		List<String[]> table = new ArrayList<>();
		table.add(new String[] { "student_id", "first_name", "last_name", "group_id" });
		for (Student student : students) {
			table.add(new String[] { String.valueOf(student.getId()), String.valueOf(student.getFirstName()),
					String.valueOf(student.getLastName()), String.valueOf(student.getGroupId()) });
		}

		return table;
	}

	private List<Student> getStudents(List<Map<String, Object>> studentsData) {
		List<Student> students = new ArrayList<>();
		Integer id;
		String firstName;
		String lastName;
		Integer groupId;

		for (int i = 0; i < studentsData.size(); i++) {
			id = studentsData.get(i).get("student_id") == null ? null : (Integer) studentsData.get(i).get("student_id");
			firstName = studentsData.get(i).get("first_name") == null ? null
					: (String) studentsData.get(i).get("first_name");
			lastName = studentsData.get(i).get("last_name") == null ? null
					: (String) studentsData.get(i).get("last_name");
			groupId = studentsData.get(i).get("group_id") == null ? null
					: (Integer) studentsData.get(i).get("group_id");

			students.add(new Student(id, firstName, lastName, groupId));
		}

		return students;
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

}
