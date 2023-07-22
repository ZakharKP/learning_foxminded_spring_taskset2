package ua.foxminded.foxstudent104788.javaspring.task2.task2_6_school_console_app_spring_data_jpa.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * The Course class represents a course entity.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = Constants.COURSE_TABLE_NAME)
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = Constants.COURSE_COLUMN_NAME_ID)
	private Integer id;

	@Column(name = Constants.COURSE_COLUMN_NAME_NAME)
	private String courseName;

	@Column(name = Constants.COURSE_COLUMN_NAME_DESCRIPTION)
	private String courseDescription;

	@Builder.Default
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	@ManyToMany(mappedBy = "courses", cascade = CascadeType.ALL)
	private Set<Student> students = new HashSet<>();

}
