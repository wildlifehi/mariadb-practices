-- 함수: 수학

-- abs : 절대값
select abs(-1), abs(1);

-- mod
select mod(10, 3);

-- ceil
select ceil(3.14), ceiling(3.14);

-- floar
select floor(3.14);

-- round(x) : x에 가장 근접한 정수

-- round(x, d) : x값 중에 소수점 d자리에 가장 근접한 실수
select round(1.498), round(1.498, 1), round(1.498, 0);

-- power(x, y), pow(x, y) : x^y ; 
select power(2, 10), pow(2, 10);

-- sign(x) : x의 부호를 반환
select sign(20), sign(-100), sign(0);

-- greatest(x, y, ...), least(x, y, ...)
select greatest(10, 40, 20, 50), least(10, 40, 20, 50);
select greatest('b', 'A', 'C'), greatest('hello', 'hellp', 'hellq');

