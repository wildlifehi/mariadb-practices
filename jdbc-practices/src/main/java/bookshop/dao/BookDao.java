package bookshop.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bookshop.vo.BookVo;

public class BookDao {

	public void update(BookVo vo) {
	
		public boolean insert(BookVo vo) {
			boolean result = false;
			Connection connection = null;
			PreparedStatement pstmt = null;
			
			try {
				connection = getConnection();
				
				String sql = "update book values(null, ?, ?, ?)";
				pstmt = connection.prepareStatement(sql);
				pstmt.setString(1, vo.getTitle());
				pstmt.setString(2, vo.getStateCode());
				pstmt.setLong(3, vo.getAuthorNo());
				
				int count = pstmt.executeUpdate();
				result = count == 1;
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
	
	public List<BookVo> findAll() {
		List<BookVo> result = new ArrayList<>();
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			connection = getConnection();

			String sql =
				"   select a.no, a.title, b.name, a.state_code" +
				"     from book a, author b" +
				"    where a.author_no = b.no" +
				" order by no asc";
			pstmt = connection.prepareStatement(sql);			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Long no = rs.getLong(1);
				String title = rs.getString(2);
				String authorName = rs.getString(3);
				String stateCode = rs.getString(4);
				
				BookVo vo = new BookVo();
				vo.setNo(no);
				vo.setTitle(title);
				vo.setAuthorName(authorName);
				vo.setStateCode(stateCode);
				
				result.add(vo);
			}
		} catch (SQLException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		} finally {
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
	
	private Connection getConnection() throws SQLException {
		Connection connection = null;
		
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			String url = "jdbc:mysql://192.168.10.34:3306/webdb?charset=utf8";
			connection = DriverManager.getConnection(url, "webdb", "webdb");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		}
		
		return connection;
	}



}