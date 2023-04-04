package Main;
import java.io.*;
import java.util.*;

public class Supervisor extends User {
	
	private String facultyID ;
	private String supervisorName;
	private String supervisorEmail;
	private Project[] projArr;
	private int numProj;
	
	
	
	public Supervisor(String facultyID, String supervisorName, String supervisorEmail, int numProj) throws FileNotFoundException, IOException 
	{
		this.facultyID = facultyID;
		this.supervisorName = supervisorName;
		this.supervisorEmail = supervisorEmail;
		System.out.println("Constructing");
		this.projArr = getProjArr();
		this.numProj = numProj;
	}
	public Supervisor()
	{
		
	}
	
	public Project[] getProjArr() throws FileNotFoundException, IOException
	{
		System.out.println("File found");
		List<Project> list = new ArrayList<>();
		Filepath f = new Filepath();
		try (BufferedReader r = new BufferedReader(new FileReader(f.getPath())))
		{
			String line = r.readLine();
			while (line != null)
			{
				String[] parts = line.split(",");
				System.out.println(parts[0]);
				//if (parts[0] == this.facultyID)
				//{
				//	Project p = new Project(parts[1]);
				//	list.add(p);
				//}
				line = r.readLine();
			}
		}
		
		Project[] projs = (Project[]) list.toArray();
		return projs;
	}
	
	public void createProj() {}
	
	public void viewProj() {}
	
	public void modifyProj() {}
	
	public void viewReq() {}
	
	public void viewReqHist() {}
	
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

	@Override
	public void viewRequest() {
		// TODO Auto-generated method stub
		
	}

}
