package org.SSAFP.UniCode.model.comment.service;

import java.util.List;

import org.SSAFP.UniCode.model.comment.dto.Comment;
import org.SSAFP.UniCode.model.comment.repo.CommentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	CommentRepo commentRepo;
	
	@Override
	public boolean writeFirstComment(Comment comment) throws Exception {
		boolean write = commentRepo.writeFirstComment(comment);
		if(!write) {
			throw new Exception();
		}
		return true;
	}

	@Override
	public boolean writeSecondComment(Comment comment) throws Exception {
		boolean write = commentRepo.writeSecondComment(comment);
		if(!write) {
			throw new Exception();
		}
		return true;
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
	public boolean deleteComment(int cid) throws Exception {
		boolean delete = commentRepo.deleteComment(cid);
		if(!delete) {
			throw new Exception();
		}
		return true;
	}

	public List<Comment> getComment(int bid) throws Exception {
		return commentRepo.getComment(bid);
	}

}
