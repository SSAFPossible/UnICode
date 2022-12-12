package org.SSAFP.UniCode.model.board.repo;

import java.util.List;
import java.util.Map;

import org.SSAFP.UniCode.model.board.dto.FileInfo;
import org.SSAFP.UniCode.model.board.dto.Language;
import org.SSAFP.UniCode.model.board.dto.RecruitBoard;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RecruitBoardRepo {
	
	public RecruitBoard getRecruitArticle(int bid) throws Exception;
	
	public List<RecruitBoard> getRecruitAllArticle(Language language) throws Exception;

	public boolean modifyRecruitInfo(RecruitBoard recruitBoard) throws Exception;
	
	public boolean writeTag(Map<String, Object> tag) throws Exception;

	public boolean deleteTag(int bid) throws Exception;
	
	public List<FileInfo> getFileList(int bid) throws Exception;
}
