package Main;
/**
 * @author Winfred
 * @version 1.0
 * @since 15/4/2023
 */
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class StudentMenu {
	/**
	 * Preliminary choice if action is immediately executable
	 */
	static int choice;
	static Scanner sc = new Scanner(System.in);
	
	/**
	 * Displays preliminary menu for given Student class
	 * @return choice of action
	 */
	public static int display() {
		
		System.out.println("Student menu page :");
		System.out.println("1 : Change password ");
		System.out.println("2 : View available projects ");
		System.out.println("3 : Request for project allocation");
		System.out.println("4 : View registered project ");
		System.out.println("5 : View requests status and history");
		System.out.println("6 : Request to change title");
		System.out.println("7 : Request to deregister FYP ");
		System.out.println("8 : Quit");
		choice=sc.nextInt();
		return choice;

	}
	/**
	 * Executes action depending on action chosen by Student
	 * @param c Final choice for action 
	 * @param s This Student class
	 * @throws FileNotFoundException When the csv file cannot be located
	 * @throws IOException When the I/O operation is interrupted
	 */
	public static void execution(int c,Student s) throws FileNotFoundException, IOException {
		
		switch (c) {
		
		case 1 : 
			s.changePW();
			System.out.println("Logging you out to retry password.");
			break;
		
		case 2 : 
			s.viewAvailableProjects();
			break;
			
		case 3 : 
			s.reqProj(true);
			break;
			
		case 4 : 
			s.viewProject();
			break;
			
		case 5 : 
			s.viewRequest();
			break;
			
		case 6 :
			s.reqChangeTitle();
			
			break;
			
		case 7 : 
			s.reqProj(false);
			break;
			
		default:
			System.out.println("Invalid choice!");
		}
	}	
}
