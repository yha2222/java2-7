package kr.or.ddit.member.controller;

import java.io.IOException;
import java.util.List;

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
@WebServlet("/member/update.do")
public class UpdateMemberController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		// 기본 정보 조회 => key값 필요
		String memId = req.getParameter("memId");
		
		IMemberService memService = MemberServiceImpl.getInstance();
		
		MemberVO mv = memService.getMember(memId);
		
		req.setAttribute("mv", mv);
		
		if(mv.getAtchFileId() > 0) { // 첨부파일 있으면
			
			IAtchFileService fileService = AtchFileServiceImpl.getInstance();
			AtchFileVO atchFileVO = new AtchFileVO(); // List 반환
			atchFileVO.setAtchFileId(mv.getAtchFileId());
			List<AtchFileVO> fileList = fileService.getAtchFileList(atchFileVO);
			
			req.setAttribute("fileList", fileList);
		}
		
		
		// Form에 넣을 데이터 추가하는 작업..
		req.getRequestDispatcher("/views/member/updateForm.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String memId = req.getParameter("memId"); 
		String memName = req.getParameter("memName");
		String memTel = req.getParameter("memTel");
		String memAddr = req.getParameter("memAddr");
		String atchFileId = req.getParameter("atchFileId");
		
		IAtchFileService fileService = AtchFileServiceImpl.getInstance();
		
		AtchFileVO atchFileVO = null;
		
		try {
			
			atchFileVO = fileService.saveAtchFileList(req.getParts()); // 저장되지 않으면 null값
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		
		
		IMemberService memService = MemberServiceImpl.getInstance();
		MemberVO mv = new MemberVO(memId, memName, memTel, memAddr);
		
		if(atchFileVO == null) { // 새로운 첨부파일이 존재하지 않는 경우..
			mv.setAtchFileId(Long.parseLong(atchFileId));
		}else { // null이 아님
			mv.setAtchFileId(atchFileVO.getAtchFileId());
		}
		
		int cnt = memService.modifyMember(mv);
		
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
