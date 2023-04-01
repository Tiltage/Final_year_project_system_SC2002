package project;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ReqDeregister extends Request{

	public ReqDeregister(boolean approvalStatus, String studentID, String projTitle){
		super(approvalStatus, studentID, projTitle);
		this.setType(RequestType.ReqDeregister);
	}
	
	public void addRequest()
	{
		final String FILENAME = "C:\\Users\\ryank\\Downloads\\request.csv";
		try (FileWriter fw = new FileWriter(FILENAME, true);
	             BufferedWriter bw = new BufferedWriter(fw);
	             PrintWriter out = new PrintWriter(bw)) 
		{
	            // Add a new row to the bottom of the file
	            out.printf("%s,%s,%s,%s", this.isApprovalStatus(), this.getType(), this.getStudentID(), this.getProjTitle()).println();
	            System.out.println("Data appended to file successfully!");

        } catch (IOException e) 
			{
	            e.printStackTrace();
	        }
	}
	
}
