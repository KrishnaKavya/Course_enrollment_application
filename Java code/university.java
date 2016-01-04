package demo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class University{
	static Connection con;
	public static int id;


	public static void main(String args[]) throws SQLException ,NullPointerException{
		Scanner sc= new Scanner(System.in);
		int ch;
		
		// establishing connection.
		   try { 
			   	Class.forName 

			   	("oracle.jdbc.driver.OracleDriver"); 
			   
			   	}
		   catch (ClassNotFoundException e) 
		   {
			  System.out.println ("Could not load the driver"); 
			}
		   //taking the login details of user
		System.out.println("please enter the id");
		   String user= sc.nextLine();
		   System.out.println("Please enter the password");
		   String pass=sc.nextLine();
		
		   con = DriverManager.getConnection("jdbc:oracle:thin:@csoracle.utdallas.edu:1521:student",user,pass); 
		 
		  	
		   
		   // taking student id
		   
				System.out.println("please enter student id");
				id=sc.nextInt();
					
		   
		   
		do{
			
			System.out.println("Please enter your choice");
			System.out.println("1: Add a Class");
			System.out.println("2: Delete a Class");
			System.out.println("3. See my Schedule");
			System.out.println("4: Exit");
			 ch=sc.nextInt();
			 
			 switch(ch){
			 
			 case 1: Add();
			 		 break;
			 case 2: drop();
			 		 break;
			 case 3: schedule();
			 		 break;
			 }
			 
		}while(ch!= 4 && ch<4);
		
		 System.out.println("End of the session");

	
	}


	private static void schedule() throws SQLException {
		String schedule="select S.course_number,S.section_identifier,C.course_name,S.INSTRUCTOR  from course C ,section S, Grade_report G where G.Student_number='"+id+"' and G.section_identifier=S.section_identifier and S.semester='Fall' and S.Year=2006 and S.course_number=C.course_number ";
		PreparedStatement s=con.prepareStatement(schedule);
		ResultSet r=s.executeQuery();
		while(r.next())
		{
			System.out.println(r.getString(1)+ " "+ r.getString(2)+" "+ r.getString(3)+" "+r.getString(4));
		//	System.out.println(r.getString(2));
//			System.out.println(r.getString(3));
//			System.out.println(r.getString(4));
		}
		
	}


	private static void drop() throws SQLException {
		// taking input
		Scanner sc= new Scanner(System.in);
		String coursenumber;
		String section_number;
		System.out.println("enter the Course number");
		coursenumber=sc.nextLine();
		System.out.println(" enter section number");
		section_number= sc.nextLine();
		String CompletedCourses= new String( );
		String CurrentSec=new String();
		String CurrentCourse=new String();
		String DeletedCourses= new String( );
		
		//Setting the flags
		boolean cdel=false;
		boolean sdel=false;
		
		//Spring 2007 courses can be deleted
		String valid=" select Section.Section_identifier, Section.Course_number from Section , Grade_report where Section.Section_identifier=Grade_report.Section_identifier and grade_report.student_number='"+id+"' and section.Semester='Spring' and Section.Year=2007";
		PreparedStatement v=con.prepareStatement(valid);
		ResultSet rv=v.executeQuery();
		while(rv.next()){
			CurrentSec=CurrentSec+rv.getString(1);
			CurrentCourse=CurrentCourse+rv.getString(2);
		//	System.out.println(rv.getShort(1));
			
		
		}
		if(CurrentCourse.contains(coursenumber)){

			cdel=true;
		}
		if(CurrentSec.contains(section_number)){

			sdel=true;
		}
	
		// Completed Course cannot be deleted.
		 String query5="select Section.Course_number from Section,Grade_Report where Grade_Report.Student_number='"+id+"' and Section.Section_identifier=Grade_Report.Section_identifier and Semester='Fall' and Year=2006";
		 PreparedStatement s5=con.prepareStatement(query5);
		 ResultSet r5=s5.executeQuery();
		 while(r5.next()){
			// System.out.println(r5.getString(1));
			CompletedCourses = CompletedCourses+r5.getString(1);
			
		}
	

		 
		 if(CompletedCourses.contains(coursenumber)&&!cdel &&!sdel){
				System.out.println(" The course is completed and not current enrolled so we cannot delete it");	
			}
		 else
		 {
			 String query6="delete from Grade_Report where Student_number='"+id+"' and Section_identifier="+section_number;  
			PreparedStatement s6=con.prepareStatement(query6);
			ResultSet r6=s6.executeQuery();
			System.out.println("Course Deleted Successfull");
		 
		 }
		 }
	
	

	private static void Add() throws SQLException {
		
		// taking input
		// adding null as he/she is going take it in next sem
		Scanner sc= new Scanner(System.in);
		String coursenumber;
		int section_number;
		String Major = null;
		String [] CoursesInDepartment = new String[10];
		String [] CoursesInSpring=new String[20];
		int [] SectionInSpring=new int[5];
		String Prereq=new String();
		String CompletedCourses=new String();
	
		 
		boolean CourseSpring = false;
		boolean SectionSpring= false;
		boolean PrereqCompleted=true;
		boolean alreadyTaken=true;
//		
//		// we need to print all the available courses of student with respect to the department.
		/*
		 * step 1-get major form = select Major from STUDENT where id =id
		 * step 2- get all Course numbers from course table from Major=Department. 
		 * Step 3: fetched all the courses in that department in Spring 2007. 
		 * Step 4: getting PreRequisite for the Course_number if it exits in Spring 2007 in that department.
		 * Step 5: get courses which the student finished in .
		 */
		
		System.out.println("enter the Course number");
		coursenumber=sc.nextLine();
		System.out.println("enter section number");
		section_number= sc.nextInt();
//		
		//step 1:
		
		String query1="select Major from STUDENT where Student_number="+id;
		PreparedStatement s1=con.prepareStatement(query1);
		ResultSet r1=s1.executeQuery();
		while(r1.next()){
		Major=r1.getString(1);
		}
		
		//Step 2
		String query2="select Course_number from Course where Department='"+Major+"'";
		PreparedStatement s2=con.prepareStatement(query2);
		ResultSet r2=s2.executeQuery();
		int i=0;
		while(r2.next()){
			CoursesInDepartment[i]=r2.getString(1);
			i++;
			
		}
		
		//Step 3
		for(i=0;i<CoursesInDepartment.length;i++){
			String query3="select Course_number,section_identifier from Section where Semester='Spring' and year=2007 and Course_number='"+CoursesInDepartment[i]+"'";
			//String query3="select Course_number from Section where Semester='Spring' and year=2007";
			
			PreparedStatement s3=con.prepareStatement(query3);
			ResultSet r3=s3.executeQuery();
			while(r3.next()){
				CoursesInSpring[i]=r3.getString(1);
				SectionInSpring[i]=r3.getInt(2);
			}
			
		}
		
		
		// Combining the whole available courses in spring into one.
		String a=null;
		for(int i1=0;i1<CoursesInSpring.length;i1++)
			 a=a+CoursesInSpring[i1];
		
		//checking if the input contains the available course

		if(a.contains(coursenumber))
		{
		 CourseSpring=true;
		}
		else{
			CourseSpring=false;
		}
	
		if(!CourseSpring && (coursenumber!=null)){
		System.out.println("Please enter a valid Course Number for Spring 2007");
		}
		
		
		
		
		//Validating the Section number
		i=0;
		int len=SectionInSpring.length;
		while(i<len)
		{
		
			if(SectionInSpring[i]==section_number){
				SectionSpring=true;
				break;
			}
			SectionSpring=false;
			i++;
		}
		
		if(!SectionSpring){
			System.out.println("invalid section entered");
		}
	
		
		//STEP4
		
		if(CourseSpring){
			String query4="select Prerequisite_number from Prerequisite where Course_number='"+coursenumber+"'";
			PreparedStatement s4=con.prepareStatement(query4);
			ResultSet r4=s4.executeQuery();
			i=0;
			while(r4.next()){
				Prereq=Prereq+r4.getString(1);
				i++;
			}
		}
		//STEP5
		
		String query5="select Section.Course_number from Section,Grade_Report where Grade_Report.Student_number='"+id+"' and Section.Section_identifier=Grade_Report.Section_identifier and Semester='Fall' and Year=2006";
		
		PreparedStatement s5=con.prepareStatement(query5);
		ResultSet r5=s5.executeQuery();
		while(r5.next()){
			CompletedCourses=CompletedCourses+r5.getString(1);
			//System.out.println(r5.getString(1));
		}
		
		
		
		if(CompletedCourses.contains(Prereq)){
			PrereqCompleted=true;
		}
		else
		{			PrereqCompleted=false;
		
		}
		
		if(CompletedCourses.contains(coursenumber)){
			alreadyTaken=false;
		}
				

		
		// VALIDATION BEFORE INSERTING THE DATA
		if(SectionSpring&& CourseSpring &&PrereqCompleted&& !alreadyTaken){
			System.out.println("Addition of record Successfull");
			String Finaladd="insert into Grade_Report values("+id+","+section_number+", 'null')";
		PreparedStatement f=con.prepareStatement(Finaladd);
			f.executeQuery();
			
			
	}else{

		System.out.println(" cannot add the record as it is already existing/incorrect data");
		}
		

		

			
		
		
		
	}


}