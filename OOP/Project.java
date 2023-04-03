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
	
	public Project(String studentID, String studentEmail, int projID, String supervisorName, String supervisorEmail,
			String projTitle, Status status) 
	{
		this.studentID = studentID;
		this.studentEmail = studentEmail;
		this.projID = projID;
		this.supervisorName = supervisorName;
		this.supervisorEmail = supervisorEmail;
		this.projTitle = projTitle;
		this.status = status;
	}

	public Project(String string) {
		// TODO Auto-generated constructor stub
	}

	public String getStudentID() {
		return studentID;
	}
	public void setStudentID(String studentID) {
		this.studentID = studentID;
	}
	public String getStudentEmail() {
		return studentEmail;
	}
	public void setStudentEmail(String studentEmail) {
		this.studentEmail = studentEmail;
	}
	public int getProjID() {
		return projID;
	}
	public void setProjID(int projID) {
		this.projID = projID;
	}
	public String getSupervisorName() {
		return supervisorName;
	}
	public void setSupervisorName(String supervisorName) {
		this.supervisorName = supervisorName;
	}
	public String getSupervisorEmail() {
		return supervisorEmail;
	}
	public void setSupervisorEmail(String supervisorEmail) {
		this.supervisorEmail = supervisorEmail;
	}
	public String getProjTitle() {
		return projTitle;
	}
	public void setProjTitle(String projTitle) {
		this.projTitle = projTitle;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}

	
	//set status method need?
	// get methods need? maybe for returning
	
	
	
	

}
