package Pharmacy;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class Order {
	int ord_id;
	int med_id;
	int sup_id;
	int count;
	int state;
	Order()
	{
		
	}
	
	Order(int ord_id,int med_id,int sup_id,int count)
	{
		this.ord_id=ord_id;
		this.med_id=med_id;
		this.sup_id=sup_id;
		this.count=count;
	}
	Order(int med_id,int sup_id,int count)
	{
		this.med_id=med_id;
		this.sup_id=sup_id;
		this.count=count;
	}
	int getCount()
	{
		return count;
	}
	int getState()
	{
		return state;
	}
	int getMedId()
	{
		return med_id;
	}
	int getSupId()
	{
		return sup_id;
	}
	int getOrderId()
	{
		return ord_id;
	}
	void setOrderId(int id)
	{
		this.ord_id=id;
	}
	boolean placeOrder()
	{
		try
		{
			String qy = "INSERT INTO `order` (med_id,sup_id,state,count) VALUES ("+med_id+","+sup_id+",0,"+count+")";
			Db.UpdateQuery(qy);
			return true;
			
		}
		catch(Exception e)
		{
			System.out.println(e);
			return false;
		}
	}
	void orderDetails()throws Exception
	{
		String qy ="select * from `order` where state=0";
		Statement st = (Statement)Db.con.createStatement();
		ResultSet rs = st.executeQuery(qy);
		ArrayList<Order> al = new ArrayList<>();
		while(rs.next())
		{
			al.add(new Order(rs.getInt("order_id"),rs.getInt("med_id"),rs.getInt("sup_id"),rs.getInt("count")));
		}
		Order.displayFormat();
		for(Order od:al)
		{
			od.display();
		}
	}
	void updateOrderDetails()throws Exception
	{
		Scanner s= new Scanner(System.in);
		System.out.println("-------------------------------------------------------------------------------");
		System.out.println("enter the order id");
		Order od = new Order();
		od.setOrderId(s.nextInt());
		if(isValidOrdId(od.getOrderId()))
		{
			System.out.println("Does the ordered medicine delevired(yes/no)");
			String st = s.next();
			st=st.toLowerCase();
			String str="";
			if(st.equals("yes"))
			{
				str = "update `order` set state=1 where order_id="+od.getOrderId()+"";
				Db.UpdateQuery(str);
				int count = getOrdCount(od.getOrderId());
				int mid = getOrdMid(od.getOrderId());
				Medicines me = new Medicines(mid,count);
				me.updateMedicineOrd();
				System.out.println("Order id :"+od.getOrderId()+"is delivered");
			}
			else
			{
				System.out.println("Order is not delivered");
			}
		}
		else
		{
			System.out.println("Order id is invalid");
		}
		
	}
	static void displayFormat()
	{
		System.out.println("-----------------------------------------------------------------------------------");
		System.out.println("Order_id\tmedecine_id\tsupplier_id\tQuantity");
		System.out.println("-----------------------------------------------------------------------------------");
	}
	void display()
	{
		System.out.println(ord_id+"\t\t"+med_id+"\t\t"+sup_id+"\t\t"+count);
	}
	boolean isValidOrdId(int id)throws Exception
	{
		String qy ="select * from `order` where order_id="+id+"";
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
	int getOrdCount(int id)throws Exception
	{
		String qy ="select * from `order` where order_id="+id+"";
		Statement st = (Statement)Db.con.createStatement();
		ResultSet rs = st.executeQuery(qy);
		if(rs.next())
		{
			return rs.getInt("count");
		}
		else
		{
			return 0;
		}
	}
	int getOrdMid(int id)throws Exception
	{
		String qy ="select * from `order` where order_id="+id+"";
		Statement st = (Statement)Db.con.createStatement();
		ResultSet rs = st.executeQuery(qy);
		if(rs.next())
		{
			return rs.getInt("med_id");
		}
		else
		{
			return 0;
		}
	}
}
