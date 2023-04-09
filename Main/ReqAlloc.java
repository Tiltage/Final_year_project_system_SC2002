package Main;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ReqAlloc extends Request{
	private String supID;
	private String newSupID;

	public ReqAlloc(ApprovalStatus approvalStatus, String studentID, String projTitle) throws FileNotFoundException, IOException{
		super(approvalStatus, studentID, projTitle);
		this.setType(RequestType.ReqAlloc);
		Project temp = new Project(projTitle);
		Supervisor tempsup = new Supervisor(temp.getSupervisorName());
		this.supID = tempsup.getFacultyID();
		this.newSupID = "NA";
	}
	
	public void addRequest()
	{
		Filepath f = new Filepath();
		try (FileWriter fw = new FileWriter(f.REQFILENAME, true);
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
