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
		try(BufferedReader r = new BufferedReader(new FileReader(f.getPROJFILENAME())))
		{
			int found = 0;
				String line = r.readLine();
				while(line!=null && found == 0)
				{
					// Add a new row to the bottom of the file
		            String[] parts = line.split(",");
		            if (parts[1].equals(projTitle))
		            {
		            	this.supervisorName = parts[0];
		            	this.projTitle = parts[1];
		            	this.status = Status.valueOf(parts[2]);
		            	found = 1;
		            }
		            line = r.readLine();
		            
				}
				r.close();
			//	System.out.println("Project not found!!");
	            

        } catch (IOException e) 
			{
	            e.printStackTrace();
	        }
	}
	
	public void addProject() //Parameter should be a Project class itself, not attributes of a project class
	// End state is to call p.addProject to add all its attributes into the csv file 
	{	
		Filepath f = new Filepath();
		try(FileWriter fw = new FileWriter(f.getPROJFILENAME(), true);
			BufferedReader br = new BufferedReader(new FileReader(f.getPROJFILENAME()));
	        PrintWriter out = new PrintWriter(fw)) 
		
		{
			String line;
            int lineNumber = 0;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    break;
                }
                lineNumber++;
            }
            br.close();
            if (lineNumber>0)
            {
            	out.println();
            }
            
            out.printf("%s,%s,%s", this.supervisorName,this.projTitle, this.status); 
            out.close();
		}
				
	     catch (IOException e) 
			{
	            e.printStackTrace();
	        }
	}
	
	public void editProject(String projTitle)
	{
		//Edit Project Status -> E.g a student reserved this project, supervisor reached limit of projs etc
	}
	
	public void editProject(String projTitle, String newProjTitle)
	{
/*		Filepath f = new Filepath();
		try(FileWriter fw = new FileWriter(f.getPROJFILENAME(), true);
				BufferedReader br = new BufferedReader(new FileReader(f.getPROJFILENAME()));
		        PrintWriter out = new PrintWriter(fw)) 
		{
			int found = 0;
            int lineNumber = 0;
            String line = br.readLine();
            while(line!=null && found == 0)
            {
            	if (line.trim().isEmpty()) 
            	{
            		break;
            	}
            	lineNumber++;
            	String[] parts = line.split(",");
	            if (parts[1].equals(projTitle))
	            {
	            	this.projTitle = newProjTitle;
	            	
	            	found = 1;
	            }

            	out.printf("%s,%s,%s", this.supervisorName,this.projTitle, this.status); 
            	out.close();
            }		
				
        }
		catch (IOException e) 
			{
	            e.printStackTrace();
	        }
	        */

		        
		        // Read the file into memory
		        Filepath f = new Filepath();
		        List<String> lines = new ArrayList<>();
		        try(BufferedReader br = new BufferedReader(new FileReader(f.getPROJFILENAME())))
		       	{
		            String line;
		            String newData = "x,x,x";
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
				            if (parts[1].equals(projTitle))
				            {
				            	newData = String.format("%s,%s,%s", parts[0], newProjTitle, parts[2]);
				            	found = 1;
				            }
		                }
		            }
		            	br.close();
			            System.out.println(lineNumber);
			            System.out.println(newData);
		        // Modify the data as needed
		        // the line number to overwrite (starting from 0)
		          //  System.out.println(lineNumber);
		            lines.set(lineNumber-1, newData);
		            FileWriter fw = new FileWriter(f.getPROJFILENAME());    
		        // Write the modified data back to the file
		        	PrintWriter out = new PrintWriter(fw); 
		            for (String line2 : lines) 
		            {
		                out.println(line2);
		            }
		            out.close();
		        }
		        catch (IOException e) {
		            e.printStackTrace();
		        }
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
		Project p1 = new Project("1", "xyz@e", 69, "Li Fang", "lifang123@e", "helloWorld");
		p1.addProject();
		p1.editProject("helloWorld", "I<3 SC2002");
	}
	
}
	