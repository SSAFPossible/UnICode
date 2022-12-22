package org.SSAFP.UniCode.model.board.service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.SSAFP.UniCode.model.board.dto.Board;
import org.SSAFP.UniCode.model.board.dto.BoardLike;
import org.SSAFP.UniCode.model.board.dto.FileInfo;
import org.SSAFP.UniCode.model.board.repo.BoardRepo;
import org.SSAFP.UniCode.model.comment.repo.CommentRepo;
import org.SSAFP.UniCode.model.user.repo.UserRepo;
import org.SSAFP.UniCode.score.Score;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	private BoardRepo boardRepo;

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CommentRepo commentRepo;

	@Override
	@Transactional
	public boolean writeArticle(Board board) throws Exception {
		boolean write = boardRepo.writeArticle(board);
		if (write && board.getFileList() != null) {
			write = boardRepo.uploadFileList(board);
		}
		if (write && board.getImageList() != null) {
			write = boardRepo.uploadImageList(board);
		}
		if (!write) {
			throw new Exception();
		} else { // 글 작성 성공 시 점수 증가 
			Map<String, Object> scoreInfo = new HashMap<>();
			scoreInfo.put("uid", board.getUid());
			scoreInfo.put("score", Score.BOARD_WRITE);
			write = userRepo.updateScore(scoreInfo);
			return true;
		}
	}

	@Override
	@Transactional
	public boolean modifyArticle(Board board) throws Exception {
		boolean modify = boardRepo.modifyArticle(board);
		if (modify && board.getFileList() != null) {
			modify = boardRepo.uploadFileList(board);
		}
		if (modify && board.getImageList() != null) {
			modify = boardRepo.uploadImageList(board);
		}
		if (!modify) {
			throw new Exception();
		}
		return true;
	}

	@Override
	@Transactional
	public boolean deleteArticle(int bid, String uid) throws Exception {
		boolean delete = commentRepo.dropSecondComment(bid);
		if (delete) {
			delete = boardRepo.deleteArticle(bid);
		}
		if (!delete) {
			throw new Exception();
		} else { // 글 삭제 시 점수 차감
			Map<String, Object> scoreInfo = new HashMap<>();
			scoreInfo.put("uid", uid);
			scoreInfo.put("score", -Score.BOARD_WRITE);
			delete = userRepo.updateScore(scoreInfo);
			return true;
		}
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

	@Override
	@Transactional
	// 실제 파일 삭제 메소드
	public boolean deleteFileList(int bid, String filePath, String imagePath) throws Exception {
		List<FileInfo> fileInfoList = boardRepo.getFileList(bid);
		try {
			for (FileInfo fileInfo : fileInfoList) {
				File file = new File(
						filePath + File.separator + fileInfo.getSaveFolder() + File.separator + fileInfo.getSaveFile());
				if (!file.delete()) {
					file = new File(imagePath + File.separator + fileInfo.getSaveFolder() + File.separator
							+ fileInfo.getSaveFile());
					file.delete();
				}
			}
			boardRepo.deleteFileList(bid);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}
		return true;
	}

	@Override
	@Transactional
	public boolean clickLike(BoardLike boardLike) throws Exception {
		Map<String, Integer> likeInfo = new HashMap<String, Integer>();
		likeInfo.put("bid", boardLike.getBid());
		if (boardRepo.getLike(boardLike) > 0) {
			boardRepo.likeFalse(boardLike);
			likeInfo.put("num", -1);
			boardRepo.updateLike(likeInfo);
			return false;
		} else {
			boardRepo.likeTrue(boardLike);
			likeInfo.put("num", 1);
			boardRepo.updateLike(likeInfo);
			return true;
		}
	}
}