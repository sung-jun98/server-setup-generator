package deploy;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbLogic.documentDAO;

//클라이언트로부터 입력받은 html파일을 제3자에게 제공할 떄 사용할 서블릿. 
//DB의 documents 테이블로부터 select를 한 다음, 복호화를 한 파일을 클라이언트에게 응답으로 보낸다.
//documentDAO사용
@WebServlet("/deploy/*")
public class redirctServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 String pathInfo = request.getPathInfo(); // 클라이언트 요청 경로 추출
	        if (pathInfo != null) {
	            String requestedPage = pathInfo.substring(1); // 맨 앞의 슬래시 제거
	            // requestedPage를 이용하여 DB에서 해당 페이지의 내용을 가져오는 로직을 구현
	            documentDAO docDAO = new documentDAO();
	            String pageContent = docDAO.readDocument(requestedPage);
	            
	            if (pageContent != null) {
	                response.getWriter().write(pageContent); // 페이지 내용을 응답으로 보내기
	            } else {
	                response.sendError(HttpServletResponse.SC_NOT_FOUND); // 페이지를 찾을 수 없음을 클라이언트에게 알림
	            }
	        } else {
	            response.sendError(HttpServletResponse.SC_NOT_FOUND); // 올바르지 않은 경로 요청에 대한 처리
	        }
		
	}

}
