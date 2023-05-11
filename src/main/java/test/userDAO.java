package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class userDAO {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	public userDAO() { //생성자
		try {
		String dbURL = "jdbc:mysql://localhost:3306/jspdb";
		String dbID = "root";
		String dbPassword = "pasword^1357";
		
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
				
			}catch(Exception e){
				e.printStackTrace();
			}
		System.out.println("userDAO 성공");// 테스트용. 나중에 삭제할것
	}
	
	public int login(String userID, String userPassword){
		String SQL = "SELECT userPassword FROM USER WHERE userID = ?"; //overWriteData에서 DB 컬럼값을 받아와서 userPassword 및 테이블명으로 수정할 수 있도록 수정할 것
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userID);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				if(rs.getString(1).equals(userPassword)) {
					return 1; //로그인 성공
				}else {
					return 0;// 비밀번호 틀렸을 시
				}
			}
			return -1; // 아이디가 없음
		}catch(Exception e) {
			e.printStackTrace(); 
		}
		return -2; //데이터베이스 오류
	}
	
	
}
