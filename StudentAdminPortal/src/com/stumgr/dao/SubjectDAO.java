package com.stumgr.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

import com.stumgr.bean.Subject;
import com.stumgr.dbutil.DbUtil;

public class SubjectDAO {

	public void modifySubjectMasterlist(Scanner sc) throws ClassNotFoundException, SQLException {

		displaySubjects();

		while(true){
			int optionChosen = 0;
			System.out.println("Please select from the following options:");
			System.out.println("1.Add a new Subject.");
			System.out.println("2.Delete an existing Subject.");
			System.out.println("3.Modify an existing Subject.");	
			System.out.println("4.Display existing Subjects Masterlist.");
			System.out.println("5.Go back to the main menu.");
			while (!sc.hasNextInt()) {
				String input = sc.next();
				System.out.printf("\"%s\" is not a valid input.Please choose from the given four options.", input);
			}
			optionChosen = sc.nextInt();
			//System.out.println("optionChosen-- " + optionChosen);
			switch(optionChosen) {
			case 1:
				System.out.println("You have chosen to add a subject.\n");
				addSubject(sc);
				break;
			case 2:
				System.out.println("You have chosen to delete a subject.\n");
				deleteSubject(sc);
				break;
			case 3:
				System.out.println("You have chosen to modify an existing subject.\n");
				updateSubject(sc);
				break;	
			case 4:
				displaySubjects();
				break;
			case 5:
				System.out.println("You have chosen to go back to the main menu.\n");
				return;	
			default:
				System.out.println("Please enter any one of the 4 given options.\n");				
			}
		}
	}

	public void addSubject(Scanner sc) throws ClassNotFoundException, SQLException {

		System.out.println("Please enter the name of the subject you wish to add-");
		String sSubName = sc.next();
		//if(sc.next().matches("[a-zA-Z]"))
		//System.out.println("String Entered---" + sSubName +"---" + Pattern.matches("^[a-zA-Z]*$", sSubName));
		if(Pattern.matches("^[a-zA-Z]*$", sSubName))
		{
			//sSubName = sc.next();
			Connection con = DbUtil.dbConn();
			Statement st = con.createStatement();
			String sql = "insert into SubjectTable(SubjectName) values (" +"\""+ sSubName +"\")";
			//System.out.println("sql--" + sql);
			int rows = st.executeUpdate(sql);
			if(!(rows > 0)) 
			{
				System.out.println("No rows effected");
			}
			else if(rows > 0) 
			{
				System.out.println("Number of rows effected: " + rows );
			}
		}
		else 
		{
			System.out.println("Please enter a valid Subject Name.");
		}
	}

	private void deleteSubject(Scanner sc) throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		System.out.println("Please enter the ID of the subject you wish to delete-");
		if(sc.hasNextInt())
		{
			int sSubID = sc.nextInt();
			Connection con = DbUtil.dbConn();
			Statement st = con.createStatement();
			String sql = "delete from SubjectTable where SubjectID=" + sSubID;
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
			System.out.println("Please enter a valid numeric Subject ID.");
		}
	}

	private void updateSubject(Scanner sc) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		System.out.println("Please enter the ID of the subject you wish to modify-");
		if(sc.hasNextInt())
		{
			String sSubID = sc.next();
			System.out.println("Please enter the new subject name for this ID-");
			String sSubName = sc.next();
			//System.out.println("String Entered---" + sSubName +"---" + Pattern.matches("^[a-zA-Z]*$", sSubName));
			if(sSubName.matches("^[a-zA-Z]*$"))
			{
				Connection con = DbUtil.dbConn();
				Statement st = con.createStatement();
				String sql = "update SubjectTable set SubjectName = " +"\""+ sSubName +"\" where SubjectID = " +  sSubID;
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
				//error = true;
				System.out.println("Invalid input!");
				System.out.print("Please enter a  String value for the Subject Name ");
				//sSubID = sc.next();
			}
		}
		else
		{
			//error = true;
			System.out.println("Invalid input!");
			System.out.print("Please enter an ID(Integer value) of the subject you wish to modify- ");
			//sSubID = sc.next();
		}
	}	

	public void displaySubjects() throws ClassNotFoundException, SQLException {

		Connection con = DbUtil.dbConn();
		Statement st = con.createStatement();
		String sql = "select * from SubjectTable";
		ResultSet rs = st.executeQuery(sql);
		ArrayList<Subject> list = new ArrayList<>();
		while(rs.next()) {
			Subject s = new Subject();
			s.setnSubID(rs.getInt(1));
			s.setsSubName(rs.getString(2));
			list.add(s);
		}
		if(list != null && list.size() > 0) 
		{
			System.out.println("--Existing Subjects--");
			System.out.println("---------------------------------------------------------------------------------------------");
			//System.out.println("ID" + "  " + "Name" + "  ");
			System.out.format("%7s %20s","ID", "Name");
			System.out.println("");
			for(Subject eachSub:list) {
				System.out.format("%7s %20s",eachSub.getnSubID(), eachSub.getsSubName());
				System.out.println("");
			}
		}
		System.out.println("---------------------------------------------------------------------------------------------");
	}
}
