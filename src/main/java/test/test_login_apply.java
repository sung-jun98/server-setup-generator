package test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server_setup_generator.dataHolder;

/**
 * Servlet implementation class test_login_apply
 */
//사용자에게 최종적으로 반환할 로그인 관련 서블릿. 
@WebServlet("/test_login_apply")
public class test_login_apply extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//=============로그인 기능을 실현하는데 일어날 수 있는 분기들을 위한 변수들=========(아직 userDAO에 관해 필요한 요소들은 빠져있다. 추후 추가할 것) 
	//나중에 이 변수들이 .ser로 직렬화된 dataHolder로부터 도출하도록 해야만 한다.
	
	//.ser가 있는 경로
	/*
	 * String filePath =
	 * "C:\\Users\\tjdwn\\Dev\\Workspace\\server_setup_generator\\src\\test\\dataHolder.ser";
	 * 
	 * try { FileInputStream fileIn = new FileInputStream(filePath);
	 * ObjectInputStream objectIn = new ObjectInputStream(fileIn);
	 * 
	 * dataHolder dh = (dataHolder)objectIn.readObject(); // 객체 역직렬화
	 * 
	 * //열었던 steam을 닫아준다. objectIn.close(); fileIn.close(); }catch(IOException e) {
	 * e.printStackTrace(); }
	 */
	dataHolder dh;
	
	String id = "id";
	String password = "password";
	
	boolean correct_check = true;
	String correct_path = "/server_setup_generator/test/loginResult.jsp"; 
	
	boolean id_error_check = true;
	String id_error_path = "/server_setup_generator/test/loginResult2.jsp";
	
	boolean pw_error_check = true;
	String pw_error_path = "/server_setup_generator/test/loginResult2.jsp";
	
	boolean db_error_check = true; //만약 체크 안되어 있을 경우, 원래 페이지로 리다이렉트 되도록 한다. 
	String db_error_path = "/server_setup_generator/test/loginResult2.jsp";
	
	String login_startPage = "/server_setup_generator/test/loginTest.jsp";
	 

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		userDAO userdao = new userDAO(); //이거 말고 매개변수를 받는 userDAO()를 하나 더 정의해야 한다. 매개변수는 순서대로 스키마이름, dbID, dbPW, 로그인관련 테이블명, ID필드명, PW필드명
		System.out.println("ID : " + request.getParameter(id));//
		System.out.println("PW : " + request.getParameter(password));//
		  
		//.ser의 직렬화된 객체를 역직렬화.
		try {
			this.dh = deserializeDataHolder();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		id = dh.getId();
		System.out.println(".ser로부터 받은 id는 " + id);
		
		//===================================================
		//==============여기서부터 건드리지 말것=====================
		
		int result = userdao.login(request.getParameter(id), request.getParameter(password));//id, password도 overWriteData를 통해 사용자가 정의한 값으로 대체할 수 있어야 한다.
		 
		if (result == 1 && correct_check) {//로그인 성공시
			//if(correct_check) {
				response.sendRedirect("/server_setup_generator/test/loginResult.jsp");//완료 경로 역시 사용자의 입력값에 따라 바꾸어 주어야 한다.
			//}		
		} else if(result == 0 && pw_error_check) {//비밀번호 틀렸을 시
			
		} else if(result == -1 && id_error_check) { //아이디가 없음
			if(id_error_check) {
			response.sendRedirect("/server_setup_generator/test/");
			}
		}else if(result == -2 && db_error_check) {//DB오류
			 	
		}else {//어느 경우에도 해당 안될 경우. 즉 디폴트값 설정을 여기에 해주면 된다. 
			response.sendRedirect(login_startPage);
		}
	}
	//직렬화된 dataHolder 역직렬화 해주는 메서드
	private dataHolder deserializeDataHolder() throws ClassNotFoundException {
		//.ser가 있는 경로
		String filePath = "C:\\Users\\tjdwn\\Dev\\Workspace\\server_setup_generator\\src\\test\\dataHolder.ser";
		
		try {
			FileInputStream fileIn = new FileInputStream(filePath);
			ObjectInputStream objectIn = new ObjectInputStream(fileIn);
			
			dataHolder dh = (dataHolder)objectIn.readObject(); // 객체 역직렬화
			
			//열었던 steam을 닫아준다.
			objectIn.close();
			fileIn.close();
			
			return dh;
		}catch(IOException e) {
			e.printStackTrace();
		}
		return null;
	}


}
