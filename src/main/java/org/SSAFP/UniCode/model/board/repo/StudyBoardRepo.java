package org.SSAFP.UniCode.model.board.repo;

import java.util.List;

import org.SSAFP.UniCode.model.board.dto.StudyBoard;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StudyBoardRepo {
	public List<StudyBoard> getAllBoardArticle() throws Exception;
}

