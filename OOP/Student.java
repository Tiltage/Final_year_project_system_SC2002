package OOP;
import project.*;

public class Student extends User{
	
	enum Status {Unassigned,Pending,Assigned}
	
	private String studentID;
	private String studentName;
	private Status status;
	private Project proj;
	private Project[] deRegisProjs; 
	
	public Student() {}
	
	public void viewProject() {}
	
	public void viewHistory() {}

	@Override
	public void changePW() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getPW() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void viewRequest() {
		// TODO Auto-generated method stub
		
	}
	
}
