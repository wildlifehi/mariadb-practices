-- subquery /

-- 1) select 절

-- 2) from 절의 서브 쿼리

select a.*
  from (select now() as a, sysdate() as b, 3+1 as c) a;

-- 3) where 절 의 서브쿼리
-- 예제, Fai Bale이 근무하는 부서에서 근무하는 직원의 사번, 전체 이름을 출력해보세요

select dept_no
  from dept_emp a, employees b
  where a.emp_no = b.emp_no
    and a.to_date = '9999-01-01'
	and concat(b.first_name, ' ', b.last_name) = 'Fai Bale';
    
-- 위 내용에 대한 solution

 select b.dept_no, b.first_name
   from dept_emp a, employees b, departments c
  where a.emp_no = b.emp_no
    and a.to_date = '9999-01-01'
    and dept_no = ( select a.dept_no
                      from dept_emp a, employees b
					 where a.emp_no = b.emp_no
					   and a.to_date = '9999-01-01'
					   and concat(b.first_name, ' ', b.last_name) = 'Fai Bale') ;
                       
-- 3-1) 단일행 연산자: =, >, <, >=, <=, <>, !=
-- 실습문제 1: 현재 전체사원의 평균 연봉보다 적은 급여를 받는 사원의 이름, 급여를 타나내시오.

select b.first_name, a.salary
  from salaries a, employees b
where a.emp_no = b.emp_no
  and a.to_date = '9999-01-01'
  and a.salary < (select avg(salary) from salaries where to_date='9999-01-01')
  order by a.salary desc;
  
-- 실습문제 2: 현재 가장 적은 평균 급여의 직책과 그 평균급여 - from절에서 만들기.
-- 1) 현재 가장 적은 직책의 평균 급여  
-- 1-1) 직책별 평균 급여
 select a.title, avg(salary)
 from titles a, salaries b
 where a.emp_no = b.emp_no
  and a.to_date = '9999-01-01'
  and b.to_date = '9999-01-01'
group by a.title;

-- 1-2) 이 중에서 가장 적은 급여를 골라야한다.
select min(c.avg_salary)
  from (select a.title, avg(salary) as avg_salary
			from titles a, salaries b
			where a.emp_no = b.emp_no
			and a.to_date = '9999-01-01'
			and b.to_date = '9999-01-01'
			group by a.title ) c ;
 
-- 2-1 ) sol1 : subquery
select a.title, avg(salary) as avg_salary
		from titles a, salaries b
		where a.emp_no = b.emp_no
		and a.to_date = '9999-01-01'
		and b.to_date = '9999-01-01'
		group by a.title 
        having avg_salary =  (select min(c.avg_salary)
								from (select a.title, avg(salary) as avg_salary
										from titles a, salaries b
										where a.emp_no = b.emp_no
										and a.to_date = '9999-01-01'
										and b.to_date = '9999-01-01'
										group by a.title ) c );
-- 2-1) sol2 : top-k 방법

 select a.title, avg(salary) as avg_salary
			from titles a, salaries b
			where a.emp_no = b.emp_no
			and a.to_date = '9999-01-01'
			and b.to_date = '9999-01-01'
			group by a.title 
            order by avg(salary) asc
            limit 0,1;

-- 3-2) 복수행 연산자: in, not in, any, all
-- any 사용방법
-- 1. =any : in 과 동일
-- 2. >=any, >=any : 최소값
-- 3. <=any, <=any :최대값
-- 4. <>any : not in과 동일   


-- all 사용방법
-- 1. =all : x
-- 2. >all, >=all : 최대값
-- 3. <=all, <=all :최소값
-- 4. <>all



-- 실습문제3 : 현재 급여가 50000 이상인 직원의 이름을 출력하세요. (급여가 큰 순서대로)

-- sol 1) 일반 조인으로 해결
select a.first_name, b.salary
from employees a, salaries b
where a.emp_no = b.emp_no
and b.to_date = '9999-01-01'
and b.salary > 50000
order by b.salary ;

-- sol 2)
select a.first_name, b.salary
from employees a, salaries b
where a.emp_no = b.emp_no
and b.to_date = '9999-01-01'
and (a.emp_no, b.salary) in (select emp_no, salary
								from salaries
								where to_date = '9999-01-01'
								 and salary >= 50000)
order by b.salary asc;

select emp_no, salary
from salaries
where to_date = '9999-01-01'
 and salary >= 50000;

-- 실습문제4 : 현재, 각 부서별로 최고 월급을 받는 직원의 이름과 급여를 출력하세요.

-- 핵심 서브쿼리
select a.dept_no, max(b.salary)
	from dept_emp a, salaries b
	where a.emp_no = b.emp_no
	and a.to_date = '9999-01-01'
	and b.to_date = '9999-01-01'
group by a.dept_no;

-- sol 1) where subquery : in(=any)
select d.dept_name, b.first_name, c.salary 
from dept_emp a, employees b, salaries c, departments d
where a.emp_no = b.emp_no
  and b.emp_no = c.emp_no
  and d.dept_no = a.dept_no
  and a.to_date = '9999-01-01'
  and c.to_date = '9999-01-01'
  and (a.dept_no, c.salary) in (select a.dept_no, max(b.salary)
								from dept_emp a, salaries b
								where a.emp_no = b.emp_no
								and a.to_date = '9999-01-01'
								and b.to_date = '9999-01-01'
								group by a.dept_no );
                                
-- sol 2 ) from 절 subquery로 해결

select d.dept_name, b.first_name, c.salary 
from dept_emp a,
	employees b, 
	 salaries c,  
  departments d,
    (select a.dept_no, max(b.salary) as max_salary
		from dept_emp a, salaries b
		where a.emp_no = b.emp_no
		and a.to_date = '9999-01-01'
		and b.to_date = '9999-01-01'
		group by a.dept_no ) e
where a.emp_no = b.emp_no
  and b.emp_no = c.emp_no
  and d.dept_no = a.dept_no
  and a.dept_no = e.dept_no
  and a.to_date = '9999-01-01'
  and c.to_date = '9999-01-01'
  and c.salary = e.max_salary;