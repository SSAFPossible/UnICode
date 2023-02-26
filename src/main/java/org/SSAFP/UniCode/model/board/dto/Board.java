package org.SSAFP.UniCode.model.board.dto;

import java.sql.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Board {
	private int bid;
	private String bcid;
	private String uid;
	private String title;
	private String content;
	private int hit;
	private Date createdTime;
	private Date updatedTime;
	private List<FileInfo> fileList;
	private List<FileInfo> imageList;
}