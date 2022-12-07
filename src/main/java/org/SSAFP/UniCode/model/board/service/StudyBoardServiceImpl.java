package org.SSAFP.UniCode.model.board.service;

import java.util.List;

import org.SSAFP.UniCode.model.board.dto.StudyBoard;
import org.SSAFP.UniCode.model.board.repo.StudyBoardRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Primary
public class StudyBoardServiceImpl extends BoardServiceImpl {

	@Autowired
	private StudyBoardRepo studyBoardRepo;
	
	// Category와 tag 없이 모든 studyBoard 가져올 경우
	@Transactional
	public List<StudyBoard> getAllBoardArticle() throws Exception {
		return studyBoardRepo.getAllBoardArticle();
	}

}
