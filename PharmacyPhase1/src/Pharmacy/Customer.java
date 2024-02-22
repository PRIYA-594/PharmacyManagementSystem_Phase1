package Pharmacy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
import java.util.regex.Pattern;


public class Customer{
	public int c_id;
	protected String name;
	protected String email;
	protected String Password;
	protected String gender;
	protected String address;
	protected int age;
	Customer(String email,String Password)
	{
		this.email=email;
		this.Password=Password;
	}
	protected void customerAccess()throws Exception
	{
		int id = getCustId(email);
		Medicines me = new Medicines();
		me.listOfMedicines();
		Bill pu = new Bill();
		pu.bill(id);
	}
	private int getCustId(String email)
	{
		try
		{
			String query = "select * from customer where C_email='"+email+"'";
			Statement st = (Statement)Db.con.createStatement();
			ResultSet rs = st.executeQuery(query);
			System.out.println(rs.next());
			return rs.getInt("c_id");
		}
		catch(Exception e)
		{
			return 0;
		}
	}
	public void customerSignUp() throws Exception
	{
		Scanner s = new Scanner(System.in);
		System.out.println("Enter your Name");
		String name = s.nextLine();
		
		
		String vemail ="";
		while(vemail.isEmpty())
		{
			System.out.println("Enter your email");
			String email = s.nextLine();
			if(!isExistEmail(email))
			{
				if(isValidEmail(email))
				{
					vemail=email;
				}
				else
				{
					System.out.println("Email is not valid");
				}
			}
			else
			{
				System.out.println("Email already exists");
			}
		}
		System.out.println("Enter your age");
		int age = s.nextInt();
		System.out.println("Enter your phone_no");
		String ph = s.next();
		String Password ="";
		do
		{
			System.out.println("Enter the password");
			String Pass = s.next();
			if( isValidPassword(Pass))
			{
				Password=Pass;
			}
			else
			{
				System.out.println("Password is not valid,try create a valid one");
			}
		}while(Password.isEmpty());
		System.out.println("Enter the confirm password");
		String confirmPassword=s.next();
		while(!Password.equals(confirmPassword))
		{
			System.out.println("Password and confirm Password should be same");
			confirmPassword = s.nextLine();
		}
		System.out.println("Enter your gender");
		String gender = s.next();
		String query = "insert into customer(name,gender,age,phone_no,C_email,c_password) values('"+name+"','"+gender+"',"+age+",'"+ph+"','"+vemail+"','"+Password+"')";
		Db.UpdateQuery(query);
	}
	
	public boolean isValidCust()throws Exception
	{
		Connection con = (Connection) DriverManager.getConnection(Db.url, "root", Db.passwordRoot);
		String querySelect = "select* from customer where C_email='"+email+"';";
		Statement st = (Statement) con.createStatement();
		ResultSet rs = st.executeQuery(querySelect);
		if(rs.next())
		{

			String password =rs.getString("C_password");
			if(password.equals(Password))
			{
				return true;
			}
			else
			{
				return false;
			}	
		}
		else
		{
			return false;
		}
	}
	public boolean isCustomer()throws Exception
	{
		Scanner s = new Scanner(System.in);
		if(isValidCust())
		{
			System.out.println("Valid");
			Customer ct = new Customer(email,Password);
			ct.customerAccess();
			return true;
		}
		else
		{
			System.out.println("Your credentials are incorrect, if you want to create an account enter yes");
			String choice=s.nextLine();
			if(choice.equals("yes"))
			{
				customerSignUp();
			}
		}
		return false;
	}
	public static boolean isValidEmail(String email)
	{
		String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
	    Pattern pattern = Pattern.compile(EMAIL_REGEX);
	    //System.out.println(pattern.matcher(email).matches());
	    return pattern.matcher(email).matches();   
	}
	
	public static boolean isValidPassword(String a)
	{
		String regexPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
		Pattern pattern = Pattern.compile(regexPattern);
	    return(pattern.matcher(a).matches());	
	}
	public static boolean isExistEmail(String email)throws Exception
	{
		Connection con = (Connection) DriverManager.getConnection(Db.url, "root", Db.passwordRoot);
		Statement st = (Statement) con.createStatement();
		String query = "select C_email from customer where C_email = '"+email+"';";
		ResultSet res = st.executeQuery(query);
		return (res.next());
	}

}
