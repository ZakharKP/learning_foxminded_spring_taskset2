package ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.dao.DaoConnectionHolder;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.dao.DaoCourses;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.models.Course;

/**
 * Implementation of the DaoCourses interface.
 */
@AllArgsConstructor
public class DaoCoursesImpl implements DaoCourses {

	private DaoConnectionHolder connection;

	/**
	 * Adds a list of courses to the database.
	 *
	 * @param courses the list of courses to add
	 * @return the number of affected rows in the database
	 */
	@Override
	public int addCourses(List<Course> courses) {
		Map<Object[], String> statement = new HashMap<>();
		String query = null;
		Object[] parametres = null;
		List<String> columnNames;
		for (Course course : courses) {
			columnNames = getColumnNames(course);
			if (columnNames.isEmpty()) {
				return -1;
			}

			query = getInsertQuery(columnNames);
			parametres = getParametres(columnNames.size(), course);
			statement.put(parametres, query);
		}

		return connection.updateExecute(statement);

	}

	private String getInsertQuery(List<String> columnNames) {
		StringBuilder query = new StringBuilder("INSERT INTO courses (");
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

	/**
	 * Adds a single course to the database.
	 *
	 * @param course the course to add
	 * @return the number of affected rows in the database
	 */
	@Override
	public int addCourse(Course course) {

		String query = null;
		Object[] parametres = null;
		List<String> columnNames = getColumnNames(course);

		if (columnNames.isEmpty()) {
			return -1;
		}

		query = getInsertQuery(columnNames);
		parametres = getParametres(columnNames.size(), course);

		return connection.updateExecute(query, parametres);

	}

	/**
	 * Retrieves a Course object by its course name.
	 *
	 * @param courseName the course name
	 * @return the Course object with the given name, or a Course object with null
	 *         values if not found
	 */
	@Override
	public Course getByName(String courseName) {

		StringBuilder query = new StringBuilder();

		Course course = new Course(null, courseName, null);

		List<String> columnNames = getColumnNames(course);

		query.append(getSelectAllQuery());
		query.append(getWhere(columnNames));

		List<Map<String, String>> courseData = connection.selectExecute(query.toString(), courseName);
		List<Course> courses = getCoures(courseData);

		if (courses.isEmpty()) {
			return new Course(null, null, null);
		} else {
			return courses.get(0);
		}

	}

	private List<Course> getCoures(List<Map<String, String>> courseData) {
		List<Course> courses = new ArrayList<>();
		Integer id;
		String courseName;
		String courseDescription;

		if (!courseData.get(0).get("table_name").equals("courses")) {
			return courses;
		}

		for (int i = 1; i < courseData.size(); i++) {

			id = Integer.parseInt(courseData.get(i).get("course_id"));
			courseName = courseData.get(i).get("course_name");
			courseDescription = courseData.get(i).get("course_description");
			courses.add(new Course(id, courseName, courseDescription));
		}

		return courses;
	}

	/**
	 * Retrieves a list of all courses from the database.
	 *
	 * @return the list of Course objects
	 */
	@Override
	public List<Course> getCourses() {
		String query = getSelectAllQuery();

		List<Map<String, String>> courseData = connection.selectExecute(query);

		List<Course> courses = getCoures(courseData);

		return courses;

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

	private String getSelectAllQuery() {
		return "SELECT * FROM courses ";
	}

	/**
	 * Retrieves a table representation of the list of courses.
	 *
	 * @param courses the list of courses
	 * @return a list of String arrays representing the table data
	 */
	@Override
	public List<String[]> getTable(List<Course> courses) {

		List<String[]> table = new ArrayList<>();
		table.add(new String[] { "course_id", "course_name", "course_description" });
		for (Course course : courses) {
			table.add(new String[] { String.valueOf(course.getId()), String.valueOf(course.getCourseName()),
					String.valueOf(course.getCourseDescription()) });
		}

		return table;
	}

	private void deleteLastAnd(StringBuilder query) {
		query.delete(query.length() - 5, query.length());

	}

	private void deleteLastComa(StringBuilder query) {
		query.delete(query.length() - 2, query.length());
	}
}
