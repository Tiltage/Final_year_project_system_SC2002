package projectClass;
import java.io.*;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ProjectTEST 
	{
	
		enum Status {Available, Unavailable, Allocated, Reserved}
		private String studentID ;
		private String studentEmail;
		private int projID;
		private String supervisorName;
		private String supervisorEmail;
		private String projTitle;
		private Status status;

		public void setStudent(String studentID) {
			this.studentID = studentID;
		}

		public void setStudentEmail(String studentEmail) {
			this.studentEmail = studentEmail;
		}

		public void setSupervisorName(String supervisorName) {
			this.supervisorName = supervisorName;
		}

		public void setSupervisorEmail(String supervisorEmail) {
			this.supervisorEmail = supervisorEmail;
		}
		
		//set status method need?
		// get methods need? maybe for returning
		
		public void addProject(String supervisorName, String projTitle, String status)
		{
			final String FILENAME = "C:\\Users\\reyan\\Downloads\\rollover project(2).csv";
			try (FileWriter fw = new FileWriter(FILENAME, true);
		             BufferedWriter bw = new BufferedWriter(fw);
		             PrintWriter out = new PrintWriter(bw)) 
			{

		            // Add a new row to the bottom of the file
		            out.printf("%s, %s, %s", supervisorName, projTitle, status).println();
		            System.out.println("Data appended to file successfully!");

	        } catch (IOException e) 
				{
		            e.printStackTrace();
		        }
		}
		
		public static void main(String[] args) 
		{
			Project p = new Project();
			p.addProject("Winfred", "short", "50");
			p.addProject("Ryan", "average", "60");
			p.addProject("Melvin", "average", "55");
			p.addProject("Kendrea", "shorter", "40");
		}
	}
