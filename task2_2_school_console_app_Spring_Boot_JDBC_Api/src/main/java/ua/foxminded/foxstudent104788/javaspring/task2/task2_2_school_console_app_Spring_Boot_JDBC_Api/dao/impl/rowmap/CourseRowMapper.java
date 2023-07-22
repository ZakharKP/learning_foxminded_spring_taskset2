package ua.foxminded.foxstudent104788.javaspring.task2.task2_2_school_console_app_Spring_Boot_JDBC_Api.dao.impl.rowmap;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ua.foxminded.foxstudent104788.javaspring.task2.task2_2_school_console_app_Spring_Boot_JDBC_Api.models.Course;

/**
 * RowMapper implementation for mapping rows of the ResultSet to Course objects.
 */
public class CourseRowMapper implements RowMapper<Course> {

	/**
	 * Maps a row of the ResultSet to a Course object.
	 *
	 * @param rs     The ResultSet to be mapped.
	 * @param rowNum The number of the current row.
	 * @return The mapped Course object.
	 * @throws SQLException If an SQL exception occurs.
	 */
	@Override
	public Course mapRow(ResultSet rs, int rowNum) throws SQLException {

		Integer courseId = rs.getInt("course_id");
		String courseName = rs.getString("course_name");
		String courseDescription = rs.getString("course_description");

		return new Course(courseId, courseName, courseDescription);
	}

}
