package ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.services.entityservices.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.dao.DaoCourseRosters;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.models.Constants;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.models.CourseRoster;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.services.entityservices.CourseRosterService;

@Log4j2
@Service
public class CourseRosterServiceJdbc implements CourseRosterService {

	@Autowired
	private DaoCourseRosters daoCourseRosters;

	/**
	 * Retrieves a table representation of the CourseRosters.
	 *
	 * @param courseRosters The list of CourseRosters.
	 * @return A list of string arrays representing the table, with each string
	 *         array representing a row of the table.
	 */
	@Override
	public List<String[]> representAsTable(List<CourseRoster> courseRosters) {

		log.info("preparing table of Course Rosters");

		List<String[]> table = new ArrayList<>();
		table.add(new String[] { Constants.COURSE_ROSTERS_COLUMN_NAME_ID, Constants.STUDENT_COLUMN_NAME_ID,
				Constants.COURSE_COLUMN_NAME_ID });
		for (CourseRoster courseRoster : courseRosters) {
			table.add(new String[] { String.valueOf(courseRoster.getId()), String.valueOf(courseRoster.getStudentId()),
					String.valueOf(courseRoster.getCourseId()) });
		}

		return table;
	}

	/**
	 * Retrieves a list of enrollments by course ID.
	 *
	 * @param courseId the ID of the course
	 * @return the list of enrollments for the given course ID
	 */
	@Override
	public List<CourseRoster> getListOfRostersByCourseId(Integer courseId) {
		return daoCourseRosters.getListOfRostersByCourseId(courseId);
	}

	/**
	 * Gets an course roster by its ID.
	 *
	 * @param id The ID of the course roster.
	 * @return An Optional containing the course roster if found, or an empty
	 *         Optional if not found.
	 */
	@Override
	public Optional<CourseRoster> getCourseRoster(Integer id) {

		log.info("Start searching of CourseRoster by id=" + id);

		return daoCourseRosters.get(id);
	}

	/**
	 * Gets List of course roster by ID List.
	 *
	 * @param ID List of the course roster.
	 * @return List containing the course roster if found, or an empty List
	 */
	@Override
	public List<CourseRoster> getListOfCourseRostersByIdsList(List<Integer> ids) {

		log.info("Start searching of CourseRosters by id List");

		List<CourseRoster> groups = new ArrayList<>();

		for (Integer id : ids) {
			daoCourseRosters.get(id).ifPresent(groups::add);
		}

		return groups;
	}

	/**
	 * Retrieves all course roster.
	 *
	 * @return A list of all course roster.
	 */
	@Override
	public List<CourseRoster> getAll() {

		log.info("Start searching of All CourseRosters");

		return daoCourseRosters.getAll();
	}

	/**
	 * Adds an course roster.
	 *
	 * @param course roster The course roster to be added.
	 * @return The number of rows affected (usually 1) if the course roster was
	 *         added successfully, or -1 if an error occurred.
	 */
	@Override
	public int saveCourseRoster(CourseRoster courseRoster) {

		log.info(String.format("Start saving new CourseRoster: id=%s, student_id=%s, course_id=%s",
				courseRoster.getId(), courseRoster.getStudentId(), courseRoster.getCourseId()));

		return daoCourseRosters.save(courseRoster);
	}

	/**
	 * Adds List of course roster.
	 *
	 * @param List of course roster The course roster to be added.
	 * @return The number of rows affected (usually 1) if the course roster was
	 *         added successfully, or -1 if an error occurred.
	 */
	@Override
	public int saveAll(List<CourseRoster> courseRosters) {

		log.info(String.format("Start saving List of %s courseReosters:", courseRosters.size()));

		int saved = 0;
		for (CourseRoster courseRoster : courseRosters) {
			int status = daoCourseRosters.save(courseRoster);
			if (status > 0) {
				saved++;
			}
		}

		log.info(saved + " new courseRosters was saved");

		return saved;
	}

	/**
	 * Deletes an course roster.
	 *
	 * @param course roster The course roster to be deleted.
	 * @return The number of rows affected (usually 1) if the course roster was
	 *         deleted successfully, or -1 if an error occurred.
	 */
	@Override
	public int deleteCourseRoster(CourseRoster courseRoster) {

		log.info(String.format("Start deleting courseRoster: id=%s, student_id=%s, course_id =%s", courseRoster.getId(),
				courseRoster.getStudentId(), courseRoster.getCourseId()));

		return daoCourseRosters.delete(courseRoster);
	}

	@Override
	public boolean isAnyCourseRosters() {

		log.info("Start counting courseRosters");

		return daoCourseRosters.size() == 0;
	}

}
