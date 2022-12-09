package com.stumgr.main;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import com.stumgr.dao.StudentDAO;
import com.stumgr.dao.SubjectDAO;
import com.stumgr.dao.TeacherDAO;
import com.stumgr.dao.AssignSubject;
import com.stumgr.dao.AssignTeacher;
import com.stumgr.dao.ClassesDAO;
import com.stumgr.dbutil.DbUtil;

public class StudentCRUDMain {

	static String sCorrectUsername = "Admin";
	static String sCorrectPassword = "Admin123";
	static boolean bVerified = false;


	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		System.out.println("*******Welcome to Student Information System*******");		
		bVerified = verifyPass(sc);
		while(true) 
		{
			if(bVerified)
			{
				System.out.println("Please select from the following options:");
				System.out.println("1.Modify the Subjects Masterlist");
				System.out.println("2.Modify the Teachers Masterlist");
				System.out.println("3.Modify the Classes Masterlist");
				System.out.println("4.Assign a subject to a class");
				System.out.println("5.Assign a teacher to a subject for a class");
				System.out.println("6.Display student details");
				System.out.println("7.Display Class Report");
				System.out.println("8.Exit the system.");
				if(sc.hasNext()) 
				{
					while (!sc.hasNextInt()) {
						String input = sc.next();
						System.out.printf("\"%s\" is not a valid input.Please choose from the given options.", input);
					}
					int choice = sc.nextInt();
					switch(choice)
					{
					case 1:					
						System.out.println("You have chosen to modify the Subjects Masterlist.");
						SubjectDAO sDaoObj = new SubjectDAO();
						sDaoObj.modifySubjectMasterlist(sc);
						break;
					case 2: 
						TeacherDAO tDaoObj = new TeacherDAO();
						tDaoObj.modifyTeachersMasterlist(sc);
						break;
					case 3: 
						ClassesDAO cDaoObj = new ClassesDAO();
						cDaoObj.modifyClassesMasterlist(sc);
						break;
					case 4: 	
						System.out.println("You have chosen to assign a subject to a class.");
						AssignSubject cstDaoObj = new AssignSubject();
						cstDaoObj.modifySubjectForClass(sc);
						break;
					case 5: 	
						System.out.println("You have chosen to assign a teacher to a subject for a class.");
						AssignTeacher cstDaoObj2 = new AssignTeacher();
						cstDaoObj2.mainAssign(sc);
						break;
					case 6: 	
						System.out.println("You have chosen to display student details.");
						StudentDAO stDaoObj = new StudentDAO();
						stDaoObj.displayStudents();
						break;
					case 7:
						System.out.println("You have chosen to display Class Report.");
						StudentDAO DaoObj = new StudentDAO();
						DaoObj.displayClassReport();
						break;
					case 8:
						System.out.println("You have chosen to close this application.\n");	
						System.exit(0);
					default:
						System.out.println("Please enter any one of the 8 given options.\n");
					}
				}
			}
			else 
			{
				System.out.println(" Invalid Username or Password!");
				bVerified = verifyPass(sc);
			}
		}
	}

	private static boolean verifyPass(Scanner sc) {
		// TODO Auto-generated method stub
		boolean bVerify= false;
		System.out.print(" Enter user name => ");
		String userName = sc.nextLine();

		System.out.print(" Enter password => ");
		String password = sc.nextLine();

		if(sCorrectUsername.equals(userName) && sCorrectPassword.equals(password))
		{
			System.out.println(" User successfully logged-in.. ");
			bVerify = true; 
		}
		return bVerify;
	}

}
