package ua.foxminded.foxstudent104788.javaspring.task2.task2_2_school_console_app_Spring_Boot_JDBC_Api.exceptions;

/**
 * The DaoException class is a custom runtime exception that is thrown when an
 * exception occurs during DAO operations.
 */
public class DaoException extends RuntimeException {

	/**
	 * Constructs a new DaoException with the specified cause.
	 *
	 * @param e The cause of the exception.
	 */
	public DaoException(Exception e) {
		super(e);
	}

}