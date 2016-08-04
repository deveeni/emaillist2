package kr.ac.sungkyul.emaillist.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import kr.ac.sungkyul.emaillist.vo.EmailListVo;

public class EmailListDao {

	//connection 코드 
	private Connection getConnection() throws SQLException {
		Connection conn = null;
		try{
		// 1.드라이버 로딩
		Class.forName("oracle.jdbc.driver.OracleDriver");

		// 2. 연결 받아오기
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		conn = DriverManager.getConnection(url, "webdb", "webdb");
	
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		
		}
		return conn;
	}
	
	
	// getList
	public List<EmailListVo> getList() {
		List<EmailListVo> list = new ArrayList<EmailListVo>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			
			conn = getConnection();
			
			// 3. statement준비
			stmt = conn.createStatement();

			// 4. sql준비및 실행
			String sql = "select no, first_name, last_name, email,to_char(reg_date,'yyyy-mm-dd') "
					+ " from EMAILLIST"
					+ " order by reg_date desc";

			rs = stmt.executeQuery(sql);

			// 5. 결과처리
			while( rs.next() ){
				Long no = rs.getLong(1);
				String firstName = rs.getString(2);
				String lastName = rs.getString(3);
				String email = rs.getString(4);
				String regDate = rs.getString(5);
				
				EmailListVo vo = new EmailListVo();
				vo.setNo(no);
				vo.setFirstName(firstName);
				vo.setLastName(lastName);
				vo.setEmail(email);
				vo.setRegDate(regDate);
				
				list.add(vo);
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return list;

	}
	
	
	
	//insert
	public boolean insert( EmailListVo vo ){
		Connection conn = null;
		PreparedStatement pstmt = null;
		int count = 0;
		
		try {
			conn = getConnection();			
	
			// 3. sql준비및 실행
			String sql = "insert into emaillist"
					+ " values(seq_emaillist.nextval,?,?,?,sysdate) ";

			//4. pstmt 준비
			pstmt = conn.prepareStatement(sql);
			
			//5.  바인딩
			pstmt.setString(1, vo.getLastName());
			pstmt.setString(2, vo.getFirstName());
			pstmt.setString(3, vo.getEmail());

			//6. sql실행
			count = pstmt.executeUpdate();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
				return false;

			}
		}

		
		return  (count  == 1);
	}

}
