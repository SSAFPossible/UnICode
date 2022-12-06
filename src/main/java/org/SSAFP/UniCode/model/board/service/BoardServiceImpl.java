package org.SSAFP.UniCode.model.board.service;

import java.util.List;

import org.SSAFP.UniCode.model.board.dto.Board;
import org.SSAFP.UniCode.model.board.repo.BoardRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	private BoardRepo boardRepo;

	@Override
	@Transactional
	public boolean writeArticle(Board board) throws Exception {
		boolean write = boardRepo.writeArticle(board);
		if(write && board.getFileList().size() > 0) {
			write = boardRepo.uploadFileList(board);
		}
		if (write && board.getImageList().size() > 0) {
			write = boardRepo.uploadImageList(board);
		}
		return write;
	}

	@Override
	@Transactional
	public boolean modifyArticle(Board board) throws Exception {
		boolean modify = boardRepo.modifyArticle(board);
		if(modify && board.getFileList().size() > 0) {
			modify = boardRepo.deleteFileList(board) && boardRepo.uploadFileList(board) && boardRepo.uploadImageList(board);
		}
		return modify;
	}

	@Override
	@Transactional
	public boolean deleteArticle(int bid) throws Exception {
		return boardRepo.deleteArticle(bid);
	}

	@Override
	public List<Board> getAllArticle(String bcid) throws Exception {
		return boardRepo.getAllArticle(bcid);
	}

	@Override
	public Board getArticle(int bid) throws Exception {
		boardRepo.updateHit(bid);
		return boardRepo.getArticle(bid);
	}

}
