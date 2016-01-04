
alter table  SECTION ADD  CONSTRAINT S_FK FOREIGN KEY (Course_number) REFERENCES COURSE(Course_number);

alter table  GRADE_REPORT ADD  CONSTRAINT GR_FK1 FOREIGN KEY (Student_number) REFERENCES STUDENT(Student_number);

alter table  GRADE_REPORT ADD  CONSTRAINT GR_FK2 FOREIGN KEY (Section_identifier) REFERENCES SECTION(Section_identifier);

alter table  PREREQUISITE ADD  CONSTRAINT PR_FK1 FOREIGN KEY (Course_number) REFERENCES Course(Course_number);