package OOP;

public class Project {
	
	enum Status {Available, Unavailable, Allocated, Reserved}
	private String studentID ;
	private String studentEmail;
	private int projID;
	private String supervisorName;
	private String supervisorEmail;
	private String projTitle;
	private Status status;

	public void setStudent(String studentID) {
		this.studentID = studentID;
	}

	public void setStudentEmail(String studentEmail) {
		this.studentEmail = studentEmail;
	}

	public void setSupervisorName(String supervisorName) {
		this.supervisorName = supervisorName;
	}

	public void setSupervisorEmail(String supervisorEmail) {
		this.supervisorEmail = supervisorEmail;
	}
	
	//set status method need?
	// get methods need? maybe for returning
	
	
	
	

}
