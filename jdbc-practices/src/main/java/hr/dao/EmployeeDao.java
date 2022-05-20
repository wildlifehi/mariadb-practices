package hr.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import hr.vo.EmployeeVo;
import test.DepartmentVo;

public class EmployeeDao {
	

	public List<EmployeeVo> findByFirstNameOrLastName(String name) {
		
		List<EmployeeVo> result = new ArrayList<>();
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			//1. JDBC Driver 로딩 (JDBC Class 로딩: class loader)
			Class.forName("org.mariadb.jdbc.Driver");
			
			//2. 연결하기
			String url = "jdbc:mysql://192.168.10.34:3306/employees?charset=utf8";
			connection = DriverManager.getConnection(url, "hr", "hr");
			
			//3. SQL준비(Statement 생성 이 아니다)
			String sql =
					" select emp_no, first_name, last_name, date_format(hire_date, '%Y-%m-%d')" +
					"	from employees " +
					"   where first_name like ?" +
					"      or last_name like ?" +
					"order by hire_date" ;
			pstmt = connection.prepareStatement(sql);
			
			//4. Parameter Mapping
			// 여기도 위에 물음표 (?) 없으면 매핑할 게 없다.
			pstmt.setString(1,"%" + name + "%");
			pstmt.setString(2,"%" + name + "%");
		
			
			//5. SQL 실행
			rs = pstmt.executeQuery();
			
			//6. 결과처리
			while(rs.next()) {
				Long no = rs.getLong(1); // DB는 1부터 시작하므로 1로 받아야 한다.
				String firstName = rs.getString(2);
				String lastName = rs.getString(3);
				String hireDate = rs.getString(4);
				
				EmployeeVo vo = new EmployeeVo();
				vo.setNo(no);
				vo.setFirstName(firstName);
				vo.setLastName(lastName);
				vo.setHireDate(hireDate);

				result.add(vo);
			}
			
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		} catch (SQLException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	
	}

	public List<EmployeeVo> findBySalary(int minSalary, int maxSalary) {
		List<EmployeeVo> result = new ArrayList<>();
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			//1. JDBC Driver 로딩 (JDBC Class 로딩: class loader)
			Class.forName("org.mariadb.jdbc.Driver");
			
			//2. 연결하기
			String url = "jdbc:mysql://192.168.10.55:3306/employees?charset=utf8";
			connection = DriverManager.getConnection(url, "hr", "hr");

			
			//3. SQL 준비
			String sql = 
					"   select a.emp_no," +
					"          a.first_name," +
					"          a.last_name," +
					"          b.salary" +
					"     from employees a, salaries b" +
					"    where a.emp_no = b.emp_no" +
					"      and b.to_date = '9999-01-01'" +
					"      and b.salary >= ?" +
					"      and b.salary <= ?" +
					" order by b.salary desc";
			pstmt = connection.prepareStatement(sql);
			
			//4. binding
			pstmt.setInt(1, minSalary);
			pstmt.setInt(2, maxSalary);
			
			//5. SQL 실행
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Long no = rs.getLong(1);
				String firstName = rs.getString(2);
				String lastName = rs.getString(3);
				int salary = rs.getInt(4);
				
				EmployeeVo vo = new EmployeeVo();
				vo.setNo(no);
				vo.setFirstName(firstName);
				vo.setLastName(lastName);
				vo.setSalary(salary);
				
				result.add(vo);
			}
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			// clean up
			try {
				if(rs != null) {
					rs.close();
				}				
				if(pstmt != null) {
					pstmt.close();
				}
				if(connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;		
	}
}
