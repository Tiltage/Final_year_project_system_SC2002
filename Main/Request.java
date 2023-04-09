package Main;
import java.io.*;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Request {
	enum RequestType {ReqAlloc, ReqDeregister, ReqChangeTitle, ReqChangeSup};
	enum ApprovalStatus {Pending,Rejected,Approved};
	private RequestType type;
	private ApprovalStatus approvalStatus;
	private String studentID;
	private String projTitle;
	
	public Request(ApprovalStatus approvalStatus, String studentID, String projTitle){
		this.approvalStatus = approvalStatus;
		this.studentID = studentID;
		this.projTitle = projTitle;
	}
	
	public void addRequest()
	{
		//To be overridden in inherited classes
	}
	
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
				     if (parts[3].equals(projTitle))
				     {
				          newData = String.format("%s,%s,%s,%s,%s,%s", status, parts[1], parts[2], parts[3], parts[4], parts[5]);
				          found = 1;
				          this.approvalStatus = status;
				          System.out.println("Project status changed successfully!");
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
	
	
	
	public static void main(String[] args) throws FileNotFoundException, IOException 
	{
		ReqAlloc r = new ReqAlloc(Request.ApprovalStatus.Pending, "Ben", "Calculator app");
		r.addRequest();
		ReqDeregister r2 = new ReqDeregister(Request.ApprovalStatus.Pending, "Ben", "Calculator app");
		r2.addRequest();
		ReqChangeTitle r3 = new ReqChangeTitle(Request.ApprovalStatus.Pending, "Ben", "Calculator app", "li fang", "Calc app");
		r3.addRequest();
		ReqChangeSup r4 = new ReqChangeSup(Request.ApprovalStatus.Pending, "Ben", "Calculator app", "li fang", "TA tan");
		r4.addRequest();

	}
	
	
}
