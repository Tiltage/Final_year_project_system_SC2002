package project;
import java.io.*;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Request {
	enum RequestType {ReqAlloc, ReqDeregister, ReqChangeTitle, ReqChangeSup};
	private boolean approvalStatus;
	private RequestType type;
	private String studentID;
	private String projTitle;
	
	public Request(boolean approvalStatus, String studentID, String projTitle){
		this.approvalStatus = approvalStatus;
		this.studentID = studentID;
		this.projTitle = projTitle;
	}
	
	public void addRequest()
	{
		//To be overridden in inherited classes
	}
	
	public void updateRequest(boolean status)
	{
		//Iterate through request csv file to identify line to edit approvalStatus. 
		//While addRequest needed to be overridden, this one probably does not.
	}
	
	public boolean isApprovalStatus() {
		return approvalStatus;
	}
	public void setApprovalStatus(boolean approvalStatus) {
		this.approvalStatus = approvalStatus;
	}
	public RequestType getType() {
		return type;
	}
	public void setType(RequestType type) {
		this.type = type;
	}
	public String getStudentID() {
		return studentID;
	}
	public void setStudentID(String studentID) {
		this.studentID = studentID;
	}
	public String getProjTitle() {
		return projTitle;
	}
	public void setProjTitle(String projTitle) {
		this.projTitle = projTitle;
	}
	
	
	
	public static void main(String[] args) 
	{
		ReqAlloc r = new ReqAlloc(false, "Ben", "Calculator app");
		r.addRequest();
		ReqDeregister r2 = new ReqDeregister(false, "Ben", "Calculator app");
		r2.addRequest();
		ReqChangeTitle r3 = new ReqChangeTitle(false, "Ben", "Calculator app", "li fang", "Calc app");
		r3.addRequest();
		ReqChangeSup r4 = new ReqChangeSup(false, "Ben", "Calculator app", "li fang", "TA tan");
		r4.addRequest();

	}
	
	
}
