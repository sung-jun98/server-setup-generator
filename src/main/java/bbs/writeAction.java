package bbs;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server_setup_generator.dataHolder;

//클라이언트가 업로드한 웹페이지중, 게시물 업로드와 관련된 form은 여기로 연결된다.
@WebServlet("/writeAction")
public class writeAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	dataHolder dh;
    //필요한 설정 변수들 디폴트값 정의 -> 나중에 dataHolder로부터 deserialize 한 값으로 덮어씌울 것이다.
	String bbsTitle = "bbsTitle";
	String bbsContent = "bbsContent";
	String userID = "userID";
	
	String successPath = "flowchart/demo.jsp"; //결과가 성공적일시 
	String dataHolderPath = "dataHolder.ser";
	
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		bbsDAO bbsDAO = new bbsDAO();
		this.dataHolderPath = "dataHolder" + (String) request.getParameter("sessionID") + ".ser";

		//dataHolder 역직렬화 
		try {
			this.dh = deserializeDataHolder();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		//dataHolder로부터 분석
		analyzeDataHolder(this.dh);
		//==============================================
		//==========여기서부터 실질적인 게시물 업로드 로직=========
		
		//클라이언트가 게시물 제목이나 내용을 작성하지 않았을 시
		if(request.getParameter(bbsTitle) == null || request.getParameter(bbsContent) == null) {
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('입력 안된 사항이 있습니다.')");
			script.println("history.back()");
			script.println("</script>");
			
		}else {
			//DB에 저장이 실패했을시 
			int result = bbsDAO.write(request.getParameter(bbsTitle), request.getParameter(userID), 
					request.getParameter(bbsContent));
			if(result == -1) {
				PrintWriter script = response.getWriter();
				script.println("<script>");
				script.println("alert('글쓰기에 실패했습니다.')");
				script.println("history.back()");
				script.println("</script>");
			}else {
				//DB에 저장 성공했을 시 다음 연결 페이지로 이동
				response.sendRedirect(successPath);
			}
		}
	}
	//================핵심로직 끝=====================
	
	//직렬화된 dataHolder 역직렬화 해주는 메서드
		private dataHolder deserializeDataHolder() throws ClassNotFoundException {
			//.ser가 있는 경로
			//String filePath = "C:\\Users\\tjdwn\\Dev\\Workspace\\server_setup_generator\\src\\test\\dataHolder.ser";
			String filePath = "C:\\Users\\tjdwn\\Dev\\Workspace\\server_setup_generator\\src\\test\\" + this.dataHolderPath;
			
			try {
				FileInputStream fileIn = new FileInputStream(filePath);
				ObjectInputStream objectIn = new ObjectInputStream(fileIn);
				
				dataHolder dh = (dataHolder)objectIn.readObject(); // 객체 역직렬화
				
				//열었던 steam을 닫아준다.
				objectIn.close();
				fileIn.close();
				
				return dh;
			}catch(IOException e) {
				e.printStackTrace();
			} 
			return null;
		}
	
	//역직렬화된 dataHolder로부터 맞춤 데이터 추출하는 함수 구현해야 한다.
		
	private void analyzeDataHolder(dataHolder dh) {
		this.bbsTitle = dh.getBbsTitle();
		this.bbsContent = dh.getBbsContent();
		this.userID = dh.getUserID();
		
		this.successPath = dh.getSuccessPath();
		
		
		
	}
}
