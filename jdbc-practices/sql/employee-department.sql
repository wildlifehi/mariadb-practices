

select * from department;

insert into department values(null, '솔류션');
delete from department where no in (1,3,16,17,18,4);

alter table department auto_increment=0;



-- findAll
  select no 
    from department 
order by no desc;

-- EmployeeDao:findAll
  select no, name, department_no
    from department
order by no desc;


-- EmployeeDao:delete()
delete from employee;

-- DepartmentDao:update




