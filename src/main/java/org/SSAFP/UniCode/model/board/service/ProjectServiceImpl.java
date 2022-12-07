package org.SSAFP.UniCode.model.board.service;

import org.SSAFP.UniCode.model.board.dto.Project;
import org.SSAFP.UniCode.model.board.repo.ProjectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectServiceImpl {

	@Autowired
	ProjectRepo projectRepo;
	
	public boolean registProject(Project project) throws Exception{
		boolean regist = projectRepo.registProject(project);
		if(regist && project.getMainImg() != null) {
			regist = projectRepo.uploadMainImg(project);
		}
		return regist;
	}
}