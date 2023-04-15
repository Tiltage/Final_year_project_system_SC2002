package Main;
import java.io.*;

import java.util.*;
/**
 * @author Ryan, Melvin, Reyan
 * @version 1.0
 * @since 15/4/2023
 */
public class Supervisor implements User {
	/**
	 * FacultyID of Supervisor
	 */
	private String facultyID ;
	/**
	 * Name of Supervisor
	 */
	private String supervisorName;
	/**
	 * Email of Supervisor
	 */
	private String supervisorEmail;
	/**
	 * Password of Supervisor
	 */
	private String password;
	/**
	 * Array of Project classes created by this Supervisor
	 */
	private Project[] projArr;
	/**
	 * Number of created projects that has been assigned to a studenet
	 */
	private int numAllocProjs;
	/**
	 * Total number of created projects
	 */
	private int numProj=0;
	
	
	/**
	 * Default constructor for Supervisor class
	 * @param facultyID FacultyID of Supervisor
	 * @param supervisorName Name of Supervisor
	 * @param supervisorEmail Email of Supervisor
	 * @throws FileNotFoundException When the csv file cannot be located
	 * @throws IOException When the I/O operation is interrupted
	 */
	public Supervisor(String facultyID, String supervisorName, String supervisorEmail) throws FileNotFoundException, IOException 
	{
		this.facultyID = facultyID;
		this.supervisorName = supervisorName;
		this.supervisorEmail = supervisorEmail;
		this.projArr = generateProjArr();
		this.numProj = projArr.length;
	}
	
	/**
	 * Creates a Supervisor class from Supervisor name
	 * @param supName Supervisor's name
	 * @throws FileNotFoundException When the csv file cannot be located
	 * @throws IOException When the I/O operation is interrupted
	 */
	public Supervisor(String supName) throws FileNotFoundException, IOException
	{
		this.supervisorName = supName;
		Filepath f = new Filepath();
		try(BufferedReader r = new BufferedReader(new FileReader(f.getSUPFILENAME())))
		{
			String csvSplitBy = ",";
	int found = 0;
		String line = r.readLine();
		while(line!=null && found == 0)
		{
			// Add a new row to the bottom of the file
            String[] email = line.split(csvSplitBy);
            String supervisorEmail = email[1];
            String parts[] = email[1].split("@");
	            if (email[0].equals(supervisorName))
	            {
	            	this.facultyID = parts[0];
	            	this.supervisorEmail = email[1];
	            	this.password = email[2];
	            	found = 1;
	            }
	            line = r.readLine();
			}
            

    } catch (IOException e) 
		{
            e.printStackTrace();
        }
	this.projArr = generateProjArr();
	this.numProj = projArr.length;
	this.numAllocProjs = generateAllocProjs(projArr);
}
	
	/**
	 * Creates a Supervisor class from facultyID and password
	 * Used when logging in
	 * @param facultyID FacultyID of Supervisor
	 * @param password Password of Supervisor
	 * @throws FileNotFoundException When the csv file cannot be located
	 * @throws IOException When the I/O operation is interrupted
	 */
	public Supervisor(String facultyID, String password) throws FileNotFoundException, IOException
	{
		this.facultyID = facultyID;
		this.password = password;
		Filepath f = new Filepath();
		try(BufferedReader r = new BufferedReader(new FileReader(f.getSUPFILENAME())))
		{
			int found = 0;
				String line = r.readLine();
				while(line!=null && found == 0)
				{
					// Add a new row to the bottom of the file
	            String[] email = line.split(",");
	            String supervisorEmail = email[1];
	            String parts[] = email[1].split("@");
		            if (parts[0].equals(facultyID))
		            {
		            	this.supervisorName = email[0];
		            	this.supervisorEmail = email[1];
		            	found = 1;
		            }
		            line = r.readLine();
				}
	            
		 }
	    catch (IOException e) 
			{
	            e.printStackTrace();
	        }
		this.projArr = generateProjArr();
		this.numProj = projArr.length;
		this.numAllocProjs = generateAllocProjs(projArr);
	}
		
	
	/**
	 * Looks through the project csv file to identify and instantiate all projects created by Supervisor.
	 * Project classes are stored in an array.
	 * @return An array of project classes instantiated by Project Title
	 * @throws FileNotFoundException When the csv file cannot be located
	 * @throws IOException When the I/O operation is interrupted
	 */
	public Project[] generateProjArr() throws FileNotFoundException, IOException
	{
		List<Project> list = new ArrayList<>();
		Filepath f = new Filepath();
		//System.out.println(this.supervisorName);
	try (BufferedReader r = new BufferedReader(new FileReader(f.getPROJFILENAME())))
	{
		String line = r.readLine();
		while (line != null)
		{
			String[] parts = line.split(",");
				if (parts[0].equals(this.supervisorName))
				{
					Project p = new Project(parts[1]);
					list.add(p);
				}
				line = r.readLine();
			}
		}
		
		int n = list.size();
	    Project[] projs = new Project[n];
	    for(int i=0; i<n; i++) 
	    {
	        projs[i] = list.get(i);
	    }
		return projs;
	}
	
	/**
	 * Returns the number of projects that have been allocated to a student
	 * @param projArr An array of Projects that is under this Supervisor
	 * @return
	 */
	private int generateAllocProjs(Project[] projArr)
	{
		int count = 0;
		for (int i=0; i<projArr.length; i++)
		{
			if (projArr[i].getStatus().toString().equals("Allocated"))
			{
				count++;
			}
		}
		return count;
	}
	
	/**
	 * @return Number of projects that has been allocated
	 */
	public int getNumAllocProjs()
	{
		return this.numAllocProjs;
	}
	
	/**
	 * @return Supervisor's facultyID
	 */
	public String getFacultyID()
	{
		return facultyID;
	}
	/*
	 * Sets the facultyID
	 */
	public void setFacultyID(String facultyID)
	{
		this.facultyID = facultyID;
	}
	/*
	 * Gets the number of projects created by this Supervisor
	 */
	public int getNumProj()
	{
	  return numProj;
	}
	
	/**
	 * Appends a new project to the project csv file
	 */
	public void createProj() 
	{
	String projTitle;
	Scanner sc = new Scanner(System.in);
		
	System.out.println("Please enter project title:");
	projTitle = sc.nextLine();
	//		System.out.println("Number of allocated projects " + this.numAllocProjs);
	if (this.numAllocProjs < 2)
	{
		Project p1 = new Project(this.supervisorName, projTitle, Project.Status.Available);
		p1.addProject();
	}
	else
	{
		System.out.println("Reached maximum number of allocated projects! Created project will be listed as \"Unavailable\"! ");
			Project p2 = new Project(this.supervisorName, projTitle, Project.Status.Unavailable);
			p2.addProject();
	}
	this.numProj++;
	}
	
	/**
	 * Prints all projects created by this Supervisor
	 */
	public void viewProject() 
	{
		int count=1;
		Filepath f = new Filepath();
		try (BufferedReader r = new BufferedReader(new FileReader(f.getPROJFILENAME())))
		{
			String line = r.readLine();
			line=r.readLine();
			System.out.println("Your name is " + this.supervisorName);
		System.out.println("The projects under you are: ");
		while (line != null)
		{
			String[] parts = line.split(",");
			
			if (parts[0].equals(this.supervisorName))
			{
				System.out.println("ID: " + count + " : " + parts[1] + " -- " + "Status: " + parts[2]);
				
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
	
	/*
	 * Edits a created Project's title directly in the csv file
	 */
	public void modifyProj() {
		int projNumber;
		String line;
		String projTitle, newProjTitle;
		Scanner sc = new Scanner(System.in);
		this.viewProject();
		System.out.println("Enter project number to modify");
		projNumber = sc.nextInt();
	
		Filepath f = new Filepath();
		try (BufferedReader r = new BufferedReader(new FileReader(f.getPROJFILENAME())))
		{
			line = r.readLine();
			for(int i=0;i<projNumber;i++)
			{
				line = r.readLine();
			}
			
			String[] parts = line.split(",");
			
			if (parts[0].equals(this.supervisorName)) {
				projTitle = parts[1];
				System.out.println("Project: " + projTitle);
			}
			else {
				System.out.println("Invalid project");
				return;
			}
			
			System.out.println("Please enter new project title:");
			sc.nextLine();
			newProjTitle = sc.nextLine();
			Project p = new Project(projTitle);
			p.editProject(projTitle, newProjTitle);
			
			Student student = new Student(parts[2]);
			student.updateStudent(projTitle, this.supervisorName, newProjTitle);
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
	/**
	 * Prints incoming requests from Student to Change Title
	 * Prints outgoing requests to Coordinator to Change Supervisor
	 */
	public void viewRequest() throws FileNotFoundException, IOException
	{
		int choice;
		Filepath f = new Filepath();
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Request type:");
	System.out.println("(1) Incoming Requests to change project title");
	System.out.println("(2) Outgoing Requests to change supervisor");
	choice=sc.nextInt();
	
	switch (choice) {
	
	case 1 : 
		try (BufferedReader r = new BufferedReader(new FileReader(f.getREQFILENAME())))
		{
			int count=1;
			String line = r.readLine();
			String[] header = line.split(",");
			System.out.println("");
			
			while (line != null) {
				String[] parts = line.split(",");
				if (parts[4].equals(this.supervisorName)&&parts[0].equals("Pending")&&parts[1].equals("ReqChangeTitle"))
				{
					System.out.println("**NEW**");
					System.out.println("Pending request "+count);
					System.out.println("Student: " +parts[2]);
					System.out.println("Project Title: " +parts[3]);
					System.out.println("New Title: " +parts[5]);
					System.out.println("");
					count++;
				}
				line = r.readLine();
			}
			if(count==1) {
				System.out.println("No pending requests.");
				System.out.println("");
			}
		}
		break;
	
	case 2 : 
		try (BufferedReader r = new BufferedReader(new FileReader(f.getREQFILENAME())))
		{
			int count=1;
			String line = r.readLine();
			String[] header = line.split(",");
			System.out.println("");
			
			while (line != null) {
				String[] parts = line.split(",");
				if (parts[4].equals(this.supervisorName)&&parts[0].equals("Pending")&&parts[1].equals("ReqChangeSup"))
				{
					System.out.println("Pending request "+count);
					System.out.println("Student: " +parts[2]);
					System.out.println("Project Title: " +parts[3]);
					System.out.println("Current Supervisor: " +parts[4]);
					System.out.println("New Supervisor: " +parts[5]);
					System.out.println("");
					count++;
				}
				line = r.readLine();
			}
			if(count==1) {
				System.out.println("No pending requests.");
				System.out.println("");
				}
			}
			break;
		
		default:
		}
	}
	
	/**
	 * Prints all non-pending Change Title requests
	 * Prints all non-pending Change Supervisor requests
	 * @throws FileNotFoundException When the csv file cannot be located
	 * @throws IOException When the I/O operation is interrupted
	 */
	public void viewReqHist() throws FileNotFoundException, IOException {
		int choice;
		Filepath f = new Filepath();
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Request type:");
	System.out.println("(1) View Change project title Requests");
	System.out.println("(2) View Change supervisor Requests");
	choice=sc.nextInt();
	
	switch (choice) {
	
	// Change ProjTitle
	case 1 : 
		try (BufferedReader r = new BufferedReader(new FileReader(f.getREQFILENAME())))
		{
			String line = r.readLine();
			String[] header = line.split(",");
			int count=1;
			while (line != null) {
				String[] parts = line.split(",");
				if (parts[4].equals(this.supervisorName) && !parts[0].equals("Pending") && parts[1].equals("ReqChangeTitle"))
				{
					System.out.println("Request "+count);
					System.out.println("Approval Status: "+parts[0]);
					System.out.println("Student: " +parts[2]);
					System.out.println("Project Title: " +parts[3]);
					System.out.println("New Title: " +parts[5]);
					System.out.println("");
					count++;
				}
				line = r.readLine();
			}
			if(count==1) {
				System.out.println("No previous requests.");
				System.out.println("");
			}
		}
		break;
	
	// Change Sup
	case 2 : 
		try (BufferedReader r = new BufferedReader(new FileReader(f.getREQFILENAME())))
		{
			String line = r.readLine();
			String[] header = line.split(",");
			int count=1;
			while (line != null) {
				String[] parts = line.split(",");
				if (parts[4].equals(this.supervisorName)&&!parts[0].equals("Pending")&&parts[1].equals("ReqChangeSup"))
				{
					System.out.println("Request "+count);
					System.out.println("Approval Status: "+parts[0]);
					System.out.println("Student: " +parts[2]);
					System.out.println("Project Title: " +parts[3]);
					System.out.println("New Supervisor: " +parts[5]);
					System.out.println("");
					count++;;
				}
				line = r.readLine();
			}
			if(count==1) {
				System.out.println("No previous requests.");
				System.out.println("");
				}
			}
			break;
		
		default:
		}
	}
	
	/**
	 * Approves an incoming Change Title request
	 * Edits the request csv file to change status
	 * Edits the student csv file to reflect new Project title
	 * @throws FileNotFoundException When the csv file cannot be located
	 * @throws IOException When the I/O operation is interrupted
	 */
	public void approveReq() throws FileNotFoundException, IOException {
		int choice=0;
		Filepath f = new Filepath();
		Scanner sc = new Scanner(System.in);
		
		do {
			int count=1;
			//View request
		try (BufferedReader r = new BufferedReader(new FileReader(f.getREQFILENAME())))
		{
			String line = r.readLine();
			String[] header = line.split(",");
			System.out.println("");
			
			while (line != null) {
				String[] parts = line.split(",");
				if (parts[4].equals(this.supervisorName)&&parts[0].equals("Pending")&&parts[1].equals("ReqChangeTitle"))
				{
					System.out.println("Pending request "+count);
					System.out.println("Student: " +parts[2]);
					System.out.println("Project Title: " +parts[3]);
					System.out.println("New Title: " +parts[5]);
					System.out.println("");
					count++;
				}
				line = r.readLine();
			}
		}
		
		//if no more requests
		if(count==1) {
			System.out.println("There are no more requests to approve");
			System.out.println();
			break;
		}
		
		//Accepting request			
		System.out.println("Enter request number to accept (Enter -1 to exit): ");
		choice=sc.nextInt();
		
		if(choice<1)return;
		
		try (BufferedReader r = new BufferedReader(new FileReader(f.getREQFILENAME())))
		{
			count=1;
			String line = r.readLine();
			String[] header = line.split(",");
			System.out.println("");
			
			while (line != null) {
				String[] parts = line.split(",");
				if (parts[4].equals(this.supervisorName) && parts[0].equals("Pending") && parts[1].equals("ReqChangeTitle"))
				{
					if(choice==count) {
						Project p = new Project(parts[3]);
						p.editProject(parts[3],parts[5]);
						
						ReqChangeTitle request = new ReqChangeTitle(Request.ApprovalStatus.Approved, "", "", "", "");
							request.updateRequest(parts[3], Request.ApprovalStatus.Approved);
							
							Student student = new Student(parts[2]);
							student.updateStudent(parts[3], this.supervisorName, parts[5]);
							break;
						}
						
						count++;
					}
					line = r.readLine();
				}
			}
		}while(choice>0);
		
		return;
	}
		
	
	/**
	 * Rejects an incoming Change Title request
	 * Edits request csv file to change status
	 * @throws FileNotFoundException When the csv file cannot be located
	 * @throws IOException When the I/O operation is interrupted
	 */
	public void rejectReq() throws FileNotFoundException, IOException {
		int choice=0;
		System.out.println("File found");
	Filepath f = new Filepath();
	Scanner sc = new Scanner(System.in);
	
	do {
		int count=1;
		//View request
		try (BufferedReader r = new BufferedReader(new FileReader(f.getREQFILENAME())))
		{
			String line = r.readLine();
			String[] header = line.split(",");
			System.out.println("");
			
			while (line != null) {
				String[] parts = line.split(",");
				if (parts[4].equals(this.supervisorName)&&parts[0].equals("Pending")&&parts[1].equals("ReqChangeTitle"))
				{
					System.out.println("Pending request "+count);
					System.out.println("Student: " +parts[2]);
					System.out.println("Project Title: " +parts[3]);
					System.out.println("New Title: " +parts[5]);
					System.out.println("");
					count++;
				}
				line = r.readLine();
			}
		}
		
		//if no more requests
		if(count==1) {
			System.out.println("There are no more requests to reject");
			System.out.println();
			break;
		}
		
		//Accepting request			
		System.out.println("Enter request number to reject (Enter -1 to exit): ");
		choice=sc.nextInt();
		
		if(choice<1)return;
		
		try (BufferedReader r = new BufferedReader(new FileReader(f.getREQFILENAME())))
		{
			count=1;
			String line = r.readLine();
			String[] header = line.split(",");
			System.out.println("");
			
			while (line != null) {
				String[] parts = line.split(",");
				if (parts[4].equals(this.supervisorName) && parts[0].equals("Pending") && parts[1].equals("ReqChangeTitle"))
				{
					if(choice==count) {
						ReqChangeTitle request = new ReqChangeTitle(Request.ApprovalStatus.Rejected, "", "", "", "");
							request.updateRequest(parts[3], Request.ApprovalStatus.Rejected);
							break;
						}
						
						count++;
					}
					line = r.readLine();
				}
			}
		}while(choice>0);
		
		return;
	}
	
	/**
	 * Changes password for this Supervisor class
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
	if (newPW.equals(this.getPW())) {
		System.out.println("New password cannot be the same as the current one");
		return;
	}
	
	this.updateSupervisor(newPW);
	System.out.println("Password changed successfully!");
		return;
		
	}
	/**
	 * Submits a request to Change Supervisor for a created project
	 */
	public void requestTransfer() 
	  {
	      Filepath f = new Filepath();
	      
	    //Looking up and printing all allocated requests
	  try(BufferedReader r = new BufferedReader(new FileReader(f.getPROJFILENAME())))
	  {
	    int NumOfProjFound = 0;
	    int lineNumber = 0;
	    String line = r.readLine();
	    String projList[] = new String[100];
	    
	    while(line!=null) {
	      lineNumber++;
	            String[] parts = line.split(",");
	            if (parts[0].equals(this.supervisorName) && parts[2].equals("Allocated"))
	            {
	              NumOfProjFound++;
	              System.out.printf("Project %d: %s", NumOfProjFound, parts[1]).println();
	              projList[NumOfProjFound] = parts[1];
	            }
	            line = r.readLine();              
	    }
	    r.close();
	    if (NumOfProjFound == 0)
	    {
	      System.out.println("You do not have any projects!");
	      return;
	    }
	      
	
		String projectName = "";
		String newSupName;
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Please enter the project number of the project to change supervisor: ");
		int projectNum=sc.nextInt();
		sc.nextLine();
		
		if(checkDupReq(this.supervisorName, projList[projectNum])) {
			  System.out.println("You still have a pending request to change supervisor for this project");
			  return;
		  }
		
		//adding request
		int Found = 0;
		
		while(Found==0) {
			BufferedReader Br = new BufferedReader(new FileReader(f.getSUPFILENAME()));
			System.out.println("Please enter name of new supervisor (Enter -1 to exit): ");
		    newSupName=sc.nextLine();
		    
		    if(newSupName.equals("-1"))break;
		    
		    if (newSupName.equalsIgnoreCase(this.supervisorName))
		    {
		    	System.out.println("You cannot transfer a project to yourself\n");
		    	continue;
		    }
		    String line2 = Br.readLine();
		      while(line2!=null)
		      {
		        // Add a new row to the bottom of the file
		              String[] parts2 = line2.split(",");
		              if (parts2[0].equalsIgnoreCase(newSupName))
		              {
		            	  Project temp = new Project(projList[projectNum]);
		            	  String studentName = temp.getStudentName();
		            	  Student s = new Student(studentName);
		            	  
		            	  
		            	  String studentID = s.getID();
		            	  System.out.println("Change supervisor of project:" + projList[projectNum] + " to " + newSupName);
		            	  ReqChangeSup re = new ReqChangeSup(Request.ApprovalStatus.Pending, studentID, projList[projectNum], this.supervisorName, newSupName);
		            	  re.addRequest();
		            	  return;
		              }
		              line2 = Br.readLine();              
		      }
		      Br.close();
		      System.out.println("Supervisor you are trying to change to does not exist!! \n"); 	      
			    }
	
			      }
		      
		      catch (IOException e){
		    	  e.printStackTrace();
		    }
		  }
		/**
		 * Checks if there is a duplicate Change Supervisor that is currently pending
		 * @param supName Involved Supervisor's name
		 * @param projTitle Involved Supervisor's title
		 * @return True/on check condition 
		 */
		public boolean checkDupReq(String supName, String projTitle) {
			Filepath f = new Filepath();
			try (BufferedReader r = new BufferedReader(new FileReader(f.getREQFILENAME())))
			{	
				String line = r.readLine();
				line=r.readLine();
				while (line != null)
				{
					String[] parts = line.split(",");
			
			if (parts[4].equals(supName) && parts[3].equals(projTitle)&& parts[0].equals("Pending") && parts[1].equals("ReqChangeSup")) //to check if have any pending change title req
			{
				return true;
			}
	
			line = r.readLine();
		}
	} catch (IOException e) {
		// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}
	/**
	 * Updates Supervisor's password in the csv file
	 * @param password New password
	 */
	private void updateSupervisor(String password)
	{	        
	    // Read the file into memory
	Filepath f = new Filepath();
	List<String> lines = new ArrayList<>();
	try(BufferedReader br = new BufferedReader(new FileReader(f.getSUPFILENAME())))
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
			     if (parts[0].equals(this.getSupervisorName()))
			     {
			          System.out.println("Current ID: " + this.getID());
			          System.out.println("Updating password...");
			          newData = String.format("%s,%s,%s", parts[0], parts[1], password);
			          found = 1;
			          System.out.println("Supervisor password changed successfully!");
			     }
	        }
	    }
	    if (found == 0)
	    {
	         System.out.println("Supervisor not found! Password not changed!");
	         return;
	    }
	    br.close();
	        
	    lines.set(lineNumber-1, newData);			// the line number to overwrite (starting from 0)
	    FileWriter fw = new FileWriter(f.getSUPFILENAME());    
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
	 * @return Supervisor's password
	 */
	public String getPW() {
		return this.password;
	}
	
	/**
	 * @return Supervisor's facultyID
	 */
	@Override
	public String getID() {
		return this.facultyID;
	}
	
	/**
	 * @return Supervisor's array of created projects
	 */
	public Project[] getProjArr()
	{
		return this.projArr;
	}
	
	/**
	 * @return Supervisor's name
	 */
	public String getSupervisorName() {
		return supervisorName;
	}
}
