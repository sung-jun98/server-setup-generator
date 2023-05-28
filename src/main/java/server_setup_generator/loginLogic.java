package server_setup_generator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import test.test_login;

//로그인 기능과 관련되어있는 정보를 operatorInfo(DataHolder안에 있는 변수)와 Application Scope로부터 필요한 부분만 추출하는 클래스
public class loginLogic {
	private Map<String, Map<String, ArrayList<String>>> operatorInfo;
	
	public loginLogic(Map<String, Map<String, ArrayList<String>>> operatorInfo) {
		this.operatorInfo = operatorInfo; //사용할 데이터 객체를 생성자를 통해서 받아온다.
	}
	
	//test_login_apply.java에서 사용할 데이터를 추출한다.
	public void extract() {
		
		
	}
	

	/*
	 * private static void saveClassFile(Class<?> modifiedClass, String
	 * outputDirectory) throws IOException { // 클래스 파일 저장 byte[] modifiedClassBytes
	 * = SecurityActions.getClassBytes(modifiedClass); String className =
	 * modifiedClass.getName(); String filePath = outputDirectory + File.separator +
	 * className.replace('.', File.separatorChar) + ".class"; File outputFile = new
	 * File(filePath); outputFile.getParentFile().mkdirs(); try (FileOutputStream
	 * fileOutputStream = new FileOutputStream(outputFile)) {
	 * fileOutputStream.write(modifiedClassBytes); } }
	 * 
	 * private static class SecurityActions { static byte[] getClassBytes(Class<?>
	 * clazz) throws IOException { String className = clazz.getName().replace('.',
	 * '/') + ".class"; return
	 * clazz.getClassLoader().getResourceAsStream(className).readAllBytes(); } }
	 */
	
}
