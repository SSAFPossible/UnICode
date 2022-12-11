package org.SSAFP.UniCode.model.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExhibitBoard extends Board {
	private int likeCnt;				// 좋아요
	private Project project;			// 프로젝트
}
