package Main;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class SupervisorMenu {
	
	static int choice;
	//Supervisor sp = new Supervisor();
	static Scanner sc = new Scanner(System.in);
	
	
	public static int display() {
		
		System.out.println("Supervisor menu page :");
		System.out.println("1 : Change password ");
		System.out.println("2 : Create/View/Update Projects ");
		System.out.println("3 : Approve/Reject/View requests");
		System.out.println("4 : Request to transfer student");
		System.out.println("5 : Quit");
		choice=sc.nextInt();
		
		switch (choice) {
		
		case 2 : 

			System.out.println("6 : Create new project ");
			System.out.println("7 : View projects ");
			System.out.println("8 : Update projects "); //to modify title himself
			choice=sc.nextInt();
			break;
		
		case 3 :
			
			System.out.println("9 : View requests "); // //includes new and untouched requests
			System.out.println("10 : View request history ");
			System.out.println("11 : Approve requests "); //includes change title requests
			System.out.println("12 : Reject requests ");
			choice=sc.nextInt();
			break;
		
		default :
		
		}
		return choice;
	}
	
	public static void execution(int c,Supervisor sp) throws FileNotFoundException, IOException {
		
		switch (c) {
		
		case 1 : 
			sp.changePW();
			//if change password need quit and relogin to try again
			System.out.println("Logging you out to retry password");
			break;
		
		case 6 : 
			sp.createProj();
			break;
			
		case 7 : 
			sp.viewProj();
			break;
			
		case 8 : 
			sp.modifyProj();
			break;
			
		case 9 : 
			sp.viewRequest();
			break;
			
		case 10 :
			sp.viewReqHist();
			
			break;
			
		case 11 : 
//			sp.approveReq();
			break;
		
		case 12 : 
//			sp.rejectReq();
			break;
			
		case 4 : 
			sp.requestTransfer();
			break;
			
		default:
			System.out.println("Invalid choice");
		}
	}	
	
	
	
}
