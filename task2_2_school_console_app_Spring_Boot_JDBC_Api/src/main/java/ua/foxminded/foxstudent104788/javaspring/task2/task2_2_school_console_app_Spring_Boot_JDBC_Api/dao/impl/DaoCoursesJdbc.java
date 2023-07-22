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
import ua.foxminded.foxstudent104788.javaspring.task2.task2_2_school_console_app_Spring_Boot_JDBC_Api.dao.DaoCourses;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_2_school_console_app_Spring_Boot_JDBC_Api.dao.impl.rowmap.CourseRowMapper;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_2_school_console_app_Spring_Boot_JDBC_Api.models.Course;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_2_school_console_app_Spring_Boot_JDBC_Api.services.QueryBuilder;

/**
 * Implementation of the {@link DaoCourses} interface.
 */
@AllArgsConstructor
@Repository
public class DaoCoursesJdbc implements DaoCourses {

	private static final Logger logger = LogManager.getLogger(DaoCoursesJdbc.class);

	private final JdbcTemplate jdbcTemplate;

	private final QueryBuilder queryBuilder = new QueryBuilder("courses");

	private final RowMapper<Course> rowMapper = new CourseRowMapper();

	/**
	 * Adds a single course to the database.
	 *
	 * @param course the course to add
	 * @return the number of affected rows in the database
	 */
	@Override
	public int save(Course course) {

		String query = null;
		Object[] parametres = null;
		List<String> columnNames = getColumnNames(course);

		if (columnNames.isEmpty()) {
			return -1;
		}

		query = queryBuilder.getInsertQuery(columnNames);
		parametres = getParametres(columnNames.size(), course);

		return jdbcTemplate.update(query, parametres);
	}

	/**
	 * Retrieves a Course by its ID.
	 *
	 * @param id The ID of the Course to retrieve.
	 * @return An Optional containing the Course associated with the specified ID if
	 *         it exists, or an empty Optional if it doesn't exist.
	 */
	@Override
	public Optional<Course> get(Integer id) {
		StringBuilder query = new StringBuilder(queryBuilder.getSelectAllQuery());
		query.append(queryBuilder.getWhere("course_id"));

		try {
			return Optional.of(jdbcTemplate.queryForObject(query.toString(), rowMapper, id));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	/**
	 * Retrieves a list of all courses from the database.
	 *
	 * @return the list of Course objects
	 */
	@Override
	public List<Course> getAll() {
		List<Map<String, Object>> coursesData = jdbcTemplate.queryForList(queryBuilder.getSelectAllQuery());

		return getCoures(coursesData);

	}

	/**
	 * Retrieves a Course object by its course name.
	 *
	 * @param courseName the course name
	 * @return the Course object with the given name, or a Course object with null
	 *         values if not found
	 */
	@Override
	public Optional<Course> getByName(String courseName) {

		StringBuilder query = new StringBuilder();

		Course course = new Course(null, courseName, null);

		List<String> columnNames = getColumnNames(course);

		query.append(queryBuilder.getSelectAllQuery());
		query.append(queryBuilder.getWhere("course_name"));

		try {
			return Optional.of(jdbcTemplate.queryForObject(query.toString(), rowMapper, courseName));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}

	}

	/**
	 * Deletes a Course from the database.
	 *
	 * @param course The Course to be deleted.
	 * @return The number of rows affected (usually 1) if the Course was deleted
	 *         successfully, or -1 if an error occurred.
	 */
	@Override
	public int delete(Course course) {
		StringBuilder query = new StringBuilder();
		Object[] parametres = null;
		List<String> columnNames = getColumnNames(course);
		parametres = getParametres(columnNames.size(), course);

		if (columnNames.isEmpty()) {
			return -1;
		}

		query.append(queryBuilder.getDeleteQuery());
		query.append(queryBuilder.getWhere(columnNames));

		return jdbcTemplate.update(query.toString(), parametres);
	}

	/**
	 * Retrieves a table representation of the list of courses.
	 *
	 * @param courses the list of courses
	 * @return a list of String arrays representing the table data
	 */
	@Override
	public List<String[]> representAsTable(List<Course> courses) {

		List<String[]> table = new ArrayList<>();
		table.add(new String[] { "course_id", "course_name", "course_description" });
		for (Course course : courses) {
			table.add(new String[] { String.valueOf(course.getId()), String.valueOf(course.getCourseName()),
					String.valueOf(course.getCourseDescription()) });
		}

		return table;
	}

	private List<Course> getCoures(List<Map<String, Object>> courseData) {
		List<Course> courses = new ArrayList<>();
		Integer id;
		String courseName;
		String courseDescription;

		for (int i = 0; i < courseData.size(); i++) {

			id = courseData.get(i).get("course_id") == null ? null : (Integer) courseData.get(i).get("course_id");
			courseName = courseData.get(i).get("course_name") == null ? null
					: (String) courseData.get(i).get("course_name");
			courseDescription = courseData.get(i).get("course_description") == null ? null
					: (String) courseData.get(i).get("course_description");
			courses.add(new Course(id, courseName, courseDescription));
		}

		return courses;
	}

	private Object[] getParametres(int size, Course course) {
		Object[] parametres = new Object[size];
		int i = 0;
		if (course.getId() != null) {
			parametres[i] = course.getId();
			i++;
		}
		if (course.getCourseName() != null) {
			parametres[i] = course.getCourseName();
			i++;
		}
		if (course.getCourseDescription() != null) {
			parametres[i] = course.getCourseDescription();
			i++;
		}

		return parametres;
	}

	private List<String> getColumnNames(Course course) {
		List<String> columnNames = new ArrayList<>();
		if (course.getId() != null) {
			columnNames.add("course_id");
		}
		if (course.getCourseName() != null) {
			columnNames.add("course_name");
		}
		if (course.getCourseDescription() != null) {
			columnNames.add("course_description");
		}

		return columnNames;
	}

}
