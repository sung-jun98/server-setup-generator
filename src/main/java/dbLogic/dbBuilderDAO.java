package dbLogic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


//클라이언트의 기호대로 테이블을 create할때 필요한 서버의 기본적인 세팅 
public class dbBuilderDAO {
	private Connection conn;
	//private ResultSet rs;
	
	//로컬DB와 연결하기 위한 과정
	public dbBuilderDAO() {
		try {
			String dbURL = "jdbc:mysql://localhost:3306/jspdb";
			String dbID = "root";
			String dbPassword = "pasword^1357";
			
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
					
				}catch(Exception e){
					e.printStackTrace();
				}
			System.out.println("(dbBuilderDAO) dbBuilderDAO 성공");// 테스트용. 나중에 삭제할것
		}
	
	public void createTable(String CREATEsql) {
		try {
			Statement statement = conn.createStatement();
			statement.executeUpdate(CREATEsql);
	        System.out.println("테이블이 생성되었습니다.");
	          
	        // Statement, Connection 객체 닫기
	        statement.close();
	        conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
}
