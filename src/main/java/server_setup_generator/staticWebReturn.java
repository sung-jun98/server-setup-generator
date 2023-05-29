package server_setup_generator;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.ServletContext;
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
	     operatorData_to_dataHolder converter = new operatorData_to_dataHolder(objectMapper);
	     dataHolder dh = converter.change(); //단순히 canvas의 내용만이 담겨있는 미완성 dataHolder이다. 
	     
	     ServletContext sc = getServletContext(); //전달할 ServletContext 생성
	     loginLogic loginLogic = new loginLogic(dh, sc); //생성한 미완성 dataHolder와 servletContext를 매개변수로 넘긴다.
	     loginLogic.extract();
	     dh = loginLogic.getDh(); //dataHolder 내용을 한번 새로 업데이트한다. 여기서 받은 dh 객체를 직렬화해야 한다.
	     
//	     System.out.println("업데이트된 dh의 ID는 " + dh.getId());
//	     System.out.println("업데이트된 dh의 PW는 " + dh.getPassword());
	     
	     Map<String, Map<String, ArrayList<String>>> operatorInfo = dh.getOperatorInfo(); //이제 여기 operatorInfo를 통해서 canvas의 정보에 대해 쉽게 접근할 수 있다.

	     
		
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
	
	
	
