package Main;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ReqDeregister extends Request{
	private String supID;
	private String newSupID;

	public ReqDeregister(ApprovalStatus approvalStatus, String studentID, String projTitle){
		super(approvalStatus, studentID, projTitle);
		this.setType(RequestType.ReqDeregister);
		this.supID = "na";
		this.newSupID = "na";
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

	public String getSupID() {
		return supID;
	}

	public void setSupID(String supID) {
		this.supID = supID;
	}

	public String getNewSupID() {
		return newSupID;
	}

	public void setNewSupID(String newSupID) {
		this.newSupID = newSupID;
	}
	
}
