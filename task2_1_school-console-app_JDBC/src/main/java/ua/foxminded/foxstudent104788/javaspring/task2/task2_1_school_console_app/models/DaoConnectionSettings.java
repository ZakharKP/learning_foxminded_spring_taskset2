package ua.foxminded.foxstudent104788.javaspring.task2.task2_1_school_console_app.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * The DaoConnectionSettings class represents the connection settings for a DAO.
 */
@Getter
@Setter
@AllArgsConstructor
public class DaoConnectionSettings {

	private String url;
	private String userName;
	private String password;

}
