package org.SSAFP.UniCode.model.comment.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.SSAFP.UniCode.model.comment.dto.Comment;
import org.SSAFP.UniCode.model.comment.repo.CommentRepo;
import org.SSAFP.UniCode.model.user.repo.UserRepo;
import org.SSAFP.UniCode.score.Score;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	CommentRepo commentRepo;
	
	@Autowired
	UserRepo userRepo;
	
	@Override
	public boolean writeFirstComment(Comment comment) throws Exception {
		boolean write = commentRepo.writeFirstComment(comment);
		if(!write) {
			throw new Exception();
		} else { // 댓글 작성 성공 시 점수 증가
			Map<String, Object> scoreInfo = new HashMap<>();
			scoreInfo.put("uid", comment.getUid());
			scoreInfo.put("score", Score.COMMENT_WRITE);
			write = userRepo.updateScore(scoreInfo);
			return true;
		}
	}

	@Override
	public boolean writeSecondComment(Comment comment) throws Exception {
		boolean write = commentRepo.writeSecondComment(comment);
		if(!write) {
			throw new Exception();
		} else { // 댓글 작성 성공 시 점수 증가
			Map<String, Object> scoreInfo = new HashMap<>();
			scoreInfo.put("uid", comment.getUid());
			scoreInfo.put("score", Score.COMMENT_WRITE);
			write = userRepo.updateScore(scoreInfo);
			return true;
		}
	}
	
	@Override
	@Transactional
	public boolean modifyComment(Comment comment) throws Exception {
		if(!commentRepo.modifyComment(comment)) {
			throw new Exception();
		}
		return true;
	}

	@Override
	@Transactional
	public boolean deleteComment(int cid, String uid) throws Exception {
		boolean delete = commentRepo.deleteComment(cid);
		if(!delete) {
			throw new Exception();
		} else { // 댓글 삭제 시 점수 차감
			Map<String, Object> scoreInfo = new HashMap<>();
			scoreInfo.put("uid", uid);
			scoreInfo.put("score", -Score.COMMENT_WRITE);
			delete = userRepo.updateScore(scoreInfo);
			return true;
		}
	}

	public List<Comment> getComment(int bid) throws Exception {
		return commentRepo.getComment(bid);
	}
}