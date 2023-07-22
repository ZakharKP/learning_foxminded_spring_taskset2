package ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The Group class represents a group entity.
 */
@AllArgsConstructor
@Getter
public class Group {

	private Integer id;
	private String groupName;

	/**
	 * Compares this group with the specified object for equality.
	 *
	 * @param obj The object to compare to.
	 * @return {@code true} if the specified object is equal to this group,
	 *         {@code false} otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}

		Group other = (Group) obj;

		if (id != null && id.equals(other.getId())) {
			return true;
		}

		return groupName.equals(other.getGroupName());
	}
}