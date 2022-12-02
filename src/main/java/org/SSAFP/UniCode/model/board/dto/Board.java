package org.SSAFP.UniCode.model.board.dto;

import java.sql.Date;
import java.util.List;

import lombok.Data;

@Data
public class Board {
	private int bid;
	private int uid;
	private String title;
	private String content;
	private int hit;
	private Date createdTime;
	private Date updatedTime;
	private List<FileInfo> fileList;
}