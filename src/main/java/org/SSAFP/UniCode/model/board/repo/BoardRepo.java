package org.SSAFP.UniCode.model.board.repo;

import java.util.List;

import org.SSAFP.UniCode.model.board.dto.Board;
import org.SSAFP.UniCode.model.board.dto.FileInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.context.annotation.Primary;

@Mapper
public interface BoardRepo {
	
	public boolean writeArticle(Board board) throws Exception;
	
	public boolean modifyArticle(Board board) throws Exception;
	
	public boolean deleteArticle(int bid) throws Exception;
	
	public List<Board> getAllArticle(String bcid) throws Exception;
	
	public Board getArticle(int bid) throws Exception;
	
	public boolean uploadFileList(Board board) throws Exception;
	
	public boolean uploadImageList(Board board) throws Exception;

	public boolean deleteFileList(int bid) throws Exception;

	public List<FileInfo> getFileList(int bid) throws Exception;
	
	public boolean updateHit(int bid) throws Exception;
}