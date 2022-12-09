package org.SSAFP.UniCode.model.board.service;

import java.io.File;
import java.util.List;

import org.SSAFP.UniCode.model.board.dto.ExhibitBoard;
import org.SSAFP.UniCode.model.board.dto.FileInfo;
import org.SSAFP.UniCode.model.board.dto.Language;
import org.SSAFP.UniCode.model.board.dto.RecruitBoard;
import org.SSAFP.UniCode.model.board.repo.ExhibitBoardRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ExhibitBoardServiceImpl {

	@Autowired
	ExhibitBoardRepo exhibitBoardRepo;
	
	@Transactional
	public boolean writeArticle(ExhibitBoard exhibitBoard) throws Exception{
		boolean write = exhibitBoardRepo.writeArticle(exhibitBoard);
		if(write && exhibitBoard.getFileList() != null) {
			write = exhibitBoardRepo.uploadFileList(exhibitBoard);
		}
		if (write && exhibitBoard.getImageList() != null) {
			write = exhibitBoardRepo.uploadImageList(exhibitBoard);
		}
		return write;
	}
	
	public boolean modifyArticle(ExhibitBoard exhibitBoard) throws Exception{
		boolean modify = exhibitBoardRepo.modifyArticle(exhibitBoard);
		if(modify && exhibitBoard.getFileList() != null) {
			modify = exhibitBoardRepo.uploadFileList(exhibitBoard);
		}
		if(modify && exhibitBoard.getImageList() != null) {
			modify = exhibitBoardRepo.uploadImageList(exhibitBoard);
		}
		return modify;
	}
//	
//	public boolean deleteArticle(int bid) throws Exception{
//		
//	}
//
	public List<ExhibitBoard> getExhibitAllArticle(Language language) throws Exception {
		return exhibitBoardRepo.getExhibitAllArticle(language);
	}
//	
//	public List<ExhibitBoard> getAllArticleByLike(int bcid, String dir) throws Exception{
//		
//	}
//
	public ExhibitBoard getArticle(int bid) throws Exception{
		return exhibitBoardRepo.getArticle(bid);
	}
//
//	public ExhibitBoard getArticleByMemberId(int bid) throws Exception{
//		
//	}
//
//	public ExhibitBoard getArticleByLang(int bid) throws Exception{
//		
//	}
//	

	// 실제 파일 삭제 메소드
	@Transactional
	public boolean deleteFileList(int bid, int pid, String filePath, String imagePath) throws Exception{
		// 프로젝트 전시글 파일 조회
		List<FileInfo> fileInfoList = exhibitBoardRepo.getFileList(bid);
		if(fileInfoList.size() > 0) {
			try {
				// 프로젝트 전시글 내부 파일 삭제
				for(FileInfo fileInfo: fileInfoList) {
					File file = new File(filePath + File.separator + fileInfo.getSaveFolder() + File.separator + fileInfo.getSaveFile());
					if(!file.delete()) {
						file = new File(imagePath + File.separator + fileInfo.getSaveFolder() + File.separator + fileInfo.getSaveFile());
						file.delete();
					}
				}
				exhibitBoardRepo.deleteFileList(bid);
			} catch(Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}
//
//	public boolean clickLike(int bid) throws Exception{
//		
//	}
}
