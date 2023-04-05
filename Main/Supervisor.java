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
	
	public void createProj() {}
	
	public void viewProj() {}
	
	public void modifyProj() {}
	
	public void viewReq() {}
	
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
	
	public ReqChangeTitle approveReq(ReqChangeTitle r) {
		return r;
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
	
	public Project[] getProjArr()
	{
		return this.projArr;
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
				String line = r.readLine();
				String[] header = line.split(",");
				System.out.println(header[2] + "  ||  " + header[3] + "  ||  " + "New Title");
				while (line != null) {
					String[] parts = line.split(",");
					if (parts[4].equals(this.facultyID)&&parts[0].equals("Pending")&&parts[1].equals("ReqChangeTitle"))
					{
						System.out.println(parts[2] + "  ||  " + parts[3] + "  ||  " + parts[5]);
					}
					line = r.readLine();
				}
			}
			break;
		
		case 2 : 
			try (BufferedReader r = new BufferedReader(new FileReader(f.getREQFILENAME())))
			{
				String line = r.readLine();
				String[] header = line.split(",");
				System.out.println(header[2] + "  ||  " + header[3] + "  ||  " + "Old Supervisor" + "  ||  " + "New Supervisor");
				while (line != null) {
					String[] parts = line.split(",");
					if (parts[4].equals(this.facultyID)&&parts[0].equals("Pending")&&parts[1].equals("ReqChangeSup"))
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
