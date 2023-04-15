package Main;

import java.io.*;
/**
 * @author Ryan
 * @version 1.0
 * @since 15/4/2023
 */
public class ReqChangeSup extends Request{
	/**
	 * Old Supervisor's Name
	 */
	private String supName;
	/**
	 * New Supervisor's Name
	 */
	private String newSupName;
	/**
	 * Constructor for Request Change Supervisor class
	 * @param approvalStatus The approval status of the request
	 * @param studentID The studentID of the student involved
	 * @param projTitle The title of the project involved
	 * @param supName The old Supervisor's name
	 * @param newSupName The new Supervisor's name
	 */
	public ReqChangeSup(ApprovalStatus approvalStatus, String studentID, String projTitle, String supName, String newSupName){
		super(approvalStatus, studentID, projTitle);
		this.setType(RequestType.ReqChangeSup);
		this.supName = supName;
		this.newSupName = newSupName;
	}
	/**
	 * Appends a new request to the Request csv file with its attributes
	 */
	public void addRequest()
	  {
	    Filepath f = new Filepath();
	    try (FileWriter fw = new FileWriter(f.getREQFILENAME(), true);
	               BufferedReader br = new BufferedReader(new FileReader(f.getREQFILENAME()));
	               PrintWriter out = new PrintWriter(fw)) 
	    {
	      
	            br.close();
	              // Add a new row to the bottom of the file
	              out.printf("%s,%s,%s,%s,%s,%s", this.isApprovalStatus(), this.getType(), this.getStudentID(), this.getProjTitle(),this.supName,this.newSupName).println();
	              System.out.println("Request sent successfully");

	        } catch (IOException e) 
	      	  {
	              e.printStackTrace();
	          }
	  }

}
