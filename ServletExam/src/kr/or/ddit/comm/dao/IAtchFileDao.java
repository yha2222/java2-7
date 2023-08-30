package kr.or.ddit.comm.dao;

import java.util.List;

import kr.or.ddit.comm.vo.AtchFileVO;

public interface IAtchFileDao {
	
	// 첨부파일 저장
	public int insertAtchFile(AtchFileVO atchFileVO);
	
	// 첨부파일 상세정보 저장
	public int insertAtchFileDetail(AtchFileVO atchFileVO);
	
	// 첨부파일 목록 조회하기
	public List<AtchFileVO> getAtchFileList(AtchFileVO atchFileVO);
	
	// 첨부파일 세부정보 조회하기
	public AtchFileVO getAtchFileDetatil(AtchFileVO atchFileVO);
	
}
