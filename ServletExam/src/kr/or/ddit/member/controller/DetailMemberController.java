package kr.or.ddit.member.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.ddit.comm.service.AtchFileServiceImpl;
import kr.or.ddit.comm.service.IAtchFileService;
import kr.or.ddit.comm.vo.AtchFileVO;
import kr.or.ddit.member.service.IMemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.member.vo.MemberVO;

@WebServlet("/member/detail.do")
public class DetailMemberController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
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
		req.getRequestDispatcher("/views/member/detail.jsp").forward(req, resp);
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
