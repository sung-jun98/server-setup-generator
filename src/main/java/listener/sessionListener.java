package listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;


@WebListener
public class sessionListener implements HttpSessionListener, HttpSessionAttributeListener {
	// 세션이 생성될 때 초기화 작업 수행
	public void sessionCreated(HttpSessionEvent event) {
        // 세션 속성으로 초기화할 데이터를 설정
       
        System.out.println("Session Created: " + event.getSession().getId());
        event.getSession().setAttribute("inputID", "id");
        event.getSession().setAttribute("inputPW", "password");
        event.getSession().setAttribute("id_DB", "userID");
        event.getSession().setAttribute("pw_DB", "userPW");
        
        event.getSession().setAttribute("bbsTitle", "bbsTitle");
        event.getSession().setAttribute("bbsContent", "bbsContent");
        event.getSession().setAttribute("userID", "userID");
        //'저장할 DB정보'오퍼레이터의 디폴트 Attr값 
        event.getSession().setAttribute("dbTableNameBBS", "bbs");
//        event.getSession().setAttribute("bbsID_db", "bbsID");
//        event.getSession().setAttribute("bbsTitle_db", "bbsTitle");
//        event.getSession().setAttribute("userID_db", "userID");
//        event.getSession().setAttribute("bbsDate_db", "bbsDate");
//        event.getSession().setAttribute("bbsAvailable_db", "bbsAvailable");

        //'저장할 DB정보(회원가입)'오퍼레이터의 디폴트 Attr값
        event.getSession().setAttribute("SignUpDbTableName", "signup");
        
        //'가입 페이지' 오퍼레이터의 디폴트 Attr값
        event.getSession().setAttribute("signUpID", "signUpID");
        event.getSession().setAttribute("signUpPW", "signUpPW");
        event.getSession().setAttribute("signUpEmail", "signUpEmail");
        
        
    }
	public void attributeAdded(HttpSessionBindingEvent event) {
        // 세션에 속성이 추가될 때 처리 작업 수행
	   System.out.println("Attribute Added: " + event.getName() + " = " + event.getValue());
	}

	public void attributeRemoved(HttpSessionBindingEvent event) {
	    // 세션에서 속성이 삭제될 때 처리 작업 수행
	    System.out.println("Attribute Removed: " + event.getName());
	}

	public void attributeReplaced(HttpSessionBindingEvent event) {
	    // 세션의 속성이 변경될 때 처리 작업 수행
	    System.out.println("Attribute Replaced: " + event.getName() + " = " + event.getValue());
	}
}
