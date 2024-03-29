package Main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
/**
 * @author Winfred, Melvin
 * @version 1.0
 * @since 15/4/2023
 */
public class Main {
	/**
	 * Main function
	 * @param args Default constructor
	 * @throws FileNotFoundException When the csv file cannot be located
	 * @throws IOException When the I/O operation is interrupted
	 */

	public static void main(String[] args) throws FileNotFoundException, IOException 
	{
		/**
		 * To be used in switch case to display different menus depending on User
		 */
		int userType;
		/**
		 * To store action taken by user
		 */
		int menuChoice;
		/**
		 * Gatekeep login page unless password and UserID is valid for given userType
		 */
		boolean access = false;
		/**
		 * Entered password by User
		 */
		String password;
		/**
		 * FacultyID / StudentID 
		 */
		String UserID=null;
		System.out.println("Enter User type (-1 to quit) ");
		System.out.println("1. Student");
		System.out.println("2. Supervisor");
		System.out.println("3. FYP Coordinator");
		
		Scanner sc = new Scanner(System.in);
		userType = sc.nextInt();
		sc.nextLine();  				//input buffer
		
		
		while (userType>0) {
			
		switch(userType)
		{
		case 1:
			{
				while (access == false) 
				{
					System.out.println("Please input Student UserID (-1 to quit) : ");
					UserID=sc.nextLine();
					if (UserID.equals("-1")) {
						break;
					}
					
					System.out.println("Please input password : ");
					password=sc.nextLine();
					if (check(UserID, password, 1) == true)
					{
						access = true;
						Student s = new Student(UserID,password);
						menuChoice=StudentMenu.display();
						
						while (menuChoice != 8) 
						{
							StudentMenu.execution(menuChoice,s);
							if (menuChoice == 1) {
								access=false;
								break;
							}
							menuChoice=StudentMenu.display();
						}
					}
				}
				access=false;
				break;
			}
		case 2:
			{
				access=false;
				while (access==false) {
					System.out.println("Please input Supervisor UserID (-1 to quit): ");
					UserID=sc.nextLine();
					if (UserID.equals("-1")) {
						break;
					}
					
					System.out.println("Please input password : ");
					password=sc.nextLine();
					if (check(UserID, password, 2) == true)
			        {
						access = true;
						Supervisor sp = new Supervisor(UserID, password);
						menuChoice=SupervisorMenu.display();
						
						while (menuChoice != 5) {
							SupervisorMenu.execution(menuChoice,sp);
							if (menuChoice == 1) {
								access=false;
								break;
							}
							menuChoice=SupervisorMenu.display();
						}
			        }
				}
				access=false;
				break;
			}
		case 3:
			{
				access=false;
				while (access==false) 
				{
					System.out.println("Please input Coordinator UserID (-1 to quit): ");
					UserID=sc.nextLine();
					if (UserID.equals("-1")) {
						break;
					}
					
					System.out.println("Please input password : ");
					password=sc.nextLine();
					if (check(UserID, password, 3) == true) {
						access=true;
						Coordinator co = new Coordinator(UserID, password);
						menuChoice=CoordinatorMenu.display();
						
						while (menuChoice != 7) 
						{
							CoordinatorMenu.execution(menuChoice,co);
							if (menuChoice == 1) {
								access=false;
								break;
							}
							menuChoice=CoordinatorMenu.display();
						}
					}
				}
				access=false;
				break;
			}
		
		default:
			System.out.println("Invalid Choice");
		}
		System.out.println("Enter User type (-1 to quit) ");
		System.out.println("1. Student");
		System.out.println("2. Supervisor");
		System.out.println("3. FYP Coordinator");
		userType = sc.nextInt();
		sc.nextLine();   //input buffer
		}
		System.out.println("Thank you for using the program!");
	}
	
	/**
	 * Checks if UserID and Password is valid
	 * @param UserID This user's keyed in UserID
	 * @param pw This user's keyed in password
	 * @param choice Determines which csv to look through
	 * @return True/False depending if login details are valid
	 * @throws IOException When the I/O operation is interrupted
	 */
	
	private static boolean check(String UserID, String pw, int choice) throws IOException
	{
		Filepath f = new Filepath();
		String filename = null;
		switch (choice)
		{
			case 1: //Student login
			{
				filename = f.getSTUDFILENAME();
				break;
			}
			case 2: //Supervisor login
			{
				
				filename = f.getSUPFILENAME();
				break;
				
			}
			case 3: //Coordinator login
			{
				
				filename = f.getCOORDFILENAME();
				break;
				
			}
		}
		try(BufferedReader r = new BufferedReader(new FileReader(filename)))
		{
			String csvSplitBy = ",";
			String line = r.readLine();
			line = r.readLine();
			while(line!=null)
			{
				// Add a new row to the bottom of the file
	            String[] parts = line.split(csvSplitBy);
	            String password = parts[2];
	            String email[][] = new String[parts.length][];
	            for (int x = 0; x < parts.length; x++)
	            {
	            	email[x] = parts[x].split("@");
		
	            }
	            if (email[1][0].equals(UserID))
	            {
	            	if (password.equals(pw))
	            	{
	            		return true;
	            	}
	            	System.out.println("Incorrect password!");
	            	return false;
	            }
	            line = r.readLine();
			}
			System.out.println("UserID does not exist!");
			return false;
		
		
		}
	}
}