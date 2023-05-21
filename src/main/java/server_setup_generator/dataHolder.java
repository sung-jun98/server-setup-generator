package server_setup_generator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//클래스와 클래스간에 데이터를 주고받을 때 쓰는 중간클래스. 이를 나중에 애플리케이션 스코프에 넣어서 동시성 문제 해결할 것이다. 
public class dataHolder {
	private Map<String, Map<String, ArrayList<String>>> OperatorInfo; // 여기에 저장된다.
	
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
	
}


