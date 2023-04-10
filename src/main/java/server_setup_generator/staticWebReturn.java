package server_setup_generator;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/hello")
public class staticWebReturn extends HttpServlet{
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException{
		System.out.println(request);
		
	}
}

