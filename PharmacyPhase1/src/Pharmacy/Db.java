package Pharmacy;

import java.sql.*;
import java.util.Scanner;

public class Db {
	//static String passwordRoot;
	static String passwordRoot = "pRiya@2004";
	static String url;
	static Connection con = null;
	static int UpdateQuery(String query)throws Exception
	{
		con = DriverManager.getConnection(Db.url, "root", Db.passwordRoot);
		Statement st = (Statement) con.createStatement();
        return st.executeUpdate(query);
	}
	static void getConnection() throws SQLException
	{
		Scanner s = new Scanner(System.in);
		int f=0;
		do
		{
//			System.out.println("Enter Your Root Password");
//			passwordRoot = s.nextLine();
			url = "jdbc:mysql://localhost:3306/pharmacyManagementSystem";
			try
			{
				Class.forName("com.mysql.cj.jdbc.Driver");
				f=0;
				con = (Connection) DriverManager.getConnection(url,"root",passwordRoot);
			}
			catch(Exception e)
			{
				f=1;
				System.out.println("ERROR!"+e);
			}
			if(f==0)
			{
				System.out.println("DataBase Connected\n");
			}
			else
			{
				System.out.println("\nYOUR PASSWORD IS WRONG");
			}
		}while(f==1);
			}
}
