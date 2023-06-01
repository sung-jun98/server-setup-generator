package server_setup_generator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//클래스와 클래스간에 데이터를 주고받을 때 쓰는 중간클래스. 이를 나중에 애플리케이션 스코프에 넣어서 동시성 문제 해결할 것이다. 
public class dataHolder implements Serializable {
	private Map<String, Map<String, ArrayList<String>>> OperatorInfo; // canvas내 operator간의 관계도
	
	private String id;
	private String password;
	
	private boolean correct_check;
	private String correct_path = "/server_setup_generator/test/loginResult.jsp"; 
	//경로명은 나중에 processHTML.java 완성할때까지 하드코딩으로 이렇게 둔다. 밑에 나머지도 마찬가지
	private boolean id_error_check;
	private String id_error_path = "/server_setup_generator/test/loginResult2.jsp";
	
	private boolean pw_error_check;
	private String pw_error_path = "/server_setup_generator/test/loginResult2.jsp";
	
	private boolean db_error_check; //만약 체크 안되어 있을 경우, 원래 페이지로 리다이렉트 되도록 한다. 
	private String db_error_path = "/server_setup_generator/test/loginResult2.jsp";
	
	private String login_startPage; //만약 실패시 연결 페이지를 설정 안했다면, 디폴트로 해당 페이지로 이동하도록 한다.
	
	//OperatorInfo에 대한 getter
	public Map<String, Map<String, ArrayList<String>>> getOperatorInfo() {
        return OperatorInfo;
    }
	
	public dataHolder(){
		OperatorInfo = new HashMap<String, Map<String, ArrayList<String>>>(); //생성자를 받으면 외부 Map을 하나 생성한다.
	}
	
	//title 추가
	public void addOperatorTitle(String title) {
		OperatorInfo.put(title, new HashMap<String, ArrayList<String>>()); //title이 하나씩 추가할 때마다 OperatorInfo에 Map을 하나씩 생성한다.
	}
	
	//title 밑에 label과 connectedOp 추가(만약 이미 label이 존재할 시, connectedOp 배열 뒤에 추가하는 형식으로 한다.)
	public void addLabel_connectedOps(String title, String label, String connectedOp) {
		Map<String, ArrayList<String>> label_conOps = OperatorInfo.get(title);
		
		System.out.println("label_conOps : " + label_conOps.entrySet()); //테스트코드
		
		if(label_conOps.isEmpty()) {
			label_conOps = new HashMap<>();
			OperatorInfo.put(title, label_conOps);
		}
		
		ArrayList<String> connectedOperators = label_conOps.get(label);//만약 해당 label에 관한 다이어그램이 처음 들어왔을 시
		if(connectedOperators == null) {
			connectedOperators = new ArrayList<>();
			label_conOps.put(label, connectedOperators);
		}
		connectedOperators.add(connectedOp);
		
		
	}
	 //구조 테스트해보기 위한 코드
	  public void printData() {
		  
	        for (String title : OperatorInfo.keySet()) {
	            System.out.println("Title: " + title);
	            Map<String, ArrayList<String>> label_conOps = OperatorInfo.get(title);
	            for (String label : label_conOps.keySet()) {
	                System.out.println("Label: " + label);
	                ArrayList<String> connectedOperators = label_conOps.get(label);
	                for (String connectedOp : connectedOperators) {
	                    System.out.println("Connected Operator: " + connectedOp);
	                }
	            }
	            System.out.println("--------------------------");
	        }
	    }
	 //=========================================================
	//==================getter/setter 모음========================
	  public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public boolean isCorrect_check() {
			return correct_check;
		}

		public void setCorrect_check(boolean correct_check) {
			this.correct_check = correct_check;
			
			//System.out.println("Correct_check : " + correct_check);//
		}

		public boolean isId_error_check() {
			return id_error_check;
		}

		public void setId_error_check(boolean id_error_check) {
			this.id_error_check = id_error_check;
			
			//System.out.println("iderror_check : " + id_error_check);//
		}

		public boolean isPw_error_check() {
			return pw_error_check;
		}

		public void setPw_error_check(boolean pw_error_check) {
			this.pw_error_check = pw_error_check;
			
			//System.out.println("pw_error_check : "+ pw_error_check);
		}

		public boolean isDb_error_check() {
			return db_error_check;
		}

		public void setDb_error_check(boolean db_error_check) {
			this.db_error_check = db_error_check;
			
			//System.out.println("db_error_check : " + db_error_check);
		}
			
		//경로에 관한 setter는 완성될 상대경로까지 고려해서 만든다.
		public String getLogin_startPage() {
			return login_startPage;
		}

		public void setLogin_startPage(String login_startPage) {
			this.login_startPage = login_startPage;
			System.out.println("loginStartPage : " + login_startPage);
		}
}


