package server_setup_generator;

import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

//로그인 기능과 관련되어있는 정보를 operatorInfo(DataHolder안에 있는 변수)와 Application Scope로부터 필요한 부분만 추출하는 클래스
public class loginLogic {
	//
	private dataHolder dh;
	//private ServletContext sc;
	private HttpSession sc;
	private Map<String, Map<String, ArrayList<String>>> operatorInfo;
	
	
	public loginLogic(dataHolder dh, HttpSession sc) {
		this.dh = dh; //사용할 데이터 객체를 생성자를 통해서 받아온다.
		this.sc = sc;
		
		this.operatorInfo = dh.getOperatorInfo();
	}
	//extract()를 완료한 dataHolder를 반환받기 위해서 만든 getter
	public dataHolder getDh() {
		return dh;
	}


	//test_login_apply.java에서 사용할 데이터를 추출한다.
	public void extract() {
		//inputID, inputPW 추출
		//디폴트값은 id/userPassword이다.
		dh.setId((String)sc.getAttribute("inputID"));
		dh.setPassword((String) sc.getAttribute("inputPW"));
		
		//output_0인 '일치'가 어딘가로 연결되어 있는지 체크
		//나중에 여기가 어디로 연결되어 있는지도 분기문 더 추가해 볼 것
		//만약 여기서 연결되어 있는 오퍼레이터가 리턴페이지가 아닐 경우에는 디폴트 경로도 추가해 줄것.
		if(operatorInfo.get("로그인 기능").get("output_0") != null) {
			dh.setCorrect_check(true);
		}else {
			dh.setCorrect_check(false);
		}
		
		//아이디가 없을 경우
		if(operatorInfo.get("로그인 기능").get("output_1") != null && 
				operatorInfo.get("로그인 기능").get("output_1").contains("아이디가 없을 경우")) {
			dh.setId_error_check(true);
			
			//'리턴 페이지n' 오퍼레이터와 연결되어 있을 경우 
			if(operatorInfo.get("아이디가 없을 경우").get("output_0") != null) {
				String outputOp_title_of_idError= operatorInfo.get("아이디가 없을 경우").get("output_0").get(0);//리턴페이지 오퍼레이터의 title 출력됨
				
				
				outputOp_title_of_idError = checkRegex(outputOp_title_of_idError);
				dh.setId_error_path((String) sc.getAttribute(outputOp_title_of_idError));
			}
		}else {
			dh.setId_error_check(false);
		}
		
		//비밀번호가 일치하지 않을 경우
		if(operatorInfo.get("로그인 기능").get("output_1") != null && 
				operatorInfo.get("로그인 기능").get("output_1").contains("비밀번호가 틀릴 경우")) {
			dh.setPw_error_check(true);
			//'리턴 페이지n' 오퍼레이터와 연결되어 있을 경우 
			if(operatorInfo.get("비밀번호가 틀릴 경우").get("output_0") != null) {
				String outputOp_title_of_pwError= operatorInfo.get("비밀번호가 틀릴 경우").get("output_0").get(0);//리턴페이지 오퍼레이터의 title 출력됨
				
				
				outputOp_title_of_pwError = checkRegex(outputOp_title_of_pwError);
				dh.setPw_error_path((String) sc.getAttribute(outputOp_title_of_pwError));
			}
		}else {
			dh.setPw_error_check(false);
		}
		
		//DB오류가 일어났을 경우
		if(operatorInfo.get("로그인 기능").get("output_1") != null && 
				operatorInfo.get("로그인 기능").get("output_1").contains("DB오류가 발생했을 경우")) {
			dh.setDb_error_check(true);
			//'리턴 페이지n' 오퍼레이터와 연결되어 있을 경우 
			if(operatorInfo.get("DB오류가 발생했을 경우").get("output_0") != null) {
				String outputOp_title_of_dbError= operatorInfo.get("DB오류가 발생했을 경우").get("output_0").get(0);//리턴페이지 오퍼레이터의 title 출력됨
				
				
				outputOp_title_of_dbError = checkRegex(outputOp_title_of_dbError);
				dh.setDb_error_path((String) sc.getAttribute(outputOp_title_of_dbError));
				System.out.println("DB오류 관련 로직 실행중");
			}
		}else {
			dh.setDb_error_check(false);
		}
		
		//'입력값' 오퍼레이터의 파일 업로드란에 사용자가 파일을 업로드 했을 시
		//다만, 상대경로는 빠져있다. setter에서 수정하든 여기서 수정하든 나중에 손봐야 한다.
		if(sc.getAttribute("입력값") != null) {
			dh.setLogin_startPage((String)sc.getAttribute("입력값"));
		}
		
	}
	
	//연결되어 있는 오퍼레이터의 타이틀이 예상과 일치하는지 정규식을 통해 체크하고 리턴하는 메서드
	private String checkRegex(String opTitle) {
		//정규식 정의 -> Matcher에 등록. 만약 opTitle이 리턴 페이지로 시작하면 matcher.find() 값은 true, 아닐시 false
		String pattern = "^리턴 페이지";
		Matcher matcher = Pattern.compile(pattern).matcher(opTitle);
		
		if(matcher.find()) {
			//이 밑에 리턴 페이지 파일을 물리적으로 저장하는 로직 작성
			return opTitle;
		}else {
			return null;
		}
		
	} 
	

	
	
}
