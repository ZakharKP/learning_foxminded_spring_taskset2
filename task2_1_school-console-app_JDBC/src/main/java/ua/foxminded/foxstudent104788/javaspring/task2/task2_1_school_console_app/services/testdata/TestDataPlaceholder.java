package ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.services.testdata;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import lombok.AllArgsConstructor;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.dao.DaoConnectionHolder;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.dao.DaoCourses;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.dao.DaoEnrollments;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.dao.DaoGroups;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.dao.DaoStudents;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.dao.impl.DaoCoursesImpl;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.dao.impl.DaoEnrollmentsIpml;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.dao.impl.DaoGroupsImpl;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.dao.impl.DaoStudentsImpl;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.models.Course;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.models.Enrollment;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.models.Group;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.models.Student;

/**
 * Class fill in tables with test data
 */
@AllArgsConstructor
public class TestDataPlaceholder {

	private DaoConnectionHolder connection;

	public void generateData() {

		Path resourseDir = Paths.get("src", "main", "resourses");

		TestDataPathStorage pathStorage = new TestDataPathStorage(resourseDir);
		TestDataGenerator dataGenerator = new TestDataGenerator(pathStorage);
		List<Group> groups = dataGenerator.getGroups();
		List<Course> courses = dataGenerator.getCourses();
		List<Student> students = dataGenerator.getStudents();
		List<Enrollment> enrollment = dataGenerator.getEnrollments();

		fillInDataBase(groups, courses, students, enrollment);
	}

	private void fillInDataBase(List<Group> groups, List<Course> courses, List<Student> students,
			List<Enrollment> enrollment) {

		fillInCoursesTable(courses);

		fillInGroupTable(groups);

		fillInStudentTable(students);

		fillInEnrollmentsTable(enrollment);

	}

	private void fillInCoursesTable(List<Course> courses) {
		DaoCourses daoCourses = new DaoCoursesImpl(connection);

		for (Course course : courses) {
			daoCourses.addCourse(course);
		}
	}

	private void fillInGroupTable(List<Group> groups) {
		DaoGroups daoGroups = new DaoGroupsImpl(connection);

		daoGroups.addGroups(groups);

	}

	private void fillInStudentTable(List<Student> students) {

		DaoStudents daoStudents = new DaoStudentsImpl(connection);

		daoStudents.addStudents(students);

	}

	private void fillInEnrollmentsTable(List<Enrollment> enrollment) {
		DaoEnrollments daoEnrollments = new DaoEnrollmentsIpml(connection);

		daoEnrollments.addEnrollments(enrollment);

	}

}
