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

public class Coordinator extends Supervisor {
	
	private String facultyID ;
	private String coordinatorName;
	private String coordinatorEmail;
	private String password;
	private Project[] projArr;
	private int numProj;
	
	Scanner sc=new Scanner(System.in);
	
	public Coordinator(String facultyID, String supervisorName, String supervisorEmail, int numProj) throws FileNotFoundException, IOException 
	{
		super(facultyID, supervisorName, supervisorEmail);
		this.password = getPW();
		//super(facultyID, supervisorName, supervisorEmail, numProj); not sure whats numproj is for
	}
	
	public Coordinator (String facultyID) throws FileNotFoundException, IOException
	{
		this.facultyID = facultyID;
		this.projArr = generateProjArr();
		Filepath f = new Filepath();
		try(BufferedReader r = new BufferedReader(new FileReader(f.getCOORDFILENAME())))
		{
			String csvSplitBy = ",";
			int found = 0;
				String line = r.readLine();
				while(line!=null && found == 0)
				{
					// Add a new row to the bottom of the file
		            String[] email = line.split(csvSplitBy);
		            String coordinatorEmail = email[0];
		            String parts[][] = new String[email.length][];
		            for (int x = 0; x < email.length; x++)
		            {
		            	parts[x] = email[x].split("@");
			
		            }
		            if (parts[1][0].equals(facultyID))
		            {
		            	this.coordinatorName = parts[0][0];
		            	this.coordinatorEmail = email[1];
		            	this.password = parts[2][0];
		            	found = 1;
		            }
		            line = r.readLine();
				}
	            

        } catch (IOException e) 
			{
	            e.printStackTrace();
	        }
	}
	
	
	public void approveReq(Boolean approve) throws FileNotFoundException, IOException 
	{
		if (approve)
		{
			Request.ApprovalStatus result = Request.ApprovalStatus.Approved;
			System.out.println("Enter the request number to approve (Enter -1 to exit): ");
			int choice;
			Scanner sc = new Scanner(System.in);
			choice = sc.nextInt();
			Filepath f = new Filepath();
			while (choice > 0)
			{
				try (BufferedReader r = new BufferedReader(new FileReader(f.getREQFILENAME())))
				{
					int count=1;
					String line = r.readLine();
					line = r.readLine();
					while (line != null) 
					{
						String[] parts = line.split(",");
						if (choice == count)
						{
							System.out.println("facultyID: " + this.getFacultyID());
							System.out.println(parts[0].equals("Pending"));
							System.out.println(parts[1].equals("ReqChangeTitle"));
							if (parts[4].equals(this.getFacultyID()) && parts[0].equals("Pending") && parts[1].equals("ReqChangeTitle"))
							{
								//If request chosen is changeTitle
								System.out.println("Title Changed successfully");
								Project p = new Project(parts[3]);
								p.editProject(parts[3],parts[5]);
								
								ReqChangeTitle request = new ReqChangeTitle(result, "", "", "", "");
								request.updateRequest(parts[3], result);
								
								Student s = new Student(parts[2]);
								s.updateStudent(p.getProjTitle(), p.getSupervisorName(), Student.Status.Assigned);
								return;
							}
							else if (parts[0].equals("Pending") && (parts[1].equals("ReqAlloc")))
							{
								//If request chosen is ReqAlloc
								//Edit Project file status and sup name to reflect changes
								System.out.println("Project Allocated successfully");
								Project p = new Project(parts[3]);
								p.editProject(parts[3],Project.Status.Allocated);
								p.editProjectSup(parts[3], parts[4]);
								
								//Edit Student file status to reflect changes
								Student s = new Student(parts[2]);
								s.updateStudent(p.getProjTitle(), p.getSupervisorName(), Student.Status.Assigned);
								
								//Edit Request file status to reflect changes
								ReqChangeTitle request = new ReqChangeTitle(result, "", "", "", "");
								request.updateRequest(parts[3], result);
								return;
							}
							else if (parts[0].equals("Pending") && parts[1].equals("ReqChangeSup"))
							{
								int choice2 = 0;
								//If request chosen is ReqChangeSup
								//If approving this request will not result in supervisor having >2 projects
							//	if (this.numProj == 2)
							//	{
									System.out.println("Supervisor has reached the maximum number of projects");
									System.out.println("Press 1 to proceed with approval (Enter -1 to go back):");
									Scanner sc1 = new Scanner(System.in);
									choice2 = sc1.nextInt();
									if (choice2 == 1)
									{
										System.out.println("Supervisor Changed successfully");
										Project p = new Project(parts[3]);
										p.editProjectSup(parts[3], parts[4]);
										
										//Edit Student file supID to reflect changes 
										Student s = new Student(parts[2]);
										s.updateStudent(p.getProjTitle(), p.getSupervisorName(), Student.Status.Assigned);
										
										//Edit Request file status to reflect changes
										ReqChangeTitle request = new ReqChangeTitle(result, "", "", "", "");
										request.updateRequest(parts[3], result);
										return;
									}
							//	}
								
							}
							line = r.readLine();
						}
						else if (parts[0].equals("Pending") && !(!parts[5].equals(this.getFacultyID()) && parts[1].equals("ReqChangeTitle")))
						{
							//Overall check to verify if its a viable request for Coordinator
							count++;
						}
						line = r.readLine();
					}
				
				}
				System.out.println("Enter the request number to approve/reject (Enter -1 to exit): ");
				choice = sc.nextInt();

			}
		}
		else
		{
			Request.ApprovalStatus result = Request.ApprovalStatus.Rejected;
			System.out.println("Enter the request number to reject (Enter -1 to exit): ");
			int choice;
			Scanner sc = new Scanner(System.in);
			choice = sc.nextInt();
			Filepath f = new Filepath();
			while (choice >0)
			{
				try (BufferedReader r = new BufferedReader(new FileReader(f.getREQFILENAME())))
				{
					int count=1;
					String line = r.readLine();
					line = r.readLine();
					while (line != null) 
					{
						String[] parts = line.split(",");
						if (choice == count)
						{
							ReqChangeTitle request = new ReqChangeTitle(result, "", "", "", "");
							request.updateRequest(parts[3], result);
							System.out.println("Request rejected");
							return;
							
						}
						if (parts[0].equals("Pending") && !(!parts[5].equals(this.getFacultyID()) && parts[1].equals("ReqChangeTitle")))
						{
							count++;
						}
						line = r.readLine();
					}
				}
			}
		}
	}
	public void viewAllProjs() {
		Filepath f = new Filepath();
		int count = 1;
		try (BufferedReader r = new BufferedReader(new FileReader(f.getPROJFILENAME())))
		{
			String line = r.readLine();
			line=r.readLine();
			System.out.println("View all Projects:");
			System.out.println("==================");
			while (line != null)
			{
				String[] parts = line.split(",");
				System.out.println(count + ")");
				System.out.println("Supervisor Name: " + parts[0]);
				System.out.println("Name of Project: " + parts[1]);
				System.out.println("Status: " + parts[2]);
				System.out.println("==================");
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
	
	public void createReport() throws FileNotFoundException, IOException {
		int count=1;
		Filepath f = new Filepath();
		try (BufferedReader r = new BufferedReader(new FileReader(f.getREQFILENAME())))
		{
			String line = r.readLine();
			line=r.readLine();
			System.out.println("All projects:");
		}
	}
	
	public void viewPendingReqCoord() {
		Filepath f = new Filepath();
		int count = 1;
		try (BufferedReader r = new BufferedReader(new FileReader(f.getREQFILENAME())))
		{
			String line = r.readLine();
			line=r.readLine();
			System.out.println("View all Pending Requests:");
			System.out.println("==================");
			while (line != null)
			{
				String[] parts = line.split(",");
				if (parts[0].equals("Pending") && !(!parts[5].equals(this.getFacultyID()) && parts[1].equals("ReqChangeTitle")))
				{
					System.out.println(count + ") **NEW** \t");
					System.out.println("Request type : " + parts[1]);
					System.out.println("Status: " + parts[0]);
					System.out.println("Name of Project: " + parts[3]);
					System.out.println("Supervisor Name: " + parts[4]);
					System.out.println("Student name: " + parts[2]);//we print all all the pending (new)
					System.out.println("==================");
					count++;
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
		System.out.println();
	}
	
	public void viewReqHistCoord()
	{
		int count=1;
		Filepath f = new Filepath();
		try (BufferedReader r = new BufferedReader(new FileReader(f.getREQFILENAME())))
		{
			String line = r.readLine();
			line=r.readLine();
			System.out.println("View Requests History:");
			while (line != null)
			{
				String[] parts = line.split(",");
				if (!parts[0].equals("Pending") && !parts[1].equals("ReqChangeTitle")) 
				{
					System.out.println(count + ")");
					System.out.println("Status: " + parts[0]);
					System.out.println("Name of Project: " + parts[3]);
					System.out.println("Supervisor Name: " + parts[4]);
					System.out.println("Student name: " + parts[2]);
					System.out.println("==================");
					count++;
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
		System.out.println();
	}
	
	@Override
	public void changePW() {
		String newPW;
		String checkPW = null;
		System.out.println("Please enter your current password: ");
		Scanner sc = new Scanner(System.in);
		checkPW = sc.next();
		sc.nextLine();
		while (this.getPW().equals(checkPW) == false){
			System.out.println("Incorrect password, please try again.");
			System.out.println("Please enter your current password: ");
			checkPW = sc.nextLine();
		}


		System.out.println("Enter your new password: ");
		newPW = sc.next();
		this.updateCoordinator(newPW);
		System.out.println("Password changed successfully!");
		return;
		
	}
	
	public String getPW() {
		return this.password;
	}
	
	public String getFacultyID()
	{
		return this.facultyID;
	}

	
	private void updateCoordinator(String password)
	{	        
        // Read the file into memory
        Filepath f = new Filepath();
        List<String> lines = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(f.getCOORDFILENAME())))
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
				     if (parts[0].equals(this.getCoordinatorName()))
				     {
				          System.out.println("Current ID: " + this.getFacultyID());
				          System.out.println("Updating password...");
				          newData = String.format("%s,%s,%s", parts[0], parts[1], password);
				          found = 1;
				          System.out.println("Coordinator password changed successfully!");
				     }
		        }
		    }
		    if (found == 0)
		    {
		         System.out.println("Coordinator not found! Password not changed!");
		         return;
		    }
		    br.close();
		        
		    lines.set(lineNumber-1, newData);			// the line number to overwrite (starting from 0)
		    FileWriter fw = new FileWriter(f.getCOORDFILENAME());    
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
	
	public String getCoordinatorName() {
		return coordinatorName;
	}

	
	
}
