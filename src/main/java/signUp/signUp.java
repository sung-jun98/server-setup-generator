package signUp;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbLogic.dataHolderDAO;
import server_setup_generator.dataHolder;
import bbs.bbsDAO;

//회원가입 로직을 실행할 서블릿
//1. dataHolder 역직렬화를 하고
//2. bbsDAO에 역직렬화한 내용을 업데이트
//3. bbsDAO로 회원가입 정보 DB에 넣기
//4. 성공했을 시 연결할 페이지를 설정
@WebServlet("/signUp")
public class signUp extends HttpServlet {
	private static final long serialVersionUID = 1L;
	dataHolder dh;
    //필요한 설정 변수들 디폴트값 정의 -> 나중에 dataHolder로부터 deserialize 한 값으로 덮어씌울 것이다.
	String signUpID = "signUpID";
	String signUpPW = "signUpPW";
	String signUpEmail = "signUpEmail"; //회원가입 하려는 유저의 HTML파일내 name태그명
	
	String signUp_successPath = "flowchart/demo.jsp"; //결과가 성공적일시 연결될 페이지
	
	//DB의 속성
	String signUpTableName_db = "signUp";
	String signUpID_db = "userID";
	String signUpPW_db = "userPW";
	String signUpEmail_db = "userEmail";
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//DB에서 클라이언트의 hidden 타입인 sessionID값으로 저장되어있는 dh객체를 가져온다.
		dataHolderDAO dhDAO = new dataHolderDAO();
		this.dh = dhDAO.readDataHolderFromDatabase((String) request.getParameter("sessionID"));
		//가져온 dh로부터 필요한 변수 값들을 추출한다.
		analyzeDataHolder(dh);
		System.out.println("\n===========(signUp.java)============================");
		System.out.println("sessionID의 이름은 " + (String) request.getParameter("sessionID"));
		System.out.println("역직렬화한 signUpID는 " + signUpID);
		System.out.println("역직렬화한 signUpPW는 " + signUpPW);
		System.out.println("역직렬화한 signUpEmail는 " + signUpEmail);
		System.out.println("역직렬화한 signUp_successPath는 " + signUp_successPath);
		System.out.println("역직렬화한 signUpTableName_db는 " + signUpTableName_db);
		System.out.println("역직렬화한 signUpID_db는 " + signUpID_db);
		System.out.println("역직렬화한 signUpPW_db : " + signUpPW_db);
		System.out.println("역직렬화한 signUpEmail_db : " + signUpEmail_db);
		System.out.println("=======================================\n");
		
		bbsDAO bbsDAO = new bbsDAO();
		//역직렬화해서 알아낸 원래 DB의 값들을 설정해준다.
		bbsDAO.signUp_setAttr(this.signUpTableName_db, this.signUpID_db, this.signUpPW_db, this.signUpEmail_db);
		//제3자 클라이언트가 입력한 값들을 DB에 넣는 작업
		int result = bbsDAO.signUp(request.getParameter(signUpID), request.getParameter(signUpPW), request.getParameter(signUpEmail));
		
		//DB에 회원가입을 하려는 회원의 정보를 넣는데 성공했을 시
		if (result == 1) {
			response.sendRedirect(signUp_successPath);
			
		}else {//어느 경우에도 해당 안될 경우. 즉 디폴트값 설정을 여기에 해주면 된다. 
			response.sendRedirect("/server_setup_generator/demo.jsp");
			System.out.println("(signUp.java) DB에 작업 실패");
		}
	
	
	
	}

	//역직렬화된 dataHolder로부터 맞춤형 변수 값들을 가져옴.
	private void analyzeDataHolder(dataHolder dh) {
			
		this.signUpID = dh.getSignUpID();
		this.signUpPW = dh.getSignUpPW();
		this.signUpEmail = dh.getSignUpEmail();
			
		this.signUp_successPath = dh.getSignUp_successPath();
		
		this.signUpTableName_db = dh.getSignUpTableName_db();
		this.signUpID_db = dh.getSignUpID_db();
		this.signUpPW_db = dh.getSignUpPW_db();
		this.signUpEmail_db = dh.getSignUpEmail_db();	
	}
		
		
		
}
