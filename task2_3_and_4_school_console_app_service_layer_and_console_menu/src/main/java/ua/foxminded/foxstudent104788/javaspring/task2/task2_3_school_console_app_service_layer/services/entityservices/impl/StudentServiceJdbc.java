package ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.services.entityservices.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.dao.DaoStudents;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.models.Constants;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.models.Student;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.services.entityservices.StudentService;
@Log4j2
@Service
public class StudentServiceJdbc implements StudentService {

	@Autowired
	private DaoStudents daoStudents;

	
	/**
	 * Retrieves a table representation of the students.
	 *
	 * @param students The list of students.
	 * @return A list of string arrays representing the table, with each string
	 *         array representing a row of the table.
	 */
	@Override
	public List<String[]> representAsTable(List<Student> students) {

		log.info("preparing table of students");

		List<String[]> table = new ArrayList<>();
		
		table.add(new String[] { Constants.STUDENT_COLUMN_NAME_ID, Constants.STUDENT_COLUMN_NAME_FIRST_NAME,
				Constants.STUDENT_COLUMN_NAME_LAST_NAME, Constants.GROUP_COLUMN_NAME_ID });
		
		for (Student student : students) {
			table.add(new String[] { String.valueOf(student.getId()), String.valueOf(student.getFirstName()),
					String.valueOf(student.getLastName()), String.valueOf(student.getGroupId()) });
		}

		return table;
	}

	/**
	 * Retrieves the group IDs based on the count of students in each group.
	 *
	 * @param groupsCount The count of students in a group.
	 * @return A list of students associated with the specified group count.
	 */
	@Override
	public List<Integer> getGroupsIdsByStudentsCount(Integer groupsCount) {
		log.info("Start research of Groups Ids by count = " + groupsCount);
		return daoStudents.getGroupSIdCount(groupsCount);
	}

	/**
	 * Gets an student by its ID.
	 *
	 * @param id The ID of the student.
	 * @return An Optional containing the student if found, or an empty Optional if
	 *         not found.
	 */
	@Override
	public Optional<Student> getStudent(Integer id){	
		log.info("Start searching of Student by id=" + id);
		return daoStudents.get(id);
	}
	
	/**
	 * Gets List of students by ID List.
	 *
	 * @param ID List of the student.
	 * @return List containing the students if found, or an empty List
	 */
	@Override
	public List<Student> getListOfStudentsByIdsList(List<Integer> ids){		
		List<Student> students = new ArrayList<>();
		
		log.info("Start searching of Students by id List");
		for(Integer id : ids) {
			daoStudents.get(id).ifPresent(students :: add);
		}
		
		return students; 
	}

	/**
	 * Retrieves all students.
	 *
	 * @return A list of all students.
	 */
	@Override
	public List<Student> getAll(){
		
		log.info("Start research of All Students");
		return daoStudents.getAll();
	}

	/**
	 * Adds an student.
	 *
	 * @param student The student to be added.
	 * @return The number of rows affected (usually 1) if the student was added
	 *         successfully, or -1 if an error occurred.
	 */
	@Override
	public int saveNewStudent(Student student) {
		
		log.info(String.format("Start saving new student: id=%s, %s %s, in the %s group", student.getId(),
				student.getFirstName(), student.getLastName(), student.getGroupId()));
		return daoStudents.save(student);
	}
	
	/**
	 * Adds List of students.
	 *
	 * @param List of students The students to be added.
	 * @return The number of rows affected (usually 1) if the student was added
	 *         successfully, or -1 if an error occurred.
	 */
	@Override
	public int saveAllStudents(List<Student> students) {
		
		log.info(String.format("Start saving List of %s students:", students.size()));
		
		int saved = 0;
		for (Student student : students) {
			int status = daoStudents.save(student);
			if(status > 0) {
				saved++;
			}
		}
		
		log.info(saved + " new students was saved");
		return saved;
	}

	/**
	 * Deletes an student.
	 *
	 * @param student The student to be deleted.
	 * @return The number of rows affected (usually 1) if the student was deleted
	 *         successfully, or -1 if an error occurred.
	 */
	@Override
	public int deleteStudent(Student student) {
		
		log.info(String.format("Start deleting student: id=%s, %s %s, group_id=%s", student.getId(),
				student.getFirstName(), student.getLastName(), student.getGroupId()));
		
		return daoStudents.delete(student);
	}

	@Override
	public boolean isAnyStudent() {
		
		log.info("Start counting students");
		
		return daoStudents.size() == 0;
	}

}
