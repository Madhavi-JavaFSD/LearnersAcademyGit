package com.stumgr.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import com.stumgr.bean.Class_Subject_Teacher;
import com.stumgr.dbutil.DbUtil;

public class AssignSubject {

	public void modifySubjectForClass(Scanner sc) throws ClassNotFoundException, SQLException {	

		displaySubjectsForClass();
		while(true)
		{
			int optionChosen = 0;
			System.out.println("\n\nPlease select from the following options:");
			System.out.println("1.Add a subject to a class.");
			System.out.println("2.Delete the subject that had been assigned earlier for a class.");
			System.out.println("3.Display Class-Subject-Teacher table.");
			System.out.println("4.Go back to the main menu.");
			while (!sc.hasNextInt()) {
				String input = sc.next();
				System.out.printf("\"%s\" is not a valid input.Please choose from the given four options.", input);
			}
			optionChosen = sc.nextInt();
			//System.out.println("optionChosen-- " + optionChosen);
			switch(optionChosen) {
			case 1:
				//System.out.println("You have chosen to add a subject for a class.\n");
				addSubject(sc);
				break;
			case 2:
				System.out.println("You have chosen to delete the subject that had been assigned earlier for a class.\n");
				deleteSubject(sc);
				break;
			case 3:
				displaySubjectsForClass();
				break;	
			case 4:
				System.out.println("You have chosen to go back to the main menu.\n");
				return;	
			default:
				System.out.println("Please enter any one of the 4 given options.\n");				
			}

		}

	}
	private void addSubject(Scanner sc) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		System.out.println("You have chosen to add a subject for a class.\n--Please note that you can add only those subjects and classes which are "
				+ "\nalready present in the ClassTable table and SubjectTable table.--");
		System.out.println("Please enter the ID of the class-");
		int nClassID = 0;
		int nSubID = 0;
		if(sc.hasNextInt())
		{
			nClassID = sc.nextInt();
			System.out.println("Please enter the ID of the subject you wish to add to the class-");
			if(sc.hasNextInt())
			{
				nSubID = sc.nextInt();
				Connection con = DbUtil.dbConn();
				Statement st = con.createStatement();
				String sql = "insert into Class_Subject_Teacher(ClassID,SubjectID) values ( " + nClassID + "," + nSubID + ")";
				System.out.println("sql--" + sql);
				int rows = 0;
				try
				{
					rows = st.executeUpdate(sql);	
					if(!(rows > 0)) 
					{
						System.out.println("No rows effected");
					}
					else if(rows > 0) 
					{
						System.out.println("Number of rows effected: " + rows );
					}
				}
				catch (SQLException e) 
				{
					//System.out.println("SQL State--" + e.getSQLState());
					if (e instanceof SQLIntegrityConstraintViolationException) 
					{
						System.out.println("--Make sure the Class ID and Subject ID exists in the ClassTable and SubjectTable respectively.--");
					}
				}
			}
			else
			{
				System.out.println("Please enter valid numeric Subject ID.");
			}
		}
		else
		{
			System.out.println("Please enter valid numeric Class ID.");
		}


	}
	private void deleteSubject(Scanner sc) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		System.out.println("Please enter the ID of the class you wish to delete-");
		int nClassID = sc.nextInt();
		System.out.println("Please enter the ID of the subject you wish to delete-");
		int nSubID = sc.nextInt();
		Connection con = DbUtil.dbConn();
		Statement st = con.createStatement();
		String sql = "delete from Class_Subject_Teacher where ClassID=" + nClassID + " and SubjectID=" + nSubID;
		//System.out.println("sql--" + sql);
		int rows = st.executeUpdate(sql);
		if(!(rows > 0)) {
			System.out.println("No rows effected");
		}
		else if(rows > 0) {
			System.out.println("Number of rows effected: " + rows );
		}
	}
	private void displaySubjectsForClass() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		Connection con = DbUtil.dbConn();
		Statement st = con.createStatement();
		String sql = "select * from Class_Subject_Teacher";
		ResultSet rs = st.executeQuery(sql);
		ArrayList<Class_Subject_Teacher> list = new ArrayList<>();
		while(rs.next()) {
			Class_Subject_Teacher s = new Class_Subject_Teacher();
			s.setnClassID(rs.getInt(1));
			s.setnSubID(rs.getInt(2));
			s.setnTeacherID(rs.getInt(3));
			list.add(s);
		}
		if(list != null && list.size() > 0) 
		{
			System.out.println("--Existing Subjects for Classes--");
			System.out.println("---------------------------------------------------------------------------------------------");
			//System.out.println("ClassID" + "  " + "SubjectID" + "  "+ "  " + "TeacherID");
			System.out.format("%7s %20s %20s","ClassID", "SubjectID", "TeacherID");
			System.out.println("");
			for(Class_Subject_Teacher eachClass:list) 
			{
				//System.out.println(eachClass.getnClassID() + "  " + eachClass.getnSubID() + "  " + eachClass.getnTeacherID());
				System.out.format("%7s %20s %20s",eachClass.getnClassID(), eachClass.getnSubID(), eachClass.getnTeacherID());
				System.out.println("");
			}
			
			System.out.println("---------------------------------------------------------------------------------------------");
		}
		else if(list != null && list.size() == 0)
		{
			System.out.println("No entries found in the Class_Subject_Teacher table.");
		}
	}
	public void mainAssign(Scanner sc) throws ClassNotFoundException, SQLException {
		displaySubjectsForClass();
		while(true)
		{
			int optionChosen = 0;
			System.out.println("\n\nPlease select from the following options:");
			System.out.println("1.Add a subject to a class.");
			System.out.println("2.Delete the subject that had been assigned earlier for a class.");
			System.out.println("3.Display existing Class Masterlist.");
			System.out.println("4.Go back to the main menu.");
			while (!sc.hasNextInt()) {
				String input = sc.next();
				System.out.printf("\"%s\" is not a valid input.Please choose from the given four options.", input);
			}
			optionChosen = sc.nextInt();
			//System.out.println("optionChosen-- " + optionChosen);
			switch(optionChosen) {
			case 1:
				//System.out.println("You have chosen to add a subject for a class.\n");
				addSubject(sc);
				break;
			case 2:
				System.out.println("You have chosen to delete the subject that had been assigned earlier for a class.\n");
				deleteSubject(sc);
				break;
			case 3:
				displaySubjectsForClass();
				break;	
			case 4:
				System.out.println("You have chosen to go back to the main menu.\n");
				return;	
			default:
				System.out.println("Please enter any one of the 4 given options.\n");				
			}

		}
	}
	public void assignTeachertoClass(Scanner sc) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		displaySubjectsForClass();
		System.out.println("You have chosen to assign a teacher to a subject for a class.\n--"
				+ "Please note that you can add a teacher only if the teacher ID is "
				+ "\nalready present in the Teacher table.--");
		System.out.println("Please enter the ID of the class-");
		int nClassID = 0;
		int nSubID = 0;
		int nTeacherID = 0;
		if(sc.hasNextInt())
		{
			nClassID = sc.nextInt();
			System.out.println("Please enter the ID of the subject you wish to add to the class-");
			if(sc.hasNextInt())
			{
				nSubID = sc.nextInt();
				System.out.println("Please enter the ID of the Teacher you wish to assign to the class-subject");
				if(sc.hasNextInt()) 
				{
					nTeacherID = sc.nextInt();
					Connection con = DbUtil.dbConn();
					Statement st = con.createStatement();
					//UPDATE Class_Subject_Teacher SET TeacherID=1 WHERE ClassID=1 and SubjectID=1;
					String sql = "update Class_Subject_Teacher set TeacherID = " + nTeacherID + " where ClassID=" + nClassID +" and SubjectID=" + nSubID;
					System.out.println("sql--" + sql);
					int rows = 0;
					try
					{
						rows = st.executeUpdate(sql);	
						if(!(rows > 0)) 
						{
							System.out.println("No rows effected");
						}
						else if(rows > 0) 
						{
							System.out.println("Number of rows effected: " + rows );
						}
					}
					catch (SQLException e) 
					{
						//System.out.println("SQL State--" + e.getSQLState());
						if (e instanceof SQLIntegrityConstraintViolationException) 
						{
							System.out.println("--Make sure the Class ID and Subject ID exists in the ClassTable and SubjectTable respectively.--");
						}
					}
				}
				else
				{
					System.out.println("Please enter valid numeric Teacher ID.");
				}
			}
			else
			{
				System.out.println("Please enter valid numeric Subject ID.");
			}
		}
		else
		{
			System.out.println("Please enter valid numeric Class ID.");
		}		
	}
	public void deleteTeacherAssigned(Scanner sc) throws SQLException, ClassNotFoundException
	{

		// TODO Auto-generated method stub
		displaySubjectsForClass();
		System.out.println("You have chosen to delete the teacher- entry assigned to a subject for a class.");
		System.out.println("Please enter the ID of the class-");
		int nClassID = 0;
		int nSubID = 0;
		if(sc.hasNextInt())
		{
			nClassID = sc.nextInt();
			System.out.println("Please enter the ID of the subject you wish to add to the class-");
			if(sc.hasNextInt())
			{
				nSubID = sc.nextInt();
				Connection con = DbUtil.dbConn();
				Statement st = con.createStatement();
				String sql = "update Class_Subject_Teacher set TeacherID = NULL where ClassID=" + nClassID +" and SubjectID=" + nSubID;
				System.out.println("sql--" + sql);
				int rows = 0;
				try
				{
					rows = st.executeUpdate(sql);	
					if(!(rows > 0)) 
					{
						System.out.println("No rows effected");
					}
					else if(rows > 0) 
					{
						System.out.println("Number of rows effected: " + rows );
					}
				}
				catch (SQLException e) 
				{
					//System.out.println("SQL State--" + e.getSQLState());
					if (e instanceof SQLIntegrityConstraintViolationException) 
					{
						System.out.println("--Make sure the Class ID and Subject ID exists in the ClassTable and SubjectTable respectively.--");
					}
				}
			}
			else
			{
				System.out.println("Please enter valid numeric Subject ID.");
			}
		}
		else
		{
			System.out.println("Please enter valid numeric Class ID.");
		}		

	}	
}
