-- 기본 SQL 문제입니다.

-- 문제1.
-- 사번이 10944인 사원의 이름은(전체 이름)

  select a.emp_no, concat(a.first_name, ' ', a.last_name)
	from employees a 
   where emp_no = 10944;

-- 문제2. 
-- 전체직원의 다음 정보를 조회하세요. 가장 선임부터 출력이 되도록 하세요. 출력은 이름, 성별,  
-- 입사일 순서이고 “이름”, “성별”, “입사일로 컬럼 이름을 대체해 보세요.

  select a.first_name as '이름' , a.gender as '성별' , a.hire_date as '입사일'
    from employees a
order by a.hire_date ;

-- 문제3.
-- 여직원과 남직원은 각 각 몇 명이나 있나요?

  select (select count(*) from employees a where a.gender = 'm' ) as '남직원수', 
         (select count(*) from employees a where a.gender = 'f' ) as '여직원수';
     
  select if(gender = 'M', '남자', '여자') as '성별', count(*) as '수'
    from employees
group by gender;

-- 문제4.
-- 현재 근무하고 있는 직원 수는 몇 명입니까? (salaries 테이블을 사용합니다.) 
  select count(*)
    from salaries a
   where a.to_date = '9999-01-01';

-- 문제5.
-- 부서는 총 몇 개가 있나요?
  select count(distinct a.dept_no)
    from dept_emp a;

-- 문제6.
-- 현재 부서 매니저는 몇 명이나 있나요?(역임 매너저는 제외)
  select count(*)
	from dept_manager a
   where a.to_date = '9999-01-01';

-- 문제7.
-- 전체 부서를 출력하려고 합니다. 순서는 부서이름이 긴 순서대로 출력해 보세요.

  select a.dept_name, length(a.dept_name)
    from departments a
order by length(a.dept_name) desc;

-- 문제8.
-- 현재 급여가 120,000이상 받는 사원은 몇 명이나 있습니까?

  select count(*)
    from salaries a, employees b
   where a.salary >= 120000
	 and a.to_date = '9999-01-01'
     and a.emp_no = b.emp_no;

-- 문제9.
-- 어떤 직책들이 있나요? 중복 없이 이름이 긴 순서대로 출력해 보세요.

  select distinct a.title, length(a.title)
    from titles a
order by length(a.title) desc;


-- 문제10
-- 현재 Enginner 직책의 사원은 총 몇 명입니까?

  select count(*)
    from titles a
   where a.title = 'Engineer';

-- 문제11
-- 사번이 13250(Zeydy)인 지원이 직책 변경 상황을 시간순으로 출력해보세요.

  select a.emp_no, a.first_name, b.title, b.from_date
	from employees a, titles b
   where a.emp_no=13250
order by b.from_date

