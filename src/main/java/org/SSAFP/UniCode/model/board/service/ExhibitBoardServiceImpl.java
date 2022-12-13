package org.SSAFP.UniCode.model.board.service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.SSAFP.UniCode.model.board.dto.BoardLike;
import org.SSAFP.UniCode.model.board.dto.ExhibitBoard;
import org.SSAFP.UniCode.model.board.dto.ExhibitBoardParam;
import org.SSAFP.UniCode.model.board.dto.FileInfo;
import org.SSAFP.UniCode.model.board.dto.ProjectMember;
import org.SSAFP.UniCode.model.board.repo.ExhibitBoardRepo;
import org.SSAFP.UniCode.model.user.repo.UserRepo;
import org.SSAFP.UniCode.score.Score;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ExhibitBoardServiceImpl {

	@Autowired
	ExhibitBoardRepo exhibitBoardRepo;
	
	@Autowired
	UserRepo userRepo;
	
	@Transactional
	public boolean writeArticle(ExhibitBoard exhibitBoard) throws Exception{
		boolean write = exhibitBoardRepo.writeArticle(exhibitBoard);
		if(write && exhibitBoard.getFileList() != null) {
			write = exhibitBoardRepo.uploadFileList(exhibitBoard);
		}
		if (write && exhibitBoard.getImageList() != null) {
			write = exhibitBoardRepo.uploadImageList(exhibitBoard);
		}
		if(!write) {
			throw new Exception();
		} else {
			Map<String, Object> scoreInfo = new HashMap<>();
			
			// 글 작성 성공 시 점수 증가
			scoreInfo.put("uid", exhibitBoard.getUid());
			scoreInfo.put("score", Score.BOARD_WRITE);
			write = userRepo.updateScore(scoreInfo);
			
			// 프로젝트 참여자 점수 증가
			scoreInfo.put("score", Score.PROJECT_JOIN);
			for(String uid : exhibitBoard.getProject().getMember().getUid()) {
				scoreInfo.put("uid", uid);
				write = userRepo.updateScore(scoreInfo);	
			}
			return true;
		}
	}

	@Transactional
	public boolean modifyArticle(ExhibitBoard exhibitBoard) throws Exception{
		boolean modify = exhibitBoardRepo.modifyArticle(exhibitBoard);
		if(modify && exhibitBoard.getFileList() != null) {
			modify = exhibitBoardRepo.uploadFileList(exhibitBoard);
		}
		if(modify && exhibitBoard.getImageList() != null) {
			modify = exhibitBoardRepo.uploadImageList(exhibitBoard);
		}
		if(!modify) {
			throw new Exception();
		}
		return true;
	}

	@Transactional
	public boolean deleteArticle(int bid, String uid) throws Exception{
		boolean delete = exhibitBoardRepo.deleteArticle(bid);
		if(!delete) {
			throw new Exception();
		} else {
//			Map<String, Object> scoreInfo = new HashMap<>();
//			
//			// 글 작성 성공 시 점수 증가
//			scoreInfo.put("uid", uid);
//			scoreInfo.put("score", -Score.BOARD_WRITE);
//			delete = userRepo.updateScore(scoreInfo);
//			
//			// 프로젝트 참여자 점수 증가
//			scoreInfo.put("score", -Score.PROJECT_JOIN);
//			for(String uid : exhibitBoard.getProject().getMember().getUid()) {
//				scoreInfo.put("uid", uid);
//				delete = userRepo.updateScore(scoreInfo);	
//			}
			return true;
		}
	}

	public List<ExhibitBoard> getAllExhibitArticle(ExhibitBoardParam exhibitBoardParam) throws Exception {
		return exhibitBoardRepo.getAllExhibitArticle(exhibitBoardParam);
	}
	
	public ExhibitBoard getArticle(int bid) throws Exception{
		exhibitBoardRepo.updateHit(bid);
		return exhibitBoardRepo.getArticle(bid);
	}
	
	// 실제 파일 삭제 메소드
	@Transactional
	public boolean deleteFileList(int bid, String filePath, String imagePath) throws Exception{
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
				throw new Exception();
			}
		}
		return true;
	}

	@Transactional
	public boolean clickLike(BoardLike boardLike) throws Exception {
		Map<String, Integer> likeInfo = new HashMap<String, Integer>();
		likeInfo.put("bid", boardLike.getBid());
		if(exhibitBoardRepo.getLike(boardLike) > 0) {
			exhibitBoardRepo.likeFalse(boardLike);
			likeInfo.put("num", -1);
			exhibitBoardRepo.updateLike(likeInfo);
			return false;
		} else {
			exhibitBoardRepo.likeTrue(boardLike);
			likeInfo.put("num", 1);
			exhibitBoardRepo.updateLike(likeInfo);
			return true;
		}
	}
}