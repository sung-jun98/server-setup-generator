package server_setup_generator;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import bbs.writeActionLogic;
import dbLogic.dataHolderDAO;
import redirect.redirectLogic;
import signUp.signUpLogic;

@WebServlet(urlPatterns = "/staticWebReturn", loadOnStartup = 1) // 웰컴 페이지 설정
public class staticWebReturn extends HttpServlet {
	private static final long serialVersionUID = 1L;
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
	     
	 //---------------HttpSession 생성 -------------
	     //sessionListener sl = new sessionListener();
	     //String sessionID = sl.getSessionID();
	     HttpSession session = request.getSession();
	     String sessionID = session.getId();
	     System.out.println(sessionID);
	     
	     
	     //request로부터 추출한 key, value값을 ServletContext에 입력
	     //getServletContext().setAttribute("sessionID", sessionID);
	     
	     //System.out.println("servletContext내부의 sessionID : " + getServletContext().getAttribute("sessionID"));
	 
	      
	      //---------동적으로 클래스 생성 테스트 코드-----------
	     operatorData_to_dataHolder converter = new operatorData_to_dataHolder(objectMapper);
	     dataHolder dh = converter.change(); //단순히 canvas의 내용만이 담겨있는 미완성 dataHolder이다. 
	     
	     //ServletContext sc = getServletContext(); //전달할 ServletContext 생성
	     //loginLogic loginLogic = new loginLogic(dh, sc); //생성한 미완성 dataHolder와 servletContext를 매개변수로 넘긴다.
	     //=======로그인 관련========
	     loginLogic loginLogic = new loginLogic(dh, session);
	     loginLogic.extract();
	     dh = loginLogic.getDh(); //dataHolder 내용을 한번 새로 업데이트한다. 여기서 받은 dh 객체를 직렬화해야 한다.
	     
	     //=======게시물 업로드 관련======
	     writeActionLogic writeActionLogic = new writeActionLogic(dh, session);
	     writeActionLogic.extract();
	     dh = writeActionLogic.getDh();
	     
	     //=======회원가입 관련=========
	     signUpLogic signUpLogic = new signUpLogic(dh, session);
	     signUpLogic.extract();
	     dh = signUpLogic.getDh();
	     
	     //=======리다이렉트 설정 관련======
	     redirectLogic redirectLogic = new redirectLogic(dh, session);
	     redirectLogic.extract();
	     dh = redirectLogic.getDh();
	     
	     //========dataHolder 객체 DB에 저장=========
	     //dataHolder_to_serial(dh, sessionID); //기존에 로컬 서버에 .ser형태로 저장하던 방식
	     dataHolderDAO dhDAO = new dataHolderDAO(); //DB에 저장하는 새로운 방식
	     dhDAO.saveHolder(dh, sessionID);
	     
	     System.out.println("업데이트된 dh의 userID는 " + dh.getUserID());
	     System.out.println("업데이트된 dh의 successPath는 " + dh.getSuccessPath());
	     
	     //Map<String, Map<String, ArrayList<String>>> operatorInfo = dh.getOperatorInfo(); //이제 여기 operatorInfo를 통해서 canvas의 정보에 대해 쉽게 접근할 수 있다.
	     
	     
		
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
		/*
		 * System.out.println("getFlowchartData : " +
		 * flowchartData.getFlowchartData().getOperators().
		 * get("operator1").getProperties().getTitle());
		 */
        
        
        return flowchartData;
        
       
	}
	
	//완성된 dataHolder 객체를 이진화하여 클라이언트에게 리턴할 것이다.
	//어떻게 리턴할지는 아직 안정함
	public void dataHolder_to_serial(dataHolder dh, String sessionID) {
		//저장할 파일 경로
		String directoryPath = "C:\\Users\\tjdwn\\Dev\\Workspace\\server_setup_generator\\src\\test";
		//String directoryPath = "../../../../src/test";
		//===========
		
		//=============
		
		String filePath = directoryPath + "/dataHolder" + sessionID + ".ser"; 
		
		try {
			FileOutputStream fileOut = new FileOutputStream(filePath);
			ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
			
			objectOut.writeObject(dh); //객체를 직렬화하여 파일에 쓴다.
			
			//종료하기 전에 닫아준다.
			objectOut.close();
			fileOut.close();
			
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
	
	
	
