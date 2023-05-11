package server_setup_generator;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//캔버스상에서 input/output 태그를 수정하거나 HTML 파일을 입력으로 넣었을 떄 그 정보를 가지고 있을 서블릿. getData를 하고 메인작업을 시작하기 전 이 클래스 내 메서드를 실행해 업데이트를 한다.
//만약 입력받은 HTML 파일에서 form태그가 없다면 경고 메시지를 응답으로 보낸다.
@WebServlet("/overWriteData")
public class overWriteData extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
    public overWriteData() {
        super();
        // TODO Auto-generated constructor stub
    }
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
	}

}
