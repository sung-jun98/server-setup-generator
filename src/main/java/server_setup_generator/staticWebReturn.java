package server_setup_generator;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.stream.Collectors;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;

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
		
//request 내용확인 용도
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
	        } catch (JSONException e) {
	            // JSON 파싱 오류 처리
	            e.printStackTrace();
	            return;
	        }
	        System.out.print("jsonObject : ");
	        System.out.println(jsonObject);

	        // JSON 객체에서 필요한 값 추출
	        Object resultData_operators = null;
	        Object resultData_links = null;
	        try {
	        	
	        	resultData_operators = jsonObject.get("operators");
	        	resultData_links = jsonObject.get("links");
	        } catch (JSONException e) {
	            // 필요한 값이 없는 경우 처리
	            e.printStackTrace();
	            return;
	        }
	        
	        System.out.println("resultData_operators : "+ resultData_operators);
	        System.out.println("resultData_links :" + resultData_links);

	        //String result_string = flowchartData.toString();
	        
	        
		/*
		Enumeration<String> parameterNames = request.getParameterNames();
		while (parameterNames.hasMoreElements()) {
		    String paramName = parameterNames.nextElement();
		    System.out.println("Parameter Name: " + paramName + ", Value: " + request.getParameter(paramName));
		}
		
		 
		String tmp_result = request.getParameter("myData[operators][operator1][top]");
		System.out.println("tmp_result : " + tmp_result);
		if(tmp_result == null) {
		    System.out.println("flowchartData parameter not found");
		    return;
		}else {
		JSONObject jsonData = new JSONObject(tmp_result);
		
		JSONObject operators = jsonData.getJSONObject("operators");
		JSONObject links = jsonData.getJSONObject("links");

		
        System.out.println(operators.toString());//전달받은 JSON 데이터중 operators 요소 출력
        System.out.println(links.toString());//전달받은 JSON 데이터중 operators 요소 출력
        
		}*/
    }
}
	
	
	/*
	private String pageContent;
	
	public staticWebReturn() { //디폴트 웰컴 페이지 설정. 
		this.pageContent = "<h1>Welcome to My Web Application!</h1>";
	}
	public staticWebReturn(String pageContent) {
		this.pageContent = pageContent;
		
	}
    
    
    public String getPageContent() { //디버깅용 메서드
    	return this.pageContent;
    }*/
	
/*
@WebServlet("/hello")
public class staticWebReturn extends HttpServlet{
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException{
		System.out.println(request);
		
	}
}*/

