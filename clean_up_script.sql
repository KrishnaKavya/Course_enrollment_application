alter table SECTION DROP CONSTRAINT S_FK  ;

alter table GRADE_REPORT DROP CONSTRAINT GR_FK1 ;

alter table GRADE_REPORT DROP CONSTRAINT GR_FK2 ;

alter table PREREQUISITE DROP CONSTRAINT PR_FK1 ;

drop table SECTION ;

drop table GRADE_REPORT ;

drop table PREREQUISITE ;

drop table COURSE ;

drop table STUDENT ;
