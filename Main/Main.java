package Main;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		
	int userType;
	int menuChoice;
	boolean access = false;
	String password;
	String UserID;
	System.out.println("Enter User type ");
	System.out.println("1. Student");
	System.out.println("2. Supervisor");
	System.out.println("3. FYP Coordinator");
	
	Scanner sc = new Scanner(System.in);
	userType = sc.nextInt();
	
	while (userType == 1 || userType==2 || userType ==3) {
		
	switch(userType)
	{
	case 1:
		access=false;
		while (access==false) {
			System.out.println("Please input Student UserID : ");
			UserID=sc.next(); //nextline vs next 
		/* if (UserID not in excel){
			System.out.println("Invalid UserID");
			continue;
			}*/
			
			System.out.println("Please input password : ");
			password=sc.next();
		/* if (UserID in excel and the password is incorrect ){
		  	System.out.println("Invalid Password");
			continue;
			}		 */
			access=true;
		}
		Student s = new Student();
		menuChoice=StudentMenu.display();
		
		while (menuChoice != 8) {
			StudentMenu.execution(menuChoice,s);
			if (menuChoice == 1) {
				break;
			}
			menuChoice=StudentMenu.display();
		}

		break;
		
	case 2:
		access=false;
		while (access==false) {
			System.out.println("Please input Supervisor UserID : ");
			UserID=sc.next(); //nextline vs next 
		/* if (UserID not in excel){
			System.out.println("Invalid UserID");
			continue;
			}*/
			
			System.out.println("Please input password : ");
			password=sc.next();
		/* if (UserID in excel and the password is incorrect ){
		  	System.out.println("Invalid Password");
			continue;
			}		 */
			access=true;
		}
		Supervisor sp = new Supervisor();
		menuChoice=SupervisorMenu.display();
		
		while (menuChoice != 5) {
			SupervisorMenu.execution(menuChoice,sp);
			if (menuChoice == 1) {
				break;
			}
			menuChoice=SupervisorMenu.display();
		}
		break;
		
	case 3:
		access=false;
		while (access==false) {
			System.out.println("Please input Coordinator UserID : ");
			UserID=sc.next(); //nextline vs next 
		/* if (UserID not in excel){
			System.out.println("Invalid UserID");
			continue;
			}*/
			
			System.out.println("Please input password : ");
			password=sc.next();
		/* if (UserID in excel and the password is incorrect ){
		  	System.out.println("Invalid Password");
			continue;
			}		 */
			access=true;
		
		
		Coordinator co = new Coordinator();
		menuChoice=CoordinatorMenu.display();
		
		while (menuChoice != 7) {
			CoordinatorMenu.execution(menuChoice,co);
			if (menuChoice == 1) {
				break;
			}
			menuChoice=CoordinatorMenu.display();
		}
	}
		break;
		
	default:
		System.out.println("Invalid Choice");
	}
	
	System.out.println("1. Student");
	System.out.println("2. Supervisor");
	System.out.println("3. FYP Coordinator");
	userType = sc.nextInt();
	}
}
}