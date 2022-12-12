package org.SSAFP.UniCode.model.comment.repo;

import java.util.List;

import org.SSAFP.UniCode.model.comment.dto.Comment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentRepo {
	
	public boolean writeFirstComment(Comment comment) throws Exception;
	
	public boolean writeSecondComment(Comment comment) throws Exception;
	
	public boolean modifyComment(Comment comment) throws Exception;
	
	public boolean deleteComment(int cid) throws Exception;

	public List<Comment> getComment(int bid) throws Exception;
}
