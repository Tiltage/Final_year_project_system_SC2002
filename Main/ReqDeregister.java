package Main;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
/**
 * @author Ryan
 * @version 1.0
 * @since 15/4/2023
 */
public class ReqDeregister extends Request{
	/**
	 * Supervisor's ID
	 */
	private String supID;
	/**
	 * New Supervisor's Name
	 */
	private String newSupName;
	/**
	 * Old Supervisor's Name
	 */
	private String supervisorName;
	/**
	 * Constructor for Request Deregister class
	 * @param approvalStatus The approval status of the request
	 * @param studentID The studentID of the student involved
	 * @param projTitle The title of the project involved
	 * @throws FileNotFoundException When the csv file cannot be located
	 * @throws IOException When the I/O operation is interrupted
	 */
	public ReqDeregister(ApprovalStatus approvalStatus, String studentID, String projTitle) throws FileNotFoundException, IOException{
		super(approvalStatus, studentID, projTitle);
		this.setType(RequestType.ReqDeregister);
		Project temp = new Project(projTitle);
		Supervisor tempsup = new Supervisor(temp.getSupervisorName());
		this.supID = tempsup.getFacultyID();
		this.newSupName = "NA";
		this.supervisorName = tempsup.getSupervisorName();

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
			out.printf("%s,%s,%s,%s,%s,%s", this.isApprovalStatus(), this.getType(), this.getStudentID(), this.getProjTitle(),this.supervisorName,this.newSupName).println();
            System.out.println("Request sent successfully");

        } catch (IOException e) 
			{
	            e.printStackTrace();
	        }
	}
	
	
	
}
