package server_setup_generator;

import java.util.HashMap;
import java.util.Map;

public class operatorData_to_dataHolder{
	private OperatorData opData;
	public operatorData_to_dataHolder(OperatorData opData) {
		this.opData = opData;
	}
	
	public dataHolder change() {
	//flowchartData.getFlowchartData().getOperators().get("operator1").getProperties().getTitle()
		Map<String, Operator> id_opTitle = opData.getFlowchartData().getOperators(); //id_opTitle.get("0")과 같은 방식으로 접근가능
		Map<String, String> map_of_opID = new HashMap<String, String>(); //links를 파싱할 때 operator 정보가 정수로 표시된다. 이를 바꿀때 참고할 Map
		//System.out.println("id_opTitle의 size : " + id_opTitle.size());//테스트코드
		
		
		//반복문으로 title을 OperatorData로부터 데이터를 추출해 dataHolder안에 넣는다.
		dataHolder dataHolder = new dataHolder();
		for (Map.Entry<String, Operator> original_Operators: id_opTitle.entrySet()) {
			String id = original_Operators.getKey();
			Operator op = original_Operators.getValue();
			String title = op.getProperties().getTitle();
			
			
			
			map_of_opID.put(id, title);
			
			dataHolder.addOperatorTitle(title);//title 입력.
			//System.out.println("id : " + id + ", " + "title : " + title);//테스트용
			
		}
		
		//links정보 추출 및 DataHolder안에 set.
		Map<String, Link> original_links = opData.getFlowchartData().getLinks();
		for ( Map.Entry<String, Link> detail_links : original_links.entrySet()) {
			String fromOp = detail_links.getValue().getFromOperator();
			String fromConn = detail_links.getValue().getFromConnector();
			String toOp = detail_links.getValue().getToOperator();
			String toConn = detail_links.getValue().getToConnector();
			//정수형태의 ID가 있다면 이를 title명으로 바꾸어준다.
			fromOp = map_of_opID.get(fromOp);
			toOp = map_of_opID.get(toOp);
			
			//System.out.println("fromOp : " + fromOp + "\n fromConn : " + fromConn + "\n toOp : " +  toOp + "\n toConn : " + toConn + '\n');//테스트용 코드
				
			dataHolder.addLabel_connectedOps(fromOp, fromConn, toOp);
			dataHolder.addLabel_connectedOps(toOp, toConn, fromOp);
		}
		dataHolder.printData();
		
		return dataHolder; //저장 완료된 dataHolder를 반환한다.
	}
	
	
	
}
