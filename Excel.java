package test;

    import java.io.*;  
    import java.util.Scanner;  
    public class Excel 
    {  
    	public static void main(String[] args) throws Exception  
    	{  
    		//parsing a CSV file into Scanner class constructor  
    		Scanner sc = new Scanner(new File("/Users/rey/Downloads/faculty_list_csv.csv"));  
    		sc.useDelimiter(",");   //sets the delimiter pattern  
    		while (sc.hasNext())  //returns a boolean value  
    		{  
    			System.out.print(sc.next());  //find and returns the next complete token from this scanner  
    		}   
    		sc.close();  //closes the scanner  
    	}  
    }  