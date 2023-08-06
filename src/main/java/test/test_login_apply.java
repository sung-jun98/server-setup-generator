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


//사용자에게 최종적으로 반환할 로그인 관련 서블릿. 
@WebServlet("/test_login_apply")
public class test_login_apply extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//=============로그인 기능을 실현하는데 일어날 수 있는 분기들을 위한 변수들=========(아직 userDAO에 관해 필요한 요소들은 빠져있다. 추후 추가할 것) 
	//나중에 이 변수들이 .ser로 직렬화된 dataHolder로부터 도출하도록 해야만 한다.
	
	
	dataHolder dh;
	//만약 dataHolder.ser에 누락된 변수가 있다면 해당 변수들로 디폴트 설정을 해둔다.
	String id = "id";
	String password = "password";
	String sessionID = "";
	String dataHolderPath = "dataHolder.ser";
	
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
		System.out.println("(test_login_apply) ID : " + request.getParameter("id"));//
		System.out.println("(test_login_apply) PW : " + request.getParameter("password"));//
		System.out.println("(test_login_apply) hidden input sessionID : " + request.getParameter("sessionID"));
		
		this.dataHolderPath = "dataHolder" + (String) request.getParameter("sessionID") + ".ser";
		//.ser의 직렬화된 객체를 역직렬화.
		try {
			this.dh = deserializeDataHolder();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		analyzeDataHolder(dh); //역직렬화된 dh의 맞춤형 변수 가져옴
		
		System.out.println("=======================================");
		System.out.println("dataHolderPath의 이름은 " + this.dataHolderPath);
		System.out.println(".ser로부터 받은 id는 " + id);
		System.out.println(".ser로부터 받은 password는 " + password);
		System.out.println(".ser로부터 받은 correct_check는 " + correct_check);
		System.out.println(".ser로부터 받은 id_error_check는 " + id_error_check);
		System.out.println(".ser로부터 받은 pw_error_check는 " + pw_error_check);
		System.out.println(".ser로부터 받은 db_error_check는 " + db_error_check);
		System.out.println("역직렬화한 loginStartPage : " + login_startPage);
		System.out.println("역직렬화한 id_error_path : " + id_error_path);
		System.out.println("역직렬화한 pw_error_path : " + pw_error_path);
		System.out.println("역직렬화한 db_error_path : " + db_error_path);
		System.out.println("=======================================");
		
		//===================================================
		//==============여기서부터 건드리지 말것=====================
		
		int result = userdao.login(request.getParameter(id), request.getParameter(password));//id, password도 overWriteData를 통해 사용자가 정의한 값으로 대체할 수 있어야 한다.
		 
		if (result == 1 && correct_check) {//로그인 성공시
			//if(correct_check) {
				response.sendRedirect("/server_setup_generator/test/loginResult.jsp");//완료 경로 역시 사용자의 입력값에 따라 바꾸어 주어야 한다.
			//}	
				System.out.println("로그인 성공");
		} else if(result == 0 && pw_error_check) {//비밀번호 틀렸을 시
			System.out.println("비밀번호 틀림");
		} else if(result == -1 && id_error_check) { //아이디가 없음
			if(id_error_check) {
			response.sendRedirect("/server_setup_generator/test/");
			}
		}else if(result == -2 && db_error_check) {//DB오류
			 System.out.println("DB오류 일어남\n");
		}else {//어느 경우에도 해당 안될 경우. 즉 디폴트값 설정을 여기에 해주면 된다. 
			response.sendRedirect(login_startPage);
			System.out.println("그 어느 경우에도 해당 안됨");
		}
	}
	//직렬화된 dataHolder 역직렬화 해주는 메서드
	private dataHolder deserializeDataHolder() throws ClassNotFoundException {
		//.ser가 있는 경로
		//String filePath = "C:\\Users\\tjdwn\\Dev\\Workspace\\server_setup_generator\\src\\test\\dataHolder.ser";
		String filePath = "C:\\Users\\tjdwn\\Dev\\Workspace\\server_setup_generator\\src\\test\\" + this.dataHolderPath;
		
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
	
	//역직렬화된 dataHolder로부터 맞춤형 변수 값들을 가져옴.
	//아직 path는 추가 안해줬음. 나중에 loginLogic 완성하고 마저 완성할것 -> 완성함
	private void analyzeDataHolder(dataHolder dh) {
		
		this.id = dh.getId();
		this.password = dh.getPassword();
		
		this.correct_check = dh.isCorrect_check();
		
		this.db_error_check = dh.isDb_error_check();
		this.db_error_path = dh.getDb_error_path();
		
		this.id_error_check = dh.isId_error_check();
		this.id_error_path = dh.getId_error_path();
		
		this.pw_error_check = dh.isPw_error_check();
		this.pw_error_path = dh.getPw_error_path();
		
		if(dh.getLogin_startPage() != null) {
			this.login_startPage = dh.getLogin_startPage();
		};
		
	}


}
