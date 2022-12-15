package org.SSAFP.UniCode.model.algorithm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Algo {
	private int aid;			// pk
	private String site;		// 사이트
	private String name;		// 문제 이름
	private String url;			// 문제 link
	private int solved;			// 푼 문제 여부
}
