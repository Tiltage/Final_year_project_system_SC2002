package Main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class CoordinatorMenu {
	static int choice;
	static int choice2;
	static String keyword="x";
	static Scanner sc = new Scanner(System.in);
	
	
	public static int display() {
		
		//Testing
		
		System.out.println("Coordinator menu page :");
		System.out.println("1 : Change password ");
		System.out.println("2 : Create/Update/View your Projects ( AS COORDINATOR ) "); //supervisor
		System.out.println("3 : Approve/Reject/View your Requests ( AS COORDINATOR )");
		System.out.println("4 : Request to transfer student");
		System.out.println("5 : View all Requests ( AS COORDINATOR )");
		System.out.println("6 : View all projects ( AS COORDINATOR )");
		System.out.println("7 : Quit");
		choice=sc.nextInt();
		
		switch (choice) {
		
		case 2 : 

			System.out.println("8 : Create new project ");
			System.out.println("9 : View projects ");
			System.out.println("10 : Update projects "); //to modify title himself
			choice=sc.nextInt();
			break;
		
		case 3 :
			
			System.out.println("11 : View pending requests "); // //includes new and untouched requests
			System.out.println("12 : View request history ");
			System.out.println("13 : Approve requests "); //includes change title requests
			System.out.println("14 : Reject requests ");
			choice=sc.nextInt();
			break;
		
		default :
		
		}
		return choice;
	}
	
	public static void execution(int c,Coordinator co) throws FileNotFoundException, IOException {
		
		switch (c) {
		
		case 1 : 
			co.changePW();
			//if change password need quit and relogin to try again
			System.out.println("Logging you out to retry password");
			break;
		
		case 8 : 
			co.createProj();
			break;
			
		case 9 : 
			co.viewProj();
			break;
			
		case 10 : 
			co.modifyProj();
			break;
			
		case 11 : 
			co.viewPendingReq();
			break;
			
		case 12 :
			co.viewReqHist();			
			break;
			
		case 13 : 
			co.approveReq(true);
			break;
		
		case 14 : 
			co.approveReq(false);
			break;
			
		case 4 : 
			co.requestTransfer();
			break;
			
		case 5 : 
			
			//first we print out all the requests (maybe including history)
			co.viewPendingReq();
			break;
			//then we will prompt for either approve/reject
			//then we call the functions
			
			
			
			
		
			
		case 6 : 
			co.viewAllProjs(); //by title and availability
			while (keyword.compareTo("0")!=0) {
				System.out.println("Search by keywords (Enter 0 to quit) :");
				keyword=sc.next();
				co.viewProj();
			}
			
			
			break;
			
			
		default:
			System.out.println("Invalid choice");
		}
	}	
}
