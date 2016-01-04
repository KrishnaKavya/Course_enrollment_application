create table STUDENT (
Name varchar(10) NOT NULL, 
Student_number int , 
class int NOT NULL, 
Major varchar(10) NOT NULL,
PRIMARY KEY (Student_number) 
);


create table COURSE(
Course_name varchar(30) NOT NULL,
Course_number varchar(10),
Credit_hours int  NOT NULL,
Department varchar(10) NOT NULL,
PRIMARY KEY (Course_number),
Unique(Course_name) 
);

create table SECTION(

Section_identifier int  ,
Course_number varchar(10)  NOT NULL,
Semester varchar(10) NOT NULL,
Year int NOT NULL,
Instructor varchar(10)  NOT NULL,

PRIMARY KEY (Section_identifier)

);

create table GRADE_REPORT(

Student_number int,
Section_identifier int, 
Grade	varchar(20) ,

PRIMARY KEY (Student_number, Section_identifier)


);

create table PREREQUISITE(

Course_number varchar(10)  NOT NULL,
Prerequisite_number varchar(10) NOT NULL,

PRIMARY KEY(Course_number, Prerequisite_number)
);



alter table  SECTION ADD  CONSTRAINT S_FK FOREIGN KEY (Course_number) REFERENCES COURSE(Course_number);

alter table  GRADE_REPORT ADD  CONSTRAINT GR_FK1 FOREIGN KEY (Student_number) REFERENCES STUDENT(Student_number);

alter table  GRADE_REPORT ADD  CONSTRAINT GR_FK2 FOREIGN KEY (Section_identifier) REFERENCES SECTION(Section_identifier);

alter table  PREREQUISITE ADD  CONSTRAINT PR_FK1 FOREIGN KEY (Course_number) REFERENCES Course(Course_number);
