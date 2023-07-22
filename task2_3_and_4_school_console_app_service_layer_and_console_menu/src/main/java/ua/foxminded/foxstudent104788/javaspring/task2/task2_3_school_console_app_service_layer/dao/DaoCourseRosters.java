package ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.dao;

import java.util.List;

import ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.models.CourseRoster;

/**
 * The DaoCourseRosters interface provides methods for interacting with
 * Course Rosters in a database.
 */
public interface DaoCourseRosters extends DaoEntity<CourseRoster>{

	

	/**
	 * Retrieves a list of enrollments by course ID.
	 *
	 * @param courseId the ID of the course
	 * @return the list of enrollments for the given course ID
	 */
	List<CourseRoster> getListOfRostersByCourseId(Integer courseId);
}
