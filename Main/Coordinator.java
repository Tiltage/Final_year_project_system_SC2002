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

import Main.Request.ApprovalStatus;

public class Coordinator extends Supervisor {
	
	private String coordinatorName;
	private String coordinatorEmail;
	private String password;
	private Project[] projArr;
	private int numProj;
	private int numAllocProjs;
	
	Scanner sc=new Scanner(System.in);
	
	public Coordinator(String facultyID, String supervisorName, String supervisorEmail, int numProj) throws FileNotFoundException, IOException 
	{
		super(facultyID, supervisorName, supervisorEmail);
		this.password = getPW();
		//super(facultyID, supervisorName, supervisorEmail, numProj); not sure whats numproj is for
	}
	
	public Coordinator (String facultyID, String password) throws FileNotFoundException, IOException
	{
		super(facultyID,password);
		this.password = password;
	}
	
	private static String getCoordName(String facultyID)
	{
		String coordinatorName = null;
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
	            String coordinatorEmail = email[1];
	            String parts[] = email[1].split("@");
	            if (parts[0].equals(facultyID))
	            {
	            	coordinatorName = email[0];
	            }
	            line = r.readLine();
			}        
        } catch (IOException e) 
			{
	            e.printStackTrace();
	        }
    	return coordinatorName;
	}
	
	
	public void approveReqCoord(Boolean approve) throws FileNotFoundException, IOException 
	{
		this.viewPendingReqCoord();
		System.out.println("Enter the request number to approve / reject (Enter -1 to exit): ");
		int choice;
		Scanner sc = new Scanner(System.in);
		choice = sc.nextInt();
		Request.ApprovalStatus result = approve ? Request.ApprovalStatus.Approved : Request.ApprovalStatus.Rejected;
		
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
						
						if (parts[0].equals("Pending") && (parts[1].equals("ReqAlloc")))
						{
							//If request chosen is ReqAlloc
							
							//Edit Request file status to reflect changes
							ReqAlloc request = new ReqAlloc(result, parts[2], parts[3]);
							request.updateRequest(parts[3], result);
							
							if (approve)
							{
								System.out.println("Entered-----------------------------------");
								boolean choice2 = true;
								//If request chosen is ReqChangeSup
								//If approving this request will not result in supervisor having >2 projects
								Supervisor temp = new Supervisor(parts[4]);
								System.out.println("Num of allocated projects: " + temp.getNumAllocProjs());
								if (temp.getNumAllocProjs() >= 2)
								{
									choice2 = false;
									System.out.println("Supervisor has reached the maximum number of projects. Currently overseeing " + temp.getNumAllocProjs() + " projects.");
									System.out.println("Press 1 to proceed with approval (Enter -1 to go back):");
									Scanner sc1 = new Scanner(System.in);
									choice2 = sc1.nextInt() == 1 ? true : false;
								}
								
								if (choice2)
								{
									//Edit Project file status and sup name to reflect changes
									System.out.println("Project Allocated successfully");
									Project p = new Project(parts[3]);
									p.editProject(parts[3],Project.Status.Allocated);
																		
									//Edit Student file status to reflect changes
									Student s = new Student(parts[2]);
									s.updateStudent(p.getProjTitle(), p.getSupervisorName(), Student.Status.Assigned);
									if (temp.getNumAllocProjs() >= 1)  //Reyan changed this to >= 1 cos this.getNumAllocProj doesnt increment after approving reqAlloc
									{
										changeAllOthersToAvailOrUnavail(parts[4], 1); 
									}
								
								}
															
							} 
								
						return;	
						}
						else if (parts[0].equals("Pending") && parts[1].equals("ReqChangeSup"))
						{
							ReqChangeSup request = new ReqChangeSup(result, parts[2], parts[3], parts[4], parts[5]);
							request.updateRequest(parts[3], result);
							
							if (approve)
							{
								boolean choice2 = true;
								//If request chosen is ReqChangeSup
								//If approving this request will not result in supervisor having >2 projects
								System.out.println("parts[5]:" + parts[5]);
								Supervisor temp = new Supervisor(parts[5]);
								if (temp.getNumAllocProjs() >= 2)
								{
									choice2 = false;
									System.out.println("Supervisor has reached the maximum number of projects. Currently overseeing " + temp.getNumAllocProjs() + " projects.");
									System.out.println("Press 1 to proceed with approval (Enter -1 to go back):");
									Scanner sc1 = new Scanner(System.in);
									choice2 = sc1.nextInt() == 1 ? true : false;
								}
								
								if (choice2)
								{
									Project p = new Project(parts[3]);
									p.editProjectSup(parts[3], parts[5]);
									
									//Edit Student file supID to reflect changes 
									Student s1 = new Student(parts[2]);
									s1.updateStudent(p.getProjTitle(), p.getSupervisorName(), Student.Status.Assigned);
									//Edit Request file status to reflect changes
									
									Supervisor oldSup = new Supervisor(parts[4]);
									if (oldSup.getNumAllocProjs() < 2)
									{
										changeAllOthersToAvailOrUnavail(parts[4], 0);
									}
									Supervisor newSup = new Supervisor(parts[5]);
									if (newSup.getNumAllocProjs() >= 2)
									{
										changeAllOthersToAvailOrUnavail(parts[5], 1);
									}
									System.out.println("Supervisor Changed successfully");
								}
								
							}
							return;
						}
						
						else if (parts[0].equals("Pending") && (parts[1].equals("ReqDeregister")))
						{
							//If request chosen is ReqDeregister
							
							//Edit Request file status to reflect changes
							ReqDeregister request = new ReqDeregister(result, parts[2], parts[3]);
							request.updateRequest(parts[3], result);
							
							if (approve)
							{
								//Edit Project file status and sup name to reflect changes
								System.out.println("Project Deregistered successfully");
								Project p = new Project(parts[3]);
								p.editProject(parts[3],Project.Status.Available);
								
								//Edit Student file status to reflect changes
								Student s = new Student(parts[2]);
								s.updateStudent(p.getProjTitle(), p.getSupervisorName(), Student.Status.Ended);
								Supervisor temp = new Supervisor(parts[5]);
								if (temp.getNumAllocProjs() < 2)
								{
									changeAllOthersToAvailOrUnavail(parts[4], 0);
								}
							
							} 
							return; 
						}
					}
					else if (parts[0].equals("Pending") && !(parts[1].equals("ReqChangeTitle")))
					{
						System.out.println("Supervisor name: " + parts[4]);
						//Overall check to verify if its a viable request for Coordinator
						count++;
					}
					line = r.readLine();
				}
				
				System.out.println("Enter another request number to approve/reject (Enter -1 to exit): ");
				choice = sc.nextInt();
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
	    int choice;
	    int count;
	    Filepath f = new Filepath();
	    String search;
	    System.out.println("Search filter");
	    System.out.println("1 : Supervisor name");
	    System.out.println("2 : Student name");
	    System.out.println("3 : Project status");
	    System.out.println("4 : Project title");
	    System.out.println("-1 : Exit");
	    choice=sc.nextInt();
	    sc.nextLine();
	    
	    while (choice!=-1) {
	      
	      switch (choice) {
	      
	      case 1: 
	        System.out.println("Enter Supervisor name :");
	        search=sc.nextLine();
	        while (search.length()<2) {
	          System.out.println("Please enter a more specific input!");
	          System.out.println("Enter Supervisor name :");
	          search=sc.nextLine();
	        }
	    
	        count = 1;
	        try (BufferedReader r = new BufferedReader(new FileReader(f.getPROJFILENAME())))
	        {
	          String line = r.readLine();
	          line=r.readLine();
	          System.out.println("View all Projects Reports with " + "'" + search + "' :" );
	          System.out.println("==================");
	          while (line != null)
	          {
	            String[] parts = line.split(",");
	            
	            if (parts[0].toLowerCase().contains(search.toLowerCase())) {
	              
	              Project p=new Project(parts[1]);              
	              System.out.println(count + ")");
	              System.out.println("Supervisor Name: " + parts[0]);
	              System.out.println("Name of Project: " + parts[1]);
	              System.out.println("Status: " + parts[2]);
	              System.out.println("Name of student: " + p.getStudentName());
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
	        if (count==1) {
	          System.out.println("Not found");
	        }
	        
	        System.out.println();
	        break;
	        
	      case 2: 
	        System.out.println("Enter Student name :");
	        search=sc.nextLine();
	        while (search.length()<3) {
	          System.out.println("Please enter a more specific input!");
	          System.out.println("Enter Student name :");
	          search=sc.nextLine();
	        }
	        
	        count = 1;
	        try (BufferedReader r = new BufferedReader(new FileReader(f.getSTUDFILENAME())))
	        {
	          String line = r.readLine();
	          line=r.readLine();
	          System.out.println("View all Projects Reports with " + "'" + search + "' :" );
	          System.out.println("==================");
	          while (line != null)
	          {
	            String[] parts = line.split(",");
	            
	            if (parts[0].toLowerCase().contains(search.toLowerCase())) {
	              
	              System.out.println(count + ")");
	              System.out.println("Name of student: " + parts[0]);
	              System.out.println("Supervisor Name: " + parts[5]);
	              System.out.println("Name of Project: " + parts[4]);
	              System.out.println("Status: " + parts[6]);
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
	        if (count==1) {
	          System.out.println("Not found");
	        }
	        
	        System.out.println();
	        
	        break;
	        
	      case 3: 
	        
	        System.out.println("Enter Status :");
	        search=sc.nextLine();
	        while (search.length()<3) 
	        {
	        	System.out.println("Please enter a more specific input!");
	          	System.out.println("Enter Status :");
	          	search=sc.nextLine();
	        }
	        
	        count = 1;
	        try (BufferedReader r = new BufferedReader(new FileReader(f.getPROJFILENAME())))
	        {
	          String line = r.readLine();
	          line=r.readLine();
	          System.out.println("View all Projects Reports with " + "'" + search + "' :" );
	          System.out.println("==================");
	          while (line != null)
	          {
	            String[] parts = line.split(",");
	            
	            if (parts[2].toLowerCase().contains(search.toLowerCase())) {
	              
	              Project p=new Project(parts[1]);              
	              System.out.println(count + ")");
	              System.out.println("Status: " + parts[2]);
	              System.out.println("Supervisor Name: " + parts[0]);
	              System.out.println("Name of Project: " + parts[1]);
	              System.out.println("Name of student: " + p.getStudentName());
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
	        if (count==1) {
	          System.out.println("Not found");
	        }
	        
	        System.out.println();
	        break;
	        
	      case 4: 
		        
		        System.out.println("Enter Title :");
		        search=sc.nextLine();
		        while (search.length()<3) 
		        {
		        	System.out.println("Please enter a more specific input!");
		          	System.out.println("Enter Title :");
		          	search=sc.nextLine();
		        }
		        
		        count = 1;
		        try (BufferedReader r = new BufferedReader(new FileReader(f.getPROJFILENAME())))
		        {
		          String line = r.readLine();
		          line=r.readLine();
		          System.out.println("View all Projects Reports with " + "'" + search + "' :" );
		          System.out.println("==================");
		          while (line != null)
		          {
		            String[] parts = line.split(",");
		            
		            if (parts[1].toLowerCase().contains(search.toLowerCase())) {
		              
		              Project p=new Project(parts[1]);              
		              System.out.println(count + ")");
		              System.out.println("Name of Project: " + parts[1]);
		              System.out.println("Status: " + parts[2]);
		              System.out.println("Supervisor Name: " + parts[0]);
		              System.out.println("Name of student: " + p.getStudentName());
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
		        if (count==1) {
		          System.out.println("Not found");
		        }
		        
		        System.out.println();
		        break;
	        
	        
	      default: System.out.println("Invalid input");
	      }      
	      System.out.println("Search filter");
	      System.out.println("1 : Supervisor name");
	      System.out.println("2 : Student name");
	      System.out.println("3 : Project status");
	      System.out.println("4 : Project title");
	      System.out.println("-1 : Exit");
	      choice=sc.nextInt();
	      sc.nextLine();
	      
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
				if (parts[0].equals("Pending") && !(parts[1].equals("ReqChangeTitle")))
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
	
	private void changeAllOthersToAvailOrUnavail(String supName, int mode)
	 {
	  //If mode is 1, change all Available to Unavailable, else change Unavailable to available
	  String cur = mode == 1 ? "Available" : "Unavailable";
	  String changeTo = mode == 1 ? "Unavailable" : "Available";
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
	          String[] parts = line.split(",");
	       if (parts[0].equals(supName) && parts[2].equals(cur))
	       {
	      newData = String.format("%s,%s,%s,%s", parts[0], parts[1], changeTo, parts[3]);            
	       }
	       else
	       {
	        newData = String.format("%s,%s,%s,%s", parts[0], parts[1], parts[2], parts[3]);
	       }
	       lines.add(newData);
	       lineNumber++;
	      }
	      br.close();
	          
	      FileWriter fw = new FileWriter(f.getPROJFILENAME());    
	      PrintWriter out = new PrintWriter(fw); 
	      for (String line2 : lines) 
	      {
	       out.println(line2);     // Write the modified data back to the file
	      }
	      out.close();
	  }
	        catch (IOException e) 
	        {
	         e.printStackTrace();
	        }
	 }
/*	public static void main(String[] args) throws FileNotFoundException, IOException
	{
		Coordinator co = new Coordinator("Li fang", "password");
		co.changeAllOthersToAvailOrUnavail("Bo An", 1);
	}
	*/
}

