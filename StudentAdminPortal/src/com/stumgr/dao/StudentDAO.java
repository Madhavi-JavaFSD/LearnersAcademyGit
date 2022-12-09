package com.stumgr.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.stumgr.bean.Student;
import com.stumgr.dbutil.DbUtil;

public class StudentDAO {

	public int insert(Student s) throws ClassNotFoundException, SQLException {

		Connection con = DbUtil.dbConn();
		return 0;
	}

	public void displayStudents() throws ClassNotFoundException, SQLException {

		Connection con = DbUtil.dbConn();
		Statement st = con.createStatement();
		String sql= "select s.StudentID, s.StudentName, ct.StandardName from student as s left outer join ClassTable as ct on s.ClassID=ct.ClassId;";
		ResultSet rs = st.executeQuery(sql);
		if (rs.next() == false) 
		{
			System.out.println("No students have been assigned to classes yet.");
		} 
		else 
		{
			System.out.println("---------------------------------------------------------------------------------------------");
			System.out.println("--Student Details--");
			System.out.println("---------------------------------------------------------------------------------------------");
			System.out.format("%7s %20s %10s", "StudentID", " StudentName", "Class");
			System.out.println(); 
			do {					
				System.out.format("%7s %20s %10s", rs.getInt(1), rs.getString(2), rs.getString(3));  
				System.out.println();  				
			} while (rs.next());
			System.out.println("---------------------------------------------------------------------------------------------");
		}
	}
	public void displayClassReport() throws ClassNotFoundException,SQLException {
		//display student ID, student Name, their class, each subject and their respective teacher
		Connection con = DbUtil.dbConn();
		Statement st = con.createStatement();
		String sql="select s.StudentID, s.StudentName, ct.StandardName, st.SubjectName, tt.TeacherName from Student as s \r\n"
				+ "left outer join Class_Subject_Teacher as cst on s.ClassID=cst.ClassID\r\n"
				+ "left outer join ClassTable as ct on s.ClassID=ct.ClassId\r\n"
				+ "left outer join SubjectTable as st on cst.SubjectID=st.SubjectID\r\n"
				+ "left outer join Teacher as tt on cst.TeacherID=tt.TeacherID";
		ResultSet rs = st.executeQuery(sql);
		if (rs.next() == false) 
		{
			System.out.println("No students have been assigned to classes yet.");
		} 
		else
		{
			System.out.println("---------------------------------------------------------------------------------------------");
			System.out.format("%7s %20s %7s %15s %20s ", "StudentID", "StudentName","Class", "SubjectName", "Teacher");
			System.out.println(); 
			System.out.println("---------------------------------------------------------------------------------------------");
			System.out.println(); 
			do{
				System.out.format("%7s %20s %7s %15s %20s", rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));  
				System.out.println();  
				//System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " +rs.getString(4) + " "+ rs.getString(5));
			}while(rs.next());
			System.out.println("---------------------------------------------------------------------------------------------");
		}
		
	}

}
