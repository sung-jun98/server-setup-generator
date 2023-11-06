package dbLogic;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.jsoup.nodes.Document;


import server_setup_generator.dataHolder;
//import server_setup_generator.staticWebReturn;

public class dataHolderDAO {
	private ResultSet rs;
	private Connection conn;
	private PreparedStatement pstmt;
	
	//생성자 : 필요한 정보 세팅
	public dataHolderDAO() {
		try {
			String dbURL = "jdbc:mysql://localhost:3306/jspdb";
			String dbID = "root";
			String dbPassword = "pasword^1357";
			
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
					
			}catch(Exception e){
					e.printStackTrace();
			}
			System.out.println("(dataHolderDAO.java) dataHolderDAO 성공");// 테스트용. 나중에 삭제할것
	}

	//staticWebReturn에서 완성한 dataHolder를 이진화후 DB에 저장한다.
		public void saveHolder(dataHolder dh, String sessionID) {
		
	        try {
	            //conn = getConnection();
	        	//holderName에는 sessionID가, content에는 dh를 이진화한 데이터가 들어간다. 
	            String sql = "INSERT INTO dataHolders (holderName, content) VALUES (?, ?) ON DUPLICATE KEY UPDATE content = ?";
	            pstmt = conn.prepareStatement(sql);

	            // dataHolder 객체를 직렬화하고 바이트 배열로 변환
	            byte[] serializedHolder = serializeHolder(dh);

	            // PreparedStatement에 바이트 배열을 설정
	            pstmt.setString(1, sessionID);
	            pstmt.setBytes(2, serializedHolder);
	            pstmt.setBytes(3, serializedHolder);
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
		private byte[] serializeHolder(dataHolder dh) {
			// 객체를 직렬화
			try {
	        ByteArrayOutputStream bos = new ByteArrayOutputStream();
	        ObjectOutputStream out = new ObjectOutputStream(bos);
	        out.writeObject(dh);
	        out.close();
	        
	        byte[] serializedData = bos.toByteArray();
			return serializedData;
			} catch (IOException e){
				 e.printStackTrace(); 
			     return null;
			}
			/*
				 * ByteArrayOutputStream bos = new ByteArrayOutputStream(); ObjectOutputStream
				 * out = new ObjectOutputStream(bos); out.writeObject(doc); out.close(); return
				 * bos.toByteArray();
				 */
		}
		

	    // DB에서 읽어들인 바이트 배열을 dataHolder 객체로 역직렬화하는 메서드
		//test_login_apply, writeAction에서 사용할것
	    public dataHolder deserializeHolder(byte[] serializedData) {
	    	 try (ByteArrayInputStream bis = new ByteArrayInputStream(serializedData);
	                 ObjectInputStream in = new ObjectInputStream(bis)) {
	                Object obj = in.readObject();
	                if (obj instanceof dataHolder) {
	                	System.out.println("(dataHolderDAO.java) deserializeHolder 실행완료");
	                    return (dataHolder) obj;
	                }
	            } catch (IOException | ClassNotFoundException e) {
	                e.printStackTrace();
	            }
	            return null;
	    }
	    
	    // 데이터베이스로부터 데이터를 읽어올 메서드
	    public dataHolder readDataHolderFromDatabase(String holderName) {
	        String sql = "SELECT content FROM dataHolders WHERE holderName = ?";
	        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	            pstmt.setString(1, holderName);
	            try (ResultSet rs = pstmt.executeQuery()) {
	                if (rs.next()) {
	                    byte[] serializedData = rs.getBytes("content");
	                    System.out.println("(dataHolderDAO.java) DB에서 dataHolder 조회 완료");
	                    return deserializeHolder(serializedData);
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return null;
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
