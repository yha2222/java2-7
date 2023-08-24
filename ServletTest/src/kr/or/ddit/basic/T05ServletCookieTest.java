package kr.or.ddit.basic;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class T05ServletCookieTest extends HttpServlet {
/*
 	서블릿 쿠키에 대하여
 	(웹서버와 브라우저는 애플리케이션을 사용하는 동안 필요한 값을 쿠키를 통해 공유하여 상태를 유지함.)
 	1. 구성요소?
 	- 이름
 	- 값
 	- 유효기간(초)
 	- 도메인 : ex) www.somehost.com, .somehost.com 등
 	- 경로 : 쿠키를 공유할 기준 경로를 지정 => 지정하지 않으면 실행한 URL의 경로부분 사용됨
 	
 	2. 동작방식
 	- 쿠키생성단계 : 생성한 쿠키를 응답데이터의 헤더에 저장하여 웹브라우저에 전송
 	- 쿠키저장단계 : 웹브라우저는 응답데이터에 포함한 쿠키를 쿠키저장소에 보관
 	- 쿠키전송단계 : 웹브라우저는 저장한 쿠키를 요청이 있을 때마다 웹서버에 전송(삭제되기 전까지 계속 전송함)
 				 웹서버는 브라우저가 전송한 쿠키를 사용해서 필요한 작업을 수행
 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		// 쿠키 생성(설정) 예제
		//setCookieExam(req, resp);
		
		// 쿠키 정보 읽기 예제
		//readCookieExam(req, resp);
		
		// 쿠키 정보 삭제 예제
		deleteCookieExam(req, resp);
		
	}
	
	private void deleteCookieExam(HttpServletRequest req, HttpServletResponse resp) throws IOException {
	/*
	 	사용 중인 쿠키 정보 삭제하는 방법
	 	1. 사용 중인 쿠키 정보를 이용하여 쿠키 객체를 생성
	 	2. 쿠키 객체의 최대 지속 시간을 0으로 설정(삭제하겠다는 의미)
	 	3. 설정한 쿠키 객체를 응답 헤더에 추가하여 전송
	 */
	
		Cookie[] cookies = req.getCookies();
		
		///////////////////////////////////////
		
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html");
		
		PrintWriter out = resp.getWriter();
		
		String title = "쿠키정보 삭제 예제";
		
		out.println("<!DOCTYPE html><html><head><title>"
				+ title + "</title></head><body>");
		// 읽기 예제 긁어옴
		if(cookies != null) {
			out.println("<h2>" + title + "</h2>");
			
			for(Cookie cookie : cookies) {
				
				// userId만 삭제
				if(cookie.getName().equals("userId")) {
					// 쿠키 제거하기
					cookie.setMaxAge(0);  // 지우라는 얘기
					resp.addCookie(cookie); //??
					
					out.println("<p>삭제한 쿠키 : " + cookie.getName() + "</p><br>");
				}
				
				out.print("<p>name : " + cookie.getName() + "</p><br>");
				out.print("<p>value : " + cookie.getValue() + "</p><br>");
				
				out.print("<hr>");
			}
		}else {
			out.println("<h2>쿠키정보가 없습니다.</h2>");
		}
		out.println("</body></html>");
	
}

	private void readCookieExam(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// 현재 도메인에서 사용 중인 쿠키 객체 배열 가져오기
		Cookie[] cookies = req.getCookies();
		
		/////////////////////////////////////////
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html");
		
		PrintWriter out = resp.getWriter();
		String title = "쿠키정보 읽기 예제";
		
		out.println("<!DOCTYPE html><html><head><title>"
				+ title + "</title></head><body>");
		
		if(cookies != null) {
			out.println("<h2>" + title + "</h2>");
			
			for(Cookie cookie : cookies) {
				out.print("<p>name : " + cookie.getName() + "</p><br>");
				out.print("<p>value : " + cookie.getValue() + "</p><br>");
				
				out.print("<hr>");
			}
		}else {
			out.println("<h2>쿠키정보가 없습니다.</h2>");
		}
		out.println("</body></html>");
	
}

	private void setCookieExam(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		// 1. 쿠키를 생성한다. 사용불가문자(공백, []()=, "/?@:;) = RFC2965
		Cookie userId = new Cookie("userId", req.getParameter("userId"));
		// 쿠키값에 한글을 사용 시 인코딩 처리를 해준다.
		Cookie name = new Cookie("name", URLEncoder.encode(req.getParameter("name"), "UTF-8"));
		
		// 2. 쿠키 소멸 시간 설정(setMaxAge : 초단위)
		//   => 지정하지 않으면 웹브라우저를 종료할 때 쿠키를 함께 삭제한다.
		userId.setMaxAge(60 * 60 * 24);  // 하루(24시간)
		userId.setHttpOnly(true);  // 자바스크립트를 이용한 직접 접근 방지
		
		name.setMaxAge(60 * 60 * 48);    // 이틀(48시간)
		
		//////////////////////////////////
		
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html");
		
		// 응답헤더에 쿠키 추가하기
		resp.addCookie(userId);
		resp.addCookie(name);
		
		PrintWriter out = resp.getWriter();
		
		String title = "쿠키 설정 예제";
		
		out.println("<!DOCTYPE html><htm;><head><title>"
				+ title + "</title></head><body>"
				+ "<h1 align=\"center\">" + title + "</h1>"
				+ "<ul><li><b>ID</b>: "
				+ req.getParameter("userId") + "</li>"
				+ "<li><b>이름</b>: "
				+ req.getParameter("name") + "</li></ul>"
				+ "</body></html>"
				);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
