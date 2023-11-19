package bbs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//게시물 작성 기능에 관한 정보를 담고 있는 클래스
public class bbsDAO {
	private Connection conn;
	private ResultSet rs;
	
	//DB의 속성
	private String bbsTableName = "bbs";
	private String bbsID = "bbsID";
	private String bbsTitle = "bbsTitle";
	private String userID = "userID";
	private String bbsDate = "bbsDate";
	private String bbsContent = "bbsContent";
	private String bbsAvailable = "bbsAvailable";
	
	//회원가입 관련 필요한 DB속성
	//DB의 속성
	private String signUpTableName_db = "signUp";
	private String signUpID_db = "userID";
	String signUpPW_db = "userPW";
	String signUpEmail_db = "userEmail";
	
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
	
	//dataHolder로부터 추출한 '저장할 DB정보' 오퍼레이터와 연결된 속성으로 바꾸어주는 메서드
	public void setAttr(String bbsTableName, String bbsID, String bbsTitle, String userID, String bbsDate, 
			String bbsContent, String bbsAvailable) {
		if(bbsTableName != null) {
			this.bbsTableName = bbsTableName;
		}
		if(bbsID != null) {
			this.bbsID = bbsID;
		}
		if(bbsTitle != null) {
			this.bbsTitle = bbsTitle;
		}
		if(userID != null) {
			this.userID = userID;
		}
		if(bbsDate != null) {
			this.bbsDate = bbsDate;
		}
		if(bbsContent != null) {
			this.bbsContent = bbsContent;
		}
		if(bbsAvailable != null) {
			this.bbsAvailable = bbsAvailable;
		}
	}
	
	//dataHolder로부터 추출한 '저장할 DB정보(회원가입)' 오퍼레이터와 연결된 속성으로 바꾸어주는 메서드
		public void signUp_setAttr(String signUpTableName_db, String signUpID_db, String signUpPW_db, String signUpEmail_db) {
		if(signUpTableName_db != null) {
			this.signUpTableName_db = signUpTableName_db;
		}
		if(signUpID_db != null) {
			this.signUpID_db = signUpID_db;
		}
		if(signUpPW_db != null) {
			this.signUpPW_db = signUpPW_db;
		}
		if(signUpEmail_db != null) {
			this.signUpEmail_db = signUpEmail_db;
		}
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
		//this.bbsAvailable = "attr6"; //테스트용. 나중에 demo.jsp랑 flowchart.js까지 다 수정하고 마지막에 지울것
		//String SQL = "INSERT INTO BBS VALUES(?, ?, ?, ?, ?, ?)";
		//String SQL = "INSERT INTO BBS (bbsID, bbsTitle, userID, bbsDate, bbsContent, bbsAvailable) VALUES(?, ?, ?, ?, ?, ?)";
		String SQL = "INSERT INTO " + this.bbsTableName + "(" + this.bbsID + ", " + this.bbsTitle + ", "
				+ this.userID + ", " + this.bbsDate + ", " + this.bbsContent + ", "
				+ this.bbsAvailable + ") VALUES(?, ?, ?, ?, ?, ?)"; 
		System.out.println("(bbsDAO.write()) 완성된 SQL문은 " + SQL);
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
	
	//게시물 삭제
	public int delete(String bbsTitle) {
		//DELETE FROM BBS WHERE bbsID = 3;
		String SQL = "DELETE FROM " + this.bbsTableName + " WHERE " + this.bbsTitle + " = ?";
		System.out.println("(bbsDAO.write()) 완성된 SQL문은 " + SQL);
		// JDBC 연결 및 PreparedStatement 설정
        try {
        	PreparedStatement pstmt = conn.prepareStatement(SQL);
            // 바인딩 변수 설정
        	pstmt.setString(1, bbsTitle);
        	
            // DELETE 문 실행
            return pstmt.executeUpdate(); //실행결과(리턴값) 반환
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1; //데이터베이스 오류
		
	}
	
	//회원가입
	public int signUp(String signUpID_db, String signUpPW_db, String signUpEmail_db) {
		String SQL = "INSERT INTO " + this.signUpTableName_db + "(" + this.signUpID_db + ", " + this.signUpPW_db + ", "
				+ this.signUpEmail_db + ") VALUES(?, ?, ?)"; 
		System.out.println("(bbsDAO.write()) 완성된 SQL문은 " + SQL);
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, signUpID_db);
			pstmt.setString(2, signUpPW_db);
			pstmt.setString(3, signUpEmail_db);

			return pstmt.executeUpdate(); //실행 결과(정수값) 리턴
		} catch(Exception e) {
			e.printStackTrace();
		}finally {
			//closeResources(conn, pstmt, null);
		}
		return -1; //데이터베이스 오류
		
	}
	
	//메모리 누수 방지를 위해 DB관련 작업이 끝났다면 관련 객체들을 종료해준다.
		private void closeResources(Connection conn, PreparedStatement stmt, ResultSet rs) {
		    if (rs != null) {
		        try {
		            rs.close();
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		    }
		    
		    if (stmt != null) {
		        try {
		            stmt.close();
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		    }
		    
		    if (conn != null) {
		        try {
		            conn.close();
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		    }
		}
	
}











