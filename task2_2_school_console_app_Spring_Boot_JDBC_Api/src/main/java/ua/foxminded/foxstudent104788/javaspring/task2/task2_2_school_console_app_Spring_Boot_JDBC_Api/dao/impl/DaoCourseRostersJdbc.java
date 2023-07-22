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
import ua.foxminded.foxstudent104788.javaspring.task2.task2_2_school_console_app_Spring_Boot_JDBC_Api.dao.DaoCourseRosters;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_2_school_console_app_Spring_Boot_JDBC_Api.dao.impl.rowmap.CourseRosterRowMapper;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_2_school_console_app_Spring_Boot_JDBC_Api.models.CourseRoster;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_2_school_console_app_Spring_Boot_JDBC_Api.services.QueryBuilder;

/**
 * Implementation of the {@link DaoCourseRosters} interface for managing
 * enrollments in the database.
 */
@AllArgsConstructor
@Repository
public class DaoCourseRostersJdbc implements DaoCourseRosters {

	private static final Logger logger = LogManager.getLogger(DaoCourseRostersJdbc.class);

	private final JdbcTemplate jdbcTemplate;

	private final QueryBuilder queryBuilder = new QueryBuilder("course_rosters");

	private final RowMapper<CourseRoster> rowMapper = new CourseRosterRowMapper();

	/**
	 * Adds an enrollment to the database.
	 *
	 * @param courseRoster the enrollment to add
	 * @return the number of affected rows in the database
	 */
	@Override
	public int save(CourseRoster courseRoster) {
		String query = null;
		Object[] parametres = null;
		List<String> columnNames = getColumnNames(courseRoster);

		if (columnNames.isEmpty()) {
			return -1;
		}

		query = queryBuilder.getInsertQuery(columnNames);
		parametres = getParametres(columnNames.size(), courseRoster);

		return jdbcTemplate.update(query, parametres);
	}

	/**
	 * Retrieves a CourseRoster by its ID.
	 *
	 * @param id The ID of the CourseRoster.
	 * @return An Optional containing the CourseRoster associated with the specified
	 *         ID if it exists, or an empty Optional if not found.
	 */
	@Override
	public Optional<CourseRoster> get(Integer id) {
		StringBuilder query = new StringBuilder(queryBuilder.getSelectAllQuery());
		query.append(queryBuilder.getWhere("course_roster_id"));

		try {
			return Optional.of(jdbcTemplate.queryForObject(query.toString(), rowMapper, id));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	/**
	 * Retrieves all CourseRosters.
	 *
	 * @return A list of all CourseRosters in the database.
	 */
	@Override
	public List<CourseRoster> getAll() {
		List<Map<String, Object>> courseRoasterData = jdbcTemplate.queryForList(queryBuilder.getSelectAllQuery());

		return getCourseRoasters(courseRoasterData);
	}

	/**
	 * Deletes an enrollment from the database.
	 *
	 * @param courseRoster the enrollment to delete
	 * @return the number of affected rows in the database
	 */
	@Override
	public int delete(CourseRoster courseRoster) {
		StringBuilder query = new StringBuilder();
		Object[] parametres = null;
		List<String> columnNames = getColumnNames(courseRoster);
		parametres = getParametres(columnNames.size(), courseRoster);

		if (columnNames.isEmpty()) {
			return -1;
		}

		query.append(queryBuilder.getDeleteQuery());
		query.append(queryBuilder.getWhere(columnNames));

		return jdbcTemplate.update(query.toString(), parametres);
	}

	/**
	 * Retrieves a list of enrollments by course ID.
	 *
	 * @param courseId the ID of the course
	 * @return the list of enrollments for the given course ID
	 */
	@Override
	public List<CourseRoster> getListOfRostersByCourseId(Integer courseId) {

		StringBuilder query = new StringBuilder();

		query.append(queryBuilder.getSelectAllQuery());
		query.append(queryBuilder.getWhere("course_id"));

		List<Map<String, Object>> courseRostersData = jdbcTemplate.queryForList(query.toString(), courseId);
		List<CourseRoster> courseRosters = getCourseRoasters(courseRostersData);

		return courseRosters;

	}

	/**
	 * Retrieves a table representation of the CourseRosters.
	 *
	 * @param courseRosters The list of CourseRosters.
	 * @return A list of string arrays representing the table, with each string
	 *         array representing a row of the table.
	 */
	@Override
	public List<String[]> representAsTable(List<CourseRoster> courseRosters) {
		List<String[]> table = new ArrayList<>();
		table.add(new String[] { "course_roaster_id", "student_id", "course_id" });
		for (CourseRoster courseRoster : courseRosters) {
			table.add(new String[] { String.valueOf(courseRoster.getId()), String.valueOf(courseRoster.getStudentId()),
					String.valueOf(courseRoster.getCourseId()) });
		}

		return table;
	}

	private List<CourseRoster> getCourseRoasters(List<Map<String, Object>> courseRoastersData) {
		List<CourseRoster> courseRosters = new ArrayList<>();
		Integer id;
		Integer studentId;
		Integer courseId;

		for (int i = 0; i < courseRoastersData.size(); i++) {
			id = courseRoastersData.get(i).get("course_roaster_id") == null ? null
					: (Integer) courseRoastersData.get(i).get("course_roster_id");
			studentId = courseRoastersData.get(i).get("student_id") == null ? null
					: (Integer) courseRoastersData.get(i).get("student_id");
			courseId = courseRoastersData.get(i).get("course_id") == null ? null
					: (Integer) courseRoastersData.get(i).get("course_id");
			courseRosters.add(new CourseRoster(id, studentId, courseId));
		}

		return courseRosters;
	}

	private List<String> getColumnNames(CourseRoster courseRoster) {
		List<String> columnNames = new ArrayList<>();
		if (courseRoster.getId() != null) {
			columnNames.add("course_roster_id");
		}
		if (courseRoster.getStudentId() != null) {
			columnNames.add("student_id");
		}
		if (courseRoster.getCourseId() != null) {
			columnNames.add("course_id");
		}

		return columnNames;
	}

	private Object[] getParametres(int size, CourseRoster courseRoster) {
		Object[] parametres = new Object[size];
		int i = 0;
		if (courseRoster.getId() != null) {
			parametres[i] = courseRoster.getId();
			i++;
		}
		if (courseRoster.getStudentId() != null) {
			parametres[i] = courseRoster.getStudentId();
			i++;
		}
		if (courseRoster.getCourseId() != null) {
			parametres[i] = courseRoster.getCourseId();
		}

		return parametres;
	}


}
