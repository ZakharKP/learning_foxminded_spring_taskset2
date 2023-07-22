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
import ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.dao.DaoCourseRosters;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.dao.impl.helpers.QueryBuilder;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.dao.impl.rowmap.CourseRosterRowMapper;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.models.Constants;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.models.CourseRoster;

/**
 * Implementation of the {@link DaoCourseRosters} interface for managing
 * enrollments in the database.
 */
@Log4j2
@AllArgsConstructor
@Repository
public class DaoCourseRostersJdbc implements DaoCourseRosters {

	private final JdbcTemplate jdbcTemplate;

	private final QueryBuilder queryBuilder = new QueryBuilder(Constants.COURSE_ROSTERS_TABLE_NAME);

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

		log.info(String.format("Saving new course_roster: id=%s, student_id=%s, course_id=%s", courseRoster.getId(),
				courseRoster.getStudentId(), courseRoster.getCourseId()));

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
		query.append(queryBuilder.getWhere(Constants.COURSE_ROSTERS_COLUMN_NAME_ID));

		log.info("Getting course roster by id=" + id);

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

		log.info("Getting all course Rosters");

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

		log.info(String.format("Deleting course_roster: id=%s, student_id=%s, course_id=%s", courseRoster.getId(),
				courseRoster.getStudentId(), courseRoster.getCourseId()));

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
		query.append(queryBuilder.getWhere(Constants.COURSE_COLUMN_NAME_ID));

		List<Map<String, Object>> courseRostersData = jdbcTemplate.queryForList(query.toString(), courseId);
		List<CourseRoster> courseRosters = getCourseRoasters(courseRostersData);

		log.info("Getting all course rosters by course_id=" + courseId);

		return courseRosters;

	}

	private List<CourseRoster> getCourseRoasters(List<Map<String, Object>> courseRoastersData) {
		List<CourseRoster> courseRosters = new ArrayList<>();
		Integer id;
		Integer studentId;
		Integer courseId;

		for (int i = 0; i < courseRoastersData.size(); i++) {
			id = courseRoastersData.get(i).get(Constants.COURSE_ROSTERS_COLUMN_NAME_ID) == null ? null
					: (Integer) courseRoastersData.get(i).get(Constants.COURSE_ROSTERS_COLUMN_NAME_ID);
			studentId = courseRoastersData.get(i).get(Constants.STUDENT_COLUMN_NAME_ID) == null ? null
					: (Integer) courseRoastersData.get(i).get(Constants.STUDENT_COLUMN_NAME_ID);
			courseId = courseRoastersData.get(i).get(Constants.COURSE_COLUMN_NAME_ID) == null ? null
					: (Integer) courseRoastersData.get(i).get(Constants.COURSE_COLUMN_NAME_ID);
			courseRosters.add(new CourseRoster(id, studentId, courseId));
		}

		return courseRosters;
	}

	private List<String> getColumnNames(CourseRoster courseRoster) {
		List<String> columnNames = new ArrayList<>();
		if (courseRoster.getId() != null) {
			columnNames.add(Constants.COURSE_ROSTERS_COLUMN_NAME_ID);
		}
		if (courseRoster.getStudentId() != null) {
			columnNames.add(Constants.STUDENT_COLUMN_NAME_ID);
		}
		if (courseRoster.getCourseId() != null) {
			columnNames.add(Constants.COURSE_COLUMN_NAME_ID);
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

	@Override
	public int size() {

		log.info("getiing course rosters count");

		return jdbcTemplate.queryForObject(queryBuilder.getSelectCountQuery(), Integer.class);
	}

}
