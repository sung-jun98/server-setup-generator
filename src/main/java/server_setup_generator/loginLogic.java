package server_setup_generator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import test.test_login;

//로그인에 관련된 메서드들 관련. 동적으로 자바 파일을 만드는 클래스이다.
public class loginLogic {
	public void execute() {
		try {
			Enhancer enhancer = new Enhancer();
			enhancer.setSuperclass(test_login.class);
			enhancer.setCallback((MethodInterceptor) (obj, method, args, proxy)->{
				if (method.getName().equals("service")) {
					// 메서드 내의 분기문 수정
                    if ((Integer) args[0] == 0) {
                        System.out.println("result == 0");
                    } else if ((Integer) args[0] == -1) {
                        System.out.println("result == -1");
                    } else if ((Integer) args[0] == -2) {
                        System.out.println("result == -2");
                    }
				}
				return proxy.invokeSuper(obj, args);
				
			});
			// 클래스 생성
            Class<?> modifiedClass = enhancer.createClass();
            
            // 변경된 클래스 파일 저장
            saveClassFile(modifiedClass, "../test");

            System.out.println("클래스 파일이 성공적으로 수정되어 저장되었습니다.");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	

    private static void saveClassFile(Class<?> modifiedClass, String outputDirectory) throws IOException {
        // 클래스 파일 저장
        byte[] modifiedClassBytes = SecurityActions.getClassBytes(modifiedClass);
        String className = modifiedClass.getName();
        String filePath = outputDirectory + File.separator + className.replace('.', File.separatorChar) + ".class";
        File outputFile = new File(filePath);
        outputFile.getParentFile().mkdirs();
        try (FileOutputStream fileOutputStream = new FileOutputStream(outputFile)) {
            fileOutputStream.write(modifiedClassBytes);
        }
    }
    
    private static class SecurityActions {
        static byte[] getClassBytes(Class<?> clazz) throws IOException {
            String className = clazz.getName().replace('.', '/') + ".class";
            return clazz.getClassLoader().getResourceAsStream(className).readAllBytes();
        }
    }
	
}
