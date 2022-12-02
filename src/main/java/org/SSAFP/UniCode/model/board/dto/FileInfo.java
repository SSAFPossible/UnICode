package org.SSAFP.UniCode.model.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileInfo {
	private int fid;
	private int bid;
	private String type;
	private String saveFolder;
	private String originFile;
	private String saveFile;
}
