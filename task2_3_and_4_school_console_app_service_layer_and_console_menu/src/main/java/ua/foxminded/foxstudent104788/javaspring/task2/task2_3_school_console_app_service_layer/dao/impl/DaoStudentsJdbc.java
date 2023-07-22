package ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.dao.impl;

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
import lombok.extern.log4j.Log4j2;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.dao.DaoStudents;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.dao.impl.helpers.QueryBuilder;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.dao.impl.rowmap.StudentRowMapper;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.models.Constants;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.models.Student;

/**
 * Implementation of the {@link DaoStudents} interface using JDBC and Spring
 * Boot.
 */
@Log4j2
@AllArgsConstructor
@Repository
public class DaoStudentsJdbc implements DaoStudents {

	private final JdbcTemplate jdbcTemplate;

	private final QueryBuilder queryBuilder = new QueryBuilder(Constants.STUDENT_TABLE_NAME);

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

		log.info(String.format("Saving new student: id=%s, %s %s, in the %s group", student.getId(),
				student.getFirstName(), student.getLastName(), student.getGroupId()));

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
		query.append(queryBuilder.getWhere(Constants.STUDENT_COLUMN_NAME_ID));

		log.info("getting student by id=" + id);
		
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

		log.info("getting students by list of ids");

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

		log.info("getting all students");

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

		log.info(String.format("Deleting student: id=%s, %s %s, in the %s group", student.getId(),
				student.getFirstName(), student.getLastName(), student.getGroupId()));

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
		query.append(queryBuilder.getGroupByCountQuery(Constants.GROUP_COLUMN_NAME_ID, Constants.LESS_OR_EQUALS));

		List<Map<String, Object>> studentsData = jdbcTemplate.queryForList(query.toString(), groupsCount);

		for (Student student : getStudents(studentsData)) {
			if (!groupIds.contains(student.getGroupId()) && student.getGroupId() != null) {
				groupIds.add(student.getGroupId());
			}
		}

		log.info("preparing groupids list by student count");

		return groupIds;

	}

	private List<Student> getStudents(List<Map<String, Object>> studentsData) {
		List<Student> students = new ArrayList<>();
		Integer id;
		String firstName;
		String lastName;
		Integer groupId;

		for (int i = 0; i < studentsData.size(); i++) {
			id = studentsData.get(i).get(Constants.STUDENT_COLUMN_NAME_ID) == null ? null
					: (Integer) studentsData.get(i).get(Constants.STUDENT_COLUMN_NAME_ID);
			firstName = studentsData.get(i).get(Constants.STUDENT_COLUMN_NAME_FIRST_NAME) == null ? null
					: (String) studentsData.get(i).get(Constants.STUDENT_COLUMN_NAME_FIRST_NAME);
			lastName = studentsData.get(i).get(Constants.STUDENT_COLUMN_NAME_LAST_NAME) == null ? null
					: (String) studentsData.get(i).get(Constants.STUDENT_COLUMN_NAME_LAST_NAME);
			groupId = studentsData.get(i).get(Constants.GROUP_COLUMN_NAME_ID) == null ? null
					: (Integer) studentsData.get(i).get(Constants.GROUP_COLUMN_NAME_ID);

			students.add(new Student(id, firstName, lastName, groupId));
		}

		return students;
	}

	private List<String> getColumnNames(Student student) {
		List<String> columnNames = new ArrayList<>();
		if (student.getId() != null) {
			columnNames.add(Constants.STUDENT_COLUMN_NAME_ID);
		}
		if (student.getFirstName() != null) {
			columnNames.add(Constants.STUDENT_COLUMN_NAME_FIRST_NAME);
		}
		if (student.getLastName() != null) {
			columnNames.add(Constants.STUDENT_COLUMN_NAME_LAST_NAME);
		}
		if (student.getGroupId() != null) {
			columnNames.add(Constants.GROUP_COLUMN_NAME_ID);
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

	@Override
	public int size() {

		log.info("counting all students");

		return jdbcTemplate.queryForObject(queryBuilder.getSelectCountQuery(), Integer.class);
	}

}
