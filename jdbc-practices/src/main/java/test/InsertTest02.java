package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class InsertTest02 {
	
	public static void main(String[] args) {
		insert("cs");
		insert("경영지원팀");
		insert("인프라");
	}
	
	public static boolean insert(String name) {
		boolean result = false;
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		try {
			//1. JDBC Driver 로딩 (JDBC Class 로딩: class loader)
			Class.forName("org.mariadb.jdbc.Driver");
			
			//2. 연결하기
			String url = "jdbc:mysql://192.168.10.34:3306/webdb?charset=utf8";
			connection = DriverManager.getConnection(url, "webdb", "webdb");
			
			//3. SQL 준비(Statement 생성이 아니다)
			String sql = "insert into department values(null, ?)";
			pstmt = connection.prepareStatement(sql);
			
			// mapping(binding) 작업.
			pstmt.setString(1, name);
			
			
			//4. SQL 실행
			int count = pstmt.executeUpdate(); // preparedstatement는 여기에 sql넣어선 안된다.
			result = count == 1;
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

}

// 여기서 DB에 접속해 sql 데이터를 넣을 수 있다.
// 자료를 넣은 뒤에 워크벤치에서 select * from department;를 수행해보자.