package ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.dao.impl.rowmap;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.models.Group;

/**
 * Implementation of the {@link RowMapper} interface used to map rows from a
 * {@link ResultSet} to {@link Group} objects.
 */
public class GroupRowMapper implements RowMapper<Group> {

	/**
	 * Maps a row from the ResultSet to a Group object.
	 *
	 * @param rs     The ResultSet containing the row data.
	 * @param rowNum The row number.
	 * @return The Group object mapped from the row data.
	 * @throws SQLException If an SQL exception occurs.
	 */
	@Override
	public Group mapRow(ResultSet rs, int rowNum) throws SQLException {

		Integer groupId = rs.getInt("group_id");
		String groupName = rs.getString("group_name");

		return new Group(groupId, groupName);
	}

}
