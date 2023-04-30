package server_setup_generator;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/*
@WebServlet(urlPatterns = "/", loadOnStartup = 1) // 웰컴 페이지 설정
public class staticWebReturn extends HttpServlet {
	private String pageContent;
	
	public staticWebReturn() { //디폴트 웰컴 페이지 설정. 
		this.pageContent = "<h1>Welcome to My Web Application!</h1>";
	}
	public staticWebReturn(String pageContent) {
		this.pageContent = pageContent;
		
	}
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 웰컴 페이지의 동작 설정
        response.getWriter().write(this.pageContent); // 응답 데이터 설정
    }
    
    public String getPageContent() { //디버깅용 메서드
    	return this.pageContent;
    }
}/*

@WebServlet("/hello")
public class staticWebReturn extends HttpServlet{
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException{
		System.out.println(request);
		
	}
}*/

