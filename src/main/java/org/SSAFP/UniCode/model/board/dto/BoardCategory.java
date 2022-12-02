package org.SSAFP.UniCode.model.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardCategory {
	private int bid;
	private int main_class;
	private int middle_class;
}
