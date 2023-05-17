package server_setup_generator;
//클래스와 클래스간에 데이터를 주고받을 때 쓰는 중간클래스. get/set메서드를 통해서 데이터를 조작해야하므로 getter,setter를 만들어준다.
public class dataHolder {
	private static dataForLogin dataForLogin;

	public static dataForLogin getDataForLogin() {
		return dataForLogin;
	}

	public static void setDataForLogin(dataForLogin dataForLogin) {
		dataHolder.dataForLogin = dataForLogin;
	}
	
	
}

//로그인 오퍼레이터와 관련되어있는 데이터를 저장할 클래스
class dataForLogin{
	//userDAO관련 정보들 -> 스키마이름, dbID, dbPW, 로그인관련 테이블명, ID필드명, PW필드명
	
	
	//다이어그램간의 links 관련 (boolean타입)
}