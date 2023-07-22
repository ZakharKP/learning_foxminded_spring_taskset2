package ua.foxminded.foxstudent104788.javaspring.task2.task2_2_school_console_app_Spring_Boot_JDBC_Api.dao.impl.rowmap;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ua.foxminded.foxstudent104788.javaspring.task2.task2_2_school_console_app_Spring_Boot_JDBC_Api.models.Student;

/**
 * RowMapper implementation for mapping rows of the ResultSet to Student
 * objects.
 */
public class StudentRowMapper implements RowMapper<Student> {

	/**
	 * Maps a single row of the ResultSet to a Student object.
	 *
	 * @param rs     The ResultSet containing the row data.
	 * @param rowNum The row number being mapped.
	 * @return The mapped Student object.
	 * @throws SQLException if a SQLException is encountered during the mapping
	 *                      process.
	 */
	@Override
	public Student mapRow(ResultSet rs, int rowNum) throws SQLException {

		Integer studentId = rs.getInt("student_id");
		String firstName = rs.getString("first_name");
		String lastName = rs.getString("last_name");
		Integer groupId = rs.getInt("group_id");
		return new Student(studentId, firstName, lastName, groupId);
	}

}
