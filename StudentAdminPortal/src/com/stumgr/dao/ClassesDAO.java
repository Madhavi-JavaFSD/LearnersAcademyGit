package com.stumgr.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

import com.stumgr.bean.Classes;
import com.stumgr.dbutil.DbUtil;

public class ClassesDAO {

	
	public void modifyClassesMasterlist(Scanner sc) throws ClassNotFoundException, SQLException {

		System.out.println("You have chosen to modify the Classes Masterlist.");
		displayClassList();

		while(true)
		{
			int optionChosen = 0;
			System.out.println("Please select from the following options:");
			System.out.println("1.Add a new Class.");
			System.out.println("2.Delete an existing Class.");
			System.out.println("3.Modify an existing Class.");	
			System.out.println("4.Display existing Class Masterlist.");
			System.out.println("5.Go back to the main menu.");
			while (!sc.hasNextInt()) {
				String input = sc.next();
				System.out.printf("\"%s\" is not a valid input.Please choose from the given four options.", input);
			}
			optionChosen = sc.nextInt();
			//System.out.println("optionChosen-- " + optionChosen);
			switch(optionChosen) {
			case 1:
				System.out.println("You have chosen to add a Class entry.");
				addClass(sc);
				break;
			case 2:
				System.out.println("You have chosen to delete a Class entry.");
				deleteClass(sc);
				break;
			case 3:
				System.out.println("You have chosen to modify an existing Class entry.");
				updateClass(sc);
				break;	
			case 4:
				displayClassList();
				break;	
			case 5:
				System.out.println("You have chosen to go back to the main menu.\n");
				return;	
			default:
				System.out.println("Please enter any one of the 4 given options.\n");				
			}
		}
	}
	
	private void addClass(Scanner sc) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		System.out.println("Please enter the Class you wish to add-");
		String sClass = sc.next();
		//System.out.println("sClass--" + sClass);
		
		boolean bPatternMatch = Pattern.matches("^[0-9]{1}[A-Z]{1}", sClass);
	
		//System.out.println("Pattern.matches---" + bPatternMatch);
		if(bPatternMatch)
		{
			//System.out.println("In pattern matched");
			Connection con = DbUtil.dbConn();
			Statement st = con.createStatement();
			String sql = "insert into ClassTable(StandardName) values (" +"\""+ sClass +"\")";
			System.out.println("sql--" + sql);
			int rows = st.executeUpdate(sql);
			System.out.println("After execute stmt");
			if(!(rows > 0)) {
				System.out.println("No rows effected");
			}
			else if(rows > 0) {
				System.out.println("Number of rows effected: " + rows );
			}
			displayClassList();
		}
		else
		{
			System.out.println("Invalid input!");
	        System.out.print("Please enter the Class Name in the form of GradeSection."
	       		+ "\nFor ex- 1A or 9B 0r 12C");
		}
		System.out.println("\n");
	}

	private void deleteClass(Scanner sc) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		System.out.println("Please enter the ID of the Class you wish to delete-");
		if(sc.hasNextInt())
		{
			int nClassID = sc.nextInt();
			Connection con = DbUtil.dbConn();
			Statement st = con.createStatement();
			String sql = "delete from ClassTable where ClassID=" + nClassID;
			//System.out.println("sql--" + sql);
			int rows = st.executeUpdate(sql);
			if(!(rows > 0)) {
				System.out.println("No rows effected");
			}
			else if(rows > 0) {
				System.out.println("Number of rows effected: " + rows );
			}
		}
		else
		{
			System.out.println("Please enter the ID(Integer value) of the Class you wish to delete");
		}
		
	}
	private void updateClass(Scanner sc) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		System.out.println("Please enter the ID of the Class you wish to modify-");
		int nClassID = 0;
		if(!sc.hasNextInt()) {
			 //error = true;
	         System.out.println("Invalid input!");
	         System.out.print("Please enter the ID(Integer value) of the Class you wish to modify- ");
	         //nClassID = sc.nextInt();
		}
		else if(sc.hasNextInt())
		{
			nClassID = sc.nextInt();
			System.out.println("Please enter the new Class name for this ID-");
			String sStandardName = sc.next();
			if(sStandardName.matches("^[0-9]{1}[A-Z]{1}"))
			{				
				Connection con = DbUtil.dbConn();
				Statement st = con.createStatement();
				String sql = "update ClassTable set StandardName = " +"\""+ sStandardName +"\" where ClassID = " +  nClassID;
				//System.out.println("sql--" + sql);
				int rows = st.executeUpdate(sql);
				if(!(rows > 0)) {
					System.out.println("No rows effected");
				}
				else if(rows > 0) {
					System.out.println("Number of rows effected: " + rows );
				}
			}
			else
			{
				System.out.println("Invalid input!");
		        System.out.print("Please enter the Class Name in the form of GradeSection."
		        		+ "\nFor ex- 1A or 9B 0r 12C");
			}		
		}
	}
	
	void displayClassList() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		Connection con = DbUtil.dbConn();
		Statement st = con.createStatement();
		String sql = "select * from ClassTable";
		ResultSet rs = st.executeQuery(sql);
		ArrayList<Classes> list = new ArrayList<>();
		while(rs.next()) {
			Classes s = new Classes();
			s.setnClassID(rs.getInt(1));
			s.setnStandard(rs.getString(2));
			list.add(s);
		}
		if(list != null && list.size() > 0) 
		{
			System.out.println("--Existing Classes--");
			System.out.println("---------------------------------------------------------------------------------------------");
			//System.out.format("ID" + "  " + "Name" + "  ");
			System.out.format("%7s %20s","ID", "Name");
			System.out.println();
			for(Classes eachClass:list) {
				System.out.format("%7s %20s", eachClass.getnClassID() , eachClass.getnStandard());
				System.out.println();
			}
			System.out.println("---------------------------------------------------------------------------------------------");
			System.out.println("");
		}	
	}

}
