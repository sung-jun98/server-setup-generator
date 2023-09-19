package bbs;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;

import server_setup_generator.dataHolder;

public class deleteActionLogic {
	public dataHolder dh;
	public HttpSession hs;
	private Map<String, Map<String, ArrayList<String>>> operatorInfo;
	
	public deleteActionLogic(dataHolder dh, HttpSession hs) {
		this.dh = dh;
		this.hs = hs;
		this.operatorInfo = dh.getOperatorInfo();
	}
	
	//extract()를 완료한 dataHolder를 반환받기 위해서 만든 getter
	public dataHolder getDh() {
		return dh;
	}
	
	//현재 operatorInfo의 구조에 따라서 필요한 요소를 채워넣는다.
	public void extract() {
		
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
