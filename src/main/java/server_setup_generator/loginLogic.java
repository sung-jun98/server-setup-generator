package server_setup_generator;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.ServletContext;

//로그인 기능과 관련되어있는 정보를 operatorInfo(DataHolder안에 있는 변수)와 Application Scope로부터 필요한 부분만 추출하는 클래스
public class loginLogic {
	//
	private dataHolder dh;
	private ServletContext sc;
	private Map<String, Map<String, ArrayList<String>>> operatorInfo;
	
	
	public loginLogic(dataHolder dh, ServletContext sc) {
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
		dh.setId((String)sc.getAttribute("inputID"));
		dh.setPassword((String) sc.getAttribute("inputPW"));
		
		//output_1인 '일치'가 어딘가로 연결되어 있는지 체크
		//나중에 여기가 어디로 연결되어 있는지도 분기문 더 추가해 볼 것
		//만약 여기서 연결되어 있는 오퍼레이터가 리턴페이지가 아닐 경우에는 디폴트 경로도 추가해 줄것.
		if(operatorInfo.get("로그인 기능").get("output_0") != null) {
			dh.setCorrect_check(true);
		}else {
			dh.setCorrect_check(false);
		}
		
		//아이디가 없을 경우
		if(operatorInfo.get("로그인 기능").get("output_1").contains("아이디가 없을 경우")) {
			dh.setId_error_check(true);
		}else {
			dh.setId_error_check(false);
		}
		
		//비밀번호가 일치하지 않을 경우
		if(operatorInfo.get("로그인 기능").get("output_1").contains("비밀번호가 틀릴 경우")) {
			dh.setPw_error_check(true);
		}else {
			dh.setPw_error_check(false);
		}
		
		//DB오류가 일어났을 경우
		if(operatorInfo.get("로그인 기능").get("output_1").contains("DB오류가 발생했을 경우")) {
			dh.setDb_error_check(true);
		}else {
			dh.setDb_error_check(false);
		}
		
		
	}
	

	
	
}
