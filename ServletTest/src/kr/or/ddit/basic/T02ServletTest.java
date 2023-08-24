package kr.or.ddit.basic;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class T02ServletTest extends HttpServlet {

/*
 	서블릿 동작 방식에 대하여
 	
 	1. 사용자(클라이언트)가 URL을 클릭하면 HTTP Request를 서블릿 컨테이너로 전송
 	2. 컨테이너는 web.xml에 정의된 URL패턴을 확인하여 어느 서블릿을 통해 처리해야 할지를 검색
 		(로딩이 안된 경우에는 로딩 처리함. 로딩 시 init()메서드 호출됨)
 	3. 서블릿 컨테이너는 요청을 처리할 개별 스레드 객체를 생성하려 해당 서블릿 객체의 service()메서드를 호출
 		(HttpServletRequest 및 HttpServletResponse 객체를 넘겨줌)
 	4. service()메서드는 메서드 타입을 체크하여 적절한 메서드를 호출
 		(doGet, doPost, doPut, doDelte 등 -> do~~ 중에 브라우저 방식으로 요청하는 거)
 	5. 요청 처리가 완료되면 HttpServletRequest 및  HttpServletResponse객체는 소멸
 	6. 컨테이너로부터 서블릿이 제거되는 경우에 destroy()메서드가 호출
 */

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		// 요청 객체의 메서드 확인하기
		System.out.println("getCharacterEncoding() : " + req.getCharacterEncoding());
		System.out.println("getContentLength() : " + req.getContentLength());
		System.out.println("getQueryString() : " + req.getQueryString());
		System.out.println("getProtocol() : " + req.getProtocol());
		System.out.println("getMethod() : " + req.getMethod());
		System.out.println("getRequestURI() : " + req.getRequestURI());
		System.out.println("getRemoteAddr() : " + req.getRemoteAddr());
		System.out.println("getRemotePort() : " + req.getRemotePort());
		
		// 요청 메시지로부터 name값을 가져오기
		String name = req.getParameter("name");
		
		System.out.println("name => " + name);
		
		// 요청 객체에 정보 저장하기
		req.setAttribute("tel", "1111-1111");
		req.setAttribute("addr", "대전시 중구 오류동");
		
		// 요청 객체에 저장된 정보 꺼내오기
		System.out.println("tel => " + req.getAttribute("tel"));
		System.out.println("addr => " + req.getAttribute("addr"));
		
		////////////////////////////////
		
		resp.setCharacterEncoding("UTF-8"); // 인코딩 정보 알려줌
		resp.setContentType("text/plain");
		
		PrintWriter out = resp.getWriter(); // 입력하면 상대방에게 response message
		out.println("name => " + name);
		out.println("서블릿 경로 => " + req.getServletPath());
		out.println("서블릿 컨택스트 경로 => " + req.getContextPath());
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
