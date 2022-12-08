package org.SSAFP.UniCode.model.board.service;

import java.io.File;

import org.SSAFP.UniCode.model.board.dto.Project;
import org.SSAFP.UniCode.model.board.dto.ProjectImg;
import org.SSAFP.UniCode.model.board.repo.ProjectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	public boolean modifyProject(Project project) throws Exception{
		boolean modify = projectRepo.modifyProject(project);
		if(modify && project.getMainImg() != null) {
			modify = projectRepo.uploadMainImg(project);
		}
		return modify;
	}
	
	// 실제 파일 삭제 메소드
	@Transactional
	public boolean deleteMainImg(int pid, String imagePath) throws Exception{
		ProjectImg mainImg = projectRepo.getMainImg(pid);
		if(mainImg != null) {
			try {
				File file = new File(imagePath + File.separator + mainImg.getSaveFolder() + File.separator + mainImg.getSaveFile());
				file.delete();
				projectRepo.deleteMainImg(pid);
			} catch(Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}
}