package org.SSAFP.UniCode.model.board.service;

import java.io.File;
import java.util.List;

import org.SSAFP.UniCode.model.board.dto.ExhibitBoard;
import org.SSAFP.UniCode.model.board.dto.FileInfo;
import org.SSAFP.UniCode.model.board.dto.ProjectImg;
import org.SSAFP.UniCode.model.board.repo.ExhibitBoardRepo;
import org.SSAFP.UniCode.model.board.repo.ProjectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ExhibitBoardServiceImpl {

	@Autowired
	ExhibitBoardRepo exhibitBoardRepo;

	@Autowired
	ProjectRepo projectRepo;
	
	@Transactional
	public boolean writeArticle(ExhibitBoard exhibitBoard) throws Exception{
		boolean write = exhibitBoardRepo.writeArticle(exhibitBoard);
		if(write && exhibitBoard.getFileList().size() > 0) {
			write = exhibitBoardRepo.uploadFileList(exhibitBoard);
		}
		if (write && exhibitBoard.getImageList().size() > 0) {
			write = exhibitBoardRepo.uploadImageList(exhibitBoard);
		}
		return write;
	}
}
