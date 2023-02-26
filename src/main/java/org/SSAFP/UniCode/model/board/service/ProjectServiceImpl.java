package org.SSAFP.UniCode.model.board.service;

import java.io.File;
import java.util.List;

import org.SSAFP.UniCode.model.board.dto.Project;
import org.SSAFP.UniCode.model.board.dto.ProjectMainImg;
import org.SSAFP.UniCode.model.board.repo.ProjectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProjectServiceImpl {

	@Autowired
	ProjectRepo projectRepo;

	@Transactional
	public boolean registProject(Project project) throws Exception{
		boolean regist = projectRepo.registProject(project);
		if(regist && project.getMainImg() != null) {
			regist = projectRepo.uploadMainImg(project);
		}
		if(regist && project.getLanguage() != null) {
			regist = projectRepo.registProjectLanguage(project);
		}
		if(regist && project.getMember() != null) {
			regist = projectRepo.registProjectMember(project);
		}
		if(!regist) {
			throw new Exception();
		}
		return true;
	}

	@Transactional
	public boolean modifyProject(Project project) throws Exception{
		// 프로젝트 수정 & 프로젝트 개발 언어 삭제 & 프로젝트 멤버 삭제
		boolean modify = projectRepo.modifyProject(project) && projectRepo.deleteProjectLanguage(project.getPid()) && projectRepo.deleteProjectMember(project.getPid());
		// 개발 언어 업로드
		if(modify && project.getLanguage() != null) {
			modify = projectRepo.registProjectLanguage(project);
		}
		// 대표 이미지 업로드
		if(modify && project.getMainImg() != null) {
			modify = projectRepo.uploadMainImg(project);
		}
		// 프로젝트 멤버 업로드
		if(modify && project.getMember() != null) {
			modify = projectRepo.registProjectMember(project);
		}
		if(!modify) {
			throw new Exception();
		}
		return true;
	}
	
	public List<String> getProjectLanguage(int pid) throws Exception{
		return projectRepo.getProjectLanguage(pid);
	}
	
	public List<String> getProjectMember(int pid) throws Exception{
		return projectRepo.getProjectMember(pid);
	}
	
	// 실제 파일 삭제 메소드
	@Transactional
	public boolean deleteMainImg(int pid, String imagePath) throws Exception{
		ProjectMainImg mainImg = projectRepo.getMainImg(pid);
		if(mainImg != null) {
			try {
				File file = new File(imagePath + File.separator + mainImg.getSaveFolder() + File.separator + mainImg.getSaveFile());
				file.delete();
				projectRepo.deleteMainImg(pid);
			} catch(Exception e) {
				e.printStackTrace();
				throw new Exception();
			}
		}
		return true;
	}
}