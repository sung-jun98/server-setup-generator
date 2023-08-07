package bbs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

//게시물 작성 기능에 관한 정보를 담고 있는 클래스
public class bbsDAO {
	private Connection conn;
	private ResultSet rs;
	
	public bbsDAO() { //생성자
		try {
		String dbURL = "jdbc:mysql://localhost:3306/jspdb";
		String dbID = "root";
		String dbPassword = "pasword^1357";
		
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
				
			}catch(Exception e){
				e.printStackTrace();
			}
		System.out.println("(bbsDAO) bbsDAO 성공");// 테스트용. 나중에 삭제할것
	}
	
	//작성된 시각을 알기 위한 메서드
	public String getDate() {
		String SQL = "SELECT NOW()";
		try {
			PreparedStatement psmt = conn.prepareStatement(SQL);
			rs = psmt.executeQuery();
			if(rs.next()) {
				return rs.getString(1);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "";//데이터베이스 오류
	}
	
	//현재 업로드 하는 게시물이 몇번째 게시물인지 얻어온다.
	public int getNext() {
		String SQL = "SELECT bbsID FROM BBS ORDER BY bbsID DESC";
		try {
			PreparedStatement psmt = conn.prepareStatement(SQL);
			rs = psmt.executeQuery();
			if(rs.next()) {
				return rs.getInt(1) + 1;
			}
			return 1; //첫번째 게시물인 경우
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return -1; //데이터베이스 오류
	}
	
	//작성한 게시물 저장
	public int write(String bbsTitle, String userID, String bbsContent) {
		String SQL = "INSERT INTO BBS VALUES(?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, getNext());
			pstmt.setString(2, bbsTitle);
			pstmt.setString(3, userID);
			pstmt.setString(4, getDate());
			pstmt.setString(5, bbsContent);
			pstmt.setInt(6, 1); 
			
			
			return pstmt.executeUpdate(); //실행 결과(정수값) 리턴
		} catch(Exception e) {
			e.printStackTrace();
		}
		return -1; //데이터베이스 오류
	}
	
	
}











