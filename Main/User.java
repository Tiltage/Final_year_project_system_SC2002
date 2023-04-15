package Main;
/**
 * @author Winfred
 * @version 1.0
 * @since 15/4/2023
 */
import java.io.FileNotFoundException;
import java.io.IOException;

public interface User {
	/**
	 * Changes password for this user
	 */
	public abstract void changePW();
	/**
	 * @return User's password
	 */
	public abstract String getPW();
	/**
	 * @return User's ID
	 */
	public abstract String getID();
	/**
	 * Ensures all classes implements viewRequest in their own way
	 * @throws FileNotFoundException When the csv file cannot be located
	 * @throws IOException When the I/O operation is interrupted
	 */
	public abstract void viewRequest() throws FileNotFoundException, IOException;

	/**
	 * 	 * Ensures all classes implements viewProject in their own way
	 */
	public abstract void viewProject();
	
}
