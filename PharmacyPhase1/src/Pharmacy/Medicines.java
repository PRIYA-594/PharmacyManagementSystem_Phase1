package Pharmacy;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class Medicines {
	int med_id;
	String med_cat;
	String name;
	String description;
	float price;
	int count;
	String mfg;
	String exp;
	float tot_price;
	int sup_id;
	Medicines()
	{
		
	}
	Medicines(int id)
	{
		this.med_id=id;
	}
	Medicines(int med_id,int count)
	{
		this.med_id=med_id;
		this.count=count;
	}
	Medicines(int med_id,String name,String description,String med_cat,float price,int count)
	{
		this.med_id=med_id;
		this.name=name;
		this.description=description;
		this.med_cat=med_cat;
		this.count=count;
		this.price=price;
	}
	
	Medicines(int med_id,String name,int count,float price,float tot_price)
	{
		this.med_id=med_id;
		this.name = name;
		this.count=count;
		this.price=price;
		this.tot_price=tot_price;
	}
	void setMedId(int id)
	{
		this.med_id=id;
	}
	int getMedId()
	{
		return med_id;
	}
	String getMedCat()
	{
		return med_cat;
	}
	String getName()
	{
		return name;
	}
	String getmfg()
	{
		return mfg;
	}
	void setMfg(String mfg)
	{
		this.mfg=mfg;
	}
	String getExp()
	{
		return exp;
	}
	void setExp(String exp)
	{
		this.exp=exp;
	}
	String getDescription()
	{
		return description;
	}
	float getPrice()
	{
		return price;
	}
	int getCount()
	{
		return count;
	}
	void setMedCat(String med_cat)
	{
		this.med_cat=med_cat;
	}
	void setName(String name)
	{
		this.name = name;
	}
	void setDescription(String description)
	{
		this.description=description;
	}
	void setPrice(float price)
	{
		this.price = price;
	}
	void setCount(int count)
	{
		this.count=count;
	}
	void setSupId(int sup_id)
	{
		this.sup_id=sup_id;
	}
	int getSupId()
	{
		return sup_id;
	}
	public String getString()
	{
		Scanner s = new Scanner(System.in);
		String str = s.nextLine();
		return str;
	}
	void insertMedicine()throws Exception
	{
		Scanner s = new Scanner(System.in);
		System.out.println("Enter the medicine Category(tablet/tonic/other products)");
		setMedCat(s.nextLine());
		System.out.println("Enter name of the medicine");
		setName(s.nextLine());
		System.out.println("Enter the description of the medicine");
		setDescription(s.nextLine());
		System.out.println("Enter the price of the medicine");
		setPrice(s.nextFloat());
		System.out.println("Enter Manufacturing date (YYYY-MM-DD)");
		setMfg(s.next());
		System.out.println("Enter Expiry date (YYYY-MM-DD)");
		setExp(s.next());
		System.out.println("Enter the count avaiable");
		setCount(s.nextInt());
		System.out.println("Enter the supplier id");
		setSupId(s.nextInt());
		String query = "insert into medicines(med_cat,name,description,price,count,mfg,exp,sup_id) values('"+getMedCat()+"','"+getName()+"','"+getDescription()+"',"+getPrice()+","+getCount()+",'"+getmfg()+"','"+getExp()+"',"+getSupId()+")";
		Db.UpdateQuery(query);
	}
	void updateMedicine()throws Exception
	{
		System.out.println("Enter the medicine name or id which needs to be updated");
		Scanner s = new Scanner(System.in);
		String name = s.nextLine();
		
		int ch =0;
		String field="";
		String update = "";
		int contin = 0;
		boolean status = false;
		do {
			System.out.println("Enter which field needs to be updated");
			System.out.println("1.medicine Category\n2.description\n3.price\n4.count\n5.name\n6.Manufacturing date\n7.Expiry date");
			ch=s.nextInt();
			switch(ch)
			{
			case 1:
				field="med_cat";
				System.out.println("enter the medicine category");
				update = getString();;
				break;
			case 2:
				field="description";
				System.out.println("enter the description");
				update= getString();;
				break;
			case 3:
				field="price";
				System.out.println("enter the price");
				float price = s.nextFloat();
				update = Float.toString(price);
				break;
			case 4:
				field="count";
				System.out.println("enter the count");
				int count = s.nextInt();
				update = Integer.toString(count);
				break;
			case 5:
				field="name";
				System.out.println("enter the name");
				update = getString();
				break;
			case 6:
				field="mfg";
				System.out.println("Enter the mfg");
				update = getString();
				break;
			case 7:
				field="exp";
				System.out.println("Enter the exp");
				update = getString();
				break;
			default:
				System.out.println("Sorry choice is Invalid");
			}
			status=updateQy(name,field,update);
			if(status)
			{
				System.out.println("Updated Successfully");
			}
			else
			{
				System.out.println("Updation failed");
			}
			System.out.println("If you want to continue the operation press 1");
			contin=s.nextInt();
		}while(contin==1);
	}
	boolean updateQy(String name,String field,String cat)
	{
		try {
			String query="update medicines set "+field+"='"+cat+"' where name='"+name+"' or med_id='"+name+"'";
			Db.UpdateQuery(query);
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}
	void deleteMedicine()
	{
		try {
			Scanner s = new Scanner(System.in);
			System.out.println("Enter the medicine name or id to be deleted");
			String str = s.nextLine();
			String query="delete from medicines where name ='"+str+"' or med_id='"+str+"'";
			Db.UpdateQuery(query);
			System.out.println("Deletion Completed");
		}
		catch(Exception e)
		{
			System.out.println("Deletion is failed");
		}
		
	}
	void expiryMed()throws Exception
	{
		String query = "select * from medicines where datediff(exp,CURDATE())<=20;";
		Statement st = (Statement)Db.con.createStatement();
		ResultSet rs = st.executeQuery(query);
		ArrayList<Medicines> medi = new ArrayList<>();
		
		while(rs.next())
		{
			int id = rs.getInt("med_id");
			String name = rs.getString("name");
			String des=rs.getString("description");
			String medCat = rs.getString("med_cat");
			float price = rs.getFloat("price");
			int count = rs.getInt("count");
			medi.add(new Medicines(id,name,des,medCat,price,count));
			//System.out.println("\nid : "+id+"\nName : "+name+"\nCategory : "+medCat+"\ndescription : "+des+"\nprice : "+price+"\nCount : "+count);
		}
		if(!rs.next())
		{
			System.out.println("Null");
		}
		else
		{
			Medicines.medicineDisplay();
			for(Medicines m:medi)
			{
				m.displayMedicine();
			}
		}
		
		
	}
	void delMedExp()throws Exception
	{
		String query = "select * from medicines where datediff(exp,curdate())<=15";
		Statement st = (Statement)Db.con.createStatement();
		ResultSet rs = st.executeQuery(query);
		//ArrayList<Integer> al = new ArrayList<>();
		while(rs.next())
		{
			String query1="update set count=count-"+rs.getInt("count")+" from medicines where med_id="+rs.getInt("med_id")+"";
			Db.UpdateQuery(query1);
		}
		
	}
	void showDetails()throws Exception
	{
		Scanner s = new Scanner(System.in);
		System.out.println("Enter the name of the medicine or it's id to show details");
		String str = s.nextLine();
		Statement st = (Statement)Db.con.createStatement();
		String query="select * from medicines where name = '"+str+"' or med_id='"+str+"'";
		ResultSet rs = st.executeQuery(query);
		ArrayList<Medicines> medi = new ArrayList<>();
		while(rs.next())
		{
			int id = rs.getInt("med_id");
			String name = rs.getString("name");
			String des=rs.getString("description");
			String medCat = rs.getString("med_cat");
			float price = rs.getFloat("price");
			int count = rs.getInt("count");
			medi.add(new Medicines(id,name,des,medCat,price,count));
			//System.out.println("\nid : "+id+"\nName : "+name+"\nCategory : "+medCat+"description : "+des+"price : "+price+"Count : "+count);
		}
		Medicines.medicineDisplay();
		for(Medicines m:medi)
		{
			m.displayMedicine();
		}
	}
	void outOfStock()throws Exception
	{
		String query = "select * from medicines where count<10";
		Statement st = (Statement)Db.con.createStatement();
		ResultSet rs = st.executeQuery(query);
		ArrayList<Medicines> medi = new ArrayList<>();
		while(rs.next())
		{
			int id = rs.getInt("med_id");
			String name = rs.getString("name");
			String des=rs.getString("description");
			String medCat = rs.getString("med_cat");
			float price = rs.getFloat("price");
			int count = rs.getInt("count");
			medi.add(new Medicines(id,name,des,medCat,price,count));
			//System.out.println("\nid : "+id+"\nName : "+name+"\nCategory : "+medCat+"\ndescription : "+des+"\nprice : "+price+"\nCount : "+count);
		}
		Medicines.medicineDisplay();
		for(Medicines m:medi)
		{
			m.displayMedicine();
		}
	}
	void listOfMedicines()throws Exception
	{
		String query="select * from medicines where datediff(exp,CURDATE())>20";
		Statement st = (Statement)Db.con.createStatement();
		ResultSet rs = st.executeQuery(query);
		ArrayList<Medicines> medi = new ArrayList<>();
		while(rs.next())
		{
			int id = rs.getInt("med_id");
			String name = rs.getString("name");
			String des=rs.getString("description");
			String medCat = rs.getString("med_cat");
			float price = rs.getFloat("price");
			int count = rs.getInt("count");
			medi.add(new Medicines(id,name,des,medCat,price,count));
//			System.out.println("\nid : "+id+"\nName : "+name+"\nCategory : "+medCat+"description : "+des+"price : "+price+"Count : "+count);
		}
		Medicines.medicineDisplay();
		for(Medicines m:medi)
		{
			m.displayMedicine();
		}
		
	}
	boolean isValidMed()
	{
		try {
			String query = "select * from medicines where med_id='"+med_id+"'";
			Statement st = (Statement)Db.con.createStatement();
			ResultSet rs = st.executeQuery(query);
			if(rs.next())
			{
				return true;
			}
			else
			{
				return false;
			}
			
		}
		catch(Exception e)
		{
			return false;
		}
	}
	float getMedPrice()throws Exception{
		String query = "select * from medicines where med_id='"+med_id+"'";
		Statement st = (Statement)Db.con.createStatement();
		ResultSet rs = st.executeQuery(query);
		float pr = 0.0f;
		while(rs.next())
		{
			pr=rs.getFloat("price");
		}
		return pr;
	}
	String getMedName()throws Exception{
		String query = "select * from medicines where med_id='"+med_id+"'";
		Statement st = (Statement)Db.con.createStatement();
		ResultSet rs = st.executeQuery(query);
		String str="";
		while(rs.next())
		{
			str=rs.getString("name");
		}
		return str;
	}
	boolean sufficient()throws Exception
	{
		String query = "select * from medicines where med_id='"+med_id+"'";
		Statement st = (Statement)Db.con.createStatement();
		ResultSet rs = st.executeQuery(query);
		int c=0;
		while(rs.next())
		{
			c =rs.getInt("count");
		}
		
		if(count>c)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	static void displayFormat()
	{
		System.out.println("-----------------------------------------------------------------------------------");
		System.out.println("id\t\t\tName\t\tQuantity\t\tPrice\t\t\tTotal Price");
		System.out.println("-----------------------------------------------------------------------------------");
	}
	void display()
	{
		System.out.format(" %-9s          %-9s      %5d           %9.2f                %14.2f \n",med_id,name,count,price,tot_price);
	}
	static void medicineDisplay()
	{
		System.out.println("--------------------------------------------------------------------------------------------------------------------------");
		System.out.println("Medicine_id\tName\t\tType\t\tDescription\t\t\t\t\tPrice\t\tQuantity");
		System.out.println("--------------------------------------------------------------------------------------------------------------------------");
	}
	void displayMedicine()
	{
		int l = description.length();
		for(int i=l;i<40;i++)
		{
			description+=" ";
		}
//		System.out.println(description);
//		System.out.println(description.length());
		System.out.println("\t"+med_id+"\t"+name+"\t"+med_cat+"\t\t"+description+"\t"+price+"\t\t"+count);
		//System.out.format("%-9s %-9s %-9s %-9s %9.2f %5d\n",med_id,name,med_cat,description,price,count);
	}
	
	String getMedicineName()throws Exception
	{
		String query = "select * from medicines where med_id="+getMedId()+"";
		Statement st = (Statement)Db.con.createStatement();
		ResultSet rs = st.executeQuery(query);
		if(rs.next())
		{
			return rs.getString("name");
		}
		else
		{
			System.out.println(".....");
			return null;
		}
	}
	
	int getSupIdFromMedId(int id)throws Exception
	{
		String str = "select * from medicines where med_id="+id+"";
		Statement st = (Statement)Db.con.createStatement();
		ResultSet rs = st.executeQuery(str);
		if(rs.next())
		{
			return rs.getInt("sup_id");
		}
		else
		{
			System.out.println("Invalid medicine id");
			return 0;
		}
	}
	void orderMedicine()throws Exception
	{
		Scanner s = new Scanner(System.in);
		System.out.println("Enter the medicine id which needs to be ordered");
		Medicines me = new Medicines();
		me.setMedId(s.nextInt());
		me.setSupId(me.getSupIdFromMedId(me.getMedId()));
		System.out.println("Enter the medicine count need to be ordered");
		int c = s.nextInt();
		Supplier sp = new Supplier(me.getSupId());
		sp.supplierDetailsForOrder();
		Order od = new Order(me.getMedId(),me.getSupId(),c);
		if(od.placeOrder())
		{
			System.out.println("Order placed...");
		}
		else
		{
			System.out.println("Order not placed");
		}
	}
	void updateMedicineOrd()throws Exception
	{
		String str = "update medicines set count=count+"+getCount()+" where med_id="+getMedId()+"";
		Db.UpdateQuery(str);
	}
}
