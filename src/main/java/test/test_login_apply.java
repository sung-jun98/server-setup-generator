package test;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class test_login_apply
 */
//사용자에게 최종적으로 반환할 로그인 관련 서블릿. 
@WebServlet("/test_login_apply")
public class test_login_apply extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		userDAO userdao = new userDAO(); //이거 말고 매개변수를 받는 userDAO()를 하나 더 정의해야 한다. 매개변수는 순서대로 스키마이름, dbID, dbPW, 로그인관련 테이블명, ID필드명, PW필드명
		System.out.println("ID : " + request.getParameter("id"));//
		System.out.println("PW : " + request.getParameter("password"));//
		
		
		int result = userdao.login(request.getParameter("id"), request.getParameter("password"));//id, password도 overWriteData를 통해 사용자가 정의한 값으로 대체할 수 있어야 한다.
		 
		if (result == 1) {//로그인 성공시
			response.sendRedirect("/server_setup_generator/test/loginResult.jsp");//완료 경로 역시 사용자의 입력값에 따라 바꾸어 주어야 한다.
		} else if(result == 0) {//비밀번호 틀렸을 시
			
		} else if(result == -1) { //아이디가 없음
			
		}else if(result == -2) {//DB오류
			
		}
	}
	


}
