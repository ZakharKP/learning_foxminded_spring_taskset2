package ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.services.entityservices;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.dao.DaoGroups;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.models.Group;
import ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.services.entityservices.impl.GroupServiceJdbc;

@SpringBootTest(classes = { GroupServiceJdbc.class })
class GroupServiceJdbcTest {

	@MockBean
	DaoGroups daoGroups;

	@Autowired
	GroupServiceJdbc groupServiceJdbc;

	@Test
	void testRepresentAsTableListOfGroup() {
		List<Group> groups = new ArrayList<>();

		groups.add(new Group(null, "xx-11"));
		groups.add(new Group(2, "xy-15"));
		groups.add(new Group(3, "xz-16"));

		List<String[]> actual = groupServiceJdbc.representAsTable(groups);

		List<String[]> expected = new ArrayList<>();
		expected.add(new String[] { "group_id", "group_name" });
		expected.add(new String[] { "1", "xx-11" });
		expected.add(new String[] { "2", "xy-15" });
		expected.add(new String[] { "3", "xz-16" });
		assertEquals(expected.get(2)[1], actual.get(2)[1]);
	}

	@Test
	void testGetInteger() {
		Group expected = getGroup();
		when(daoGroups.get(expected.getId()))
				.thenReturn(Optional.of(new Group(expected.getId(), expected.getGroupName())));
		Group actual = groupServiceJdbc.getGroup(expected.getId()).get();
		assertEquals(expected, actual);
	}

	@Test
	void testGetListOfInteger() {
		List<Integer> ids = new ArrayList<>();
		List<Group> expected = getGroupList();
		expected.stream().forEach(x -> ids.add(x.getId()));

		when(daoGroups.get(anyInt())).thenAnswer(invocation -> {
			return expected.stream().filter(x -> x.getId() == invocation.getArgument(0)).findFirst();
		});

		List<Group> actual = groupServiceJdbc.getListOfGroupByIdsList(ids);

		assertEquals(expected, actual);

	}

	@Test
	void testGetAll() {
		List<Group> expected = getGroupList();

		when(daoGroups.getAll()).thenReturn(expected);

		List<Group> actual = groupServiceJdbc.getAll();
		assertEquals(expected, actual);
	}

	@Test
	void testSave() {
		List<Group> groups = getGroupList();

		when(daoGroups.save(any(Group.class))).thenAnswer(invocation -> {

			groups.add(invocation.getArgument(0));
			return 1;

		});

		Group group = new Group(5, "zn-32");

		groupServiceJdbc.saveGroup(group);

		assertTrue(groups.contains(group));

	}

	@Test
	void testSaveAll() {
		List<Group> expected = getGroupList();
		List<Group> actual = new ArrayList<>();

		when(daoGroups.save(any(Group.class))).thenAnswer(invocation -> {

			actual.add(invocation.getArgument(0));
			return 1;

		});
		
		groupServiceJdbc.saveAllGroups(expected);
		
		assertEquals(expected, actual);

	}

	@Test
	void testDelete() {
		List<Group> expected = getGroupList();
		List<Group> actual = getGroupList();

		when(daoGroups.delete(any(Group.class))).thenAnswer(invocation -> {

			actual.remove(actual.indexOf(invocation.getArgument(0)));
			return 1;

		});
		
		groupServiceJdbc.deleteGroup(actual.get(1));
		
		expected.remove(1);
		
		assertEquals(expected, actual);
	}

	

	@Test
	void isEmpty() {

		List<Group> groups = getGroupList();

		when(daoGroups.size())
				.thenReturn(groups.size());
		
		assertFalse(groupServiceJdbc.isAnyGroup());

	}

	private List<Group> getGroupList() {
		List<Group> groups = new ArrayList<>();

		groups.add(new Group(1, "xx-11"));
		groups.add(new Group(2, "xy-15"));
		groups.add(new Group(3, "xz-16"));

		return groups;
	}

	private Group getGroup() {
		List<Group> groups = getGroupList();
		Collections.shuffle(groups);

		return groups.get(0);
	}

}
