package org.SSAFP.UniCode.model.comment.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comment {
	private int cid;
	private String comment;
	private int bid;
	private String createdTime;
	private String updatedTime;
	private int parentCid;
	private String uid;
	private boolean deleted;
	private List<Comment> secondComment;
}
