package org.SSAFP.UniCode.model.board.service;

import java.util.List;

import org.SSAFP.UniCode.model.board.dto.Board;
import org.SSAFP.UniCode.model.board.dto.BoardCategory;
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
		if(board.getFileList().size() != 0) {
			write = boardRepo.uploadFileList(board);
		}
		return write;
	}

	@Override
	@Transactional
	public boolean modifyArticle(Board board) throws Exception {
		return boardRepo.modifyArticle(board);
	}

	@Override
	@Transactional
	public boolean deleteArticle(int bid) throws Exception {
		return boardRepo.deleteArticle(bid);
	}

	@Override
	public List<Board> getAllArticle(BoardCategory boardCategory) throws Exception {
		return boardRepo.getAllArticle(boardCategory);
	}

	@Override
	public Board getArticle(int bid) throws Exception {
		return boardRepo.getArticle(bid);
	}

}
