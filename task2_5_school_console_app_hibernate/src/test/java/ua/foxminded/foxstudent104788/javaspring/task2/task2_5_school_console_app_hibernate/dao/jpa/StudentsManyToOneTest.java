package ua.foxminded.foxstudent104788.javaspring.task2.task2_5_school_console_app_hibernate.dao.jpa;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.jdbc.Sql;

import ua.foxminded.foxstudent104788.javaspring.task2.task2_5_school_console_app_hibernate.dao.impl.DaoGroupsImpl;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_5_school_console_app_hibernate.dao.impl.DaoStudentsImpl;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_5_school_console_app_hibernate.models.ForTestsEntitiesCreator;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_5_school_console_app_hibernate.models.Group;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_5_school_console_app_hibernate.models.Student;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
		DaoStudentsImpl.class, DaoGroupsImpl.class }))

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

@Sql(scripts = { "/scripts/table_creation_script.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class StudentsManyToOneTest {

	@Autowired
	private DaoStudentsImpl daoStudents;

	@Autowired
	private DaoGroupsImpl daoGroups;

	@BeforeEach
	void beforeEach() {
		for (Group group : ForTestsEntitiesCreator.getNewGroups()) {
			daoGroups.save(group);
		}

		List<Student> students = ForTestsEntitiesCreator.getNewStudents();

		for (Student student : students) {
			daoStudents.save(student);
		}
		students = daoStudents.getAll();
		addBoth(daoGroups.getAll(), students);
		for (Student student : students) {
			daoStudents.update(student);
		}
		
	}

	@Test
	void testSave() {

		int expected = 1;

		int actual = -1;

		Student student = ForTestsEntitiesCreator.getNewStudent();
				
			

		int status = daoStudents.save(student);
		student.setGroup(daoGroups.get(2).get());
		student.getGroup().getStudents().add(student);
		daoStudents.update(student);
		for(Student s : daoGroups.get(2).get().getStudents()) {
			System.out.println(s);
		}

		student = daoStudents.get(student.getId()).get();
		if (status > 0) {
			actual++;
			if (daoGroups.get(2).get().getStudents().contains(daoStudents.get(student.getId()).get()))
				actual++;
		}

		assertEquals(expected, actual);
	}

	@Test
	void testGet() {

		Student student = daoStudents.get(6).get();

		Group group = daoGroups.get(student.getGroup().getId()).get();

		String expected = student.toString();

		String actual = group.getStudents().stream().filter(x -> x.equals(student)).findAny().get().toString();

		assertEquals(expected, actual);
	}

	@Test
	void testDelete() {

		int expected = 1;

		int actual = -1;

		Student student = daoStudents.get(6).get();

		int groupId = student.getGroup().getId();

		student.getGroup().getStudents().remove(student);

		int status = daoStudents.delete(student);

		Group group = daoGroups.get(groupId).get();

		if (status > 0) {
			actual++;
			if (!group.getStudents().contains(student)) {
				actual++;
			if (daoStudents.size() != 9)
				actual = (int) daoStudents.size();
			}
		}

		for(Student s: daoStudents.getAll()) {
			System.out.println(s);
		}
		for(Student s: daoGroups.get(groupId).get().getStudents()) {
			System.out.println(s);
		}
		
	//	assertEquals(student, daoStudents.get(6).get());
		assertEquals(expected, actual);
	}

	@Test
	void testUpdate() {

		int expected = 1;

		int actual = -2;

		Student student = daoStudents.get(1).get();

		int previuosGroupId = student.getGroup().getId();

		int newGroupId = 2;

		student.getGroup().getStudents().remove(student);

		student.setGroup(daoGroups.get(newGroupId).get());

		student.getGroup().getStudents().add(student);

		int status = daoStudents.update(student);

		Group group = daoGroups.get(newGroupId).get();

		if (status > 0) {
			actual++;
			if (group.getStudents().contains(student)) {
				actual++;

				if (!daoGroups.get(previuosGroupId).get().getStudents().contains(student)) {
					actual++;
				}
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
