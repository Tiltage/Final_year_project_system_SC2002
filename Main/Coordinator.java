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
	
	Scanner sc=new Scanner(System.in);
	
	public Coordinator(String facultyID, String supervisorName, String supervisorEmail, int numProj) throws FileNotFoundException, IOException 
	{
		super(facultyID, supervisorName, supervisorEmail);
		//super(facultyID, supervisorName, supervisorEmail, numProj); not sure whats numproj is for
	}
	
	public Coordinator(String facultyID) throws FileNotFoundException, IOException
	{
		super(facultyID);
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
								//If request chosen is ReqChangeSup
								//If approving this request will not result in supervisor having >2 projects
								//Edit Project file sup name to reflect changes
								//Edit Student file supID to reflect changes 
								//Edit Request file status to reflect changes
								return;
								
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
		int count=1;
		Filepath f = new Filepath();
		try (BufferedReader r = new BufferedReader(new FileReader(f.getREQFILENAME())))
		{
			String line = r.readLine();
			line=r.readLine();
			System.out.println("All projects:");
			while (line != null)
			{
				String[] parts = line.split(",");
				System.out.println(count + " : " + parts[1]);
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
	
	public void viewPendingReq() {
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
	
	public void viewReqHist()
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
				if (!parts[0].equals("Pending"))
				{
					System.out.println(count + " : " + parts[0]);
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
	
	
	
	
	
	
	
}
