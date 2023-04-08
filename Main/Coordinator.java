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
	
	public void approveReq(Boolean approve) {
		
		if (approve==true) {    			//for approval
			
			//1. ask for what type of request (alloc,delloc,changesup)
			//2. iterate and print through each request with an index
			//3. ask them for an index 
			//4. iterate through again and do the necessary changes in the csv file
			//5. do the necessary changes to the relevant objects
			
			System.out.println("Enter which type of request");
			System.out.println("1. Allocation request");
			System.out.println("2. Transfer student request");
			System.out.println("3. De-allocation request");
			System.out.println("4. Quit");
			
			int choice=sc.nextInt();
			
			while (choice!=4) {
				
				switch (choice) {
				
				case 1: 
					int count=1;
					int index;
					Filepath f = new Filepath();
					try (BufferedReader r = new BufferedReader(new FileReader(f.getREQFILENAME())))
					{
						String line = r.readLine();
						line=r.readLine();
						System.out.println("View all pending Allocation Requests:");
						while (line != null)
						{
							String[] parts = line.split(",");
							if (parts[0].equals("Pending") && parts[1].equals("ReqAlloc"))
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
					
					System.out.println("Enter which request to approve");
					index=sc.nextInt();
					
					
					try (BufferedReader r = new BufferedReader(new FileReader(f.getREQFILENAME())))
					{	
						int count2=1;
						String line = r.readLine();
						line=r.readLine();
						System.out.println("View all pending Allocation Requests:");
						while (line != null)
						{
							String[] parts = line.split(",");
							if (parts[0].equals("Pending") && parts[1].equals("ReqAlloc"))
							{
								System.out.println(count + " : " + parts[0]);
								count2++;
							}
							line = r.readLine();
							
							if (count2==index) {
								//how the fk do i edit the csv from pending to approved
								//change the attributes of the objects involved
							}

						       
							
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

						break;
						
				case 2: 
						break;
					
				
				case 3: 
						break;
					
				default : System.out.println("Invalid input");
				}
				
				
				
				
			}
			
		
		
		if (approve==false) {				//for reject
			
			//1. ask for what type of request (alloc,delloc,changesup)
			//2. iterate and print through each request with an index
			//3. ask them for an index 
			//4. iterate through again and do the necessary changes in the csv file
			//5. do the necessary changes to the relevant objects
			
			System.out.println("Request rejected");
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
		int count=1;
		Filepath f = new Filepath();
		try (BufferedReader r = new BufferedReader(new FileReader(f.getREQFILENAME())))
		{
			String line = r.readLine();
			line=r.readLine();
			System.out.println("View all Pending Requests:");
			while (line != null)
			{
				String[] parts = line.split(",");
				if (parts[0].equals("Pending"))
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

	public void createNewProj() {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	
	
	
}
