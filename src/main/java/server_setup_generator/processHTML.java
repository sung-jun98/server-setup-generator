package server_setup_generator;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

//input 태그의 file 업로드를 했을 떄 연결될 서블릿. 그리고 이 서블릿의 파싱 결과는 ServletContext에 저장된다.
@WebServlet("/processHTML")
@MultipartConfig
public class processHTML extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("processHTML 서블릿 실행 시작");
		request.setCharacterEncoding("utf-8"); //한글꺠짐 방지
		
		// 이 밑에 getPart를 통해 name속성에 따라 다른 로직이 실행되게끔 분기문으로 조절해야 할 것 같다.
		Part filePart = request.getPart("loginStartPage"); //HTML 에서 지정한 name 속성으로 Part 인터페이스를 만든다.
		String filename = filePart.getSubmittedFileName(); //Part 인터페이스에서 파일의 원래 이름을 읽어들이는 메서드.
		String opTitle = request.getParameter("opTitle");
		
		//System.out.println("HTML 파일 수신 완료/ filename : " + filename);//테스트용 
		System.out.println("opTitle은 " + opTitle + '\n');//테스트
		//파일업로드를 한 오퍼레이터의 title이 키가 되고, 업로드한 파일명이 value가 된 채로 ServletContext에 업로드
		getServletContext().setAttribute(opTitle, filename); 
		
		//우선 오퍼레이터 '입력값'에서 파일을 업로드했을시
		if(opTitle.equals("입력값")) {
			
			//임시 저장할 파일 객체 만들기. 나중에 .jsp확장자 말고 .html일 경우도 정의해주기
			File tempFile = File.createTempFile("loginTempFile", ".jsp"); 
			//클라이언트로부터 입력받은 파일을 tempFile에 복사한다.
			try(InputStream partInputStream = filePart.getInputStream()){
				Files.copy(partInputStream, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
			}catch(IOException e) {
				System.out.println(e); 
			}
			
			
			//임시 파일을 Jsoup으로 로드
			Document doc = Jsoup.parse(tempFile, "UTF-8");
			
			Elements forms = doc.select("form");
			
			boolean isIDinputExist = false;
			boolean isPWinputExist = false;
			String val_of_id = (String) getServletContext().getAttribute("inputID");
			String val_of_pw = (String) getServletContext().getAttribute("inputPW");
			System.out.println("val_of_id : " + val_of_id + "\n val_of_pw : " + val_of_pw);
			//모든 form 내부에 있는 모든 input태그의 name값을 비교
			for (Element form : forms) {		
				Elements inputs = form.select("input[name]");
				for(int i=0; i<inputs.size(); i++) {
					if(inputs.get(i).attr("name").equals(val_of_id)) {
						isIDinputExist = true;
					}else if(inputs.get(i).attr("name").equals(val_of_pw)) {
						isPWinputExist = true;
					}
					
					//두 input 태그를 모두 포함하는 form일 경우 -> form 태그의 action 속성을 바꾸어준다.
					if(isIDinputExist && isPWinputExist) {
						form.attr("action", "../test_login_apply");
						System.out.println("form의 action 속성값은 : " + form.attr("action"));
						
						// 이 밑에 바꾼 속성을 바탕으로 새로 파일을 만들어 주는 코드를 만들어야 한다.
					}
					
				}
			}
			
			
			tempFile.delete(); 
		}
		
	}
	
	//아직 미완성
	//오퍼레이터 '리턴 페이지'에서 클라이언트가 파일 업로드를 했을 시 
	private void checkRegex(String opTitle, Part filePart) {
		//정규식 정의 -> Matcher에 등록. 만약 opTitle이 리턴 페이지로 시작하면 matcher.find() 값은 true, 아닐시 false
		String pattern = "^리턴 페이지";
		Matcher matcher = Pattern.compile(pattern).matcher(opTitle);
		
		if(matcher.find()) {
			//이 밑에 리턴 페이지 파일을 물리적으로 저장하는 로직 작성
		}
		
	}

}
