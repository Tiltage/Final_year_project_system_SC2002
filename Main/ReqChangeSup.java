package Main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ReqChangeSup extends Request{
	private String supName;
	private String newSupName;
	
	public ReqChangeSup(ApprovalStatus approvalStatus, String studentID, String projTitle, String supName, String newSupName){
		super(approvalStatus, studentID, projTitle);
		this.setType(RequestType.ReqChangeSup);
		this.supName = supName;
		this.newSupName = newSupName;
	}
	
	public String getSupName() {
		return supName;
	}
	public void setSupName(String supName) {
		this.supName = supName;
	}
	public String getNewSupName() {
		return newSupName;
	}
	public void setNewSupName(String newSupName) {
		this.newSupName = newSupName;
	}
	
	public void addRequest()
	{
		Filepath f = new Filepath();
		try (FileWriter fw = new FileWriter(f.REQFILENAME, true);
	             BufferedWriter bw = new BufferedWriter(fw);
	             PrintWriter out = new PrintWriter(bw)) 
		{
	            // Add a new row to the bottom of the file
	            out.printf("%s,%s,%s,%s,%s,%s", this.isApprovalStatus(), this.getType(), this.getStudentID(), this.getProjTitle(),this.supName,this.newSupName).println();
	            System.out.println("Data appended to file successfully!");

        } catch (IOException e) 
			{
	            e.printStackTrace();
	        }
	}

}
