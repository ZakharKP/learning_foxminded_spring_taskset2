package ua.foxminded.foxstudent104788.javaspring.task2.task2_6_school_console_app_spring_data_jpa.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.jdbc.Sql;

import ua.foxminded.foxstudent104788.javaspring.task2.task2_6_school_console_app_spring_data_jpa.models.ForTestsEntitiesCreator;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_6_school_console_app_spring_data_jpa.models.Group;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_6_school_console_app_spring_data_jpa.repository.GroupsRepository;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = { GroupsRepository.class }))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = { "/scripts/table_creation_script.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class GroupsRepositoryTest {

	@Autowired
	private GroupsRepository groupsRepository;

	@Test
	void testSave() {
		Group group = ForTestsEntitiesCreator.getNewGroup();

		groupsRepository.save(group);
		long actual = groupsRepository.count();

		assertTrue(actual > 0);
	}

	@Test
	void testGetInteger() {
		List<Group> groups = ForTestsEntitiesCreator.getNewGroups();

		for (Group group : groups) {
			groupsRepository.save(group);
		}

		Group actual = groupsRepository.findById(3).get();

		String expected = "xz-16";

		assertEquals(expected, actual.getGroupName());

	}

	@Test
	void testGetAll() {
		List<Group> groups = ForTestsEntitiesCreator.getNewGroups();
		List<Group> expected = ForTestsEntitiesCreator.getGroups();

		for (Group group : groups) {
			groupsRepository.save(group);
		}

		List<Group> actual = groupsRepository.findAll();

		assertEquals(expected, actual);
	}

	@Test
	void testDelete() {
		List<Group> groups = ForTestsEntitiesCreator.getNewGroups();
		List<Group> expected = ForTestsEntitiesCreator.getGroups();

		for (Group group : groups) {
			groupsRepository.save(group);
		}

		groupsRepository.delete(groups.get(1));

		expected.remove(1);

		List<Group> actual = groupsRepository.findAll();

		assertEquals(expected, actual);
	}

	@Test
	void Update() {
		List<Group> groups = ForTestsEntitiesCreator.getNewGroups();

		for (Group group : groups) {
			groupsRepository.save(group);
		}

		Group expected = ForTestsEntitiesCreator.getGroups().get(1);
		expected.setGroupName("YY-77");

		groupsRepository.save(expected);

		Group actual = groupsRepository.findById(expected.getId()).get();

		assertEquals(expected, actual);
	}

	@Test
	void testSize() {

		List<Group> groups = ForTestsEntitiesCreator.getNewGroups();

		for (Group group : groups) {
			groupsRepository.save(group);
		}

		long actual = groupsRepository.count();

		assertEquals(groups.size(), actual);
	}
}
