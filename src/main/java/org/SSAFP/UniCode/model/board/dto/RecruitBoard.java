package org.SSAFP.UniCode.model.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecruitBoard extends Board {
	private boolean open;			// 인원 모집 중인지 마감되었는지 여부
	private int maxMember;			// 최대 인원
	private int like;				// 좋아요
}
