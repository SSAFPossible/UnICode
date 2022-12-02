package org.SSAFP.UniCode.model.board.service;

import java.util.List;

import org.SSAFP.UniCode.model.board.dto.Board;
import org.SSAFP.UniCode.model.board.dto.BoardCategory;

public interface BoardService {
	
	public boolean writeArticle(Board board) throws Exception;
	
	public boolean modifyArticle(Board board) throws Exception;
	
	public boolean deleteArticle(int bid) throws Exception;
	
	public List<Board> getAllArticle(BoardCategory boardCategory) throws Exception;
	
	public Board getArticle(int bid) throws Exception;
}
