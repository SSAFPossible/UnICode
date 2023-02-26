package org.SSAFP.UniCode.model.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserImg {
	private int fid;
	private int uid;
	private String type;
	private String saveFolder;
	private String originFile;
	private String saveFile;
}
