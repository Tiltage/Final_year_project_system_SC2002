package Main;

import java.io.*;

public class ReqChangeSup extends Request{
	private String supName;
	private String newSupName;
	
	public ReqChangeSup(ApprovalStatus approvalStatus, String studentID, String projTitle, String supName, String newSupName){
		super(approvalStatus, studentID, projTitle);
		this.setType(RequestType.ReqChangeSup);
		this.supName = supName;
		this.newSupName = newSupName;
	}
	
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
