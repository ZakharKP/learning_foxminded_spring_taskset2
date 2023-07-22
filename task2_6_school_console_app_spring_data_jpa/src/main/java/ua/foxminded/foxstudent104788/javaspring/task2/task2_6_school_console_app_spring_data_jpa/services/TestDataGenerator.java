
package ua.foxminded.foxstudent104788.javaspring.task2.task2_6_school_console_app_spring_data_jpa.services;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_6_school_console_app_spring_data_jpa.exceptions.ResourseException;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_6_school_console_app_spring_data_jpa.models.Course;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_6_school_console_app_spring_data_jpa.models.Group;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_6_school_console_app_spring_data_jpa.models.Student;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_6_school_console_app_spring_data_jpa.services.impl.ResourceLoaderImpl;

/**
 * The TestDataGenerator class is responsible for generating test data for
 * groups, courses, students, and enrollments.
 */

@Log4j2
@AllArgsConstructor
public class TestDataGenerator {

	private TestDataPathStorage pathStorage;

	private static final Random random = new Random();

	private static final int MAX_STUDENTS_IN_GROUP = 30;
	private static final int MIN_STUDENTS_IN_GROUP = 10;
	private static final int STUDENTS_AMOUNT = 200;
	private static final int GROUPS_AMOUNT = 10;
	private static final int MAX_STUDENTS_AT_COURSE = 3;

	/**
	 * Generates a list of groups with randomly generated names.
	 *
	 * @return The list of generated groups.
	 */

	public List<Group> getGroups() {

		List<Group> groups = new ArrayList<>();

		for (int i = 0; i < GROUPS_AMOUNT; i++) {

			Group group = getGroup();

			while (groups.contains(group)) {
				group = getGroup();
			}
			groups.add(group);
		}

		log.info("Groups was gererated");
		return groups;
	}

	private Group getGroup() {
		String groupName;

		char randomChar1 = (char) (random.nextInt('z' - 'a' + 1) + 'a');
		char randomChar2 = (char) (random.nextInt('z' - 'a' + 1) + 'a');
		int randomInt = random.nextInt(100);

		groupName = String.valueOf(randomChar1) + String.valueOf(randomChar2) + "-" + String.valueOf(randomInt);
		return Group.builder().groupName(groupName).build();
	}

	/**
	 * Generates a list of courses by reading from a file.
	 *
	 * @return The list of generated courses.
	 */

	public List<Course> getCourses() {

		List<Course> courses = new ArrayList<>();
		for (Map.Entry<String, String> course : new ResourceLoaderImpl().getMapFromFile(pathStorage.getTestCourses())
				.entrySet()) {

			courses.add(Course.builder().courseName(course.getKey()).courseDescription(course.getValue()).build());

		}

		log.info("Courses was gererated");
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

			for (int i = 0; i < STUDENTS_AMOUNT; i++) {
				Student student = createStudent(firstNames, lastNames);
				while (students.contains(student)) {
					student = createStudent(firstNames, lastNames);
				}
				students.add(student);
			}

		} catch (IOException e) {
			log.error("getStudents():Resourse");
			throw new ResourseException(e);
		}

		log.info("Students was gererated");
		return students;
	}

	private Student createStudent(List<String> firstNames, List<String> lastNames) {

		int firstNameIndex = random.nextInt(firstNames.size());
		int lastNameIndex = random.nextInt(lastNames.size());

		return Student.builder()
				.firstName(firstNames.get(firstNameIndex))
				.lastName(lastNames.get(lastNameIndex))
				.build();
	}

	public void addGroupsToStudents(List<Student> students, List<Group> groups) {

		for (int i = 0; i < groups.size(); i++) {
			int studentsCount = random.nextInt(MAX_STUDENTS_IN_GROUP - MIN_STUDENTS_IN_GROUP - 1)
					+ MIN_STUDENTS_IN_GROUP;
			addGroup(studentsCount, groups.get(i), students);
		}

		correctGroupsRequirement(students, groups);

	}

	private void addGroup(int studentsCount, Group group, List<Student> students) {

		Collections.shuffle(students);

		for (int i = 0; i < studentsCount; i++) {
			students.get(i).setGroup(group);
			group.getStudents().add(students.get(i));
		}

	}

	private void correctGroupsRequirement(List<Student> students, List<Group> groups) {

		for (int i = 0; i < groups.size(); i++) {
			final int x = i;
			long studentsCount = students.stream().filter(student -> student.getGroup() != null)
					.filter(student -> student.getGroup().equals(groups.get(x))).count();

			if (studentsCount < MIN_STUDENTS_IN_GROUP || studentsCount > MAX_STUDENTS_IN_GROUP) {

				students.stream().filter(student -> student.getGroup() != null)
						.filter(student -> student.getGroup().equals(groups.get(x))).forEach(student -> {

							student.getGroup().getStudents().remove(student);
							student.setGroup(null);

						});
			}
		}

	}

	/**
	 * added courses to students
	 */
	public void addCoursesToStudents(List<Student> students, List<Course> courses) {

		for (int i = 0; i < students.size(); i++) {

			addCoursesForThatStudent(students.get(i), courses);

		}

	}

	private void addCoursesForThatStudent(Student student, List<Course> courses) {

		int courseCapability = courses.size();

		int coursesForEach = random.nextInt(MAX_STUDENTS_AT_COURSE) + 1;

		for (int i = 0; i < coursesForEach; i++) {

			int courseIndex = random.nextInt(courseCapability);

			Course course = courses.get(courseIndex);

			while (student.getCourses().contains(course)) {
				courseIndex = random.nextInt(courseCapability);
				course = courses.get(courseIndex);
			}
			student.getCourses().add(course);
			course.getStudents().add(student);
		}

	}

}
