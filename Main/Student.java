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
/**
 * @author Winfred
 * @version 1.0
 * @since 15/4/2023
 */
public class Student implements User{
	/**
	 * Enum list for Student's FYP allocation status
	 */
	enum Status {Unassigned,Pending,Assigned,Ended}
	/**
	 * Student's ID
	 */
	private String studentID;
	/**
	 * Student's Name
	 */
	private String studentName;
	/**
	 * Student's Email
	 */
	private String studentEmail;
	/**
	 * Student's password
	 */
	private String password;
	/**
	 * Student's project allocation status
	 */
	private Status status; 
	/**
	 * Student's assigned Project class
	 */
	private Project proj; 
	
	
	Scanner sc = new Scanner (System.in);
	/**
	 * Creates a new Student with studentID and password.
	 * Used when logging in.
	 * @param studentID Student's ID
	 * @param password Student's password
	 * @throws FileNotFoundException When the csv file cannot be located
	 * @throws IOException When the I/O operation is interrupted
	 */
	public Student(String studentID, String password) throws FileNotFoundException, IOException 
	 {
	  this.studentID = studentID;
	  this.password = password;
	  Filepath f = new Filepath();
	  try(BufferedReader r = new BufferedReader(new FileReader(f.getSTUDFILENAME())))
	  {
	   int found = 0;
	   String line = r.readLine();
	   while(line!=null && found == 0)
	   {
	    // Add a new row to the bottom of the file
	             String[] email = line.split(",");
	             String studentEmail = email[1];     
	             String parts[] = email[1].split("@");//not sure what to add here
	             if (parts[0].equals(studentID))
	             {
	              this.studentName = email[0];
	              this.studentEmail = studentEmail;
	              this.proj = getProj(studentID);
	              this.status = setStatus(email[6]);
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
	 
	/**
	 * Creates a new Student with student name
	 * @param studName Student's Name
	 * @throws FileNotFoundException When the csv file cannot be located
	 * @throws IOException When the I/O operation is interrupted
	 */
	 public Student(String studName) throws FileNotFoundException, IOException
	 {
	  this.studentName = studName;
	  Filepath f = new Filepath();
	  try(BufferedReader r = new BufferedReader(new FileReader(f.getSTUDFILENAME())))
	  {
	   int found = 0;
	   String line = r.readLine();
	   while(line!=null && found == 0)
	   {
	    // Add a new row to the bottom of the file
	             String email[] = line.split(",");
	             String studEmail = email[1];
	             String parts[] = email[1].split("@");
	             if (email[0].equals(studentName))
	             {
	              this.studentID = parts[0];    
	              this.studentEmail = studEmail;
	              this.password = email[2];
	              this.proj = getProj(studentID);
	              this.status = setStatus(email[6]);
	              found = 1;
	             }
	             line = r.readLine();
	   }
	            

	        } catch (IOException e) 
	   {
	             e.printStackTrace();
	         }
	 }
	 
	 /**
	  * Creates a new Student with student ID
	  * @param studID Student's ID
	  * @param dummy Placeholder for method overloading
	 * @throws FileNotFoundException When the csv file cannot be located
	 * @throws IOException When the I/O operation is interrupted
	  */
	 public Student(String studID, int dummy) throws FileNotFoundException, IOException
	 {
	  this.studentID = studID;
	  Filepath f = new Filepath();
	  try(BufferedReader r = new BufferedReader(new FileReader(f.getSTUDFILENAME())))
	  {
	   int found = 0;
	   String line = r.readLine();
	   while(line!=null && found == 0)
	   {
	    // Add a new row to the bottom of the file
             String email[] = line.split(",");
             String studentEmail = email[1];     
             String parts[] = email[1].split("@");//not sure what to add here
             if (parts[0].equals(studentID))
             {
              this.studentName = email[0];
              this.studentEmail = studentEmail;
              this.proj = getProj(studentID);
              this.password = email[2];
              this.status = setStatus(email[6]);
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
	
	 /**
	  * Returns Student's assigned project. 
	  * Returns NULL if unallocated.
	  * @param studentID Student's ID
	  * @return Student's allocated project class
	  * @throws IOException When the I/O operation is interrupted
	  */
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
	
	/**
	 * Changes password for this Student
	 * Overrides the User's changePW() implemented method
	 */
	@Override
	public void changePW() 
	{
		String newPW;
		String checkPW = null;
		System.out.println("Please enter your current password: ");
		Scanner sc = new Scanner(System.in);
		checkPW = sc.nextLine();
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
		while (newPW.equals(this.getPW())) {
			System.out.println("New password cannot be the same as the current one.");
			System.out.println("Enter your new password: ");
			newPW = sc.next();
		}
		
		this.updateStudent(newPW);
		System.out.println("Password changed successfully!");
		return;
		
	}

	/**
	 * Prints out all Projects listed as "Available".
	 * Looks through project csv file
	 */
	public void viewAvailableProjects() {
	    int count=1;
	    Filepath f = new Filepath();
	    
	    if (this.status==Status.Ended) {
	    	System.out.println("You are not allowed to make a selection again as you deregistered your FYP.");
	    	return;
	    }
	    
	    if (this.status==Status.Assigned) {
	    	System.out.println("You have already been assigned a project.");
	    	return;
	    	
	    }
	    
	    
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
	/**
	 * Submits a deallocation / allocation request for a project of their choice
	 * Ensures Student's current status allows for project allocation / deallocation
	 * @param req Detemines if it is a allocation / deallocation request
	 * @throws FileNotFoundException When the csv file cannot be located
	 * @throws IOException When the I/O operation is interrupted
	 */
	  public void reqProj(boolean req) throws FileNotFoundException, IOException {

	    if (req==true)                 //for req project allocation
	    {
	    int index;
	    String projectTitle = "NA";
	    String supName = "NA";
	    viewAvailableProjects();
	    int valid=0;
	    
	    if (this.status==Status.Pending) {
	      System.out.println("You have a pending request for a project."); //sanity check
	      return;
	    }
	    
	    if (this.status==Status.Assigned) {
	      //System.out.println("You have been assigned a project."); //sanity check
	      return;
	    }

	    if (this.status==Status.Ended) {
	    	//System.out.println("You are not allowed to make a selection again as you deregistered your FYP.");
	    	return;
	    }
	    
	    int count=1;
	    int total=1;
	    Filepath f = new Filepath();
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
	      System.out.println("Enter the Number ID of the project you want to be allocated (-1 to quit) : ");
	      System.out.println();
	      index=sc.nextInt();
	      
	      if (index==-1) {
	    	  return;
	      }
	      
	      if (index>=total || index<1) {
	        System.out.println("Invalid selection!");
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
	          System.out.println("This project is unavailable.");
	          continue;
	        }
	        
	        if (parts[2].equals("Available"))
	        {  
	              projectTitle = parts[1];
	              supName=parts[0];
	              Project p= new Project(projectTitle);
	              
	              System.out.println(projectTitle);    //if available, we print out again and break out
	              System.out.println();
	              this.status=Status.Pending;
	              
	              updateStudent(projectTitle,supName,Status.Pending);      //updating student csv          
	              p.editProject(projectTitle,Project.Status.Reserved);          //updating project csv
	              
	              valid=1;                             //this for breaking
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
	        
	        
	        else {          //for request de-allocation, reject if got pending req
	          
	          if (this.status==Status.Pending) {
	            System.out.println("You have a pending request for a project."); //sanity check
	            return;
	          }
	          
	          if (this.status==Status.Unassigned) {
	            System.out.println("You have not been assigned a project."); //sanity check
	            return;
	          }
	          
	  	    if (this.status==Status.Ended) {
		    	System.out.println("You are not allowed to make a selection again as you deregistered your FYP.");
		    }
	          
	          String title="NA";
	          int count=1;
	          Filepath f = new Filepath();
	         
	        System.out.println("Confirm selection:");
	        System.out.println("1 : Continue");
	        System.out.println("-1 : Quit");
	        int confirm = sc.nextInt();
	        
	        if (confirm!=1) {
	        	return;
	        }
	        
	  		try (BufferedReader r = new BufferedReader(new FileReader(f.getREQFILENAME())))
			{	
				String line = r.readLine();
				line=r.readLine();
				while (line != null)
				{
					String[] parts = line.split(",");
					
					if (parts[2].equals(this.studentID) && parts[0].equals("Pending") && parts[1].equals("ReqChangeTitle")) //to check if have any pending change title req
					{
						System.out.println("You already have a pending request to change title.");
						return;
					}
					
					if (parts[2].equals(this.studentID) && parts[0].equals("Pending") && parts[1].equals("ReqDeregister"))
					{
						System.out.println("You already have a pending request to deregister a project.");
						return;	
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
	          
	          
	         try (BufferedReader r = new BufferedReader(new FileReader(f.getSTUDFILENAME())))
	          {  
	            String line = r.readLine();
	            line=r.readLine();
	            while (line != null)
	            {
	              String[] parts = line.split(",");
	              
	              if (parts[3].equals(this.studentID))
	              {
	                title=parts[4];                //iterate to find the current title new (need it to pass in as parameter)
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
	          System.out.println(title);
	          ReqDeregister r = new ReqDeregister(Request.ApprovalStatus.Pending, this.studentID, title);
	          r.addRequest();
	          }  
	        }
	          
	/**
	 * Prints out details of Student's allocated project
	 * Ensures Project exists
	 */
	public void viewProject() {
		
	    if (this.status==Status.Ended) {
	    	System.out.println("You are not allowed to view as you deregistered your FYP.");
	    	return;
	    }
		
	    if (this.status != Status.Assigned) {
			System.out.println("You have not been assigned a project.");    //sanity check
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

	/**
	 * Prints out pending requests followed by historical requests
	 */
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
				
				if ( (!parts[1].equals("ReqChangeSup")) && parts[2].equals(this.studentID)) //Check for student id and the relevant request type
				{
					if (parts[0].equals("Pending")) {
						System.out.println("**NEW** \t");
						System.out.println("Request type : " + parts[1]);
						System.out.println("Status : " + parts[0]);
						System.out.println("Name of Project : " + parts[3]);
						System.out.println( "Supervisor Name : " + parts[4]);//we print all all the pending (new)
						System.out.println();
					}
					recent=count; //will set the index last request into recent
						
				}
				count++;
				line = r.readLine();
			}
				
			if (recent==-1) {
				System.out.println("You have not sent in a request."); //after iterating if never find matches, means never request
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
			System.out.println("Request history : ");
			
			while (line != null)
			{
				String[] parts = line.split(",");
					
				if ((!parts[1].equals("ReqChangeSup")) && parts[2].equals(this.studentID))
				{	
					if (!parts[0].equals("Pending")) {
						System.out.println("**OLD** \t");
						System.out.println("Request type : " + parts[1]);
						System.out.println("Status : " + parts[0]);
						System.out.println("Name of Project : " + parts[3]);
						System.out.println( "Supervisor Name : " + parts[4]);//we print all all the pending (new)
						System.out.println();
					}
				}
				count++;
				line = r.readLine();
			}
			if (line==null) {
				System.out.println("No other request history.");
			}
			System.out.println();
				
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
				e.printStackTrace();
		}

	}
	
	/**
	 * Submits a Change Title request for allocated Project
	 * Ensures Student is currently allocated to a Project
	 * Ensures no pending deallocation request
	 */
	public void reqChangeTitle() {        //if got pending deallocation req, we dont allow
		
	    if (this.status==Status.Ended) {
	    	System.out.println(" You are not allowed to make a selection again as you deregistered your FYP.");
	    }
		
		if (this.status!=Status.Assigned) {
			System.out.println("You have not been assigned a project.");
			return;
		}
		
		Filepath f = new Filepath();
		try (BufferedReader r = new BufferedReader(new FileReader(f.getREQFILENAME())))
		{	
			String line = r.readLine();
			line=r.readLine();
			while (line != null)
			{
				String[] parts = line.split(",");
				
				if (parts[2].equals(this.studentID) && parts[0].equals("Pending") && parts[1].equals("ReqChangeTitle")) //to check if have any pending change title req
				{
					System.out.println("You already have a pending request to change title.");
					return;
				}
				
				if (parts[2].equals(this.studentID) && parts[0].equals("Pending") && parts[1].equals("ReqDeregister"))
				{
					System.out.println("You already have a pending request to deregister a project.");
					return;	
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
		
		
		String oldTitle="NA";
		String newTitle="NA";
		String SupervisorID="NA";
		System.out.println("Please enter the new title name :");
		newTitle=sc.nextLine();
		
		try (BufferedReader r = new BufferedReader(new FileReader(f.getSTUDFILENAME())))
		{	
			String line = r.readLine();
			line=r.readLine();
			while (line != null)
			{
				String[] parts = line.split(",");
				
				if (parts[3].equals(this.studentID)) //Check for student id and the correct request type
				{
					oldTitle=parts[4];               //store relevant data so can pass in as parameters later
					SupervisorID=parts[5];
					break;
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

		ReqChangeTitle r3 = new ReqChangeTitle(Request.ApprovalStatus.Pending, this.studentID , oldTitle, SupervisorID, newTitle);
		System.out.println("Supervisor Name: " + SupervisorID);
		System.out.println("Previous Title: " + oldTitle);
		r3.addRequest();
		System.out.println("New Title: " + newTitle);
		
	}

	/**
	 * Updates a Student's password in the csv file
	 * @param password This student's new password
	 */
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
				     if (parts[3].equals(this.getID()))
				     {
				          //System.out.println("Current ID: " + this.getID());
				          System.out.println("Updating password...");
				          newData = String.format("%s,%s,%s,%s,%s,%s,%s", parts[0], parts[1], password, parts[3], parts[4], parts[5], parts[6]);
				          found = 1;
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
	
	/**
	 * Updates a Student's allocation status in the csv file
	 * Updates Student's Project title and Supervisor Name.
	 * Will update with relevant NULL values if deallocation request calls this function
	 * @param projectTitle Allocated Project title. NULL if called by deallocation approval.
	 * @param supName Allocated Project's Supervisor' Name. NULL if called by deallocation approval.
	 * @param newStatus Student's new status
	 */
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
			         //System.out.println("Current ID: " + this.getID());
				     if (parts[3].equals(this.getID()))
				     {
				          newData = String.format("%s,%s,%s,%s,%s,%s,%s", parts[0], parts[1], parts[2], parts[3], projectTitle, supName, newStatus.toString());
				          found = 1;
				          System.out.println("Status updated to " + newStatus.toString());
				     }
		        }
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
	/**
	 * Updates Student's allocated Project's title in the csv file
	 * @param projectTitle Allocated Project title. NULL if called by deallocation approval.
	 * @param supName Allocated Project's Supervisor' Name. NULL if called by deallocation approval.
	 * @param newProjectTitle New Project's title 
	 */
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
				     if (parts[4].equals(projectTitle))
				     {
				          newData = String.format("%s,%s,%s,%s,%s,%s,%s", parts[0], parts[1], parts[2], parts[3], newProjectTitle, supName, parts[6]);
				          found = 1;
				          System.out.println("Project title changed successfully!");
				     }
		        }
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
	
	/**
	 * Returns Student's current password
	 */
	public String getPW() {
		return this.password;
	}

	/**
	 * Returns Student's ID
	 */
	@Override
	public String getID() 
	{
		return this.studentID;
	}
	 /**
	  * Converts status from a String to an Enum to be used in constructor.
	  * @param status Student's allocation status as a String
	  * @return Student's allocation status as an Enum
	  */
	private Status setStatus (String status)
	{
		this.status = Status.valueOf(status);
		return this.status;
	}
	
	

}
