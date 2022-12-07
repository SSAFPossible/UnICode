package org.SSAFP.UniCode.model.board.service;

import org.SSAFP.UniCode.model.board.dto.RecruitBoard;
import org.SSAFP.UniCode.model.board.repo.RecruitBoardRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Primary
public class RecruitBoardServiceImpl extends BoardServiceImpl {
	
	@Autowired
	private RecruitBoardRepo recruitBoardRepo;
	
	@Transactional
	public boolean modifyRecruitInfo(RecruitBoard recruitBoard) throws Exception {
		return recruitBoardRepo.modifyRecruitInfo(recruitBoard);
	}
}
