package Main;

import java.io.*;
import java.util.*;

public class Project {
	
	enum Status {Available, Unavailable, Allocated, Reserved}
	private String studentName;
	private int projID;
	private String supervisorName;
	private String projTitle;
	private Status status;
	
	public Project(String supervisorName, String projTitle, Status status) 
	{
		this.supervisorName = supervisorName;
		this.projTitle = projTitle;
		this.status = Status.Available; // Default status set to available
		Filepath f = new Filepath();
		try(BufferedReader br = new BufferedReader(new FileReader(f.getPROJFILENAME())))
		{
			String line;
	        int lineNumber = 0;
	        while ((line = br.readLine()) != null) 
	        {
	            if (line.trim().isEmpty()) 
	            {
	                break;
	            }
	            lineNumber++;
	        }
	        br.close();
	        this.projID = lineNumber;		//1st project is in row 2 cos there is a header row in row 1
		} 
		catch (IOException e) 
		{
            e.printStackTrace();
        }
	}

	public Project(String projTitle) 
	{
		Filepath f = new Filepath();
		try(BufferedReader r = new BufferedReader(new FileReader(f.getPROJFILENAME())))
		{
			int found = 0;
			int lineNumber = 0;
			String line = r.readLine();
			while(line!=null && found == 0)
			{
				// Add a new row to the bottom of the file
				lineNumber++;
	            String[] parts = line.split(",");
	            if (parts[1].equals(projTitle))
	            {
	            	this.supervisorName = parts[0];
	            	this.projTitle = parts[1];
	            	this.status = Status.valueOf(parts[2]);
	            	this.projID = lineNumber-1;
	            	this.studentName = getstudentName();//1st project is in row 2 cos there is a header row in row 1
	            	found = 1;
	            }
	            line = r.readLine();	            
			}
			r.close();
		if (found == 0)
		{
			System.out.println("Project not found! Attributes not retrieved!");
		}
	            
        } 
		catch (IOException e) 
			{
	            e.printStackTrace();
	        }
	}
	
	public void addProject() //Parameter should be a Project class itself, not attributes of a project class
	// End state is to call p.addProject to add all its attributes into the csv file 
	{	
		Filepath f = new Filepath();
		try	(FileWriter fw = new FileWriter(f.getPROJFILENAME(), true);
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
   
            out.printf("%s,%s,%s,%s", this.supervisorName,this.projTitle, this.status, this.projID).println();		//added .println() at the back cos when adding 2 projs consecutively, 2nd proj would append at the same line as 1st
            out.close();
            System.out.println("New project added successfully!");
		}
				
	     catch (IOException e) 
			{
	            e.printStackTrace();
	        }
		
	}
	
	private String getstudentName() throws FileNotFoundException, IOException
	{
		String name = null;
		Filepath f = new Filepath();
		try(BufferedReader r = new BufferedReader(new FileReader(f.getSTUDFILENAME())))
		{
			int found = 0;
			String line = r.readLine();
			while(line!=null && found == 0)
			{
	            String[] parts = line.split(",");
	            if (parts[4].equals(this.projTitle))
	            {
	            	name = parts[0];
	            	return name;
	            }
	            line = r.readLine();	            
			}
			r.close();
		}
		return name;
	}
	
	public String getStudentName()
	{
		return this.studentName;
	}
	
	public void editProject(String projTitle, Status status)
	{	        
        // Read the file into memory
        Filepath f = new Filepath();
        List<String> lines = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(f.getPROJFILENAME())))
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
				     if (parts[1].equals(projTitle))
				     {
				          newData = String.format("%s,%s,%s,%s", parts[0], parts[1], status, parts[3]);
				          found = 1;
				          this.status = status;
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
		    FileWriter fw = new FileWriter(f.getPROJFILENAME());    
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
	
	public void editProject(String projTitle, String newProjTitle)
	{	        
        // Read the file into memory
        Filepath f = new Filepath();
        List<String> lines = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(f.getPROJFILENAME())))
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
				     if (parts[1].equals(projTitle))
				     {
				          newData = String.format("%s,%s,%s,%s", parts[0], newProjTitle, parts[2],parts[3]);
				          found = 1;
				          System.out.println("Project title changed successfully!");
				     }
		        }
		    }
		    if (found == 0)
		    {
		         System.out.println("Project not found! Project title not changed!");
		         return;
		    }
		    br.close();
		        
		    lines.set(lineNumber-1, newData);			// the line number to overwrite (starting from 0)
		    FileWriter fw = new FileWriter(f.getPROJFILENAME());    
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
	
	public void editProjectSup(String projTitle, String newSup)
	{	        
        // Read the file into memory
        Filepath f = new Filepath();
        List<String> lines = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(f.getPROJFILENAME())))
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
				     if (parts[1].equals(projTitle))
				     {
				    	 System.out.println("New Supervisor name: " + newSup);
				    	 this.supervisorName = newSup;
				          newData = String.format("%s,%s,%s,%s", newSup, parts[1], parts[2], parts[3]);
				          found = 1;
				          System.out.println("Project supervisor changed successfully!");
				     }
		        }
		    }
		    if (found == 0)
		    {
		         System.out.println("Project not found! Supervisor not changed!");
		         return;
		    }
		    br.close();
		        
		    lines.set(lineNumber-1, newData);			// the line number to overwrite (starting from 0)
		    FileWriter fw = new FileWriter(f.getPROJFILENAME());    
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
	
}
	