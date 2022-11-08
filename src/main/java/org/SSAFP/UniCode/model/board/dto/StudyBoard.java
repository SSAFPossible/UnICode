package org.SSAFP.UniCode.model.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudyBoard extends Board{
	private int mainClass;		// 분류 카테고리
	private int middleClass;	// 중분류 카테고리
	private int like;			// 좋아요
}