package Main;

import java.io.*;
import java.util.*;

public class Project {
	
	enum Status {Available, Unavailable, Allocated, Reserved}
	private String studentID ;
	private String studentEmail;
	private int projID;
	private String supervisorName;
	private String supervisorEmail;
	private String projTitle;
	private Status status;
	
	public Project(String studentID, String studentEmail, int projID, String supervisorName, String supervisorEmail,
			String projTitle) 
	{
		this.studentID = studentID;
		this.studentEmail = studentEmail;
		this.projID = projID; 
		// Need to consider how to store last known project ID to +1 to previous
		// 1) While iterating, do a count and ++ to last known value
		// 2) Store number somewhere in csv (I think this is harder)
		this.supervisorName = supervisorName;
		this.supervisorEmail = supervisorEmail;
		this.projTitle = projTitle;
		this.status = Status.Available; // Default status set to available
	}

	public Project(String projTitle) 
	{
		Filepath f = new Filepath();
		try(BufferedReader r = new BufferedReader(new FileReader(f.getPath())))
		{
			String sup = "";
			String status = "";
			int found = 0;
				String line = r.readLine();
				while(line!=null && found == 0)
				{
					// Add a new row to the bottom of the file
		            String[] parts = line.split(",");
		            if (parts[1].equals(projTitle))
		            {
		            	this.supervisorName = parts[0];
		            	this.status = Status.valueOf(parts[2]);
		            	found = 1;
		            }
		            line = r.readLine();
				}
				System.out.println("Project not found!!");
	            

        } catch (IOException e) 
			{
	            e.printStackTrace();
	        }
	}
	
	public void addProject(Project p) //Parameter should be a Project class itself, not attributes of a project class
	// End state is to call p.addProject to add all its attributes into the csv file 
	{
		Filepath f = new Filepath();
		try(FileWriter fw = new FileWriter(f.getPath(), true);
	             BufferedWriter bw = new BufferedWriter(fw);
				 BufferedReader r = new BufferedReader(new FileReader(f.getPath()));
	             PrintWriter out = new PrintWriter(bw)) 
		
		{
				String line = r.readLine();
				while(line!=null)
				{
					// Add a new row to the bottom of the file
		            String[] parts = line.split(",");
		            line = r.readLine();
					out.printf("%s, %s, %s", p.supervisorName, p.projTitle, Status.Available).println(); //So on so forth for other attributes
		            System.out.println("Data appended to file successfully!");
				}
				
	            

        } catch (IOException e) 
			{
	            e.printStackTrace();
	        }
	}
	*/
	
	public void editProject(String projTitle, Status status)
	{
		//Edit Project Status -> E.g a student reserved this project, supervisor reached limit of projs etc
	}
	
	public void editProject(String projTitle, String newProjTitle)
	{
		//Edit Project Name -> Upon approval of ReqChangeTitle
	}

	public String getStudentID() {
		return studentID;
	}
	public void setStudentID(String studentID) {
		this.studentID = studentID;
	}
	public String getStudentEmail() {
		return studentEmail;
	}
	public void setStudentEmail(String studentEmail) {
		this.studentEmail = studentEmail;
	}
	public int getProjID() {
		return projID;
	}
	public void setProjID(int projID) {
		this.projID = projID;
	}
	public String getSupervisorName() {
		return supervisorName;
	}
	public void setSupervisorName(String supervisorName) {
		this.supervisorName = supervisorName;
	}
	public String getSupervisorEmail() {
		return supervisorEmail;
	}
	public void setSupervisorEmail(String supervisorEmail) {
		this.supervisorEmail = supervisorEmail;
	}
	public String getProjTitle() {
		return projTitle;
	}
	public void setProjTitle(String projTitle) {
		this.projTitle = projTitle;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}

	
	//set status method need?
	// get methods need? maybe for returning
	public static void main(String[] args) 
	{
		Project p = new Project("Sonification of geometry 1");
		System.out.println(p.getSupervisorName());
		System.out.println(p.getStatus());
	}

}

