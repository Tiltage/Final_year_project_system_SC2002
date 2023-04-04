package OOP;

import java.util.Scanner;

public class StudentMenu {
	static int choice;
	static Scanner sc = new Scanner(System.in);
	
	
	public static int display() {
		
		System.out.println("Student menu page :");
		System.out.println("1 : Change password ");
		System.out.println("2 : View available projects ");
		System.out.println("3 : Select the project to send to the coordinator");
		System.out.println("4 : View own project :");
		System.out.println("5 : View requests status and history");
		System.out.println("6 : Request to change title");
		System.out.println("7 : Request to deregister FYP ");
		System.out.println("8 : Quit");
		choice=sc.nextInt();
		return choice;

	}
	
	public static void execution(int c,Student s) {
		
		switch (c) {
		
		case 1 : 
			s.changePW();
			//if change password need quit and relogin to try again
			System.out.println("Logging you out to retry password");
			break;
		
		case 2 : 
			s.viewAvailableProjects();
			break;
			
		case 3 : 
			s.reqProj();
			break;
			
		case 4 : 
			s.viewHistory();
			break;
			
		case 5 : 
			s.viewRequest();
			break;
			
		case 6 :
			s.reqChangeTitle();
			
			break;
			
		case 7 : 
			s.reqDeregisterProject();
			break;
			
		default:
			System.out.println("Invalid");
		}
	}	
}
