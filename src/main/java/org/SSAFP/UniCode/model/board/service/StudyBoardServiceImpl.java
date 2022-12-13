package org.SSAFP.UniCode.model.board.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.SSAFP.UniCode.model.board.dto.StudyBoard;
import org.SSAFP.UniCode.model.board.dto.StudyBoardParam;
import org.SSAFP.UniCode.model.board.repo.BoardRepo;
import org.SSAFP.UniCode.model.board.repo.StudyBoardRepo;
import org.SSAFP.UniCode.model.user.repo.UserRepo;
import org.SSAFP.UniCode.score.Score;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudyBoardServiceImpl extends BoardServiceImpl {

	@Autowired
	private StudyBoardRepo studyBoardRepo;
	
	@Autowired
	private BoardRepo boardRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	// Category와 tag 없이 모든 studyBoard 가져올 경우
	@Transactional
	public List<StudyBoard> getAllStudyBoard(StudyBoardParam studyBoardParam) throws Exception {
		return studyBoardRepo.getAllStudyBoard(studyBoardParam);
	}
	
	@Transactional
	public StudyBoard getStudyBoard(int bid) throws Exception {
		boardRepo.updateHit(bid);
		return studyBoardRepo.getStudyBoard(bid);
	}

	@Transactional
	public boolean writeBoardArticle(StudyBoard studyBoard) throws Exception {
		boolean write = studyBoardRepo.writeStudyBoard(studyBoard);
		if (write && studyBoard.getFileList() != null) {
			write = boardRepo.uploadFileList(studyBoard);
		}
		if (write && studyBoard.getImageList() != null) {
			write = boardRepo.uploadImageList(studyBoard);
		}
		if (!write) {
			throw new Exception();
		} else { // 글 작성 성공 시 점수 증가 
			Map<String, Object> scoreInfo = new HashMap<>();
			scoreInfo.put("uid", studyBoard.getUid());
			scoreInfo.put("score", Score.BOARD_WRITE);
			write = userRepo.updateScore(scoreInfo);
			return true;
		}
	}
}