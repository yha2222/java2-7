package kr.or.ddit.basic;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class T07ServletContextTest extends HttpServlet {
/*
 	서블릿 컨텍스트 객체에 대해
 	1. 서블릿 프로그램이 컨테이너와 정보를 주고 받기 위한 메서드 제공
 	 ex. 파일의 MIME TYPE정보 가져오기, 요청 정보 보내기, 로깅 등
 	 
 	2. 웹어플리케이션 당 1개씩 생성됨
 */
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ServletContext ctx = req.getServletContext();
		
		System.out.println("서블릿 컨텍스트의 기본 경로 : "
				+ ctx.getContextPath());
		System.out.println("서버 정보 : " + ctx.getServerInfo());
		System.out.println("서블릿 API의 메이저 버전 정보: "
				+ ctx.getMajorVersion());
		System.out.println("서블릿 API의 마이너 버전 정보: "
				+ ctx.getMinorVersion());
		System.out.println("파일에 대한 MIME 타입 정보: "
				+ ctx.getMimeType("hello.mp3"));
		System.out.println("파일시스템 상의 실제 경로: "
				+ ctx.getRealPath("/"));
		
		// 속성값 저장
		ctx.setAttribute("attr1", "속성1"); // 지금 예가 string인 거지 아무거나 다 때려 넣을 수 있다
		// 속성값 변경
		ctx.setAttribute("attr1", "속성 11");
		// 속성값 가져오기
		System.out.println("attr1의 속성값: " + ctx.getAttribute("attr1")); 
		// 속성값 제거하기
		ctx.removeAttribute("attr1");
		
		// 로깅작업 하기
		ctx.log("서블릿 컨텍스트를 이용한 로깅 작업 중입니다...");
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
