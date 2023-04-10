package Main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Student extends User{
	
	enum Status {Unassigned,Pending,Assigned}
	
	private String studentID;
	private String studentName;
	private String studentEmail;
	private String password;
	private Status status; 
	private Project proj; 
	
	
	Scanner sc = new Scanner (System.in);
	
	public Student(String studentID) throws FileNotFoundException, IOException //copy kendrea constructor
	{
		this.studentID = studentID;
		Filepath f = new Filepath();
		try(BufferedReader r = new BufferedReader(new FileReader(f.getSTUDFILENAME())))
		{
			String csvSplitBy = ",";
			int found = 0;
				String line = r.readLine();
				while(line!=null && found == 0)
				{
					// Add a new row to the bottom of the file
		            String[] email = line.split(csvSplitBy);
		            String studentEmail = email[0];             //not sure what to add here
		            String parts[][] = new String[email.length][];
		            for (int x = 0; x < email.length; x++)
		            {
		            	parts[x] = email[x].split("@");
			
		            }
		            if (parts[1][0].equals(studentID))
		            {
		            	this.studentName = parts[0][0];
		            	this.studentEmail = email[1];
		            	this.password=email[2];
		            	this.proj = getProj(studentID);
		            	found = 1;
		            }
		            line = r.readLine();
				}
	            
        } 
		catch (IOException e) 
		{
            e.printStackTrace();
        }
	}

	private Project getProj(String studentID) throws IOException {
		Filepath f = new Filepath();
		try(BufferedReader r = new BufferedReader(new FileReader(f.getSTUDFILENAME())))
		{
			String line = r.readLine();
			String stat = null;
			String password = null;
			while(line!=null)
			{
				String[] parts = line.split(",");
				System.out.println(parts[4]);
				if (parts[4] != null && parts[3].equals(studentID))
				{
					Project p = new Project(parts[4]);
					stat = parts[6];
					password = parts[2];
					this.setStatus(stat);
					this.password = password;
					return p;
				}
				line = r.readLine();
			}
		}
		return null;
	}
	
	private Status setStatus (String status)
	{
		this.status = Status.valueOf(status);
		return this.status;
	}
	
	@Override
	public void changePW() 
	{
		String newPW;
		String checkPW = null;
		System.out.println("Please enter your current password: ");
		Scanner sc = new Scanner(System.in);
		checkPW = sc.next();
		System.out.println("Password: " + checkPW);
		System.out.println("Current PW: " + this.getPW());
		System.out.println(this.getPW().equals(checkPW) != false);
		if (this.getPW().equals(checkPW) == false)
		{
			while (this.getPW().equals(checkPW) == false)
			{
				System.out.println("Incorrect password, please try again.");
				System.out.println("Please enter your current password: ");
				checkPW = sc.nextLine();
			}
		}

		System.out.println("Enter your new password: ");
		newPW = sc.next();
		if (newPW.equals(this.getPW())) {
			System.out.println("New password cannot be the same as the current one");
			return;
		}
		
		this.updateStudent(newPW);
		System.out.println("Password changed successfully!");
		return;
		
	}

	public String getPW() {
		return this.password;
	}

	@Override
	public String getID() 
	{
		return this.studentID;
	}
	


	public void viewAvailableProjects() {
	    int count=1;
	    Filepath f = new Filepath();
	    try (BufferedReader r = new BufferedReader(new FileReader(f.getPROJFILENAME())))
	    {
	      String line = r.readLine();
	      line=r.readLine();
	      System.out.println("The title of the available projects are ");
	      System.out.println("ID : Title ");
	      while (line != null)
	      {
	        String[] parts = line.split(",");
	        
	        if (parts[2].equals("Available"))
	        {
	          System.out.println(count + " : " + parts[1] + " -- Supervisor: " + parts[0]);
	          
	        }
	        count++;  //should i put this inside?
	        line = r.readLine();
	      }
	    } catch (FileNotFoundException e) {
	      // TODO Auto-generated catch block
	      e.printStackTrace();
	    } catch (IOException e) {
	      // TODO Auto-generated catch block
	      e.printStackTrace();
	    }
	    System.out.println();
	    
	  }

	  public void reqProj(boolean req) throws FileNotFoundException, IOException {

	    if (req==true)                 //for req project allocation
	    {
	    int index;
	    String projectTitle = "NA";
	    String supName = "NA";
	    
	    int valid=0;
	    
	    if (this.status==Status.Pending) {
	      System.out.println("You have a pending request for a project"); //sanity check
	      return;
	    }
	    
	    if (this.status==Status.Assigned) {
	      System.out.println("You have been assigned a project"); //sanity check
	      return;
	    }

	    List<String> DeRegisProj = new ArrayList<>();          //declare an araylis of deregistered projects
	    
	    int count=1;
	    Filepath f = new Filepath();
	    try (BufferedReader r = new BufferedReader(new FileReader(f.getREQFILENAME())))
	    {
	      String line = r.readLine();
	      line=r.readLine();
	      while (line != null)
	      {
	        String[] parts = line.split(",");
	        if (parts[0].equals("Approved") && parts[1].equals("ReqDealloc") && parts[2].equals(this.studentID))
	        {
	          DeRegisProj.add(parts[3]);             //adding all the deregistered project into array
	        }
	        line = r.readLine();
	      }
	    } catch (FileNotFoundException e) {
	      // TODO Auto-generated catch block
	      e.printStackTrace();
	    } catch (IOException e) {
	      // TODO Auto-generated catch block
	      e.printStackTrace();
	    }
	    
	    System.out.println("List of De-registered Projects : ");
	    if (DeRegisProj.size()==0) {
	      System.out.println("None");   
	    }
	    else {
	    for (int i=0;i<DeRegisProj.size();i++) {
	        System.out.println(DeRegisProj.get(i));                //prints out all the previously registered projects
	        }
	      }
	    System.out.println();
	    
	    int total=1;
	    try (BufferedReader r = new BufferedReader(new FileReader(f.getPROJFILENAME())))
	    {  
	      String line = r.readLine();
	      line=r.readLine();
	      while (line != null)
	      {
	        total++; 
	        line = r.readLine();
	      }
	    } catch (FileNotFoundException e) {
	      // TODO Auto-generated catch block
	      e.printStackTrace();
	    } catch (IOException e) {
	      // TODO Auto-generated catch block
	      e.printStackTrace();
	    }
	    
	    
	    
	    
	    while (valid==0) {
	      System.out.println("Enter the Number ID of the project you want to be allocated : ");
	      System.out.println();
	      index=sc.nextInt();
	      
	      if (index>=total || index<1) {
	        System.out.println("Invalid selection");
	        continue;
	      }
	      
	      try (BufferedReader r = new BufferedReader(new FileReader(f.getPROJFILENAME())))
	      {
	        String line = r.readLine();
	        
	        
	        for (int i=0;i<index;i++) {
	          line=r.readLine();                //iterate the csv until the index of requested project
	        }
	        
	        String[] parts = line.split(",");
	        
	        if (!parts[2].equals("Available")) {
	          System.out.println("This project is unavailable");
	          continue;
	        }
	        
	        if (parts[2].equals("Available"))
	        {  
	          for (int j=0;j<DeRegisProj.size();j++) {
	        	  if (parts[1].equals(DeRegisProj.get(j))){
	                  
	                  System.out.println("You have registered for this project before !");   //check if they registered for the project before
	                  continue;              
	                }
	                
	              }

	              projectTitle = parts[1];
	              supName=parts[0];
	              Project p= new Project(projectTitle);
	              
	              System.out.println(projectTitle);    //if available, we print out again and break out
	              System.out.println();
	              this.status=Status.Pending;
	              
	              updateStudent(projectTitle,supName,Status.Pending);      //updating student csv          
	              p.editProject(projectTitle,Project.Status.Reserved);          //updating project csv
	              
	              valid=1;                             //this for breaking
	              
	              //student csv status not updated
	              //project csv status not updated
	            }

	          } catch (FileNotFoundException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	          } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	          }
	        }
	        
	        ReqAlloc r = new ReqAlloc(Request.ApprovalStatus.Pending, this.studentID, projectTitle);
	        r.addRequest();
	        }
	        
	        
	        else {          //for request de-allocation
	          
	          if (this.status==Status.Pending) {
	            System.out.println("You have a pending request for a project"); //sanity check
	            return;
	          }
	          
	          if (this.status==Status.Unassigned) {
	            System.out.println("You have not been assigned a project"); //sanity check
	            return;
	          }
	          
	          String title="NA";
	          int count=1;
	          Filepath f = new Filepath();
	          try (BufferedReader r = new BufferedReader(new FileReader(f.getSTUDFILENAME())))
	          {  
	            String line = r.readLine();
	            line=r.readLine();
	            while (line != null)
	            {
	              String[] parts = line.split(",");
	              
	              if (parts[2].equals(this.studentID))
	              {
	                title=parts[3];                //iterate to find the current title new (need it to pass in as parameter)
	                break;
	                
	              }
	              count++;
	              line = r.readLine();
	            }
	          } catch (FileNotFoundException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	          } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	          }
	          System.out.println();
	          
	          ReqDeregister r = new ReqDeregister(Request.ApprovalStatus.Pending, this.studentID, title);
	          r.addRequest();
	          }  
	        }
	          
	
	public void viewProject() {
		
		
		if (this.status != Status.Assigned) {
			System.out.println("You have not been assigned a project");    //sanity check
			return;
		}
		int count=1;
		Filepath f = new Filepath();
		try (BufferedReader r = new BufferedReader(new FileReader(f.getSTUDFILENAME())))
		{
			String line = r.readLine();
			line=r.readLine();
			while (line != null)
			{
				String[] parts = line.split(",");
				
				if (parts[0].equals(this.studentName))             //just printing out the details
				{	
					System.out.println("Your project details ");
					System.out.println("Project title : " + parts[4]);
					System.out.println("Your Supervisor name : " + parts[5]);
					
				}
				count++;
				line = r.readLine();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println();
		
	}

	@Override
	public void viewRequest() {
			
		int count=1; 
		int recent=-1;
		Filepath f = new Filepath();
		System.out.println("Recent request status: ");
		try (BufferedReader r = new BufferedReader(new FileReader(f.getREQFILENAME())))
		{
			String line = r.readLine();
			line=r.readLine();
			while (line != null)
			{
				String[] parts = line.split(",");
				
				if ( (!parts[1].equals("ChangeSup")) && parts[2].equals(this.studentID)) //Check for student id and the relevant request type
				{
					if (parts[0].equals("Pending")) {
						System.out.println("**NEW** \t");
						System.out.println("Request type : " + parts[1]);
						System.out.println("Status: " + parts[0]);
						System.out.println("Name of Project: " + parts[3]);
						System.out.println( "Supervisor Name: " + parts[4]);//we print all all the pending (new)
						System.out.println();
					}
					recent=count; //will set the index last request into recent
						
				}
				count++;
				line = r.readLine();
			}
				
			if (recent==-1) {
				System.out.println("You have not sent in a request"); //after iterating if never find matches, means never request
				return;
			}
				
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		System.out.println();
			
			
		try (BufferedReader r = new BufferedReader(new FileReader(f.getREQFILENAME())))
		{
			String line = r.readLine();
			line=r.readLine();
			System.out.println("Request history: ");
			while (line != null)
			{
				String[] parts = line.split(",");
					
				if ((!parts[1].equals("ChangeSup")) && parts[2].equals(this.studentID))
				{	
					if (!parts[0].equals("Pending")) {
						System.out.println("Request type :" + parts[1] + " is " + parts[0]); //print out all the approved/rejected
					}
				}
				count++;
				line = r.readLine();
			}
				
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
				e.printStackTrace();
		}

	}

	public void reqChangeTitle() {
		
		if (this.status!=Status.Assigned) {
			System.out.println("You have not been assigned a project");
			return;
		}
		
		String oldTitle="NA";
		String newTitle="NA";
		String SupervisorID="NA";
		
		System.out.println("Please enter the new title name :");
		newTitle=sc.next();
		
		Filepath f = new Filepath();
		try (BufferedReader r = new BufferedReader(new FileReader(f.getSTUDFILENAME())))
		{	
			int count=1;
			String line = r.readLine();
			line=r.readLine();
			while (line != null)
			{
				String[] parts = line.split(",");
				
				if (parts[0].equals(this.studentID)) //Check for student id and the correct request type
				{
					oldTitle=parts[3];               //store relevant data so can pass in as parameters later
					SupervisorID=parts[4];
					break;
				}
				count++;
				line = r.readLine();
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ReqChangeTitle r3 = new ReqChangeTitle(Request.ApprovalStatus.Pending, this.studentID , oldTitle, SupervisorID, newTitle);
		r3.addRequest();
		
	}

	private void updateStudent(String password)
	{	        
        // Read the file into memory
        Filepath f = new Filepath();
        List<String> lines = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(f.getSTUDFILENAME())))
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
			         System.out.println("Status: " + parts[6]);
				     if (parts[3].equals(this.getID()))
				     {
				          System.out.println("Current ID: " + this.getID());
				          System.out.println("Updating password...");
				          newData = String.format("%s,%s,%s,%s,%s,%s,%s", parts[0], parts[1], password, parts[3], parts[4], parts[5], parts[6]);
				          found = 1;
				          System.out.println("Status: " + parts[6]);
				          System.out.println("Student password changed successfully!");
				     }
		        }
		    }
		    if (found == 0)
		    {
		         System.out.println("Student not found! Password not changed!");
		         return;
		    }
		    br.close();
		        
		    lines.set(lineNumber-1, newData);			// the line number to overwrite (starting from 0)
		    FileWriter fw = new FileWriter(f.getSTUDFILENAME());    
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
	public void updateStudent(String projectTitle, String supName, Status newStatus)
	{	        
        // Read the file into memory
        Filepath f = new Filepath();
        List<String> lines = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(f.getSTUDFILENAME())))
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
			         System.out.println("Current ID: " + this.getID());
				     if (parts[3].equals(this.getID()))
				     {
				          newData = String.format("%s,%s,%s,%s,%s,%s,%s", parts[0], parts[1], parts[2], parts[3], projectTitle, supName, newStatus.toString());
				          found = 1;
				          System.out.println("Status updated to " + newStatus.toString());
				     }
		        }
		    }
		    if (found == 0)
		    {
		         System.out.println("Title not found! Assigned project title not changed!");
		         return;
		    }
		    br.close();
		        
		    lines.set(lineNumber-1, newData);			// the line number to overwrite (starting from 0)
		    FileWriter fw = new FileWriter(f.getSTUDFILENAME());    
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
	
	public void updateStudent(String projectTitle, String supName, String newProjectTitle)
	{	        
        // Read the file into memory
        Filepath f = new Filepath();
        List<String> lines = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(f.getSTUDFILENAME())))
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
			         System.out.println("Current ID: " + this.getID());
				     if (parts[4].equals(projectTitle))
				     {
				          newData = String.format("%s,%s,%s,%s,%s,%s,%s", parts[0], parts[1], parts[2], parts[3], newProjectTitle, supName, parts[6]);
				          found = 1;
				          System.out.println("Project title changed successfully!");
				     }
		        }
		    }
		    if (found == 0)
		    {
		         System.out.println("Project Title not found! Assigned project title not changed!");
		         return;
		    }
		    br.close();
		        
		    lines.set(lineNumber-1, newData);			// the line number to overwrite (starting from 0)
		    FileWriter fw = new FileWriter(f.getSTUDFILENAME());    
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
	
	
	
}
