package ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.dao.DaoConnectionHolder;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.dao.DaoEnrollments;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.models.Enrollment;

/**
 * Implementation of the DaoEnrollments interface for managing enrollments in
 * the database.
 */
@AllArgsConstructor
public class DaoEnrollmentsIpml implements DaoEnrollments {

	DaoConnectionHolder connection;

	/**
	 * Adds a list of enrollments to the database.
	 *
	 * @param enrollments the list of enrollments to add
	 * @return the number of affected rows in the database
	 */
	@Override
	public int addEnrollments(List<Enrollment> enrollments) {
		Map<Object[], String> statement = new HashMap<>();
		String query = null;
		Object[] parametres = null;
		List<String> columnNames;
		for (Enrollment enrollment : enrollments) {
			columnNames = getColumnNames(enrollment);
			if (columnNames.isEmpty()) {
				return -1;
			}

			query = getInsertQuery(columnNames);
			parametres = getParametres(columnNames.size(), enrollment);
			statement.put(parametres, query);
		}

		return connection.updateExecute(statement);
	}

	private String getInsertQuery(List<String> columnNames) {
		StringBuilder query = new StringBuilder("INSERT INTO enrollments (");
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

	/**
	 * Adds an enrollment to the database.
	 *
	 * @param enrollment the enrollment to add
	 * @return the number of affected rows in the database
	 */
	@Override
	public int addEnrollment(Enrollment enrollment) {
		String query = null;
		Object[] parametres = null;
		List<String> columnNames = getColumnNames(enrollment);

		if (columnNames.isEmpty()) {
			return -1;
		}

		query = getInsertQuery(columnNames);
		parametres = getParametres(columnNames.size(), enrollment);

		return connection.updateExecute(query, parametres);
	}

	/**
	 * Retrieves a list of enrollments by course ID.
	 *
	 * @param courseId the ID of the course
	 * @return the list of enrollments for the given course ID
	 */
	@Override
	public List<Enrollment> getByCourseId(Integer courseId) {

		StringBuilder query = new StringBuilder();
		Object[] parametres = null;
		Enrollment enrollment = new Enrollment(null, null, courseId);

		List<String> columnNames = getColumnNames(enrollment);

		query.append(getSelectAllQuery());
		query.append(getWhere(columnNames));

		parametres = getParametres(columnNames.size(), enrollment);

		List<Map<String, String>> enrollmentsData = connection.selectExecute(query.toString(), parametres);
		List<Enrollment> enrollments = getEnrollments(enrollmentsData);

		return enrollments;

	}

	private String getSelectAllQuery() {
		return "SELECT * FROM enrollments ";
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

	private List<Enrollment> getEnrollments(List<Map<String, String>> enrollmentsData) {
		List<Enrollment> enrollments = new ArrayList<>();
		Integer id;
		Integer studentId;
		Integer courseId;

		if (!enrollmentsData.get(0).get("table_name").equals("enrollments")) {
			return enrollments;
		}

		for (int i = 1; i < enrollmentsData.size(); i++) {
			id = Integer.parseInt(enrollmentsData.get(i).get("enrollment_id"));
			studentId = Integer.parseInt(enrollmentsData.get(i).get("student_id"));
			courseId = Integer.parseInt(enrollmentsData.get(i).get("course_id"));
			enrollments.add(new Enrollment(id, studentId, courseId));
		}

		return enrollments;
	}

	/**
	 * Deletes an enrollment from the database.
	 *
	 * @param enrollment the enrollment to delete
	 * @return the number of affected rows in the database
	 */
	@Override
	public int delete(Enrollment enrollment) {
		StringBuilder query = new StringBuilder();
		Object[] parametres = null;
		List<String> columnNames = getColumnNames(enrollment);

		if (columnNames.isEmpty()) {
			return -1;
		}

		query.append(getDeleteQuery());
		query.append(getWhere(columnNames));

		parametres = getParametres(columnNames.size(), enrollment);

		return connection.updateExecute(query.toString(), parametres);
	}

	private String getDeleteQuery() {

		return "DELETE FROM enrollments ";
	}

	private List<String> getColumnNames(Enrollment enrollment) {
		List<String> columnNames = new ArrayList<>();
		if (enrollment.getId() != null) {
			columnNames.add("enrollment_id");
		}
		if (enrollment.getStudentId() != null) {
			columnNames.add("student_id");
		}
		if (enrollment.getCourseId() != null) {
			columnNames.add("course_id");
		}

		return columnNames;
	}

	private Object[] getParametres(int size, Enrollment enrollment) {
		Object[] parametres = new Object[size];
		int i = 0;
		if (enrollment.getId() != null) {
			parametres[i] = enrollment.getId();
			i++;
		}
		if (enrollment.getStudentId() != null) {
			parametres[i] = enrollment.getStudentId();
			i++;
		}
		if (enrollment.getCourseId() != null) {
			parametres[i] = enrollment.getCourseId();
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
