package org.SSAFP.UniCode.model.board.repo;

import java.util.List;

import org.SSAFP.UniCode.model.board.dto.ExhibitBoard;
import org.SSAFP.UniCode.model.board.dto.FileInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ExhibitBoardRepo {
	public boolean writeArticle(ExhibitBoard exhibitBoard) throws Exception;
	
//	public boolean modifyArticle(ExhibitBoard exhibitBoard) throws Exception;
//	
//	public boolean deleteArticle(int bid) throws Exception;
//
//	public List<ExhibitBoard> getAllArticle(int bcid) throws Exception;
//	
//	public List<ExhibitBoard> getAllArticleByLike(int bcid, String dir) throws Exception;
//
//	public ExhibitBoard getArticle(int bid) throws Exception;
//
//	public ExhibitBoard getArticleByMemberId(int bid) throws Exception;
//
//	public ExhibitBoard getArticleByLang(int bid) throws Exception;
//	
	public boolean uploadFileList(ExhibitBoard exhibitBoard) throws Exception;
	
	public boolean uploadImageList(ExhibitBoard exhibitBoard) throws Exception;

	public boolean deleteFileList(ExhibitBoard exhibitBoard) throws Exception;

	public List<FileInfo> getFileList(int bid) throws Exception;

//	public boolean updateHit(int bid) throws Exception;
//	
//	public boolean clickLike(int bid) throws Exception;
}
