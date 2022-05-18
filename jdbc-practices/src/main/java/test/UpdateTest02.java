package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class UpdateTest02 {

	public static void main(String[] args) {
		// (4L, "전략기획팀");
		DepartmentVo vo = new DepartmentVo();
		vo.setNo(4L);
		vo.setName("기획");
		
		update(vo);
	}

	private static boolean update(DepartmentVo vo) {
		boolean result = false;
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		try {
			//1. JDBC Driver 로딩 (JDBC Class 로딩: class loader)
			Class.forName("org.mariadb.jdbc.Driver");
			
			//2. 연결하기
			String url = "jdbc:mysql://192.168.10.34:3306/webdb?charset=utf8";
			connection = DriverManager.getConnection(url, "webdb", "webdb");
			
			//3. SQL 준비(Statement 생성이 아니고)
			String sql =
					"update department" + 
					"   set name='" +
				    " where no=";
			pstmt = connection.prepareStatement(sql);
			
			//4. Parameter Mapping(binding)
			
			pstmt.setString(1, vo.getName());
			pstmt.setLong(2, vo.getNo());
			
			//5. SQL 실행

			int count = pstmt.executeUpdate(); // 여기서도 역시 sql 들어가있으면 안된다! 01과 비교
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