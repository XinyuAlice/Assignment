 CREATE TABLE `xinyu`.`Department`(
  `Dname` VARCHAR(45) ,
  `Dcode` INT PRIMARY KEY,
  `Dlocation` VARCHAR(45)
  );
Insert into Department
(Dname,Dcode,Dlocation)
values
('Computer Science','20','North Street'),
('English','30','East Street'),
('Engineering','02','East-west Street'),
('Physics','10','East-north Street'),
('Chemistry','04','East-south Street'),
('Stats','05','West-south Street'),
('Math','06','North-south Street'),
('Science','08','West-north Street'),
('Psychology','01','North-west Street'),
('Commerce','03','West Street');

CREATE TABLE `xinyu`.`Student`(
  `Ssn` INT PRIMARY KEY,
  `Bnum` INT ,
  `Lame` VARCHAR(45) ,
  `Fname` VARCHAR(45) ,
  `Major_code`INT NULL,
  `Minor_code` INT NULL,
  FOREIGN KEY (Major_code) REFERENCES Department(Dcode),
  FOREIGN KEY (Minor_code) REFERENCES Department(Dcode)
  );

Insert into Student values('10025','472564','Bro','James','20','03');
Insert into Student values('12145','565814','Wong','Frank','03','06');
Insert into Student values('18225','561214','Jimmy','Pam','03','02');
Insert into Student values('12125','561814','Qiu','Alia','30','02');
Insert into Student values('12325','561234','Liu','Alice','01','04');
Insert into Student values('11825','565534','Lin','Peter','03','01');
Insert into Student values('10325','521234','Yang','Nory','02','03');
Insert into Student values('11345','568234','Kim','Fiona','10','01');
Insert into Student values('12653','582461','Jin','Joyce','06','10');
Insert into Student values('11010','761761','Kiki','Ann','01','02');

SELECT * FROM xinyu.Department;
SELECT*FROM xinyu.Student;
CREATE TABLE `xinyu`.`Course` (
  `Cnum` INT PRIMARY KEY,
  `Csec` INT ,
  `Cname` VARCHAR(45) ,
  `Cdept` VARCHAR(45) 
   );
Insert into Course
(Cnum,Csec,Cname,Cdept)
values 
('27','01','Software development','Computer Science'),
('28','02','Database','Computer Science'),
('35','01','English culture','English'),
('34','01','English culture','English'),
('015','01','Introduction to Psychology','Psychology'),
('016','01','Introduction to Psychology2','Psychology'),
('050','01','Physics around us','Physics'),
('060','01','Caculus','Math'),
('061','01','Caculus2','Math'),
('039','01','Micro economics','Commerce');
CREATE TABLE `xinyu`.`Grade` (
  `Grade` INT ,
  `GSsn` INT ,
  `GCnum` VARCHAR(45) ,
  PRIMARY KEY(`GSsn`,`GCnum`)
  );
  
Insert into Grade
(Grade,GSsn,GCnum)
values 
('92','12653','060'),
('90','12653','061'),
('94','12145','060'),
('96','12145','061'),
('72','12325','50'),
('72','12325','016'),
('84','11345','15'),
('90','12653','15'),
('81','12125','35'),
('90','10010','35');

insert into Grade values
('75', '11825','039');

delete from Student
where Ssn='18225';
update Student
set Major_code='08'
where Ssn='10325';

select Student.Lame,Student.Fname
from Student
where Student.Ssn='10010';

select Cname 
from Course
where Cnum='27';

select GSsn, GCnum
from Grade
where Grade='72';

select Course.Cname
from Course
inner join Grade on Course.Cnum=Grade.GCnum;


select Student.Bnum,Student.Fname
from Student, Department
where Student.Major_code=Department.Dcode and Student.Minor_code=Department.Dcode and Student.Lame='K%'
group by Student.Ssn;

create view Student_grade
as select Student.Fname,Student.Ssn,Grade.Grade
from Student,Grade
where Student.Ssn=Grade.GSsn and  Grade.Grade>='90';

insert into Grade values('-48','11345','16');

DELIMITER $$
CREATE TRIGGER before_Checkgrade_Insert before insert on Grade for each row 
begin
if NEW.Grade < 0 THEN SET NEW.Grade=0;
end if;
END$$
insert into Grade values('-49','10325','61');

DELIMITER //
CREATE PROCEDURE getAllStudents()
BEGIN 
SELECT * FROM Student;
END//
DELIMITER ;



