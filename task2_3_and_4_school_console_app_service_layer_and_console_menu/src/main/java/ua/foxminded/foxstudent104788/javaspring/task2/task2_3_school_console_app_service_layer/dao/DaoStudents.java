package ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.dao;

import java.util.List;

import ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.models.Student;

/**
 * The DaoStudents interface provides methods for interacting with students in a
 * database.
 */
public interface DaoStudents extends DaoEntity<Student> {
	
	/**
	 * Retrieves students by their IDs.
	 *
	 * @param studentsId The list of student IDs.
	 * @return A list of students associated with the specified IDs.
	 */
	List<Student> get(List<Integer> studentsId);

	/**
	 * Retrieves the group IDs based on the count of students in each group.
	 *
	 * @param studentsCount The count of students in a group.
	 * @return A list of students associated with the specified group count.
	 */
	List<Integer> getGroupSIdCount(Integer groupsCount);

}