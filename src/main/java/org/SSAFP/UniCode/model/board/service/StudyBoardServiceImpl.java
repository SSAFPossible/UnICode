package org.SSAFP.UniCode.model.board.service;

import java.util.List;

import org.SSAFP.UniCode.model.board.dto.StudyBoard;
import org.SSAFP.UniCode.model.board.dto.StudyBoardParam;
import org.SSAFP.UniCode.model.board.repo.BoardRepo;
import org.SSAFP.UniCode.model.board.repo.StudyBoardRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudyBoardServiceImpl extends BoardServiceImpl {

	@Autowired
	private StudyBoardRepo studyBoardRepo;
	
	@Autowired
	private BoardRepo boardRepo;
	
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

}
