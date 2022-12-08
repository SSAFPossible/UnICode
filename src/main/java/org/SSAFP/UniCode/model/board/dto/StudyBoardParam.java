package org.SSAFP.UniCode.model.board.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudyBoardParam {
	private List<String> category;
	private List<String> tag;
	private List<String> categoryTag;
	private String uid;
	private boolean sortLike;
	private int categorySize;
	private int tagSize;
}
