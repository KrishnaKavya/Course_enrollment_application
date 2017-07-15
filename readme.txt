Instructions to execute the scripts

First we drop all the tables if they exist 
> start clean_up.sql

1)start university_schema.sql in [csoracle]

2)login to sql loader to enter data into tables
opt/oracle/app/csoracle/product/12.1.0/dbhome_1/bin/sqlplus

3)start remove_constraints.sql

4)/opt/oracle/app/csoracle/product/12.1.0/dbhome_1/bin/sqlldr kxm152630/123456

Control=student.ctl [ files placed in the server as well as attached to the document ]

// repeat the process for all the loading files for
courses.ctl
GradeReport.ctl
Prerequisite.ctl
Section.ctl

5) Need to restate all the constraints 

/opt/oracle/app/csoracle/product/12.1.0/dbhome_1/bin/sqlplus

Start After_insert.sql

6)Execute the java program in [csgrads1]

javac -cp .:ojdbc7.jar university.java

please enter the id:
kxm15*****

Please enter the password:
******

Sample inputs and outputs :

please enter student id : 1

Please enter your choice
1: Add a Class
2: Delete a Class
3. See my Schedule
4: Exit

Please enter your choice-->>3

CS5312 103 Discrete Structures John
CS5235 102 Networks Jawed
 ----------------------------------------------------------------
Please enter your choice -->>1

enter the Course number 
CS6987
enter the section number
109

Addition of record Successfull

--------------->> Sample output For the wrong input

Please enter your choice -->>1

enter the Course number 
CS5359
enter the section number 
500
Please enter a valid Course Number for Spring 2007
invalid section entered

-------------------------------------------------------------------

Please enter your choice-->>2

enter the Course number
CS6987
enter the section number
109

Course Deleted Successfull
--------------------------------------------------------------------

Please enter your choice-->>2

enter the Course number
MATH5302
enter section number
304
The course is completed and not current enrolled so we cannot delete it

-------------------------------------------------------------------------------
Please enter your choice -->>4
4
End of the session

--------------------------------------------------------------------------
