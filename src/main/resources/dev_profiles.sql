create database dev_profiles_db;

create table accounts
(
	id int not null,
	first_name varchar(45) not null,
	last_name varchar(45) not null,
	city varchar(45) not null,
	gender varchar(10) not null,
	username varchar(90) not null,
	constraint user_pk primary key (id)
);

create table profiles_table
(
    id int not null primary key,
    username varchar(90)  not null,
    job_title varchar(45)  not null,
    department varchar(45)  not null,
    company varchar(45)  not null,
    skill text not null
);

rename table  profiles_table to profiles;

    -- 1 task
select * from profiles
where department='Sales'
order by skill;

    -- 2 task
select job_title from profiles
group by job_title
having count(*) > 3;

    -- 3 task
select accounts.first_name,
       accounts.last_name,
       profiles.job_title,
       profiles.company
from accounts
         left join profiles
                   on accounts.id = profiles.id;

    -- 4 task
select department, count(username) as employees
from profiles
group by department
order by employees DESC;

    -- 5 task
select profiles.job_title, accounts.city
from profiles
         left join accounts
                   on profiles.id = accounts.id
where job_title = 'Editor'

    -- 6 task
select * from accounts
order by city;


    -- 7 task
update profiles
set job_title = REPLACE(job_title, 'Engineer', 'Developer')
where job_title like '%Engineer%';
