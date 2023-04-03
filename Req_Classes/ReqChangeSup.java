package project;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ReqChangeSup extends Request{
	private String supID;
	private String newSupID;
	
	public ReqChangeSup(boolean approvalStatus, String studentID, String projTitle, String supID, String newSupID){
		super(approvalStatus, studentID, projTitle);
		this.setType(RequestType.ReqChangeSup);
		this.supID = supID;
		this.newSupID = newSupID;
	}
	
	public String getSupID() {
		return supID;
	}
	public void setSupID(String supID) {
		this.supID = supID;
	}
	public String getNewSupID() {
		return newSupID;
	}
	public void setNewSupID(String newSupID) {
		this.newSupID = newSupID;
	}
	
	public void addRequest()
	{
		final String FILENAME = "C:\\Users\\ryank\\Downloads\\request.csv";
		try (FileWriter fw = new FileWriter(FILENAME, true);
	             BufferedWriter bw = new BufferedWriter(fw);
	             PrintWriter out = new PrintWriter(bw)) 
		{
	            // Add a new row to the bottom of the file
	            out.printf("%s,%s,%s,%s,%s,%s", this.isApprovalStatus(), this.getType(), this.getStudentID(), this.getProjTitle(),this.supID,this.newSupID).println();
	            System.out.println("Data appended to file successfully!");

        } catch (IOException e) 
			{
	            e.printStackTrace();
	        }
	}

}
