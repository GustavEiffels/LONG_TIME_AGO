show databases;

use cyberadam;

create table usertbl(
userid char(15) not null primary key,
name varchar(20) not null,
birthyear int not null, 
addr char(100),
mobile char(11),
mdate date);

insert into usertbl values('ailee', '에일리',1989,'미국','01000000000', '1989-5-30');

commit;

select * from usertbl;

DELIMITER //
 CREATE PROCEDURE myproc(IN _userid CHAR(15),
  IN _name VARCHAR(20), _birthyear int, _addr CHAR(10), _mobile char(11), _mdate date)
   BEGIN
   insert into usertbl values(_userid, _name, _birthyear, _addr, _mobile, _mdate);
   END //
DELIMITER ;

select * from usertbl;


create table blobtable(
	userid char(15) not null primary key,
	filename varchar(1000),
	filecontent longblob);

select * from blobtable;


CREATE TABLE dbms (
    num  int(10) NOT NULL AUTO_INCREMENT,
    name  varchar(255) NOT NULL,
    vendor varchar(255) NOT NULL,
    description varchar(255),
    PRIMARY KEY(num)
);

insert into dbms(name, vendor, description) values('Oracle','Oracle', '가장 안정적이라고 알려진 DB');
insert into dbms(name, vendor, description) values('HanaDB','SAP', '오라클 대체용으로 많이 사용');
insert into dbms(name, vendor, description) values('Tibero','티맥스소프트', '국산 관계형 데이터베이스');
insert into dbms(name, vendor, description) values('Maria DB','오픈소스', 'MySQL 대체 데이터베이스');

