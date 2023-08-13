package dbLogic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

@WebServlet("/dbBuilder")
public class dbBuilder extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public dbBuilder() {
        super();
    }
    String CREATEsql = "";
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//request 내용확인 용도
       // JSON 데이터 추출
	        BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
	        StringBuilder sb = new StringBuilder();
	        String line = null;
	        while ((line = reader.readLine()) != null) {
	            sb.append(line);
	        }
	        String jsonString = sb.toString();
	        System.out.print("jsonString : ");
	        System.out.println(jsonString);//
	   // JSON 객체 생성
	        JSONObject jsonObject;
	        try {
	            jsonObject = new JSONObject(jsonString);
	        } catch (JSONException e) {
	            // JSON 파싱 오류 처리
	            e.printStackTrace();
	            return;
	        }
	        System.out.print("jsonObject : ");
	        System.out.println(jsonObject);
	        
	        //JDBC로 실행시킬 SQL 완성 문장을 만든다.
	        dbBuilderDAO dbDAO = new dbBuilderDAO();
	        CREATEsql = makeFullSQL(jsonObject);
	        dbDAO.createTable(CREATEsql); //완성된 create문을 JDBC를 통해 실제로 실행시켜 테이블을 만든다.
	        

	        //session scope에 DB관련 정보를 입력하는 메서드
	        //checkJSONObject(jsonObject);
	        

//response관련 설정 {result:success}라고 보낸다
			response.setContentType("application/json");
		    PrintWriter out = response.getWriter();
		    JSONObject json = new JSONObject();
		    json.put("result", "success");
		    out.print(json.toString());
	}
	//전달받은 DB 페이지의 정보를 servletContext에 넣는 메서드
//	public void checkJSONObject(JSONObject jsonObject ) {
//		for(String key : jsonObject.keySet()) {
//			String value = jsonObject.getString(key);
//			System.out.println("key : " + key + ",value : " + value);
//		}
//		
//	}
//	
	//CREATE할 SQL문을 jsonObject로부터 추출하여 완성하는 메서드
	public String makeFullSQL(JSONObject jsonObject) {
		int i = 1;
		boolean primaryKeyAdded = false; //primary Key로 설정할 변수 찾기 위함
		
//		  String DDLsql_sample = "CREATE TABLE IF NOT EXISTS my_table ("
//	                + "id INT PRIMARY KEY,"
//	                + "name VARCHAR(50) NOT NULL,"
//	                + "age INT)";
                
		String DDLsql = "CREATE TABLE IF NOT EXISTS ";
        DDLsql += jsonObject.getString("tableName");
        DDLsql += "(";
        
        while(jsonObject.has("columnName" + String.valueOf(i))) {
        	
        	if (i > 1) {
                DDLsql += ", "; // 콤마를 추가 (첫 번째 컬럼 이후에 추가)
            }
        	
//        	DDLsql += (jsonObject.getString("columnName" + String.valueOf(i)) + " ");
//        	DDLsql += (jsonObject.getString("dataType" + String.valueOf(i)) + " ");
        	
        	String columnName = jsonObject.getString("columnName" + String.valueOf(i));
            String dataType = jsonObject.getString("dataType" + String.valueOf(i));

            // 해당 컬럼을 PRIMARY KEY로 추가
            if (!primaryKeyAdded && jsonObject.optString("primaryKey" + String.valueOf(i), "").equals("on")) {
                DDLsql += columnName + " " + dataType + " PRIMARY KEY";
                primaryKeyAdded = true; // Primary key 추가 완료
            } else {
                DDLsql += columnName + " " + dataType;
            }
        	
        	i++;
        }
        
        DDLsql += ")";
        
        System.out.println("(dbBuilder) DDLsql = " + DDLsql);
        return DDLsql;
		
	}
}
