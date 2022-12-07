package org.SSAFP.UniCode.model.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudyBoardParam {
	private String category;
	private String tag;
	private String uid;
	private boolean sortLike;
}
