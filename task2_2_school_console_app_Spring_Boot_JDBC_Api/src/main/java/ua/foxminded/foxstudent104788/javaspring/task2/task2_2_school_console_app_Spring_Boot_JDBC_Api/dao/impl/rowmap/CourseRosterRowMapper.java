package ua.foxminded.foxstudent104788.javaspring.task2.task2_2_school_console_app_Spring_Boot_JDBC_Api.dao.impl.rowmap;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ua.foxminded.foxstudent104788.javaspring.task2.task2_2_school_console_app_Spring_Boot_JDBC_Api.models.CourseRoster;

/**
 * RowMapper implementation for mapping rows of the ResultSet to CourseRoster
 * objects.
 */
public class CourseRosterRowMapper implements RowMapper<CourseRoster> {

	/**
	 * Maps a row of the ResultSet to a CourseRoster object.
	 *
	 * @param rs     The ResultSet to be mapped.
	 * @param rowNum The number of the current row.
	 * @return The mapped CourseRoster object.
	 * @throws SQLException If an SQL exception occurs.
	 */
	@Override
	public CourseRoster mapRow(ResultSet rs, int rowNum) throws SQLException {

		Integer courseRosterId = rs.getInt("course_roster_id");
		Integer studentId = rs.getInt("student_id");
		Integer courseId = rs.getInt("course_id");
		return new CourseRoster(courseRosterId, studentId, courseId);
	}

}