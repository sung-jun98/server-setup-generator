package signUp;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpSession;

import dbLogic.documentDAO;
import server_setup_generator.dataHolder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

//회원가입 관련 오퍼레이터를 뽑아낸 뒤, 그 내용대로 dataHolder에 set해준뒤, 완성된 dataHolder를 리턴(getDh())까지 포함.
public class signUpLogic {

	//필요한 설정 변수
	private dataHolder dh;
	private HttpSession hs;
	private Map<String, Map<String, ArrayList<String>>> operatorInfo;
		
	//생성자
	public signUpLogic(dataHolder dh, HttpSession hs) {
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
		if(operatorInfo.containsKey("회원가입")) {
			Map<String, ArrayList<String>> signUpFunction = operatorInfo.get("회원가입");
			
			//'가입 페이지'의 input 요소들을 set
			dh.setSignUpID((String)hs.getAttribute("signUpID"));
			dh.setSignUpPW((String) hs.getAttribute("signUpPW"));
			dh.setSignUpEmail((String)hs.getAttribute("signUpEmail"));
	
			//'연결 페이지' - '리턴 페이지'관련 설정
			if(signUpFunction.containsKey("output_0")) {
				String SingUp_Success_Op = operatorInfo.get("회원가입").get("output_0").get(0); //'회원가입' - '연결 페이지'와 연결되어있는 오퍼레이터명
				Map<String, ArrayList<String>> signUpRedirect= operatorInfo.get(SingUp_Success_Op);
				//'회원가입' - '연결 페이지' 오퍼레이터가 '리턴 페이지'오퍼레이터의 두번째 input과 연결되어 있을 경우에는 URL을 set, 그 이외의 경우에는 앞에 경로를 붙여서 set
				 if (signUpRedirect.containsKey("input_1") && signUpRedirect.get("input_1").contains("회원가입")) {
					 dh.setSignUp_successPath((String) hs.getAttribute(SingUp_Success_Op));
				 }else {
					 //구버전(로컬서버에 파일형태로 저장)
					 //dh.setCorrect_path("/server_setup_generator/test/" + (String) sc.getAttribute(loginSuccess_Op));
					 //신버전(DB활용)
					 dh.setSignUp_successPath("/deploy/" + (String) hs.getAttribute(SingUp_Success_Op));
				 }
				//dh.setCorrect_path((String) sc.getAttribute(loginSuccess_Op));
			}else {
				//설정을 안했을경우, dataHolder내부에 있는 디폴트 값으로 설정이 될 것이다.
			}
			
			//'저장할 DB 정보(회원가입)'오퍼레이터에 있는 내용을 dataHolder에 set
			if(operatorInfo.containsKey("저장할 DB 정보(회원가입)")) {
				//'저장할 Table명'의 값을 set
				dh.setSignUpTableName_db((String) hs.getAttribute("SignUpDbTableName"));
				
				//'저장할 DB 정보(회원가입)' 오퍼레이터의 라벨 '회원ID'타이틀값 set
				if(operatorInfo.get("저장할 DB 정보(회원가입)").containsKey("output_1")) {
					String title_signUpID= operatorInfo.get("저장할 DB 정보(회원가입)").get("output_1").get(0);
					dh.setSignUpID_db(title_signUpID);
				}
				
				//'저장할 DB 정보(회원가입)' 오퍼레이터의 라벨 '비밀번호'타이틀값 set
				if(operatorInfo.get("저장할 DB 정보(회원가입)").containsKey("output_2")) {
					String title_signUpPW= operatorInfo.get("저장할 DB 정보(회원가입)").get("output_2").get(0);
					dh.setSignUpPW_db(title_signUpPW);
				}
				
				//'저장할 DB 정보(회원가입)' 오퍼레이터의 라벨 '이메일'타이틀값 set
				if(operatorInfo.get("저장할 DB 정보(회원가입)").containsKey("output_3")) {
					String title_signUpEmail= operatorInfo.get("저장할 DB 정보(회원가입)").get("output_3").get(0);
					dh.setSignUpEmail_db(title_signUpEmail);
				}
			}
			processDocFromDB();
		}
	}
	
	//1. DB에서 '가입 페이지'로 되어있는 HTML페이지를 가져온다.
	//2. 가져온 페이지를 Document객체로 만들고, 거기서 action값을 바꾸는 작업을 한다. 
	//3. 바뀌어진 HTML페이지를 다시 DB에 '덮어씌운'다.
	public void processDocFromDB() {
		String contentPageName = (String)hs.getAttribute("가입 페이지");
		documentDAO docDAO = new documentDAO();
		//1. DB에서 '가입 페이지'로 되어있는 HTML페이지를 가져온다.
		String contentFromDB = docDAO.readDocument(contentPageName);
		
		 //2. 가져온 페이지를 Document객체로 만들고, 거기서 action값을 바꾸는 작업을 한다. 
	    if (contentFromDB != null) {
	        Document doc = Jsoup.parse(contentFromDB, "UTF-8");
	        // 여기에 추가적인 작업 수행
	        // ...
			Elements forms = doc.select("form");

			boolean isSignUpIDExist = false;
			boolean isSignUpPWExist = false;
			boolean isSignUpEmailExist = false;

			
			//문제가 하나 생긴게, 클라이언트가 태그명을 입력하기 전에 미리 웹사이트 먼저 업로드를 한다면, 밑의 코드 결과 디폴트값이 적용된다. 
			String val_of_signUpID = (String)hs.getAttribute("signUpID");
			String val_of_signUpPW = (String) hs.getAttribute("signUpPW");
			String val_of_signUpEmail = (String) hs.getAttribute("signUpEmail");
			
			// 모든 form 내부에 있는 모든 input태그의 name값을 비교
			for (Element form : forms) {
				Elements inputs = form.select("input[name]");
				
				for (int i = 0; i < inputs.size(); i++) {
					if (inputs.get(i).attr("name").equals(val_of_signUpID)) {
						isSignUpIDExist = true;
					} else if (inputs.get(i).attr("name").equals(val_of_signUpPW)) {
						isSignUpPWExist = true;
					}else if (inputs.get(i).attr("name").equals(val_of_signUpEmail)) {
						isSignUpEmailExist = true;
					}
					//System.out.println("(processHTML) " + isBbsTitleExist + " / "  + isBbsContentExist);
				}
				
				
				// 두 input 태그를 모두 포함하는 form일 경우 -> form 태그의 action 속성을 바꾸어준다.
				if (isSignUpIDExist && isSignUpPWExist && isSignUpEmailExist) {
						System.out.println("form의 action 속성값 변경 전 값은 : " + form.attr("action"));//
						form.attr("action", "../signUp");
						System.out.println("(signUpLogic.java) form의 action 속성값은 : " + form.attr("action"));//

						// form의 내부 속성에 hidden타입의 input을 이용하여 sessionID를 넣어준다.
						// 새로운 Element 객체 생성
						Element attrSessionID = doc.createElement("input");

						// Element에 속성 추가
						attrSessionID.attr("type", "hidden");
						attrSessionID.attr("name", "sessionID");
						attrSessionID.attr("value", (String) hs.getId());
						form.appendChild(attrSessionID);
						// sessionID 속성 값을 출력
						Element sessionInput = form.select("input[name=sessionID]").first();
						System.out.println("(signUpLogic/processDocFromDB) hidden input의 sessionID : " + sessionInput.attr("value"));

						// 이 밑에 바꾼 속성을 바탕으로 새로 파일을 만들어 주는 코드를 만들어야 한다.
						//saveAtServer(doc, filename);
						//3. 바뀌어진 HTML페이지를 다시 DB에 '덮어씌운'다.
						documentDAO docDAO2 = new documentDAO();
						docDAO2.saveDocument(doc, contentPageName);
						
					}
			}
	    }
	}
}

















