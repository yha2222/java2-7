package kr.or.ddit.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.or.ddit.comm.service.AtchFileServiceImpl;
import kr.or.ddit.comm.service.IAtchFileService;
import kr.or.ddit.comm.vo.AtchFileVO;
import kr.or.ddit.member.service.IMemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.member.vo.MemberVO;

@MultipartConfig
@WebServlet("/member/insert.do")
public class InsertMemberController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		req.getRequestDispatcher("/views/member/insertForm.jsp").forward(req, resp);

	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String memId = req.getParameter("memId");
		String memName = req.getParameter("memName");
		String memTel = req.getParameter("memTel");
		String memAddr = req.getParameter("memAddr");
		
		IAtchFileService fileService = AtchFileServiceImpl.getInstance();
		
		AtchFileVO atchFileVO = null;
		try {
			atchFileVO = fileService.saveAtchFileList(req.getParts()); // atchFile ID 들어있다고 가정
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		IMemberService memService = MemberServiceImpl.getInstance();
		MemberVO mv = new MemberVO(memId, memName, memTel, memAddr);
		
		// atchFileId 있다면 꺼내서 추가?
		if(atchFileVO != null) {
			mv.setAtchFileId(atchFileVO.getAtchFileId());
		}
		
		int cnt = memService.registMember(mv);
		
		String msg = "";
		
		if(cnt > 0) {
			msg = "성공";
		}else {
			msg = "실패";
		}
		
		// session 준비
		HttpSession session = req.getSession();
		
		session.setAttribute("msg", msg);
		
		req.setAttribute("msg", msg);
		
		// forword 방식
		//req.getRequestDispatcher("/member/list.do").forward(req, resp);
		
		// redirect 방식
		resp.sendRedirect(req.getContextPath() + "/member/list.do");
	}
}
