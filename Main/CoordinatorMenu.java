package Main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
/**
 * @author Winfred
 * @version 1.0
 * @since 15/4/2023
 */

public class CoordinatorMenu {
	/**
	 * Preliminary choice if action is immediately executable
	 */
	static int choice;
	/**
	 * To be used in secondary Menu if further choice is required
	 */
	static int choice2;
	static Scanner sc = new Scanner(System.in);
	
	/**
	 * Displays preliminary menu for given Coordinator class
	 * @return choice to determine if secondary menu is required
	 */
	public static int display() {
		
		System.out.println("Supervisor menu page :");
		System.out.println("1 : Create/View/Update Projects ");
		System.out.println("2 : Approve/Reject/View Requests");
		System.out.println("3 : Request to transfer student");
		System.out.println("==========================");
		System.out.println("Coordinator menu page :");
		System.out.println("4 : Change password ( AS COORDINATOR) ");
		System.out.println("5 : Generate report( AS COORDINATOR ) "); //supervisor
		System.out.println("6 : Approve/Reject/View your Requests ( AS COORDINATOR )");
		System.out.println("7 : Quit");
		choice=sc.nextInt();
		
		switch (choice) 
		{
		
		case 1: 
			System.out.println("8 : Create new project ");
			System.out.println("9 : View projects ");
			System.out.println("10 : Update projects "); //to modify title himself
			System.out.println("-1 : Exit ");
			choice=sc.nextInt();
			break;
		
		case 2:	
			System.out.println("11 : View pending requests "); // //includes new and untouched requests
			System.out.println("12 : View request history ");
			System.out.println("13 : Approve requests "); //includes change title requests
			System.out.println("14 : Reject requests ");
			System.out.println("-1 : Exit ");
			choice=sc.nextInt();
			break;
		case 5 : 
			System.out.println("15 : View all projects ");
			System.out.println("16 : Generate report"); 
			System.out.println("-1 : Exit ");
			choice=sc.nextInt();
			break;
		
		case 6 :
			System.out.println("17 : View pending requests ");
			System.out.println("18 : View request history ");
			System.out.println("19 : Approve requests "); 
			System.out.println("20 : Reject requests ");
			System.out.println("-1 : Exit ");
			choice=sc.nextInt();
			break;
			
		default :
			if (choice < 1 || choice > 20
					)
			{
				System.out.println("Invalid option!");
			}
		}
		return choice;
	}
	
	/**
	 * Executes action depending on action chosen by Coordinator
	 * @param c Final choice for action 
	 * @param co This Coordinator Class
	 * @throws FileNotFoundException When the csv file cannot be located
	 * @throws IOException When the I/O operation is interrupted
	 */
	public static void execution(int c,Coordinator co) throws FileNotFoundException, IOException {
		
		switch (c) {
		case -1:
			break;
		
		case 3 : 
			co.requestTransfer();
			break;
			
		case 4: 
			co.changePW();
			break;
		
		case 8 : 
			co.createProj();
			break;
			
		case 9 : 
			co.viewProject();
			break;
			
		case 10 : 
			co.modifyProj();
			break;
			
		case 11 : 
			co.viewRequest();
			break;
			
		case 12 :
			co.viewReqHist();			
			break;
			
		case 13 : 
			co.approveReq();
			break;
		
		case 14 : 
			co.approveReq();
			break;
			
		case 15 : 
			co.viewAllProjs();
			break;
			
		case 16:
			co.createReport();
			break;
			
		case 17:
			co.viewPendingReqCoord();
			break;
			
		case 18:
			co.viewReqHistCoord();
			break;
			
		case 19:
			co.approveReqCoord(true);
			break;
			
		case 20:
			co.approveReqCoord(false);
			break;
			
			
		default:
			System.out.println("Invalid choice!");
		}
	}	
}
