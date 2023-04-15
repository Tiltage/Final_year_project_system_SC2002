package Main;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface User {
	
	public abstract void changePW();
	
	public abstract String getPW();
	
	public abstract String getID();
	
	public abstract void viewRequest() throws FileNotFoundException, IOException;

	public abstract void viewProject();
	
}
