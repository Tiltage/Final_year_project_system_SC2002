package Main;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ReqDeregister extends Request{
	private String supID;
	private String newSupName;
	private String supervisorName;

	public ReqDeregister(ApprovalStatus approvalStatus, String studentID, String projTitle) throws FileNotFoundException, IOException{
		super(approvalStatus, studentID, projTitle);
		this.setType(RequestType.ReqDeregister);
		Project temp = new Project(projTitle);
		Supervisor tempsup = new Supervisor(temp.getSupervisorName());
		this.supID = tempsup.getFacultyID();
		this.newSupName = "NA";
		this.supervisorName = tempsup.getSupervisorName();

	}
	
	public void addRequest()
	{
		Filepath f = new Filepath();
		try (FileWriter fw = new FileWriter(f.REQFILENAME, true);
	             BufferedWriter bw = new BufferedWriter(fw);
	             PrintWriter out = new PrintWriter(bw)) 
		{
	            // Add a new row to the bottom of the file
			out.printf("%s,%s,%s,%s,%s,%s", this.isApprovalStatus(), this.getType(), this.getStudentID(), this.getProjTitle(),this.supervisorName,this.newSupName).println();
            System.out.println("Request sent successfully");

        } catch (IOException e) 
			{
	            e.printStackTrace();
	        }
	}
	
}
