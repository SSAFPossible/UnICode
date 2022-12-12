package org.SSAFP.UniCode.model.board.service;

import java.util.List;
import java.util.Map;

import org.SSAFP.UniCode.model.board.dto.Language;
import org.SSAFP.UniCode.model.board.dto.RecruitBoard;
import org.SSAFP.UniCode.model.board.repo.RecruitBoardRepo;
import org.springframework.beans.factory.annotation.Autowired;
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
		boolean modify = recruitBoardRepo.modifyRecruitInfo(recruitBoard);
		if(!modify) {
			throw new Exception();
		}
		return true;
	}
	
	@Transactional
	public boolean writeTag(Map<String, Object> tag) throws Exception {
		boolean write = recruitBoardRepo.writeTag(tag);
		if(!write) {
			throw new Exception();
		}
		return true;
	}
	
	@Transactional
	public boolean deleteTag(int bid) throws Exception {
		boolean delete = recruitBoardRepo.deleteTag(bid);
		if(!delete) {
			throw new Exception();
		}
		return true;
	}
}