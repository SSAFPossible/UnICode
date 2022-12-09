package org.SSAFP.UniCode.controller.board;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.SSAFP.UniCode.model.board.dto.ExhibitBoard;
import org.SSAFP.UniCode.model.board.dto.FileInfo;
import org.SSAFP.UniCode.model.board.dto.Language;
import org.SSAFP.UniCode.model.board.dto.ProjectMainImg;
import org.SSAFP.UniCode.model.board.service.ExhibitBoardServiceImpl;
import org.SSAFP.UniCode.model.board.service.ProjectServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/exhibit")
@Slf4j
public class ExhibitBoardController {
	private static final Logger logger = LoggerFactory.getLogger(ExhibitBoardController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private ExhibitBoardServiceImpl exhibitBoardService;
	
	@Autowired
	private ProjectServiceImpl projectService;
	
	@Value("${file.path.upload-files}")
	String filePath;
	
	@Value("${file.path.upload-images}")
	String imagePath;
	
	@PostMapping
	public ResponseEntity<String> write(@RequestPart(value="exhibitBoard") ExhibitBoard exhibitBoard, @RequestPart(value = "mainImg", required = false) MultipartFile mainImg,  @RequestPart(value = "upfile", required = false) MultipartFile[] files, @RequestPart(value = "upimage", required = false) MultipartFile[] images) throws Exception {

		String today = new SimpleDateFormat("yyMMdd").format(new Date());
		// 프로젝트 대표 이미지 업로드
		if(mainImg != null) {
			String saveFolder = imagePath + File.separator + today;
			
			File folder = new File(saveFolder);
			if (!folder.exists()) {
				folder.mkdirs();
			}
			
			ProjectMainImg projectImg = new ProjectMainImg();
			String originFileName = mainImg.getOriginalFilename();
			if (!originFileName.isEmpty()) {
				String saveFileName = System.nanoTime() + originFileName.substring(originFileName.lastIndexOf('.'));
				projectImg.setSaveFolder(today);
				projectImg.setOriginFile(originFileName);
				projectImg.setSaveFile(saveFileName);
				mainImg.transferTo(new File(folder, saveFileName));
			}
			exhibitBoard.getProject().setMainImg(projectImg);		// 프로젝트 객체에 이미지 저장
		}
		
		// 파일 업로드
		if (!files[0].getOriginalFilename().equals("")) {
			String saveFolder = filePath + File.separator + today;
			
			File folder = new File(saveFolder);
			if (!folder.exists()) {
				folder.mkdirs();
			}
			
			List<FileInfo> fileInfos = new ArrayList<FileInfo>();
			for (MultipartFile mfile : files) {
				FileInfo fileInfo = new FileInfo();
				String originFileName = mfile.getOriginalFilename();
				if (!originFileName.isEmpty()) {
					String saveFileName = System.nanoTime() + originFileName.substring(originFileName.lastIndexOf('.'));
					fileInfo.setSaveFolder(today);
					fileInfo.setOriginFile(originFileName);
					fileInfo.setSaveFile(saveFileName);
					mfile.transferTo(new File(folder, saveFileName));
				}
				fileInfos.add(fileInfo);
			}
			exhibitBoard.setFileList(fileInfos);
		}
		
		// 이미지 업로드
		if (!images[0].getOriginalFilename().equals("")) {
			String saveFolder = imagePath + File.separator + today;
			
			File folder = new File(saveFolder);
			if (!folder.exists()) {
				folder.mkdirs();
			}
			
			List<FileInfo> fileInfos = new ArrayList<FileInfo>();
			for (MultipartFile mfile : images) {
				FileInfo fileInfo = new FileInfo();
				String originFileName = mfile.getOriginalFilename();
				if (!originFileName.isEmpty()) {
					String saveFileName = System.nanoTime() + originFileName.substring(originFileName.lastIndexOf('.'));
					fileInfo.setSaveFolder(today);
					fileInfo.setOriginFile(originFileName);
					fileInfo.setSaveFile(saveFileName);
					mfile.transferTo(new File(folder, saveFileName));
				}
				fileInfos.add(fileInfo);
			}
			exhibitBoard.setImageList(fileInfos);
		}

		// 전시 게시글 저장
		if(exhibitBoardService.writeArticle(exhibitBoard)) {
			exhibitBoard.getProject().setBid(exhibitBoard.getBid());
			// 프로젝트 저장
			if(projectService.registProject(exhibitBoard.getProject()))
				return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	@GetMapping
	public ResponseEntity<List<ExhibitBoard>> getExhibitAllArticle(@RequestBody Language language) throws Exception{
		language.setNameSize(language.getName().size());
		return new ResponseEntity<List<ExhibitBoard>>(exhibitBoardService.getExhibitAllArticle(language), HttpStatus.OK);
	}
	
	@GetMapping("/{bid}")
	public ResponseEntity<ExhibitBoard> getArticle(@PathVariable("bid") int bid) throws Exception{
		ExhibitBoard exhibitBoard = exhibitBoardService.getArticle(bid);
		
		Language language = new Language();
		language.setName(projectService.getProjectLanguage(exhibitBoard.getProject().getPid()));
		exhibitBoard.getProject().setLanguage(language);
		
		return new ResponseEntity<ExhibitBoard>(exhibitBoard, HttpStatus.OK);
	}
	
	@PutMapping
	public ResponseEntity<String> modify(@RequestPart(value="exhibitBoard") ExhibitBoard exhibitBoard, @RequestPart(value = "mainImg", required = false) MultipartFile mainImg,  @RequestPart(value = "upfile", required = false) MultipartFile[] files, @RequestPart(value = "upimage", required = false) MultipartFile[] images) throws Exception {
		String today = new SimpleDateFormat("yyMMdd").format(new Date());
		
		// 프로젝트 대표 이미지 업로드
		if(mainImg != null) {
			String saveFolder = imagePath + File.separator + today;
			
			File folder = new File(saveFolder);
			if (!folder.exists()) {
				folder.mkdirs();
			}
			
			ProjectMainImg projectImg = new ProjectMainImg();
			String originFileName = mainImg.getOriginalFilename();
			if (!originFileName.isEmpty()) {
				String saveFileName = System.nanoTime() + originFileName.substring(originFileName.lastIndexOf('.'));
				projectImg.setSaveFolder(today);
				projectImg.setOriginFile(originFileName);
				projectImg.setSaveFile(saveFileName);
				mainImg.transferTo(new File(folder, saveFileName));
			}
			exhibitBoard.getProject().setMainImg(projectImg);		// 프로젝트 객체에 이미지 저장
		}
		
		// 새로운 파일 업로드
		if (!files[0].getOriginalFilename().equals("")) {
			String saveFolder = filePath + File.separator + today;
			
			File folder = new File(saveFolder);
			if (!folder.exists()) {
				folder.mkdirs();
			}
			
			List<FileInfo> fileInfos = new ArrayList<FileInfo>();
			for (MultipartFile mfile : files) {
				FileInfo fileInfo = new FileInfo();
				String originFileName = mfile.getOriginalFilename();
				if (!originFileName.isEmpty()) {
					String saveFileName = System.nanoTime() + originFileName.substring(originFileName.lastIndexOf('.'));
					fileInfo.setSaveFolder(today);
					fileInfo.setOriginFile(originFileName);
					fileInfo.setSaveFile(saveFileName);
					mfile.transferTo(new File(folder, saveFileName));
				}
				fileInfos.add(fileInfo);
			}
			exhibitBoard.setFileList(fileInfos);
		}
		
		
		// 새로운 이미지 업로드
		if (!images[0].getOriginalFilename().equals("")) {
			String saveFolder = imagePath + File.separator + today;
			
			File folder = new File(saveFolder);
			if (!folder.exists()) {
				folder.mkdirs();
			}
			
			List<FileInfo> fileInfos = new ArrayList<FileInfo>();
			for (MultipartFile mfile : images) {
				FileInfo fileInfo = new FileInfo();
				String originFileName = mfile.getOriginalFilename();
				if (!originFileName.isEmpty()) {
					String saveFileName = System.nanoTime() + originFileName.substring(originFileName.lastIndexOf('.'));
					fileInfo.setSaveFolder(today);
					fileInfo.setOriginFile(originFileName);
					fileInfo.setSaveFile(saveFileName);
					mfile.transferTo(new File(folder, saveFileName));
				}
				fileInfos.add(fileInfo);
			}
			exhibitBoard.setImageList(fileInfos);
		}
		
		// 프로젝트 대표 이미지 삭제 & 기존 파일 삭제 & article 수정
		if(projectService.deleteMainImg(exhibitBoard.getProject().getPid(), imagePath) && exhibitBoardService.deleteFileList(exhibitBoard.getBid(), filePath, imagePath) && exhibitBoardService.modifyArticle(exhibitBoard)) {
			exhibitBoard.getProject().setBid(exhibitBoard.getBid());
			// 프로젝트 수정
			if(projectService.modifyProject(exhibitBoard.getProject()))
				return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
		}
	}
	
	@DeleteMapping("/{bid}")
	public ResponseEntity<String> delete(@PathVariable("bid") int bid) throws Exception{
		// ariticle 정보 가져오기
		ExhibitBoard exhibitBoard = exhibitBoardService.getArticle(bid);
		
		// 프로젝트 대표 이미지 삭제 & 기존 파일 삭제 & article 삭제
		if(projectService.deleteMainImg(exhibitBoard.getProject().getPid(), imagePath) && exhibitBoardService.deleteFileList(exhibitBoard.getBid(), filePath, imagePath) && exhibitBoardService.deleteArticle(bid)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
		}
	}
}
