package server_setup_generator;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

//input 태그의 file 업로드를 했을 떄 연결될 서블릿. 그리고 이 서블릿의 파싱 결과는 DataHandler에 저장된다.
@WebServlet("/processHTML")
@MultipartConfig
public class processHTML extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/*
	 * public ProcessHTML() { super(); // TODO Auto-generated constructor stub }
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("processHTML 서블릿 실행 시작");
		Part filePart = request.getPart("loginStartPage"); //HTML 에서 지정한 name 속성으로 Part 인터페이스를 만든다.
		String filename = filePart.getSubmittedFileName(); //Part 인터페이스에서 파일의 원래 이름을 읽어들이는 메서드.
		
		System.out.println("filename : " + filename);//테스트용 
	}

}
