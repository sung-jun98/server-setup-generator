package server_setup_generator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class sessionListener implements HttpSessionListener  {
	String sessionID;
	public sessionListener() {
		
	}
	
    public void sessionCreated(HttpSessionEvent event) {
        HttpSession session = event.getSession();
        sessionID = session.getId(); // 새로 생성된 세션의 세션 ID를 얻음
        System.out.println("sessionID : " + sessionID);

    }
    
    public String getSessionID() {
    	return this.sessionID;
    }

    public void sessionDestroyed(HttpSessionEvent event) {
        HttpSession session = event.getSession();
        String sessionId = session.getId(); // 삭제될 세션의 세션 ID를 얻음
        // 세션 ID를 사용하여 클라이언트를 구별하는 로직 수행
        // ...
    }

}
