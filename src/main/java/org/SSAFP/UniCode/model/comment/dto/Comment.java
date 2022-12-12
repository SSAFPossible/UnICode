package org.SSAFP.UniCode.model.comment.dto;

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
	private int depth;
	private String createdTime;
	private int parentCid;
	private String uid;
}
