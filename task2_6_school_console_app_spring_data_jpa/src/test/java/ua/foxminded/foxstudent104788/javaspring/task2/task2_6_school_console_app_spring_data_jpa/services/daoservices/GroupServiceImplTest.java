
package ua.foxminded.foxstudent104788.javaspring.task2.task2_6_school_console_app_spring_data_jpa.services.daoservices;

import static org.assertj.core.api.Assertions.filter;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.DynamicTest.stream;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import ua.foxminded.foxstudent104788.javaspring.task2.task2_6_school_console_app_spring_data_jpa.models.Constants;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_6_school_console_app_spring_data_jpa.models.ForTestsEntitiesCreator;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_6_school_console_app_spring_data_jpa.models.Group;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_6_school_console_app_spring_data_jpa.models.Student;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_6_school_console_app_spring_data_jpa.repository.GroupsRepository;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_6_school_console_app_spring_data_jpa.services.impl.GroupServiceImpl;

@SpringBootTest(classes = { GroupServiceImpl.class })
class GroupServiceImplTest {

	@MockBean
	GroupsRepository groupsRepository;

	@Autowired
	GroupServiceImpl groupService;

	@Test
	final void testRepresentAsTable() {
		List<Group> groups = getGroups();

		List<String[]> actual = groupService.representAsTable(groups);

		List<String[]> expected = new ArrayList<>();
		expected.add(
				new String[] { Constants.GROUP_COLUMN_NAME_ID, Constants.GROUP_COLUMN_NAME_NAME, "student's amount" });
		expected.add(new String[] { "1", "xx-11", "2" });
		expected.add(new String[] { "2", "xy-15", "3" });
		expected.add(new String[] { "3", "xz-16", "5" });
		assertEquals(expected.get(2)[1], actual.get(2)[1]);
	}

	@Test
	final void testGetGroup() {
		Group expected = getGroups().get(0);
		when(groupsRepository.findById(expected.getId()))
				.thenReturn(Optional.of(new Group(expected.getId(), expected.getGroupName(), expected.getStudents())));
		Group actual = groupService.getGroup(expected.getId()).get();
		assertEquals(expected, actual);
	}

	@Test
	final void testGetListOfGroupByIdsList() {
		List<Integer> ids = new ArrayList<>();
		List<Group> expected = getGroups();
		expected.stream().forEach(x -> ids.add(x.getId()));

		when(groupsRepository.findAllById(anyList())).thenAnswer(invocation -> {
			List<Group> answer = new ArrayList<>();
			List<Integer> askIds = invocation.getArgument(0);
			
			for(Integer id : askIds) {
				expected.stream().filter(x -> x.getId() == id).forEach(answer :: add);
			}
			
			return answer;
		});

		List<Group> actual = groupService.getListOfGroupByIdsList(ids);

		assertEquals(expected, actual);
	}

	@Test
	final void testGetAll() {
		List<Group> expected = getGroups();

		when(groupsRepository.findAll()).thenReturn(expected);

		List<Group> actual = groupService.getAll();
		assertEquals(expected, actual);
	}

	@Test
	final void testSaveGroup() {
		List<Group> groups = getGroups();

		when(groupsRepository.save(any(Group.class))).thenAnswer(invocation -> {

			groups.add(invocation.getArgument(0));
			return invocation.getArgument(0);

		});

		Group group = ForTestsEntitiesCreator.getNewGroup();

		groupService.saveGroup(group);

		assertTrue(groups.contains(group));
	}

	@Test
	final void testSaveAllGroups() {
		List<Group> expected = getGroups();
		List<Group> actual = new ArrayList<>();

		when(groupsRepository.saveAll(anyList())).thenAnswer(invocation -> {

			actual.addAll(invocation.getArgument(0));
			return actual;

		});

		groupService.saveAllGroups(expected);

		assertEquals(expected, actual);
	}

	@Test
	final void testDeleteGroup() {
		List<Group> expected = getGroups();
		List<Group> actual = getGroups();

		Mockito.doAnswer(invocation -> {

			actual.remove(actual.indexOf(invocation.getArgument(0)));
			return null;

		}).when(groupsRepository).delete(any(Group.class));

		groupService.deleteGroup(actual.get(1));

		expected.remove(1);

		assertEquals(expected, actual);
	}

	@Test
	final void testIsEmpty() {
		List<Group> groups = getGroups();

		when(groupsRepository.count()).thenReturn((long) groups.size());

		assertFalse(groupService.isEmpty());
	}

	@Test
	final void testFindWithLessOrEqualsStudents() {

		List<Group> groups = getGroups();

		when(groupsRepository.findById(anyInt())).thenAnswer(invocation -> {
			return groups.stream().filter(x -> x.getId() == invocation.getArgument(0)).findFirst();
		});

		when(groupsRepository.count()).thenReturn((long) groups.size());

		List<Group> actual = groupService.findWithLessOrEqualsStudents(3);

		List<Group> expected = getGroups();
		expected.remove(2);

		assertEquals(expected, actual);
	}

	private List<Group> getGroups() {
		List<Group> groups = ForTestsEntitiesCreator.getGroups();

		List<Student> students = ForTestsEntitiesCreator.getStudents();

		for (int i = 0; i < students.size(); i++) {
			if (i < 2) {
				Group group = groups.get(0);
				students.get(i).setGroup(group);
				group.getStudents().add(students.get(i));
			}
			if (i < 5 && i >= 2) {
				Group group = groups.get(1);
				students.get(i).setGroup(group);
				group.getStudents().add(students.get(i));
			}
			if (i >= 5) {
				Group group = groups.get(2);
				students.get(i).setGroup(group);
				group.getStudents().add(students.get(i));
			}
		}

		return groups;
	}

}
