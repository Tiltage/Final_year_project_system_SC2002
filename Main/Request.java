package Main;
import java.io.*;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Paths;
/**
 * @author Ryan
 * @version 1.0
 * @since 15/4/2023
 */
public abstract class Request {
	/**
	 * Enum list for Request Type
	 */
	enum RequestType {ReqAlloc, ReqDeregister, ReqChangeTitle, ReqChangeSup};
	/**
	 * Enum list for Request approval status
	 */
	enum ApprovalStatus {Pending,Rejected,Approved};
	/**
	 * Request Type
	 */
	private RequestType type;
	/**
	 * Request Approval Status
	 */
	private ApprovalStatus approvalStatus;
	/**
	 * Student ID of student involved
	 */
	private String studentID;
	/**
	 * Title of Project involved
	 */
	private String projTitle;
	
	/**
	 * Default constructor for Request class
	 * @param approvalStatus The approval status of the request
	 * @param studentID The studentID of the student involved
	 * @param projTitle The title of the project involved
	 */
	public Request(ApprovalStatus approvalStatus, String studentID, String projTitle){
		this.approvalStatus = approvalStatus;
		this.studentID = studentID;
		this.projTitle = projTitle;
	}
	/**
	 * Appends a new request to the Request csv file with its attributes
	 */
	public abstract void addRequest();
	
	/**
	 * Updates a Request's approval status in the csv file
	 * @param projTitle The title of the project involved
	 * @param status The approval status of the request
	 */
	public void updateRequest(String projTitle, ApprovalStatus status)
	{
		Filepath f = new Filepath();
        List<String> lines = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(f.getREQFILENAME())))
       	{
            String line;
            String newData = "";
            int found = 0;
            int lineNumber = 0;
	        while ((line = br.readLine()) != null) 
            {
	        	if (line.trim().isEmpty()) 
		       	{
		            break;
		        }
		        lines.add(line);
		        if (found == 0)
		        {
		             lineNumber++;
			         String[] parts = line.split(",");
			        if (parts[3].equals(projTitle)&&parts[1].equals(this.getType().toString())&&parts[0].equals("Pending"))
				     {
				    	  newData = String.format("%s,%s,%s,%s,%s,%s", status, parts[1], parts[2], parts[3], parts[4], parts[5]);
				          found = 1;
				          this.approvalStatus = status;
				          System.out.println("Request status changed successfully!");
				     }
		        }
		    }
		    if (found == 0)
		    {
		         System.out.println("Project not found! Project status not changed!");
		         return;
		    }
		    br.close();
		        
		    lines.set(lineNumber-1, newData);			// the line number to overwrite (starting from 0)
		    FileWriter fw = new FileWriter(f.getREQFILENAME());    
		    PrintWriter out = new PrintWriter(fw); 
		    for (String line2 : lines) 
		    {
		    	out.println(line2);					// Write the modified data back to the file
		    }
		    out.close();
		}
        catch (IOException e) 
        {
        	e.printStackTrace();
        }
	}
	public ApprovalStatus isApprovalStatus() {
		return approvalStatus;
	}
	public void setApprovalStatus(ApprovalStatus approvalStatus) {
		this.approvalStatus = approvalStatus;
	}
	public RequestType getType() {
		return type;
	}
	public void setType(RequestType type) {
		this.type = type;
	}
	public String getStudentID() {
		return studentID;
	}
	public void setStudentID(String studentID) {
		this.studentID = studentID;
	}
	public String getProjTitle() {
		return projTitle;
	}
	public void setProjTitle(String projTitle) {
		this.projTitle = projTitle;
	}
}
