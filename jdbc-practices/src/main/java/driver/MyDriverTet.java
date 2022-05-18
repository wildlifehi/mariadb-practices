package driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDriverTet {

	public static void main(String[] args) {
		Connection connection = null;

		try {
			// 1. JDBC Driver(class) 로딩(JDBC CLASS 로딩 : class loader) - code level에서 jdbc class를 로딩하는 것
			Class.forName("driver.MyDriver");
			
			// 2.연결하기			
			String url = "jdbc:mydb://127.0.0.1:4404/webdb?charset=utf8";
			DriverManager.getConnection(url, "webdb", "webdb"); // url, 계정명, 비번 입력
			
			// 일단 중요한 것은 이 과정만 받아들이면 된다. 이제 connection에게 쿼리를 떄릴 수 있는 객체를 요청하게 될 것.
			// jdbc쓰는 것은 어렵지 않으나 그 개념정도는 이해해야할 것.
						
			
			System.out.println("connected!!");
			
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		} catch (SQLException e) {
			e.printStackTrace();
		}  finally {
			try {
				if(connection != null) {
					connection.close();
				}
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}

		

	}

}
