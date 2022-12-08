package org.SSAFP.UniCode.model.board.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Language {
	private int lid;
	private List<String> name;
	private int nameSize;
}
