package dbLogic;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.jsoup.nodes.Document;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectInput;


//processHTML에서 클라이언트로부터 입력받은 jsp, html파일을 이진화후 DB에 저장하기 위한 DAO객체
public class documentDAO {
	
	private ResultSet rs;
	private Connection conn;
	private PreparedStatement pstmt;
	
	public documentDAO() { //생성자
		try {
			
			
			String dbURL = "jdbc:mysql://localhost:3306/jspdb";
			String dbID = "root";
			String dbPassword = "pasword^1357";
			
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
					
				}catch(Exception e){
					e.printStackTrace();
				}
			System.out.println("(documentDAO.java) documentDAO 성공");// 테스트용. 나중에 삭제할것
	}
	
	//===============================================
	//============processHTML관련 메서드===============
	
	//클라이언트로부터 입력받은 document를 DB에 저장한다.(겹치는게 있다면 덮어씌운다)
	public void saveDocument(Document doc, String filename) {
	
        try {
            //conn = getConnection();
            String sql = "INSERT INTO documents (fileName, content) VALUES (?, ?) ON DUPLICATE KEY UPDATE content = ?";
            pstmt = conn.prepareStatement(sql);

            // Document 객체를 직렬화하고 바이트 배열로 변환
            byte[] serializedDoc = serializeDocument(doc);

            // PreparedStatement에 바이트 배열을 설정
            pstmt.setString(1, filename);
            pstmt.setBytes(2, serializedDoc);
            pstmt.setBytes(3, serializedDoc);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // 예외 처리
        } finally {
            // 연결 및 리소스 해제
            closeResources(conn, pstmt, null);
        }
	}
	
	//클라이언트로부터 입력받은 document를 DB에 저장한다. 
	//단, 이미 해당 파일이 DB에 존재할 시에는 저장하지 않고 그냥 넘어간다.(덮어 씌우지 않는다.)
		public void saveDocIfNotExist(Document doc, String filename) {
		
	        try {
	            //conn = getConnection();
	            String sql = "INSERT IGNORE INTO documents (fileName, content) VALUES (?, ?)";
	            pstmt = conn.prepareStatement(sql);

	            // Document 객체를 직렬화하고 바이트 배열로 변환
	            byte[] serializedDoc = serializeDocument(doc);

	            // PreparedStatement에 바이트 배열을 설정
	            pstmt.setString(1, filename);
	            pstmt.setBytes(2, serializedDoc);
	            pstmt.executeUpdate();
	        } catch (SQLException e) {
	            e.printStackTrace();
	            // 예외 처리
	        } finally {
	            // 연결 및 리소스 해제
	            closeResources(conn, pstmt, null);
	        }
		}
	
	//클라이언트로부터 입력받은 파일을 DB에 넣기 전에 String형태로 바꾸고 다시 Byte형태로 이진화를 한다. 
	private byte[] serializeDocument(Document doc) {
		String htmlContent = doc.outerHtml();
		return htmlContent.getBytes(StandardCharsets.UTF_8);
		/*
			 * ByteArrayOutputStream bos = new ByteArrayOutputStream(); ObjectOutputStream
			 * out = new ObjectOutputStream(bos); out.writeObject(doc); out.close(); return
			 * bos.toByteArray();
			 */
	}
	

    // 바이트 배열을 Document 객체로 역직렬화하는 메서드
	//test_login_apply, writeAction과 연결된 "deploy/*" redirectServlet서블릿에서 사용할것
	/*
	 * public Document deserializeDocument(byte[] serializedDoc) { try {
	 * ByteArrayInputStream bis = new ByteArrayInputStream(serializedDoc);
	 * ObjectInput in = new ObjectInputStream(bis); return (Document)
	 * in.readObject(); //이부분 HTML 파일로 고쳐야할듯 } catch (IOException |
	 * ClassNotFoundException e) { e.printStackTrace(); // 예외 처리 return null; } }
	 */
    
    //DB에서 클라이언트로부터 입력받았던 document를 불러오기하는 메서드
    public String readDocument(String requestedPage) {
    	// SQL 쿼리를 작성하여 DB에서 페이지 내용 가져오기
    	try {
	    	String content = null;
	        String query = "SELECT content FROM documents WHERE fileName = ?";
	        pstmt = conn.prepareStatement(query);
	        pstmt.setString(1, requestedPage);
	        rs = pstmt.executeQuery();
	
	        if (rs.next()) {
	            content = new String(rs.getBytes("content"), StandardCharsets.UTF_8);
	        }
	        return content;
    	} catch(SQLException e) {
            // 예외 처리
            e.printStackTrace();
            return null;
        }finally {
        	closeResources(conn, pstmt, rs);
        }
    }
    //===========processHTML관련 메서드 끝==============
    //==============================================
    
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
