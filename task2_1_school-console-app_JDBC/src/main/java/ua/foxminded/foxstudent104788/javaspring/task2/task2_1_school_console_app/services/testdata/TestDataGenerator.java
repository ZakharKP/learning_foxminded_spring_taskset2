package ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.services.testdata;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import lombok.AllArgsConstructor;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.exceptions.ResourseException;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.models.Course;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.models.Enrollment;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.models.Group;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.models.Student;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.services.impl.ResourceLoaderImpl;

/**
 * The TestDataGenerator class is responsible for generating test data for
 * groups, courses, students, and enrollments.
 */
@AllArgsConstructor
public class TestDataGenerator {

	private static final Logger logger = LogManager.getLogger(TestDataGenerator.class);

	private TestDataPathStorage pathStorage;

	/**
	 * Generates a list of groups with randomly generated names.
	 *
	 * @return The list of generated groups.
	 */
	public List<Group> getGroups() {

		List<Group> groups = new ArrayList<>();

		for (int i = 0; i < 10; i++) {
			Group group = getGroup();

			while (groups.contains(group)) {
				group = getGroup();
			}
			groups.add(group);
		}
		return groups;
	}

	private Group getGroup() {
		String groupName;

		char randomChar1 = (char) ('a' + Math.random() * ('z' - 'a' + 1));
		char randomChar2 = (char) ('a' + Math.random() * ('z' - 'a' + 1));
		int randomInt = (int) (Math.random() * 100);

		groupName = String.valueOf(randomChar1) + String.valueOf(randomChar2) + "-" + String.valueOf(randomInt);
		return new Group(null, groupName);
	}

	/**
	 * Generates a list of courses by reading from a file.
	 *
	 * @return The list of generated courses.
	 */
	public List<Course> getCourses() {

		List<Course> courses = new ArrayList<>();
		for (Map.Entry<String, String> course : new ResourceLoaderImpl(null)
				.getMapFromFile(pathStorage.getTestCourses()).entrySet()) {
			courses.add(new Course(null, course.getKey(), course.getValue()));

		}

		return courses;
	}

	/**
	 * Generates a list of students with randomly generated first names and last
	 * names.
	 *
	 * @return The list of generated students.
	 */
	public List<Student> getStudents() {
		List<Student> students = new ArrayList<>();
		try {
			List<String> firstNames = Files.readAllLines(pathStorage.getTestFirstNames());
			List<String> lastNames = Files.readAllLines(pathStorage.getTestLastNames());

			for (int i = 0; i < 200; i++) {
				Student student = createStudent(firstNames, lastNames);
				while (students.contains(student)) {
					student = createStudent(firstNames, lastNames);
				}
				students.add(student);
			}

		} catch (IOException e) {
			logger.error("getStudents():Resourse");
			throw new ResourseException(e);
		}

		fillInGroups(students);

		return students;
	}

	private Student createStudent(List<String> firstNames, List<String> lastNames) {
		int firstNameIndex = (int) (Math.random() * firstNames.size());
		int lastNameIndex = (int) (Math.random() * lastNames.size());

		return new Student(null, firstNames.get(firstNameIndex), lastNames.get(lastNameIndex), null);
	}

	private void fillInGroups(List<Student> students) {
		int min = 10;
		int max = 30;
		for (int i = 1; i <= 10; i++) {
			int studentsCount = (int) ((Math.random() * (max - min)) + min);
			addGroup(studentsCount, i, students);
		}

		correctGroupsRequirement(students);

	}

	private void correctGroupsRequirement(List<Student> students) {

		for (int i = 1; i <= 10; i++) {
			final int x = i;
			long studentsCount = students.stream().filter(student -> student.getGroupId() != null)
					.filter(student -> student.getGroupId() == x).count();

			if (studentsCount < 10 || studentsCount > 30) {
				students.stream().filter(student -> student.getGroupId() != null)
						.filter(student -> student.getGroupId() == x).forEach(student -> student.setGroupId(null));
			}
		}

	}

	private void addGroup(int studentsCount, int groupId, List<Student> students) {
		Collections.shuffle(students);
		for (int i = 0; i < studentsCount; i++) {
			students.get(i).setGroupId(groupId);
		}

	}

	/**
	 * Generates a list of enrollments for students and courses.
	 *
	 * @return The list of generated enrollments.
	 */
	public List<Enrollment> getEnrollments() {
		List<Enrollment> enrollment = new ArrayList<>();

		for (int i = 1; i <= 200; i++) {

			addCoursesForThatStudent(i, enrollment);

		}
		return enrollment;
	}

	private void addCoursesForThatStudent(int studentId, List<Enrollment> enrollment) {

		List<Integer> coursesThisStudent = new ArrayList<>();
		int courseCapability = 10;

		int min = 1;
		int max = 3;
		int coursesForEach = (int) (Math.random() * (max - min + 1)) + min;

		for (int i = 0; i < coursesForEach; i++) {

			int courseId = (int) (Math.random() * courseCapability + 1);
			while (coursesThisStudent.contains(courseId)) {
				courseId = (int) (Math.random() * courseCapability + 1);
			}
			coursesThisStudent.add(courseId);
			enrollment.add(new Enrollment(null, studentId, courseId));
		}

	}

}
