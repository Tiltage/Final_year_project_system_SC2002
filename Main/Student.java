package Main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Student extends User{
	
	enum Status {Unassigned,Pending,Assigned}
	
	private String studentID;
	private String studentName;
	private String studentEmail;
	private Status status; //need initialise to unassigned?
	private Project proj; //unimplemented
	private Project[] deRegisProjs; //unimplemented
	
	Scanner sc = new Scanner (System.in);
	
	
	public Student() {}
	
	public Student(String studentID, String studentName, String studentEmail) throws FileNotFoundException, IOException 
	{
		this.studentID = studentID;
		this.studentName = studentName;
		this.studentEmail = studentEmail;
		this.proj = getProj(studentID);
		//this.projArr = generateProjArr();
		//this.numProj = projArr.length;
	}

	private Project getProj(String studentID) throws IOException {
		Filepath f = new Filepath();
		try(BufferedReader r = new BufferedReader(new FileReader(f.getSTUDFILENAME())))
		{
			String line = r.readLine();
			while(line!=null)
			{
				String[] parts = line.split(",");
				System.out.println(parts[4]);
				if (parts[4] != null && parts[3].equals(studentID))
				{
					Project p = new Project(parts[4]);
					System.out.println("Proj: " + p.getProjTitle());
					return p;
				}
				line = r.readLine();
			}
		}
		return null;
	}

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
		            	String[] lineparts = line.split(",");
		            	System.out.println(lineparts[0] + lineparts[4]);
		            	if (!lineparts[4].equals("null"))
		            	{
		            		Project p = new Project(lineparts[4]);
		            		System.out.println("Proj: " + p.getProjTitle());
		            		System.out.println("Sup: " + p.getSupervisorName());
		            		this.proj = p;
		            	}
		            	else
		            	{
		            		System.out.println("Project is null");
		            		this.proj = null;
		            	}
		            	System.out.println(this.studentName);
		            	System.out.println(this.studentEmail);
		            	found = 1;
		            }
		            line = r.readLine();
				}
	            

        } catch (IOException e) 
			{
	            e.printStackTrace();
	        }
	}
	
	@Override
	public void changePW() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getPW() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getID() {
		// TODO Auto-generated method stub
		return null;
	}
	


	public void viewAvailableProjects() {
		int count=1;
		Filepath f = new Filepath();
		try (BufferedReader r = new BufferedReader(new FileReader(f.getPROJFILENAME())))
		{
			String line = r.readLine();
			line=r.readLine();
			System.out.println("The title of the available projects are : ");
			while (line != null)
			{
				String[] parts = line.split(",");
				
				if (parts[2].equals("Available"))
				{
					System.out.println(count + " : " + parts[1]);
					
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

	public void reqProj(boolean req) {

		if (req==true)                 //for req project allocation
		{
		int index;
		String projectTitle = "NA";
		int valid=0;
		
		if (this.status==Status.Pending) {
			System.out.println("You have a pending request for a project"); //sanity check
			return;
		}
		
		if (this.status==Status.Assigned) {
			System.out.println("You have been assigned a project"); //sanity check
			return;
		}
		
		
		while (valid==0) {
			System.out.println("Enter the Number ID of the project you want to be allocated : ");
			System.out.println();
			index=sc.nextInt();
			
			Filepath f = new Filepath();
			try (BufferedReader r = new BufferedReader(new FileReader(f.getPROJFILENAME())))
			{
				String line = r.readLine();
				
				
				for (int i=0;i<index;i++) {
					line=r.readLine();			          //iterate the csv until the index of requested project
				}
				
				String[] parts = line.split(",");
				
				if (parts[2].equals("Available"))
				{
					projectTitle = parts[1];
					System.out.println(projectTitle);    //if available, we print out again and break out
					System.out.println();
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
					System.out.println("Project title : " + parts[3]);
					System.out.println("Your Supervisor name : " + parts[4]);
					
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
		System.out.println("Recent request status");
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
						System.out.println("Request type :" + parts[1] + " is " + parts[0]); //we print all all the pending (new)
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
			System.out.println("Request history");
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

	
	
	
}
