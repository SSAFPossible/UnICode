package org.SSAFP.UniCode.model.board.repo;

import java.util.List;

import org.SSAFP.UniCode.model.board.dto.StudyBoard;
import org.SSAFP.UniCode.model.board.dto.StudyBoardParam;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StudyBoardRepo {
	
	public List<StudyBoard> getAllStudyBoard(StudyBoardParam studyBoardParam) throws Exception;

	public StudyBoard getStudyBoard(int bid) throws Exception;

	public boolean writeStudyBoard(StudyBoard studyBoard);
}

