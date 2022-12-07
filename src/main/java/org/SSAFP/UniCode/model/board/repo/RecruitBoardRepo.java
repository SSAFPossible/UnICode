package org.SSAFP.UniCode.model.board.repo;

import org.SSAFP.UniCode.model.board.dto.RecruitBoard;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RecruitBoardRepo {

	public boolean modifyRecruitInfo(RecruitBoard recruitBoard) throws Exception;
}
