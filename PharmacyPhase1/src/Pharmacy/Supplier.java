package Pharmacy;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class Supplier {
	int sup_id;
	String sup_name;
	String sup_ph;
	String sup_mail;
	Supplier()
	{
		sup_id=0;
		sup_name="";
		sup_ph="";
		sup_mail="";
	}
	Supplier(int id)
	{
		this.sup_id=id;
	}
	Supplier(int id,String name,String ph,String mail)
	{
		this.sup_id=id;
		this.sup_name=name;
		this.sup_ph=ph;
		this.sup_mail=mail;
	}
	Supplier(String name,String ph,String mail)
	{
		this.sup_name=name;
		this.sup_ph=ph;
		this.sup_mail=mail;
	}
	String getMail()
	{
		return sup_mail;
	}
	String getName()
	{
		return sup_name;
	}
	void setName(String sup_name)
	{
		this.sup_name=sup_name;
	}
	String getPh()
	{
		return sup_ph;
	}
	void setPh(String sup_ph)
	{
		this.sup_ph=sup_ph;
	}
	void setMail(String sup_mail)
	{
		this.sup_mail=sup_mail;
	}
	int getSupId()
	{
		return sup_id;
	}
	void setSupId(int id)throws Exception
	{
		if(validSupId(id))
		{
			this.sup_id=id;
		}
		else
		{
			this.sup_id=0;
		}
		
	}
	void supplierDetailsForOrder()throws Exception
	{
		String query ="select * from supplier where sup_id="+sup_id+"";
		Statement st = (Statement)Db.con.createStatement();
		ResultSet rs = st.executeQuery(query);
		ArrayList<Supplier> sup = new ArrayList<>();
		while(rs.next())
		{
			sup.add(new Supplier(rs.getInt("sup_id"),rs.getString("sup_name"),rs.getString("sup_ph"),rs.getString("sup_mail")));
			
		}
		Supplier.displayFormat();
		for(Supplier sp:sup)
		{
			sp.display();
		}
	}
	void supplierDetails()throws Exception
	{
		String query ="select * from supplier";
		Statement st = (Statement)Db.con.createStatement();
		ResultSet rs = st.executeQuery(query);
		ArrayList<Supplier> sup = new ArrayList<>();
		while(rs.next())
		{
			sup.add(new Supplier(rs.getInt("sup_id"),rs.getString("sup_name"),rs.getString("sup_ph"),rs.getString("sup_mail")));
			
		}
		Supplier.displayFormat();
		for(Supplier sp:sup)
		{
			sp.display();
		}
	}
	static void displayFormat()
	{
		System.out.println("************************************************************************************");
		System.out.println("id\tName\t\tPhone Number\t\tMail Id");
		System.out.println("************************************************************************************");
		
	}
	void display()
	{
		System.out.println(sup_id+"\t"+sup_name+"\t\t"+sup_ph+"\t\t"+sup_mail);
	}
	void addDetails()throws Exception
	{
		Scanner s = new Scanner(System.in);
		System.out.println("Enter the name of the supplier");
		setName(s.next());
		System.out.println("Phone number");
		setPh(s.next());
		System.out.println("mail");
		setMail(s.next());
		String str = "insert into supplier(sup_name,sup_ph,sup_mail) values('"+getName()+"','"+getPh()+"','"+getMail()+"')";
		Db.UpdateQuery(str);
	}
	void updateDetails()throws Exception
	{
		Scanner s = new Scanner(System.in);
		System.out.println("Enter the supplier id which data needs to be updated");
		setSupId(s.nextInt());
		if(validSupId(getSupId()))
		{
		
			int ch =0;
			String field="";
			String change="";
			String qu="";
			do
			{
				System.out.println("\n1->update name\n2->update phone number\n3->update mail\n4->Exit");
				System.out.println("Enter your choice");
				ch=s.nextInt();
				switch(ch)
				{
				case 1:
					field="sup_name";
					System.out.println("Enter the name which is to be updated");
					change=s.next();
					break;
				case 2:
					field="sup_ph";
					System.out.println("Enter the phone number which is to be updated");
					change=s.next();
					break;
				case 3:
					field="sup_mail";
					System.out.println("Enter the mail which is to be updated");
					change=s.next();
					break;
				case 4:
					System.out.println("Thank you");
					break;
				default:
					System.out.println("Invalid choice");
				}
				qu="update supplier set "+field+"='"+change+"' where sup_id="+getSupId()+"";
				Db.UpdateQuery(qu);
				
			}while(ch!=4);
		}
		else
		{
			System.out.println("Supplier id is invalid");
		}
		
	}
	boolean validSupId(int id)throws Exception
	{
		String qy = "select * from supplier where sup_id="+id+"";
		Statement st = (Statement)Db.con.createStatement();
		ResultSet rs = st.executeQuery(qy);
		if(rs.next())
		{
			return true;
		}
		else
		{
			return false;
		}
		
	}
}
