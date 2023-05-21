package test;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/test_login")
public class test_login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
 

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		userDAO userdao = new userDAO();
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
