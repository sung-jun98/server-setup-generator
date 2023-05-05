package server_setup_generator;

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

//response관련 설정
			response.setContentType("application/json");
		    PrintWriter out = response.getWriter();
		    JSONObject json = new JSONObject();
		    json.put("result", "success");
		    out.print(json.toString());
	}

}
