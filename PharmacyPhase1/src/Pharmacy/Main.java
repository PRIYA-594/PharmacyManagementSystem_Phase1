package Pharmacy;

import java.util.Scanner;

public class Main {
	public static void main(String args[])throws Exception
	{
		Db.getConnection();
		Scanner s = new Scanner(System.in);
		int ch=0;
		String Email = "";
		String password = "";
		
		do
		{
			System.out.println("\n1.Login as Admin\n2.Login as Customer\n3.SignUp as Customer\n4.Exit");
			System.out.println("Enter your choice");
			ch=s.nextInt();
			switch(ch)
			{
			case 1:
				System.out.println("---------------------------------");
				System.out.println("Enter your email");
				Email = s.next();
				System.out.println("Enter the password");
				password = s.next();
				System.out.println("---------------------------------");
				Admin ad = new Admin(Email,password);
				ad.isAdmin();
				break;
			case 2:
				System.out.println("---------------------------------");
				System.out.println("Enter your email");
				Email = s.next();
				System.out.println("Enter the password");
				password = s.next();
				System.out.println("---------------------------------");
				Customer ct = new Customer(Email,password);
				ct.isCustomer();
				break;
			case 3:
				Customer ct1 = new Customer(Email,password);
				ct1.customerSignUp();
				break;
			case 4:
				System.out.println("Thank You");
				break;
			default:
				System.out.println("Your choice is invalid");
			}
			
		}while(ch!=4);
		
	}
	
}
