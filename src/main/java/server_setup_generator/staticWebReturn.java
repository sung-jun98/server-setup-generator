package server_setup_generator;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/staticWebReturn", loadOnStartup = 1) // 웰컴 페이지 설정
public class staticWebReturn extends HttpServlet {
	@Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//response관련 설정
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		out.print("hello");
		
		
		
		System.out.println("서블릿 실행중");
		String tmp_result = request.getParameter("flowchartData");
        System.out.println(request);
        
    }
	
	
	/*
	private String pageContent;
	
	public staticWebReturn() { //디폴트 웰컴 페이지 설정. 
		this.pageContent = "<h1>Welcome to My Web Application!</h1>";
	}
	public staticWebReturn(String pageContent) {
		this.pageContent = pageContent;
		
	}
    
    
    public String getPageContent() { //디버깅용 메서드
    	return this.pageContent;
    }*/
	

}
/*
@WebServlet("/hello")
public class staticWebReturn extends HttpServlet{
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException{
		System.out.println(request);
		
	}
}*/

