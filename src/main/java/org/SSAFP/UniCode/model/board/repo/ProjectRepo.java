package org.SSAFP.UniCode.model.board.repo;

import org.SSAFP.UniCode.model.board.dto.Project;
import org.SSAFP.UniCode.model.board.dto.ProjectMainImg;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProjectRepo {
	public boolean registProject(Project project) throws Exception;
	
	public boolean registProjectLanguage(Project project) throws Exception;
	
	public boolean deleteProjectLanguage(int pid) throws Exception;
	
	public boolean modifyProject(Project project) throws Exception;

	public boolean uploadMainImg(Project project) throws Exception;

	public ProjectMainImg getMainImg(int pid) throws Exception;
	
	public boolean deleteMainImg(int pid) throws Exception;
}
