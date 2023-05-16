package server_setup_generator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import test.test_login;

//로그인 기능과 관련되어있는 정보를 OperatorData로부터 찾아내어 DataHolder안에 넣는다.
public class loginLogic extends test_login{
	OperatorData opDA;
	public loginLogic(OperatorData opD) {
		this.opDA = opD; //사용할 데이터 객체를 생성자를 통해서 받아온다.
		
		
	}
	public void execute() throws IOException {
		
		System.out.println("loginLogic.excute : " + opDA.getFlowchartData().getOperators().get("operator1").getProperties().getTitle());
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
