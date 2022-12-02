package org.SSAFP.UniCode.model.board.repo;

import java.util.List;

import org.SSAFP.UniCode.model.board.dto.Board;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardRepo {
	
	public boolean writeArticle(Board board) throws Exception;
	
	public boolean modifyArticle(Board board) throws Exception;
	
	public boolean deleteArticle(int bid) throws Exception;
	
	public List<Board> getAllArticle(String bcid) throws Exception;
	
	public Board getArticle(int bid) throws Exception;
	
	public boolean uploadFileList(Board board) throws Exception;
	
	public boolean uploadImageList(Board board) throws Exception;
	
	public boolean deleteFileList(Board board) throws Exception;
	
	public boolean updateHit(int bid) throws Exception;
}
