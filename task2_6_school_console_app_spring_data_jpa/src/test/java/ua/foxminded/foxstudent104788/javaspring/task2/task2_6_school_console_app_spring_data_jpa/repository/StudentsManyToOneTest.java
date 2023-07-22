
package ua.foxminded.foxstudent104788.javaspring.task2.task2_6_school_console_app_spring_data_jpa.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.jdbc.Sql;

import ua.foxminded.foxstudent104788.javaspring.task2.task2_6_school_console_app_spring_data_jpa.models.ForTestsEntitiesCreator;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_6_school_console_app_spring_data_jpa.models.Group;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_6_school_console_app_spring_data_jpa.models.Student;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_6_school_console_app_spring_data_jpa.repository.GroupsRepository;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_6_school_console_app_spring_data_jpa.repository.StudentsRepository;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = { StudentsRepository.class,
		GroupsRepository.class }))

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

@Sql(scripts = { "/scripts/table_creation_script.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class StudentsManyToOneTest {

	@Autowired
	private StudentsRepository studentsRepository;

	@Autowired
	private GroupsRepository groupsRepository;

	@BeforeEach
	void beforeEach() {
		for (Group group : ForTestsEntitiesCreator.getNewGroups()) {
			groupsRepository.save(group);
		}

		List<Student> students = ForTestsEntitiesCreator.getNewStudents();

		for (Student student : students) {
			studentsRepository.save(student);
		}
		students = studentsRepository.findAll();
		addBoth(groupsRepository.findAll(), students);
		for (Student student : students) {
			studentsRepository.save(student);
		}

	}

	@Test
	void testSave() {

		int expected = 1;

		int actual = 0;

		Student student = ForTestsEntitiesCreator.getNewStudent();

		studentsRepository.save(student);
		student.setGroup(groupsRepository.findById(2).get());
		student.getGroup().getStudents().add(student);
		studentsRepository.save(student);
		for (Student s : groupsRepository.findById(2).get().getStudents()) {
			System.out.println(s);
		}

		student = studentsRepository.findById(student.getId()).get();

		if (groupsRepository.findById(2).get().getStudents().contains(studentsRepository.findById(student.getId()).get()))
			actual++;

		assertEquals(expected, actual);
	}

	@Test
	void testGet() {

		Student student = studentsRepository.findById(6).get();

		Group group = groupsRepository.findById(student.getGroup().getId()).get();

		String expected = student.toString();

		String actual = group.getStudents().stream().filter(x -> x.equals(student)).findAny().get().toString();

		assertEquals(expected, actual);
	}

	@Test
	void testDelete() {

		long expected = 1;

		long actual = 0;

		Student student = studentsRepository.findById(6).get();

		int groupId = student.getGroup().getId();

		student.getGroup().getStudents().remove(student);

		studentsRepository.delete(student);

		Group group = groupsRepository.findById(groupId).get();

			if (!group.getStudents().contains(student)) {
				actual++;
				if (studentsRepository.count() != 9)
					actual = (int) studentsRepository.count();
			}
		
		assertEquals(expected, actual);
	}

	@Test
	void testUpdate() {

		int expected = 1;

		int actual = -1;

		Student student = studentsRepository.findById(1).get();

		int previuosGroupId = student.getGroup().getId();

		int newGroupId = 2;

		student.getGroup().getStudents().remove(student);

		student.setGroup(groupsRepository.findById(newGroupId).get());

		student.getGroup().getStudents().add(student);

		studentsRepository.save(student);

		Group group = groupsRepository.findById(newGroupId).get();

			if (group.getStudents().contains(student)) {
				actual++;

				if (!groupsRepository.findById(previuosGroupId).get().getStudents().contains(student)) {
					actual++;
				}
			}

		assertEquals(expected, actual);
	}

	private void addGroupToStudents(List<Group> groups, List<Student> students) {

		for (int i = 0; i < students.size(); i++) {
			if (i < 2) {
				Group group = groups.get(0);
				students.get(i).setGroup(group);
			}
			if (i < 5 && i >= 2) {
				Group group = groups.get(1);
				students.get(i).setGroup(group);

			}
			if (i >= 5) {
				Group group = groups.get(2);
				students.get(i).setGroup(group);
			}
		}

	}

	private void addBoth(List<Group> groups, List<Student> students) {

		for (int i = 0; i < students.size(); i++) {
			if (i < 2) {
				Group group = groups.get(0);
				group.getStudents().add(students.get(i));
				students.get(i).setGroup(group);
			}
			if (i < 5 && i >= 2) {
				Group group = groups.get(1);
				group.getStudents().add(students.get(i));
				students.get(i).setGroup(group);

			}
			if (i >= 5) {
				Group group = groups.get(2);
				group.getStudents().add(students.get(i));
				students.get(i).setGroup(group);
			}
		}

	}

	private void addStudentsToGroup(List<Group> groups, List<Student> students) {

		for (int i = 0; i < students.size(); i++) {
			if (i < 2) {
				Group group = groups.get(0);
				group.getStudents().add(students.get(i));
			}
			if (i < 5 && i >= 2) {
				Group group = groups.get(1);
				group.getStudents().add(students.get(i));

			}
			if (i >= 5) {
				Group group = groups.get(2);
				group.getStudents().add(students.get(i));
			}
		}

	}
}
