package Pharmacy;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class Sales {
	int sales_id;
	int cust_id;
	int med_id;
	int count;
	float amt;
	String date;
	Sales()
	{
		amt=0.0f;
	}
	Sales(int id)
	{
		cust_id=id;
	}
	Sales(int med_id,int count,float amt)
	{
		this.med_id=med_id;
		this.count=count;
		this.amt=amt;
	}
	void setCustId(int id)
	{
		cust_id=id;
	}
	int getCustId()
	{
		return cust_id;
	}
	int getCount()throws Exception
	{
		String query="select* from sales where date=CURDATE()";
		Statement st = (Statement)Db.con.createStatement();
		ResultSet rs = st.executeQuery(query);
		return rs.getInt("count");
	}
	float getAmt()throws Exception
	{
		String query="select* from sales where date=CURDATE()";
		Statement st = (Statement)Db.con.createStatement();
		ResultSet rs = st.executeQuery(query);
		return rs.getInt("total_amount");
	}
	boolean salesExist(int mid)throws Exception
	{
		String query="select* from sales where date=CURDATE() and med_id="+mid+"";
		Statement st = (Statement)Db.con.createStatement();
		ResultSet rs = st.executeQuery(query);
		return rs.next();
	}
	void billDetails()throws Exception
	{
		String query="select * from sales where cust_id="+getCustId()+" and fl=0";
		Statement st = (Statement)Db.con.createStatement();
		ResultSet rs = st.executeQuery(query);
		ArrayList<Sales> al = new ArrayList<>();
		
		while(rs.next())
		{
			int id = rs.getInt("med_id");
			int sid = rs.getInt("sales_id");
			query = "update sales set fl=1 where sales_id="+sid+"";
			Db.UpdateQuery(query);
			//Medicines me = new Medicines(id);
			//String name = me.getMedicineName();
			int count = rs.getInt("count");
			//String date = rs.getString("date");
			float amt = rs.getFloat("total_amount");
			al.add(new Sales(id,count,amt));
		}
		System.out.println("Customer Id: "+getCustId());
		Sales.displayFormat();
		for(Sales sl:al)
		{
			sl.display();
		}
		System.out.println("The bill is processed successfully");
		
	}
	static void displayFormat()
	{
		System.out.println("-----------------------------------------------------------------------------------");
		System.out.println("med_id\t\tcount\t\tPrice");
		System.out.println("-----------------------------------------------------------------------------------");
	}
	void display()
	{
		System.out.println(med_id+"\t"+count+"\t\t"+amt);
	}
	void salesQy(int c,int mid,float price,int cust_id)throws Exception
	{
//		Scanner s = new Scanner(System.in);
//		User u;
//		System.out.println("PHARMACIST Enter your email");
//		String Email = s.nextLine();
//		System.out.println("PHARMACIST Enter the password");
//		String password = s.next();
//		u = new User(3);
//		u.roleNav(Email,password);
//		String query ="insert into sales(a_id,cust_id,med_id,count,purchase_id,date,total_amount) values("+getAdminId()+","+cid+","+mid+","+c+","+purchase_id+",CURDATE(),"+price+")";
		String query ="insert into sales(med_id,count,date,total_amount,cust_id,fl) values("+mid+","+c+",CURDATE(),"+price+","+cust_id+",0)";
		Db.UpdateQuery(query);	
		
	}
	void salesReport()throws Exception
	{
		String query="select count(sales_id) as 'countOfSales' from sales where date=CURDATE()";
		Statement st = (Statement)Db.con.createStatement();
		ResultSet rs = st.executeQuery(query);
		if(rs.next())
		{
			System.out.println("No Of sales (today):"+rs.getInt("countOfSales"));
		}
//		query = "select count(sales_id) as 'countOfsales' from sales groupby date";
//		rs = st.executeQuery(query);
//		while(rs.next())
//		{
//			System.out.println(rs.getInt("countOfsales")+"\t\t"+rs.getString("date"));
//		}
		
	}
}
