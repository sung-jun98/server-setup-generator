package redirect;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import dbLogic.documentDAO;
import server_setup_generator.dataHolder;

//'리다이렉트 설정'오퍼레이터 관련 로직을 dataHolder 안에 Setting해준다. 
//staticWebReturn.java에서 사용하며, processHTML.java에서 DB와 Session에 입력한 정보를 바탕으로 동작한다.
//redirectSetting : DB에서 역직렬화후, 필요한 작업후 다시 DB에 덮어씌운다.
	
public class redirectLogic {
	private dataHolder dh;
	//private ServletContext sc;
	private HttpSession hs;
	private Map<String, Map<String, ArrayList<String>>> operatorInfo;
	
	//생성자
	public redirectLogic(dataHolder dh, HttpSession hs) {
		this.dh = dh; //사용할 데이터 객체를 생성자를 통해서 받아온다.
		this.hs = hs;
		
		this.operatorInfo = dh.getOperatorInfo();
	}
	//extract()를 완료한 dataHolder를 반환받기 위해서 만든 getter
	public dataHolder getDh() {
		return dh;
	}


	//processHTML에서 저장한 HTML파일을 DB에서 불러온다.그 다음 내부에 a태그를 설정해준다.
	public void extract() {
		if(operatorInfo.containsKey("리다이렉트 설정")) {
			Map<String, ArrayList<String>> redirectFunction = operatorInfo.get("리다이렉트 설정");
			
			//'가입 페이지'의 input 요소들을 set
			/*dh.setSignUpID((String)hs.getAttribute("signUpID"));
			dh.setSignUpPW((String) hs.getAttribute("signUpPW"));
			dh.setSignUpEmail((String)hs.getAttribute("signUpEmail"));
	*/
			//'리다리엑트 설정' 로직 관련 설정
			if(redirectFunction.containsKey("output_0")) {
				String redirect_Success_Op = operatorInfo.get("리다이렉트 설정").get("output_0").get(0); //'회원가입' - '연결 페이지'와 연결되어있는 오퍼레이터명
				Map<String, ArrayList<String>> redirectResult = operatorInfo.get(redirect_Success_Op);
				//'리다이렉트 설정'오퍼레이터가 '리턴 페이지'오퍼레이터의 첫번쨰 input과 연결되어 있을 경우에는 URL을 set, 그 이외의 경우에는 앞에 경로를 붙여서 set
				 if (redirectResult.containsKey("input_1") && redirectResult.get("input_1").contains("리다이렉트 설정")) {
					 //hs에 있는 태그 밑에 URL을 a태그의 action값으로 설정해주는 코드
					 redirectSetting((String) hs.getAttribute(redirect_Success_Op));
					 //dh.setSignUp_successPath((String) hs.getAttribute(redirect_Success_Op));
					 
				 }else {
					 //구버전(로컬서버에 파일형태로 저장)
					 //dh.setCorrect_path("/server_setup_generator/test/" + (String) sc.getAttribute(loginSuccess_Op));
					 //신버전(DB활용)
					 redirectSetting("/deploy/" + (String) hs.getAttribute(redirect_Success_Op));
					 //dh.setSignUp_successPath("/deploy/" + (String) hs.getAttribute(redirect_Success_Op));
				 }
			}	
		}
	
	}
	//1. DB에서 '가입 페이지'로 되어있는 HTML페이지를 가져온다.
	//2. 해당 페이지중 입력받은 태그명과 일치하는 element를 찾는다.
	//3. 해당 Element밑에 a태그를 삽입한뒤
	//4. 다시 DB에 '덮어씌'운다.
	public void redirectSetting(String destination) {
		String contentPageName = (String)hs.getAttribute("리다이렉트 설정");
		documentDAO docDAO = new documentDAO();
//1. DB에서 '가입 페이지'로 되어있는 HTML페이지를 가져온다.
		String contentFromDB = docDAO.readDocument(contentPageName);
		
		// Jsoup을 사용하여 HTML 파싱
        Document doc = Jsoup.parse(contentFromDB);
        String targetTagName = (String) hs.getAttribute("redirectTagName"); //클라이언트가 원하는 태그명
       
// 2. 해당 페이지중 입력받은 태그명과 일치하는 element를 찾는다.
        for (Element element : doc.select(targetTagName)) {
        	String existingOnClick = element.attr("onclick");
        	 // 이미 기존에 onclick 속성이 있는 경우, 기존 값에 새로운 동작을 추가
            if (!existingOnClick.isEmpty()) {
                existingOnClick += ";";
            }

            // 새로운 동작 추가 (여기에서는 페이지 리다이렉트)
            existingOnClick += "window.location.href='" + destination + "';";

            // onclick 속성 설정
            element.attr("onclick", existingOnClick);
        	//Element aTag =  doc.createElement("a");
            //aTag.attr("href", destination); // 원하는 링크 주소 설정
            //aTag.text("Link Text"); // 원하는 링크 텍스트 설정
            // 현재 선택된 태그 아래에 <a> 태그 추가
            //element.appendChild(aTag);
        }
//4. 다시 DB에 '덮어씌'운다.
        documentDAO docDAO2 = new documentDAO();
        docDAO2.saveDocument(doc, contentPageName);
        // 조작된 HTML을 문자열로 반환
	}
}











