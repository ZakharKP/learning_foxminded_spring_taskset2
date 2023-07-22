package ua.foxminded.foxstudent104788.javaspring.task2.task2_5_school_console_app_hibernate.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ForTestsEntitiesCreator {
	
	public static Student getNewStudent(){
		return Student.builder().firstName("John").lastName("Doe").build();
	}
	
	public static Course getNewCourse() {
		return Course.builder().courseName("foxminded").courseDescription("course for abbitions").build();
	}
	
	public static Group getNewGroup() {
		return Group.builder().groupName("xx-11").build();
	}
	
	public static List<Student> getNewStudents() {
		List<Student> students = new ArrayList<>();

		students.add(Student.builder().firstName("Pavel").lastName("Pashkin").build());
		students.add(Student.builder().firstName("Piotr").lastName("Vasiuk").build());
		students.add(Student.builder().firstName("Tasas").lastName("Mirko").build());
		students.add(Student.builder().firstName("Tasas").lastName("Zubko").build());
		students.add(Student.builder().firstName("Sem").lastName("Milek").build());
		students.add(Student.builder().firstName("Marek").lastName("Rak").build());
		students.add(Student.builder().firstName("John").lastName("Doe").build());
		students.add(Student.builder().firstName("Nikolos").lastName("Flamel").build());
		students.add(Student.builder().firstName("Bernard").lastName("Verber").build());
		students.add(Student.builder().firstName("John").lastName("Snow").build());

		return students;
	}

	public static List<Course> getNewCourses() {
		List<Course> courses = new ArrayList<>();

		courses.add(Course.builder().courseName("biology").courseDescription("anatomia Course").build());
		courses.add(Course.builder().courseName("math").courseDescription("math Course").build());
		courses.add(Course.builder().courseName("art").courseDescription("art Course").build());

		return courses;
	}
	
	public static List<Group> getNewGroups() {
		List<Group> groups = new ArrayList<>();

		groups.add(Group.builder().groupName("xx-11").build());
		groups.add(Group.builder().groupName("xy-15").build());
		groups.add(Group.builder().groupName("xz-16").build());

		return groups;
	}
	
	public static List<Course> getCourses() {
		List<Course> courses = new ArrayList<>();

		courses.add(new Course( 1, "biology", "anatomia Course", new HashSet<>()));
		courses.add(new Course( 2, "math", "math Course", new HashSet<>()));
		courses.add(new Course( 3, "art", "art Course", new HashSet<>()));

		return courses;
	}
	
	public static List<Student> getStudents() {
		List<Student> students = new ArrayList<>();

		students.add(new Student( 1, "Pavel", "Pashkin", null, new HashSet<>()));
		students.add(new Student( 2, "Piotr", "Vasiuk", null, new HashSet<>()));
		students.add(new Student( 3, "Tasas", "Mirko", null, new HashSet<>()));
		students.add(new Student( 4, "Tasas", "Zubko", null, new HashSet<>()));
		students.add(new Student( 5, "Sem", "Milek", null, new HashSet<>()));
		students.add(new Student( 6, "Marek", "Rak", null, new HashSet<>()));
		students.add(new Student( 7, "John", "Doe", null, new HashSet<>()));
		students.add(new Student( 8, "Nikolos", "Flamel", null, new HashSet<>()));
		students.add(new Student( 9, "Bernard", "Verber", null, new HashSet<>()));
		students.add(new Student( 10, "John", "Snow", null, new HashSet<>()));

		return students;
	}

	public static List<Group> getGroups() {
		List<Group> groups = new ArrayList<>();

		groups.add(new Group( 1, "xx-11", new HashSet<>()));
		groups.add(new Group( 2, "xy-15", new HashSet<>()));
		groups.add(new Group( 3, "xz-16", new HashSet<>()));

		return groups;
	}
	
}
