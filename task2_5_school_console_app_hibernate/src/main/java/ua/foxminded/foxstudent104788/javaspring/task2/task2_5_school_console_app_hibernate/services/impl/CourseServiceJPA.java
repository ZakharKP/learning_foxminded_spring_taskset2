
package ua.foxminded.foxstudent104788.javaspring.task2.task2_5_school_console_app_hibernate.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_5_school_console_app_hibernate.dao.DaoCourses;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_5_school_console_app_hibernate.models.Constants;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_5_school_console_app_hibernate.models.Course;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_5_school_console_app_hibernate.services.CourseService;

@Log4j2

@Service
public class CourseServiceJPA implements CourseService {

	@Autowired
	private DaoCourses daoCourses;

	/**
	 * Retrieves a table representation of the list of courses.
	 *
	 * @param courses the list of courses
	 * @return a list of String arrays representing the table data
	 */

	@Override

	public List<String[]> representAsTable(List<Course> courses) {

		log.info("preparing table of Courses");

		List<String[]> table = new ArrayList<>();
		table.add(new String[] { Constants.COURSE_COLUMN_NAME_ID, Constants.COURSE_COLUMN_NAME_NAME,
				Constants.COURSE_COLUMN_NAME_DESCRIPTION, "Student's amount" });
		for (Course course : courses) {
			table.add(new String[] { String.valueOf(course.getId()), String.valueOf(course.getCourseName()),
					String.valueOf(course.getCourseDescription()), String.valueOf(course.getStudents().size()) });
		}

		return table;
	}

	/**
	 * Retrieves a Course object by its course name.
	 *
	 * @param courseName the course name
	 * @return the Course object with the given name, or null if not found
	 */

	@Override

	public Optional<Course> getCourseByName(String courseName) {

		log.info("Start searching of Course by name=" + courseName);

		return daoCourses.getByName(courseName);
	}

	/**
	 * Gets an course by its ID.
	 *
	 * @param id The ID of the course.
	 * @return An Optional containing the course if found, or an empty Optional if
	 *         not found.
	 */

	@Override

	public Optional<Course> getCourse(Integer id) {

		log.info("Start searching of Course by id=" + id);

		return daoCourses.get(id);
	}

	/**
	 * Gets List of courses by ID List.
	 *
	 * @param ID List of the course.
	 * @return List containing the courses if found, or an empty List
	 */

	@Override

	public List<Course> getListOfCoursesByIdsList(List<Integer> ids) {

		log.info("Start searching of Courses by id List");

		List<Course> courses = new ArrayList<>();

		for (Integer id : ids) {
			daoCourses.get(id).ifPresent(courses::add);
		}

		return courses;
	}

	/**
	 * Retrieves all courses.
	 *
	 * @return A list of all courses.
	 */

	@Override

	public List<Course> getAll() {

		log.info("Start searching of All Courses");

		return daoCourses.getAll();
	}

	/**
	 * Adds an course.
	 *
	 * @param course The course to be added.
	 * @return The number of rows affected (usually 1) if the course was added
	 *         successfully, or -1 if an error occurred.
	 */

	@Override

	public int saveCourse(Course course) {

		log.info(String.format("Start saving new Course: id=%s, name=%s, description =%s", course.getId(),
				course.getCourseName(), course.getCourseDescription()));

		return daoCourses.save(course);
	}

	/**
	 * Adds List of courses.
	 *
	 * @param List of courses The courses to be added.
	 * @return The number of rows affected (usually 1) if the course was added
	 *         successfully, or -1 if an error occurred.
	 */

	@Override

	public int saveAllCourses(List<Course> courses) {

		log.info(String.format("Start saving List of %s courses:", courses.size()));

		int saved = 0;
		for (Course course : courses) {
			int status = daoCourses.save(course);
			if (status > 0) {
				saved++;
			}
		}

		log.info(saved + " new courses was saved");

		return saved;
	}

	/**
	 * Deletes an course.
	 *
	 * @param course The course to be deleted.
	 * @return The number of rows affected (usually 1) if the course was deleted
	 *         successfully, or -1 if an error occurred.
	 */
	@Override

	public int deleteCourse(Course course) {

		log.info(String.format("Start deleting Course: id=%s, name=%s, description=%s", course.getId(),
				course.getCourseName(), course.getCourseDescription()));

		return daoCourses.delete(course);
	}

	@Override

	public boolean isEmpty() {

		log.info("Start counting courses");

		return daoCourses.size() == 0;
	}

}
