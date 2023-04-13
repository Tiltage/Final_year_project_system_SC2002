package Main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ReqChangeTitle extends Request{
	private String supName;
	private String newProjTitle;
	
	public ReqChangeTitle(ApprovalStatus approvalStatus, String studentID, String projTitle, String supName, String newProjTitle){
		super(approvalStatus, studentID, projTitle);
		this.setType(RequestType.ReqChangeTitle);
		this.supName = supName;
		this.newProjTitle = newProjTitle;
	}
	
	public void addRequest()
	{
		Filepath f = new Filepath();
		try (FileWriter fw = new FileWriter(f.REQFILENAME, true);
	             BufferedWriter bw = new BufferedWriter(fw);
	             PrintWriter out = new PrintWriter(bw)) 
		{
	            // Add a new row to the bottom of the file
	            out.printf("%s,%s,%s,%s,%s,%s", this.isApprovalStatus(), this.getType(), this.getStudentID(), this.getProjTitle(),this.supName,this.newProjTitle).println();
	            System.out.println("Request sent successfully");

        } catch (IOException e) 
			{
	            e.printStackTrace();
	        }
	}

}
