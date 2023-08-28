package kr.or.ddit.basic;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/*
 	서블릿 3.0부터 지원하는 Part 인터페이스를 이용한 파일업로드 예제
 */
@MultipartConfig
@WebServlet("/upload2.do")
public class T13UploadControllerTest2 extends HttpServlet {
	private static final String UPLOAD_DIR = "upload_files";

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		// Multipart Parsing 전에 파라미터 값 조회해보기
		System.out.println("Multipart Parsing 전 => " + req.getParameter("sender"));
		
		// 웹애플리케이션 루트 디렉토리 기준... 업로드 경로 초기화
		String uploadPath = "d:/D_Other/" + UPLOAD_DIR;
		
		
		File uploadDir = new File(uploadPath);
		if(!uploadDir.exists()) {
			uploadDir.mkdir();
		}
		
		try {
			
			String fileName = "";
			
			for(Part part : req.getParts()) {
				
				System.out.println(part.getHeader("content-disposition")); // 각 파트 헤더 값만 찍어보긴
				
				// 꺼내오기
				/*
				 * System.out.println("전송된 part명 => " + part.getName()); // 여기서 null => 정상적인 파일
				 * 아님
				 * 
				 * System.out.println("전송된 파일명 => " + part.getSubmittedFileName()); // 저장할 때 이걸로 이름 저장하면 됨
				 */
				
				fileName = part.getSubmittedFileName();
				
				if(fileName != null && !fileName.equals("")) {
					// 파일 저장
					part.write(uploadPath + File.separator + fileName); // separator => 운영체제마다 표기법 알아서 맞춤 - util성 기능 -> 직접 / 해도 되긴 함
					
					System.out.println("파일 업로드 완료!!!" + uploadPath + File.separator + fileName);
				}
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		
	}
	
}
