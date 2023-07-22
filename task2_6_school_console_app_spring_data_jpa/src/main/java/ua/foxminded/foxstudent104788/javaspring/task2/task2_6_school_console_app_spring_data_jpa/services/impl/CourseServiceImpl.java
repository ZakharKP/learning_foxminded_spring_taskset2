
package ua.foxminded.foxstudent104788.javaspring.task2.task2_6_school_console_app_spring_data_jpa.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_6_school_console_app_spring_data_jpa.models.Constants;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_6_school_console_app_spring_data_jpa.models.Course;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_6_school_console_app_spring_data_jpa.repository.CoursesRepository;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_6_school_console_app_spring_data_jpa.services.CourseService;

@Log4j2

@Service
public class CourseServiceImpl implements CourseService {

	@Autowired
	private CoursesRepository coursesRepository;

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

		return coursesRepository.findByCourseNameContaining(courseName);
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

		return coursesRepository.findById(id);
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
			coursesRepository.findById(id).ifPresent(courses::add);
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

		return coursesRepository.findAll();
	}

	/**
	 * Adds an course.
	 *
	 * @param course The course to be added.
	 * @return The course entity of rows affected if the course was added
	 *         successfully, or null if an error occurred.
	 */

	@Override

	public Course saveCourse(Course course) {
		if (course == null) {
			log.info("Can't save - course is NULL");
			return null;
		}

		log.info("Start saving new " + course.toString());

		Course savedCourse = null;

		try {
			savedCourse = coursesRepository.save(course);
		} catch (IllegalArgumentException e) {
			log.error("Something went wrong trying to save course" + e.getMessage());
			return null;
		}

		log.info("Sucesfully saved " + course.toString());
		return savedCourse;
	}

	/**
	 * Adds List of courses.
	 *
	 * @param List of courses The courses to be added.
	 * @return The List of saved courses if the course was added successfully, or
	 *         null if an error occurred.
	 */

	@Override

	public List<Course> saveAllCourses(List<Course> courses) {
		if (courses == null) {
			log.info("Can't save - courses is NULL");
			return null;
		}

		log.info(String.format("Start saving List of %s courses:", courses.size()));

		List<Course> savedCourses = coursesRepository.saveAll(courses);

		log.info(savedCourses.size() + " new courses was saved");

		return savedCourses;
	}

	/**
	 * Deletes an course.
	 *
	 * @param course The course to be deleted.
	 */
	@Override

	public void deleteCourse(Course course) {

		if (course == null) {
			log.info("Can't delete - course is NULL");
			return;
		}

		log.info(String.format("Start deleting Course: id=%s, name=%s, description=%s", course.getId(),
				course.getCourseName(), course.getCourseDescription()));
		try {
			coursesRepository.delete(course);
			log.info("Sucesfully deleted");
		} catch (IllegalArgumentException e) {
			log.error("Something went wrong trying to delete course" + e.getMessage());
		}
	}

	@Override

	public boolean isEmpty() {

		log.info("Start counting courses");

		return coursesRepository.count() == 0;
	}

}
