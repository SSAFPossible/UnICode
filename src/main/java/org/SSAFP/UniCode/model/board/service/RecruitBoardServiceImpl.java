package org.SSAFP.UniCode.model.board.service;

import java.util.List;

import org.SSAFP.UniCode.model.board.dto.Language;
import org.SSAFP.UniCode.model.board.dto.RecruitBoard;
import org.SSAFP.UniCode.model.board.repo.RecruitBoardRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RecruitBoardServiceImpl extends BoardServiceImpl {
	
	@Autowired
	private RecruitBoardRepo recruitBoardRepo;
	
	public RecruitBoard getRecruitArticle(int bid) throws Exception {
		return recruitBoardRepo.getRecruitArticle(bid);
	}
	
	public List<RecruitBoard> getRecruitAllArticle(Language language) throws Exception {
		return recruitBoardRepo.getRecruitAllArticle(language);
	}
	
	@Transactional
	public boolean modifyRecruitInfo(RecruitBoard recruitBoard) throws Exception {
		return recruitBoardRepo.modifyRecruitInfo(recruitBoard);
	}
}
