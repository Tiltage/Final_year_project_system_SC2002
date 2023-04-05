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
	private Status status;
	private Project proj;
	private Project[] deRegisProjs; 
	
	Scanner sc = new Scanner (System.in);
	
	
	public Student() {}
	
	public void viewProject() {
		
		//need check that request is approved firs6t
		//add student name into a new column student name in projects csv
		//return the supervisor and title
		//need instantiate anyt ? 
		
		
	}
	
	public void viewHistory() {
		
		
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

	@Override
	public void viewRequest() {
		// TODO Auto-generated method stub
		
	}

	public void viewAvailableProjects() {
		int count=1;
		Filepath f = new Filepath();
		try (BufferedReader r = new BufferedReader(new FileReader(f.getPath())))
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

	public void reqProj() {
		// TODO Auto-generated method stub
		int index;
		String projectTitle = "NA";
		int valid=0;
		
		while (valid==0) {
			System.out.println("Enter the Number ID of the project you want to be allocated : ");
			System.out.println();
			index=sc.nextInt();
			
			Filepath f = new Filepath();
			try (BufferedReader r = new BufferedReader(new FileReader(f.getPath())))
			{
				String line = r.readLine();
				
				
				for (int i=0;i<index;i++) {
					line=r.readLine();
				}
				
				String[] parts = line.split(",");
				
				if (parts[2].equals("Available"))
				{
					projectTitle = parts[1];
					System.out.println(projectTitle);
					System.out.println();
					valid=1;
					
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
		r.addRequest(); //should go to request csv but currently goes to project csv

	}

	public void reqChangeTitle() {

		
	}
	
	
	
}
