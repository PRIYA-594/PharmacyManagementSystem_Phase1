package Pharmacy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Admin{
	public int a_id;
	private String email;
	private String Password;
	Admin(){
		email="";
	}
	Admin(String email,String Password)
	{
		this.email=email;
		this.Password=Password;
	}
	public String getString()
	{
		Scanner s = new Scanner(System.in);
		String str = s.nextLine();
		return str;
	}
	protected void adminAccess()throws Exception
	{
		Scanner s = new Scanner(System.in);
		Medicines me = new Medicines();
		Supplier sp = new Supplier();
		Order od = new Order();
		Bill bi = new Bill();
		System.out.println("-----------------------------------------------------");
		System.out.println("\nList of medicines need to be ordered");
		me.outOfStock();
		System.out.println("-----------------------------------------------------");
		System.out.println("\nExpiring soon medicines");
		me.expiryMed();
		me.delMedExp();
		System.out.println("-----------------------------------------------------");
		int ch = 0;
		do
		{
			System.out.println("\n1.Insert Medicine\n2.Update Medicine\n3.Delete Medicine\n4.Medicines need to be ordered\n5.List of all medicines\n6.Medicine Details\n7.Medicines expired in 20 days\n8.Bill needs to be processed\n9.sales Report\n10.purchase report\n11.Supplier details\n12.Order Medicine\n13.add supplier details\n14.update supplier details\n15.Order Details\n16.Update Order Details\n17.Exit");
			System.out.println("-----------------------------------------------------");
			System.out.println("Enter your choice");
			ch = s.nextInt();
			switch(ch)
			{
			case 1:
				me.insertMedicine();
				break;
			case 2:
				me.updateMedicine();
				break;
			case 3:
				me.deleteMedicine();
				break;
			case 4:
				me.outOfStock();
				break;
			case 5:
				me.listOfMedicines();
				break;
			case 6:
				me.showDetails();
				break;
			case 7:
				me.expiryMed();
				break;
			case 8:
				bi.billing();
				break;
			case 9:
				Sales sl = new Sales();
				sl.salesReport();
				break;
			case 10:
				bi.purchaseReport();
				break;
			case 11:
				sp.supplierDetails();
				break;
			case 12:
				me.orderMedicine();
				break;
			case 13:
				sp.addDetails();
				break;
			case 14:
				sp.updateDetails();
				break;
			case 15:
				od.orderDetails();
				break;
			case 16:
				od.updateOrderDetails();
				break;
			case 17:
				System.out.println("Thank You");
				break;
			default:
				System.out.println("Enter valid choice");
			}
			System.out.println("--------------------------------------------------------------------------------------------------");
			System.out.println("--------------------------------------------------------------------------------------------------");
		}while(ch!=17);
		
	}

	public boolean isValidAdmin()throws Exception
	{
		Connection con = (Connection) DriverManager.getConnection(Db.url, "root", Db.passwordRoot);
		String querySelect = "select* from admin where a_email='"+email+"';";
		Statement st = (Statement) con.createStatement();
		ResultSet rs = st.executeQuery(querySelect);
		
		if(rs.next())
		{
			String password =rs.getString("password");
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
	public void isAdmin()throws Exception
	{
		if(isValidAdmin())
		{
			System.out.println("Valid");
			adminAccess();
		}
		else
		{
			System.out.println("Invalid");
		}
	}
	
}
