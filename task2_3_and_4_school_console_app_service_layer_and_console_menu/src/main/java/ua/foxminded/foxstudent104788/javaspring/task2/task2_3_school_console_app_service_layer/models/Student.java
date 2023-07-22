package ua.foxminded.foxstudent104788.javaspring.task2.task2_3_school_console_app_service_layer.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * The Student class represents a student entity.
 */
@AllArgsConstructor
@Getter
public class Student {

	private Integer id;
	private String firstName;
	private String lastName;
	private @Setter Integer groupId;

	/**
	 * Compares this student with the specified object for equality.
	 *
	 * @param obj The object to compare to.
	 * @return {@code true} if the specified object is equal to this student,
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

		Student other = (Student) obj;

		if (id != null && id.equals(other.getId())) {
			return true;
		}

		return firstName.equals(other.getFirstName()) && lastName.equals(other.getLastName());
	}
}