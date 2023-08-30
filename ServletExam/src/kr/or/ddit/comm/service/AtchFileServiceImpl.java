package kr.or.ddit.comm.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import kr.or.ddit.comm.dao.AtchFileDaoImpl;
import kr.or.ddit.comm.dao.IAtchFileDao;
import kr.or.ddit.comm.vo.AtchFileVO;

public class AtchFileServiceImpl implements IAtchFileService {
	
	private static IAtchFileService fileService;
	
	private IAtchFileDao fileDao;
	
	private AtchFileServiceImpl() {
		fileDao = AtchFileDaoImpl.getInstance();
	}
	
	public static IAtchFileService getInstance() {
		if(fileService == null) {
			fileService = new AtchFileServiceImpl();
		}
		return fileService;
	}

	@Override
	public AtchFileVO saveAtchFileList(HttpServletRequest req) throws Exception { // interface에도 throws 해줘야 에러 안남
		
		String uploadPath = "d:/D_Other/upload_files";
		
		// 파일 경로 있는지 확인 없으면 만듦
		File uploadDir = new File(uploadPath);
		if(uploadDir.exists()) {
			uploadDir.mkdir();
		}
		
		AtchFileVO atchFileVO = null;
		
		boolean isFirstFile = true;  // 첫번째 파일 여부
		
		for(Part part : req.getParts()) {

			// 파일명 추출
			String fileName = part.getSubmittedFileName();
			
			// 파일 첨부 여부 확인
			if(fileName != null && !fileName.equals("")) { // 파일인 경우...
				
				if(isFirstFile) {
					isFirstFile = false; // 처음 아니면 안 타게
					
					// ATCH_FILE에 저장 - 부모에는 첫번째 한번만
					atchFileVO = new AtchFileVO();
					fileDao.insertAtchFile(atchFileVO);
				}
				
				String orignFileName = fileName; // 가져온 파일?이름 저장
				long fileSize = part.getSize(); // 파일 크기
				String saveFileName= "";		// 저장파일명
				String saveFilePath = "";
				
				// 저장할 파일 이름 랜덤 생성
				saveFileName = UUID.randomUUID().toString().replace("-", "");  // 유효 아이디 동일한 사이즈로 랜덤 추출하고 '-' 제거
				// !!이거 있어야 실질적 파일 다운로드 가능!!
				saveFilePath = uploadPath + File.separator + saveFileName;
				
				// 확장자명 추출
				// lastIndexOf로 마지막 점 위치 찾아서 리턴 => 0보다 작다 => 확장자명 없다
				String fileExtension = orignFileName.lastIndexOf(".") < 0 ? "" : 
					orignFileName.substring(orignFileName.lastIndexOf(".") + 1); // 확장자 잘라냄
				
				// 업로드 파일(원본파일) 저장하기
				part.write(saveFilePath); // 정해놓은 경로로 저장
				
				atchFileVO.setFileStreCours(saveFilePath);
				atchFileVO.setStreFileNm(saveFileName);
				atchFileVO.setFileSize(fileSize);
				atchFileVO.setFileExtsn(fileExtension);
				atchFileVO.setFileCn(""); // 설명란 -> 공백
				
				// ATCH_FILE_DETAIL에 저장 - 나머지(첨부)
				// 오리지널 정보 춫ㄹ
				fileDao.insertAtchFileDetail(atchFileVO); // 위에 객체 똑같이 그대로 이용
				
				// 임시 업로드 파일 삭제하기
				// 임시 저장 공간에 파일 떨궜을 때 그거 냅두면 디스크 낭비임..그런 거 삭제용..(업로드한 파일 지우는 거 아님)
				part.delete(); 
			}
		}
		
		return atchFileVO;
	}

	// dao가 처리한 결과만 받아옴
	@Override
	public List<AtchFileVO> getAtchFileList(AtchFileVO atchFileVO) {
		return fileDao.getAtchFileList(atchFileVO);
	}

	@Override
	public AtchFileVO getAtchFileDetail(AtchFileVO atchFileVO) {
		return fileDao.getAtchFileDetatil(atchFileVO);
	}

	public static void main(String[] args) {
		System.out.println(UUID.randomUUID().toString().replace("-", "")); 
	}
}
