package com.stumgr.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import com.stumgr.bean.Class_Subject_Teacher;
import com.stumgr.bean.Classes;
import com.stumgr.bean.Subject;
import com.stumgr.bean.Teacher;
import com.stumgr.dbutil.DbUtil;

public class AssignTeacher {

	public void mainAssign(Scanner sc) throws ClassNotFoundException, SQLException {
		displayAll();
		while(true)
		{
			int optionChosen = 0;
			System.out.println("Please select from the following options:");
			System.out.println("1.Assign a Teacher to a class");
			System.out.println("2.Delete the teacher assigned to a class and subject.");
			System.out.println("3.Modify the teacher assigned to a class and subject.");
			System.out.println("4.Display Class-Subject-Teacher table.");
			System.out.println("5.Go back to the main menu.");
			while (!sc.hasNextInt()) {
				String input = sc.next();
				System.out.printf("\"%s\" is not a valid input.Please choose from the given four options.", input);
			}
			optionChosen = sc.nextInt();
			//System.out.println("optionChosen-- " + optionChosen);
			switch(optionChosen) {
			case 1:
				//System.out.println("You have chosen to add a subject for a class.\n");
				assignTeachertoClass(sc);
				break;
			case 2:
				System.out.println("You have chosen to delete the subject that had been assigned earlier for a class.\n");
				deleteTeacherAssigned(sc);
				break;
			case 3:
				modifyTeachertoClass(sc);
				break;	
			case 4:
				displayAll();
				break;
			case 5:
				System.out.println("You have chosen to go back to the main menu.\n");
				return;	
			default:
				System.out.println("Please enter any one of the 4 given options.\n");				
			}

		}
	}
	public void assignTeachertoClass(Scanner sc) throws ClassNotFoundException, SQLException {

		// TODO Auto-generated method stub
		//displaySubjectsForClass();
		System.out.println("You have chosen to assign a teacher to a subject for a class.\n--"
				+ "Please note that you can add a teacher only if the teacher ID is "
				+ "\nalready present in the Teacher table.--");
		displayClassSubject();
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
					String sql = "insert into Class_Subject_Teacher(ClassID,SubjectID,TeacherID) values (" + nClassID +"," + nSubID + "," + nTeacherID +")";
					System.out.println("sql--assign--" + sql);
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
							displayAll();
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

	public void modifyTeachertoClass(Scanner sc) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		displayAll();
		System.out.println("You have chosen to modify the teacher assigned to a subject for a class.");
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
					System.out.println("modify--sql--" + sql);
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
	private void displayClassSubject() throws ClassNotFoundException, SQLException {
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
			System.out.println("ID" + "  " + "Class Name" + "  ");
			for(Classes eachClass:list) {
				System.out.println(eachClass.getnClassID() + "  " + eachClass.getnStandard() + "  ");
			}
			System.out.println("");
		}	

		sql = "select * from SubjectTable";
		rs = st.executeQuery(sql);
		ArrayList<Subject> list2 = new ArrayList<>();
		while(rs.next()) {
			Subject s = new Subject();
			s.setnSubID(rs.getInt(1));
			s.setsSubName(rs.getString(2));
			list2.add(s);
		}
		if(list2 != null && list2.size() > 0) 
		{
			System.out.println("--Existing Subjects--");
			System.out.println("ID" + "  " + "Subject Name" + "  ");
			for(Subject eachSub:list2) {
				System.out.println(eachSub.getnSubID() + "  " + eachSub.getsSubName() + "  ");
			}
			System.out.println("");
		}

		sql = "select * from teacher";
		rs = st.executeQuery(sql);
		ArrayList<Teacher> list3 = new ArrayList<>();
		while(rs.next()) {
			Teacher s = new Teacher();
			s.setnTeacherId(rs.getInt(1));
			s.setsTeacherName(rs.getString(2));
			list3.add(s);
		}
		if(list != null && list.size() > 0) 
		{
			System.out.println("--Existing Teachers--");
			System.out.println("ID" + "  " + "Teacher Name" + "  ");
			for(Teacher eachTeacher:list3) {
				System.out.println(eachTeacher.getnTeacherId() + "  " + eachTeacher.getsTeacherName() + "  ");
			}
			System.out.println("");
		}	
	}
	public void deleteTeacherAssigned(Scanner sc) throws SQLException, ClassNotFoundException
	{

		// TODO Auto-generated method stub
		displayAll();
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
	private void displayAll() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		Connection con = DbUtil.dbConn();
		Statement st = con.createStatement();
		String sql = "select cst.ClassID, ct.StandardName, cst.SubjectID, st.SubjectName, cst.TeacherID, tt.TeacherName from Class_Subject_Teacher as cst\r\n"
				+ "left outer join ClassTable as ct on ct.ClassID=cst.ClassID\r\n"
				+ "left outer join SubjectTable as st on cst.SubjectID=st.SubjectID\r\n"
				+ "left outer join Teacher as tt on cst.TeacherID=tt.TeacherID;";
		ResultSet rs = st.executeQuery(sql);
		/*ArrayList<Class_Subject_Teacher> list = new ArrayList<>();
		while(rs.next()) {
			Class_Subject_Teacher s = new Class_Subject_Teacher();
			s.setnClassID(rs.getInt(1));
			s.setnSubID(rs.getInt(2));
			s.setnTeacherID(rs.getInt(3));
			list.add(s);
		}
		if(list != null && list.size() > 0) 
		{
			System.out.println("-- Existing Assignment of Teachers--");
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
		}*/
		if (rs.next() == false) {
			System.out.println("No subjects have been assigned to classes yet.");
		} 
		else 
		{
			System.out.println("--Assignment of Teachers to classes and subjects--");
			System.out.println("---------------------------------------------------------------------------------------------");
			System.out.format("%7s %10s %10s %15s %10s %15s ", "ClassID","ClassName","SubjectID", "SubjectName", "TeacherID", "TeacherName");
			System.out.println(); 
			do {					
				System.out.format("%7s %10s %10s %15s %10s %15s", rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getInt(5), rs.getString(6));  
				System.out.println();  				
			} while (rs.next());
			System.out.println("---------------------------------------------------------------------------------------------");
		}

	}
}
