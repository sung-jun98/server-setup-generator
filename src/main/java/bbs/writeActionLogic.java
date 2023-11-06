package bbs;

import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;

import server_setup_generator.dataHolder;

//클라이언트가 입력한 도식도를 바탕으로 dataHolder에 설정변수를 설정하여 리턴하는 클래스
public class writeActionLogic {
	//필요한 설정 변수
	private dataHolder dh;
	private HttpSession hs;
	private Map<String, Map<String, ArrayList<String>>> operatorInfo;
	
	//생성자
	public writeActionLogic(dataHolder dh, HttpSession hs) {
		this.dh = dh; //사용할 데이터 객체를 생성자를 통해서 받아온다.
		this.hs = hs;
		this.operatorInfo = dh.getOperatorInfo();
	}
	
	//extract()를 완료한 dataHolder를 반환받기 위해서 만든 getter
	public dataHolder getDh() {
		return dh;
	}
	
	//hs로부터 특징 추출 -> dh에 set한다.
	public void extract() {
		dh.setBbsTitle((String)hs.getAttribute("bbsTitle"));
		dh.setBbsContent((String)hs.getAttribute("bbsContent"));
		dh.setUserID((String)hs.getAttribute("userID"));
		
		
		if(operatorInfo.containsKey("게시물 작성")) {
			//'리턴 페이지n' 오퍼레이터와 연결되어 있을 경우 
			if(operatorInfo.get("게시물 작성").get("output_0") != null) {
				String outputOp_title_of_writeAction= operatorInfo.get("게시물 작성").get("output_0").get(0);//리턴페이지 오퍼레이터의 title 출력됨
				
				
				outputOp_title_of_writeAction = checkRegex(outputOp_title_of_writeAction);
				dh.setSuccessPath((String) hs.getAttribute(outputOp_title_of_writeAction));
			}
			
			//'저장할 DB 정보'와 연결되어있는 DB 속성 관련 정보
			dh.setBbsTableName_db((String) hs.getAttribute("dbTableNameBBS"));
			
			if(operatorInfo.get("저장할 DB 정보").get("output_1") != null) {
				String outputOp_title_of_bbsID= operatorInfo.get("저장할 DB 정보").get("output_1").get(0);//'저장할 DB 정보' 오퍼레이터와 연결된 첫번째 오퍼레이터의 title 출력됨
				dh.setBbsID_db(outputOp_title_of_bbsID);
			}
			if(operatorInfo.get("저장할 DB 정보").get("output_2") != null) {
				String outputOp_title_of_bbsTitle= operatorInfo.get("저장할 DB 정보").get("output_2").get(0);//'저장할 DB 정보' 오퍼레이터와 연결된 첫번째 오퍼레이터의 title 출력됨
				dh.setBbsTitle_db(outputOp_title_of_bbsTitle);
			}
			if(operatorInfo.get("저장할 DB 정보").get("output_3") != null) {
				String outputOp_title_of_bbsUserID= operatorInfo.get("저장할 DB 정보").get("output_3").get(0);//'저장할 DB 정보' 오퍼레이터와 연결된 첫번째 오퍼레이터의 title 출력됨
				dh.setUserID_db(outputOp_title_of_bbsUserID);
			}
			if(operatorInfo.get("저장할 DB 정보").get("output_4") != null) {
				String outputOp_title_of_bbsDate= operatorInfo.get("저장할 DB 정보").get("output_4").get(0);//'저장할 DB 정보' 오퍼레이터와 연결된 첫번째 오퍼레이터의 title 출력됨
				dh.setBbsDate_db(outputOp_title_of_bbsDate);
			}
			if(operatorInfo.get("저장할 DB 정보").get("output_5") != null) {
				String outputOp_title_of_bbsContent= operatorInfo.get("저장할 DB 정보").get("output_5").get(0);//'저장할 DB 정보' 오퍼레이터와 연결된 첫번째 오퍼레이터의 title 출력됨
				dh.setBbsContent_db(outputOp_title_of_bbsContent);
			}
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
