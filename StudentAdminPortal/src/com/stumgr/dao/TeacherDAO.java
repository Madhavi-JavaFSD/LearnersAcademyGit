package com.stumgr.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import com.stumgr.bean.Teacher;
import com.stumgr.dbutil.DbUtil;

public class TeacherDAO {
	
	public void modifyTeachersMasterlist(Scanner sc) throws ClassNotFoundException, SQLException {

		System.out.println("You have chosen to modify the Teachers Masterlist.");
		displayTeachersList();

		while(true)
		{
			int optionChosen = 0;
			System.out.println("Please select from the following options:");
			System.out.println("1.Add a new Teacher.");
			System.out.println("2.Delete an existing Teacher.");
			System.out.println("3.Modify an existing Teacher.");	
			System.out.println("4.Display existing Teachers Masterlist.");
			System.out.println("5.Go back to the main menu.");
			while (!sc.hasNextInt()) {
				String input = sc.next();
				System.out.printf("\"%s\" is not a valid input.Please choose from the given four options.", input);
			}
			optionChosen = sc.nextInt();
			//System.out.println("optionChosen-- " + optionChosen);
			switch(optionChosen) {
			case 1:
				System.out.println("You have chosen to add a Teacher entry.\n");
				addTeacher(sc);
				break;
			case 2:
				System.out.println("You have chosen to delete a Teacher entry.\n");
				deleteTeacher(sc);
				break;
			case 3:
				System.out.println("You have chosen to modify an existing Teacher entry.\n");
				updateTeacher(sc);
				break;	
			case 4:
				displayTeachersList();
				break;	
			case 5:
				System.out.println("You have chosen to go back to the main menu.\n");
				return;	
			default:
				System.out.println("Please enter any one of the 4 given options.\n");				
			}

		}
	}

	private void addTeacher(Scanner sc) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		System.out.println("Please enter the name of the Teacher you wish to add-");
		String sTeacherName = sc.next();
		if(sTeacherName.matches("^[a-zA-Z]*$"))
		{
			Connection con = DbUtil.dbConn();
			Statement st = con.createStatement();
			String sql = "insert into Teacher(TeacherName) values (" +"\""+ sTeacherName +"\")";
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
			System.out.println("Please enter a valid Teacher Name.");
		}
	}

	private void deleteTeacher(Scanner sc) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		System.out.println("Please enter the ID of the Teacher you wish to delete-");
		if(sc.hasNextInt()) 
		{
			int nTeacherID = sc.nextInt();
			Connection con = DbUtil.dbConn();
			Statement st = con.createStatement();
			String sql = "delete from Teacher where TeacherID=" + nTeacherID;
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
			System.out.println("Please enter a valid Teacher ID.");
		}
		
	}
	private void updateTeacher(Scanner sc) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		System.out.println("Please enter the ID of the Teacher you wish to modify-");
		int nTeacherID = 0;		
		if(sc.hasNextInt())
		{
			nTeacherID = sc.nextInt();
			System.out.println("Please enter the new Teacher name for this ID-");
			String sTeacherName = sc.next();
			if(sTeacherName.matches("^[a-zA-Z]*$"))
			{
				Connection con = DbUtil.dbConn();
				Statement st = con.createStatement();
				String sql = "update Teacher set TeacherName = " +"\""+ sTeacherName +"\" where TeacherID = " +  nTeacherID;
				System.out.println("sql--" + sql);
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
				System.out.println("Please enter a valid Teacher Name.");
			}
		}
		else
		{
			 //error = true;
	         System.out.println("Invalid input!");
	         System.out.print("Please enter an ID(Integer value) of the Teacher you wish to modify- ");
	         //nTeacherID = sc.nextInt();
		}
	}
	
	private void displayTeachersList() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		Connection con = DbUtil.dbConn();
		Statement st = con.createStatement();
		String sql = "select * from teacher";
		ResultSet rs = st.executeQuery(sql);
		ArrayList<Teacher> list = new ArrayList<>();
		while(rs.next()) {
			Teacher s = new Teacher();
			s.setnTeacherId(rs.getInt(1));
			s.setsTeacherName(rs.getString(2));
			list.add(s);
		}
		if(list != null && list.size() > 0) 
		{
			System.out.println("--Existing Teachers--");
			System.out.println("---------------------------------------------------------------------------------------------");
			//System.out.println("ID" + "  " + "Name" + "  ");
			System.out.format("%7s %20s","ID", "Name");
			System.out.println("");
			for(Teacher eachTeacher:list) {
				//System.out.println(eachTeacher.getnTeacherId() + "  " + eachTeacher.getsTeacherName() + "  ");
				System.out.format("%7s %20s",eachTeacher.getnTeacherId(), eachTeacher.getsTeacherName());
				System.out.println("");
			}			
			System.out.println("---------------------------------------------------------------------------------------------");
		}	
	}
}
