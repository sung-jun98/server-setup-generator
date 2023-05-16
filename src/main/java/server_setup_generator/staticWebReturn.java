package server_setup_generator;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet(urlPatterns = "/staticWebReturn", loadOnStartup = 1) // 웰컴 페이지 설정
public class staticWebReturn extends HttpServlet {
	@Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException {

//response관련 설정
		 response.setContentType("application/json");
	     PrintWriter out = response.getWriter();
	     JSONObject json = new JSONObject();
	     json.put("result", "hello");
	     out.print(json.toString());
	     
	     //request를 자바 객체 OperatorData로 변환한 메서드
	     OperatorData objectMapper = parsingJSON(request);
	     
	     //---------동적으로 클래스 생성 테스트 코드-----------
	     loginLogic loginLogic = new loginLogic(objectMapper);
	     loginLogic.execute();//
	     
		
    }
	//request로 받은 캔버스의 데이터를 자바 객체 OperatorData로 변환한 메서드
	public OperatorData parsingJSON(HttpServletRequest request) throws UnsupportedEncodingException, IOException {
		// JSON 데이터 추출
        BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        String jsonString = sb.toString();
        System.out.println(jsonString);//
        // JSON 객체 생성
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(jsonString);
        	//jsonObject = new JSONObject(request.getParameter("flowchartData"));
        } catch (JSONException e) {
            // JSON 파싱 오류 처리
            e.printStackTrace();
            return null; //원래 여기 뭐가 있었던가..?
        }
        //System.out.print("jsonObject : ");
        //System.out.println(jsonObject);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);//알 수 없는 속성 무시하도록 설정
        OperatorData flowchartData = objectMapper.readValue(jsonString, OperatorData.class);
        
        //테스트로 ID가 "operator1"인 값의 title을 출력해 보았다. 이걸 
        System.out.println("getFlowchartData : " + flowchartData.getFlowchartData().getOperators().
        		get("operator1").getProperties().getTitle());
        
        
        return flowchartData;
        
        
	}
}
	
	
	
