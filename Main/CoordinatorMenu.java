import java.util.Scanner;

public class CoordinatorMenu {
	static int choice;
	static int choice2;
	static String keyword="x";
	//Coordinator co = new Coordinator();
	static Scanner sc = new Scanner(System.in);
	
	
	public static int display() {
		
		System.out.println("Coordinator menu page :");
		System.out.println("1 : Change password ");
		System.out.println("2 : Create/Update/View your Projects ( AS SUPERVISOR ) "); //supervisor
		System.out.println("3 : Approve/Reject/View your Requests ( AS SUPERVISOR )");
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
	
	public static void execution(int c,Coordinator co) {
		
		switch (c) {
		
		case 1 : 
			co.changePW();
			//if change password need quit and relogin to try again
			System.out.println("Logging you out to retry password");
			break;
		
		case 8 : 
			co.createNewProj();
			break;
			
		case 9 : 
			co.viewProj();
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
			
		case 4 : 
			co.requestTransfer();
			break;
			
		case 5 : 
			co.viewPendingReq();
			System.out.println("Enter selection : ");
			System.out.println("1 : Allocation request");
			System.out.println("2 : Deallocation request ");
			System.out.println("3 : Transfer student request ");
			System.out.println("4 : Quit ");
			choice2=sc.nextInt();
			
			while (choice2!=4) {
				switch (choice2) {
				
				case 1: 
					co.approveAlloc();
					break;
				
				case 2: 
					co.approveDealloc();
					break;
					
				case 3: 
					co.approveTransferStudent();
					break;
					
				default: 
					break;
				}
				System.out.println("Enter selection : ");
				System.out.println("1 : Allocation request");
				System.out.println("2 : Deallocation request ");
				System.out.println("3 : Transfer student request ");
				System.out.println("4 : Quit ");
				choice2=sc.nextInt();

			}

			break;	
			
		case 6 : 
			co.viewAllProjs(); //by title and availability
			while (keyword.compareTo("0")!=0) {
				System.out.println("Search by keywords (Enter 0 to quit) :");
				keyword=sc.next();
				co.viewProj(keyword);
			}
			
			
			break;
			
			
		default:
			System.out.println("Invalid choice");
		}
	}	
}
