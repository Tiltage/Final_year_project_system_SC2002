package Main;
import java.io.*;
import java.util.*;

public class Supervisor extends User {
	
	private String facultyID ;
	private String supervisorName;
	private String supervisorEmail;
	private Project[] projArr;
	private int numProj;
	
	
	
	public Supervisor(String facultyID, String supervisorName, String supervisorEmail) throws FileNotFoundException, IOException 
	{
		this.facultyID = facultyID;
		this.supervisorName = supervisorName;
		this.supervisorEmail = supervisorEmail;
		this.projArr = generateProjArr();
		this.numProj = projArr.length;
	}
	public Supervisor()
	{
		
	}
	public Supervisor(String facultyID) throws FileNotFoundException, IOException
	{
		this.facultyID = facultyID;
		this.projArr = generateProjArr();
		Filepath f = new Filepath();
		try(BufferedReader r = new BufferedReader(new FileReader(f.getFACFILENAME())))
		{
			String csvSplitBy = ",";
			int found = 0;
				String line = r.readLine();
				while(line!=null && found == 0)
				{
					// Add a new row to the bottom of the file
		            String[] email = line.split(csvSplitBy);
		            String supervisorEmail = email[0];
		            String parts[][] = new String[email.length][];
		            for (int x = 0; x < email.length; x++)
		            {
		            	parts[x] = email[x].split("@");
			
		            }
		            if (parts[1][0].equals(facultyID))
		            {
		            	this.supervisorName = parts[0][0];
		            	this.supervisorEmail = email[1];
		            	System.out.println(this.supervisorName);
		            	System.out.println(this.supervisorEmail);
		            	found = 1;
		            }
		            line = r.readLine();
				}
	            

        } catch (IOException e) 
			{
	            e.printStackTrace();
	        }
	}
	
	public Project[] generateProjArr() throws FileNotFoundException, IOException
	{
		List<Project> list = new ArrayList<>();
		Filepath f = new Filepath();
		try (BufferedReader r = new BufferedReader(new FileReader(f.getPROJFILENAME())))
		{
			String line = r.readLine();
			System.out.println(this.supervisorName);
			while (line != null)
			{
				String[] parts = line.split(",");
				System.out.println(parts[0]);
				if (parts[0].equals(this.supervisorName))
				{
					System.out.println(parts[1]);
					Project p = new Project(parts[1]);
					System.out.println("Added!");
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
            System.out.println(projs[i].getStudentID());
        }
		return projs;
	}
	
	public void createProj() {
		String projTitle;
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Please enter project title:");
		projTitle = sc.nextLine();
		
		Project p1 = new Project("na","na", this.supervisorName, this.facultyID, projTitle);
		p1.addProject();
	}
	
	public void viewProj() {
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
				
				if (parts[0].equals(this.supervisorName))
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
	
	public void modifyProj() {
		int projNumber;
		String line;
		String projTitle, newProjTitle;
		Scanner sc = new Scanner(System.in);
		
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
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void viewRequest() throws FileNotFoundException, IOException
	{
		int choice;
		System.out.println("File found");
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
					if (parts[4].equals(this.facultyID)&&parts[0].equals("Pending")&&parts[1].equals("ReqChangeTitle"))
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
					if (parts[4].equals(this.facultyID)&&parts[0].equals("Pending")&&parts[1].equals("ReqChangeSup"))
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
			}
			break;
		
		default:
		}
	}
	
	public void viewReqHist() throws FileNotFoundException, IOException {
		int choice;
		System.out.println("File found");
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
				System.out.println(header[2] + "  ||  " + header[3] + "  ||  " + "New Title");
				while (line != null) {
					String[] parts = line.split(",");
					if (parts[4].equals(this.facultyID)&&!parts[0].equals("Pending")&&parts[1].equals("ReqChangeTitle"))
					{
						System.out.println(parts[2] + "  ||  " + parts[3] + "  ||  " + parts[5]);
					}
					line = r.readLine();
				}
			}
			break;
		
		// Change Sup
		case 2 : 
			try (BufferedReader r = new BufferedReader(new FileReader(f.getREQFILENAME())))
			{
				String line = r.readLine();
				String[] header = line.split(",");
				System.out.println(header[2] + "  ||  " + header[3] + "  ||  " + "Old Supervisor" + "  ||  " + "New Supervisor");
				while (line != null) {
					String[] parts = line.split(",");
					if (parts[4].equals(this.facultyID)&&!parts[0].equals("Pending")&&parts[1].equals("ReqChangeSup"))
					{
						System.out.println(parts[2] + "  ||  " + parts[3] + "  ||  " + parts[4] + "  ||  " + parts[5]);
					}
					line = r.readLine();
				}
			}
			break;
		
		default:
		}
	}
	
	public void approveReq() throws FileNotFoundException, IOException {
		int choice;
		System.out.println("File found");
		Filepath f = new Filepath();
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Enter request number to accept: ");
		choice=sc.nextInt();
		
		try (BufferedReader r = new BufferedReader(new FileReader(f.getREQFILENAME())))
		{
			int count=1;
			String line = r.readLine();
			String[] header = line.split(",");
			System.out.println("");
			
			while (line != null) {
				String[] parts = line.split(",");
				if (parts[4].equals(this.facultyID)&&parts[0].equals("Pending")&&parts[1].equals("ReqChangeTitle"))
				{
					if(choice==count) {
						Project p = new Project(parts[3]);
						p.editProject(parts[3],parts[5]);
						
						ReqChangeTitle request = new ReqChangeTitle(Request.ApprovalStatus.Approved, "", "", "", "");
						request.updateRequest(parts[3], Request.ApprovalStatus.Approved);
					}
					
					count++;
				}
				line = r.readLine();
			}
		}
		return;
	}
	
	public void rejectReq() throws FileNotFoundException, IOException {
		int choice;
		System.out.println("File found");
		Filepath f = new Filepath();
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Enter request number to accept: ");
		choice=sc.nextInt();
		
		try (BufferedReader r = new BufferedReader(new FileReader(f.getREQFILENAME())))
		{
			int count=1;
			String line = r.readLine();
			String[] header = line.split(",");
			System.out.println("");
			
			while (line != null) {
				String[] parts = line.split(",");
				if (parts[4].equals(this.facultyID)&&parts[0].equals("Pending")&&parts[1].equals("ReqChangeTitle"))
				{
					if(choice==count) {
						ReqChangeTitle request = new ReqChangeTitle(Request.ApprovalStatus.Rejected, "", "", "", "");
						request.updateRequest(parts[3], Request.ApprovalStatus.Rejected);
					}
					
					count++;
				}
				line = r.readLine();
			}
		}
		return;
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
		return this.facultyID;
	}
	
	public Project[] getProjArr()
	{
		return this.projArr;
	}
	
	public void requestTransfer() {
		String projectName;
		String newSupName;
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Please enter name of project: ");
		projectName=sc.nextLine();
		sc.nextLine();
		System.out.println("Please enter name of new supervisor: ");
		newSupName=sc.nextLine();
		System.out.println("Input: " + projectName + "," + newSupName);
		
		ReqChangeSup r = new ReqChangeSup(Request.ApprovalStatus.Pending, "na", projectName, this.facultyID, newSupName);
		r.addRequest();
		
	}

}
