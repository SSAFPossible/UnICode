package org.SSAFP.UniCode.model.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExhibitBoard extends Board {
	private int like;			// 좋아요
}
