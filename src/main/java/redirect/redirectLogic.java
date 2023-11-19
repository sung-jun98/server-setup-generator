package redirect;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpSession;

import server_setup_generator.dataHolder;

//'리다이렉트 설정'오퍼레이터 관련 로직을 dataHolder 안에 Setting해준다. 
//staticWebReturn.java에서 사용하며, processHTML.java에서 DB와 Session에 입력한 정보를 바탕으로 동작한다.
public class redirectLogic {
	private dataHolder dh;
	//private ServletContext sc;
	private HttpSession sc;
	private Map<String, Map<String, ArrayList<String>>> operatorInfo;
	
	//생성자
	public redirectLogic(dataHolder dh, HttpSession sc) {
		this.dh = dh; //사용할 데이터 객체를 생성자를 통해서 받아온다.
		this.sc = sc;
		
		this.operatorInfo = dh.getOperatorInfo();
	}
	//extract()를 완료한 dataHolder를 반환받기 위해서 만든 getter
	public dataHolder getDh() {
		return dh;
	}


	//processHTML에서 저장한 HTML파일을 DB에서 불러온다.그 다음 내부에 a태그를 설정해준다.
	public void extract() {
	
	
	}
}
