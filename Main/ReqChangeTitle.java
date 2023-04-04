package project;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ReqChangeTitle extends Request{
	private String supID;
	private String newProjTitle;
	
	public ReqChangeTitle(boolean approvalStatus, String studentID, String projTitle, String supID, String newProjTitle){
		super(approvalStatus, studentID, projTitle);
		this.setType(RequestType.ReqChangeTitle);
		this.supID = supID;
		this.newProjTitle = newProjTitle;
	}
	
	public String getSupID() {
		return supID;
	}
	public void setSupID(String supID) {
		this.supID = supID;
	}
	public String getNewProjTitle() {
		return newProjTitle;
	}
	public void setNewProjTitle(String newProjTitle) {
		this.newProjTitle = newProjTitle;
	}
	
	public void addRequest()
	{
		Filepath f = new Filepath();
		try (FileWriter fw = new FileWriter(f.getPath(), true);
	             BufferedWriter bw = new BufferedWriter(fw);
	             PrintWriter out = new PrintWriter(bw)) 
		{
	            // Add a new row to the bottom of the file
	            out.printf("%s,%s,%s,%s,%s,%s", this.isApprovalStatus(), this.getType(), this.getStudentID(), this.getProjTitle(),this.supID,this.newProjTitle).println();
	            System.out.println("Data appended to file successfully!");

        } catch (IOException e) 
			{
	            e.printStackTrace();
	        }
	}

}
