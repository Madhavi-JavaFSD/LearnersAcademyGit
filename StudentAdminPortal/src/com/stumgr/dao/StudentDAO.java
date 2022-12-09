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
		String sql="select * from student";
		ResultSet rs = st.executeQuery(sql);
		ArrayList<Student> list = new ArrayList<>();
		while(rs.next()) {
			Student s = new Student();
			s.setStuID(rs.getInt(1));
			s.setStuName(rs.getString(2));
			s.setnClass(rs.getInt(3));
			list.add(s);
		}
		System.out.println("ID" + "  " + "Name" + "  " + "Class");
		for(Student eachStu:list) {
			System.out.println(eachStu.getStuID() + "  " + eachStu.getStuName() + "  " + eachStu.getnClass());
		}
		System.out.println("");
		System.out.println("");
	}
	public void displayClassReport() throws ClassNotFoundException,SQLException {
		//display student ID, student Name, their class, each subject and their respective teacher
	/*	select s.StudentID, s.StudentName, ct.StandardName, st.SubjectName, tt.TeacherName from Student as s 
		left outer join Class_Subject_Teacher as cst on s.ClassID=cst.ClassID
		left outer join ClassTable as ct on s.ClassID=ct.ClassId
		left outer join SubjectTable as st on cst.SubjectID=st.SubjectID
		left outer join Teacher as tt on cst.TeacherID=tt.TeacherID;*/
		Connection con = DbUtil.dbConn();
		Statement st = con.createStatement();
		String sql="select s.StudentID, s.StudentName, ct.StandardName, st.SubjectName, tt.TeacherName from Student as s \r\n"
				+ "left outer join Class_Subject_Teacher as cst on s.ClassID=cst.ClassID\r\n"
				+ "left outer join ClassTable as ct on s.ClassID=ct.ClassId\r\n"
				+ "left outer join SubjectTable as st on cst.SubjectID=st.SubjectID\r\n"
				+ "left outer join Teacher as tt on cst.TeacherID=tt.TeacherID";
		ResultSet rs = st.executeQuery(sql);
		//ArrayList list = new ArrayList<>();
		//System.out.println("StudentID" + "  " + "StudentName" + "  " + "Class" + "  "+ "SubjectName" + "  " + "Teacher");
		System.out.println("---------------------------------------------------------------------------------------------");
		System.out.format("%7s %20s %7s %15s %20s ", "StudentID", "StudentName","Class", "SubjectName", "Teacher");
		System.out.println(); 
		while(rs.next()) {
			System.out.format("%7s %20s %7s %15s %20s", rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));  
			System.out.println();  
			//System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " +rs.getString(4) + " "+ rs.getString(5));
		}
		System.out.println("---------------------------------------------------------------------------------------------");
	}

}
