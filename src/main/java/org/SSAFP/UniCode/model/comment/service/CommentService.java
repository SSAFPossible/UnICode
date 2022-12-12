package org.SSAFP.UniCode.model.comment.service;

import org.SSAFP.UniCode.model.comment.dto.Comment;

public interface CommentService {
	public boolean writeFirstComment(Comment comment) throws Exception;
	
	public boolean writeSecondComment(Comment comment) throws Exception;
	
	public boolean modifyComment(Comment comment) throws Exception;

	public boolean deleteComment(int cid) throws Exception;
}
