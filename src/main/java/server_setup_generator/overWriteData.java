package server_setup_generator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

//캔버스상에서 input/output 태그를 수정했을 때 그 정보를 가지고 있을 서블릿. 
//staticWebReturn.java에서 getData를 하고 메인작업을 시작하기 전 이 클래스 내 메서드를 실행해 업데이트를 한다.
@WebServlet("/overWriteData")
public class overWriteData extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
	/*
	 * public overWriteData() { super(); // TODO Auto-generated constructor stub }
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// JSON 데이터 추출
        BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        String jsonString = sb.toString();
        System.out.println(jsonString);//
        
        
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);//알 수 없는 속성 무시하도록 설정
        Map<String, Object> jsonMap = objectMapper.readValue(jsonString, new TypeReference<Map<String, Object>>() {});
        String req_key = jsonMap.keySet().stream().findFirst().orElse(null);
        String req_val= (String)jsonMap.get(jsonMap.keySet().stream().findFirst().orElse(null));
        
        System.out.println("servletContext 수정전: "  + getServletContext().getAttribute((String)req_key));
        
        //request로부터 추출한 key, value값을 ServletContext에 입력
        getServletContext().setAttribute((String)req_key, req_val);
        
        System.out.println("servletContext 수정후: "  + getServletContext().getAttribute((String)req_key));

        
//        System.out.println("keyset : " + jsonMap.keySet().stream().findFirst().orElse(null));
//        System.out.println((String)jsonMap.get(jsonMap.keySet().stream().findFirst().orElse(null)));
        
        //OperatorData flowchartData = objectMapper.readValue(jsonString, OperatorData.class);
        
        //테스트로 ID가 "operator1"인 값의 title을 출력해 보았다. 이걸 
        //System.out.println("getFlowchartData : " + flowchartData.getFlowchartData().getOperators().
        		//get("operator1").getProperties().getTitle());
        
//response관련 설정
		 response.setContentType("application/json");
	     PrintWriter out = response.getWriter();
	     JSONObject json = new JSONObject();
	     json.put("result", "overWrite Success");
	     out.print(json.toString());
		
	}

}
