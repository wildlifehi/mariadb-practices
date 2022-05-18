-- inner join

-- 예제1: 현재 근무하고 있는 직원의 이름과 직책을 출력하세요.
select a.emp_no, a.first_name, b.title
  from employees a, titles b
 where a.emp_no = b.emp_no        -- join 조건(n-1)
   and b.to_date = '9999-01-01';  -- row 선택 조건
   
-- 예제2: 현재 직원의 이름과 직책을 출력하되 여성 엔지니어만 출력하시오.

select a.emp_no, a.first_name, a.gender ,b.title
  from employees a, titles b
 where a.emp_no = b.emp_no       -- join 조건(n-1)
   and b.to_date = '9999-01-01'  -- row 선택 조건1
   and a.gender = 'F'            -- row 선택 조건2
   and b.title like 'Engineer'; -- row 선택 조건3
   
-- ANSI/ISO SQL1999 JOIN 표준문법

-- 1) natural join
-- 두 테이블에 이름이 같은 공통 컬럼이 있으면 조인 조건을 명시하지 않아도 암묵적으로 조인이 된다
select a.emp_no, a.first_name, b.title
  from employees a natural join titles b
 where b.to_date = '9999-01-01';  -- row 선택 조건

-- 2) join ~ using
-- natural join의 문제점
select count(*)
  from salaries a natural join titles b
  where a.to_date = '9999-01-01'
    and b.to_date = '9999-01-01';

select count(*)
  from salaries a join titles b using (emp_no)
  where a.to_date = '9999-01-01'
    and b.to_date = '9999-01-01';
    
select count(*)
  from salaries a join titles b on a.emp_no = b.emp_no
  where a.to_date = '9999-01-01'
    and b.to_date = '9999-01-01';
    
-- 3) join ~ on
select b.title, avg(salary)
  from salaries a join titles b on a.emp_no = b.emp_no
 where a.to_date = '9999-01-01'
   and b.to_date = '9999-01-01'
group by b.title
order by avg(a.salary) desc;

-- 실습문제 1 : 현재 회사 상황을 반영한 직원별 근무부서를 사번, 직원 전체이름, 근무부사 형태로 출력해보세요.
select a.emp_no, a.first_name, b.dept_name
  from employees a, departments b, dept_emp c
 where a.emp_no = c.emp_no
   and b.dept_no = c.dept_no
   and c.to_date = '9999-01-01';
   
-- 실습문제 2 : 현재 회사에서 지급되고 있는 급여체계를 반영한 결과를 출력하시오. 사번, 전체이름, 연봉 이런 형태로 출력
   select a.emp_no, a.first_name, b.salary
     from employees a, salaries b
    where a.emp_no = b.emp_no
	  and b.to_date = '9999-01-01'
	order by b.salary desc;
    
-- 실습문제 3 : 현재 직책별로 평균 연봉과 인원수를 구하되 직책별로 인원이 100명 이상인 직책만 출력하시오.
  select avg(b.salary), count(*)
	from titles a, salaries b 
   where a.emp_no = b.emp_no
	 and a.to_date = '9999-01-01'
	 and b.to_date = '9999-01-01' -- 여기까지 통계를 내기위한 테이블을 뽑아낸 것.
group by a.title
  having count(*) >= 100 -- 분명히 말하지만 여기서는 새로 카운트하는게 아닌 컬럼을 지칭하는것이다.
order by avg(b.salary) desc;