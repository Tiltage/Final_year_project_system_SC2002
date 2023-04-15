package Main;
/**
 * @author Ryan
 * @version 1.0
 * @since 15/4/2023
 */
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ReqChangeTitle extends Request{
	/**
	 * The Supervisor's name
	 */
	private String supName;
	/**
	 * The Project's new Title
	 */
	private String newProjTitle;
	/**
	 * Constructor for Request Change Title class
	 * @param approvalStatus The approval status of the request
	 * @param studentID The studentID of the student involved
	 * @param projTitle The title of the project involved
	 * @param supName The old Supervisor's name
	 * @param newProjTitle The new project's Title
	 */
	public ReqChangeTitle(ApprovalStatus approvalStatus, String studentID, String projTitle, String supName, String newProjTitle){
		super(approvalStatus, studentID, projTitle);
		this.setType(RequestType.ReqChangeTitle);
		this.supName = supName;
		this.newProjTitle = newProjTitle;
	}
	/**
	 * Appends a new request to the Request csv file with its attributes
	 */
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
