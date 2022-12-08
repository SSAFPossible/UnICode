package org.SSAFP.UniCode.model.board.repo;

import org.SSAFP.UniCode.model.board.dto.Project;
import org.SSAFP.UniCode.model.board.dto.ProjectImg;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProjectRepo {
	public boolean registProject(Project project) throws Exception;
	
	public boolean modifyProject(Project project) throws Exception;

	public boolean uploadMainImg(Project project) throws Exception;

	public ProjectImg getMainImg(int pid) throws Exception;
	
	public boolean deleteMainImg(int pid) throws Exception;
}
