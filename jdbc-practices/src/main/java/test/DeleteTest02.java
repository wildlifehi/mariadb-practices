package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DeleteTest02 {

	public static void main(String[] args) {
		delete(1L);
//		delete(20L); // 해당 라인을 지운다.
//		delete(14L);
//		delete();
		
		
	}

	private static void delete() {
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		try {
			//1. JDBC Driver 로딩 (JDBC Class 로딩: class loader)
			Class.forName("org.mariadb.jdbc.Driver");
			
			//2. 연결하기
			String url = "jdbc:mysql://192.168.10.34:3306/webdb?charset=utf8";
			connection = DriverManager.getConnection(url, "webdb", "webdb");
			
			//3. SQL준비(Statement 생성이 아니다)
			String sql = "delete from department";
			pstmt = connection.prepareStatement(sql);
			
			//4. Parameter Mapping 인데 여기서는 할게 없다.
			//파라미터 없어도 PreparedStatement 사용가능하다 이것만 기억
			
			//5. SQL 실행
			pstmt.executeUpdate(); // sql 들어가있으면 혼난다!
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
	}
	
	private static boolean delete(Long no) {
		boolean result = false;
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		try {
			//1. JDBC Driver 로딩 (JDBC Class 로딩: class loader)
			Class.forName("org.mariadb.jdbc.Driver");
			
			//2. 연결하기
			String url = "jdbc:mysql://192.168.10.34:3306/webdb?charset=utf8";
			connection = DriverManager.getConnection(url, "webdb", "webdb");
			
			//3. SQL 준비 (Statement 생성 x)
			String sql = "delete from department where no =?"; 
			pstmt = connection.prepareStatement(sql);
			
			//4. Parameter Mapping
			pstmt.setLong(1, no); // no는 파라미터 번호
			
			//4. SQL 실행
			int count = pstmt.executeUpdate();
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

//여기서 DB에 접속해 sql 데이터를 뺄 수 있다.