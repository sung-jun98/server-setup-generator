package listener;

import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class MyServletContextListener implements ServletContextListener, ServletContextAttributeListener {
	//처음 프로그램이 시작되자마자 필요한 요소들을 초기화한다.
    public void contextInitialized(ServletContextEvent event)  { 
    	String inputID = "id";
    	String inputPW = "userPassword"; //클라이언트의 HTML 페이지내 id, pw의 태그가 어떤 것인지 입력
    	
    	String id_DB = "userID";
    	String pw_DB = "userPW"; //userDAO()에서 참값인지를 확인할 때 쓸 변수
    	
 
    	
    	
         event.getServletContext().setAttribute("inputID", inputID);
         event.getServletContext().setAttribute("inputPW", inputPW);
         event.getServletContext().setAttribute("id_DB", id_DB);
         event.getServletContext().setAttribute("pw_DB", pw_DB);
         
         
         
    }
    
   
	
	public void contextDestroyed(ServletContextEvent event)  { 
	     
	}
	 
}
