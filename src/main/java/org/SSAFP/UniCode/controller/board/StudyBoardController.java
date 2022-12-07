package org.SSAFP.UniCode.controller.board;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.SSAFP.UniCode.model.board.dto.Board;
import org.SSAFP.UniCode.model.board.dto.FileInfo;
import org.SSAFP.UniCode.model.board.dto.StudyBoard;
import org.SSAFP.UniCode.model.board.service.BoardServiceImpl;
import org.SSAFP.UniCode.model.board.service.StudyBoardServiceImpl;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/study")
@Slf4j
public class StudyBoardController {
	private static final Logger logger = LoggerFactory.getLogger(NoticeBoardController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private StudyBoardServiceImpl studyBoardService;

	@Value("${file.path.upload-files}")
	String filePath;
	
	@Value("${file.path.upload-images}")
	String imagePath;
	
	@PostMapping()
	public ResponseEntity<String> write(@RequestPart(value="studyBoard") StudyBoard studyBoard, @RequestPart(value = "upfile", required = false) MultipartFile[] files, @RequestPart(value = "upimage", required = false) MultipartFile[] images) throws Exception {
		System.out.println(studyBoard.getUid());
		
		// 파일 업로드
		if (files != null) {
			String today = new SimpleDateFormat("yyMMdd").format(new Date());
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
			studyBoard.setFileList(fileInfos);
		}
		
		
		// 이미지 업로드
		if (images != null) {
			String today = new SimpleDateFormat("yyMMdd").format(new Date());
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
			studyBoard.setImageList(fileInfos);
		}
		
		if(studyBoardService.writeArticle(studyBoard)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
		}
	}
	
	// Category와 tag 없이 모든 studyBoard 가져올 경우   
	@GetMapping
	public ResponseEntity<List<StudyBoard>> getAllArticle() throws Exception{
		return new ResponseEntity<List<StudyBoard>>(studyBoardService.getAllBoardArticle(), HttpStatus.OK);
	}
	

	@GetMapping("/{bid}")
	public ResponseEntity<Board> getArticle(@PathVariable("bid") int bid) throws Exception{
		return new ResponseEntity<Board>(studyBoardService.getArticle(bid), HttpStatus.OK);
	}
	
	@PutMapping
	public ResponseEntity<String> modify(@RequestPart(value = "studyBoard") StudyBoard studyBoard, @RequestPart(value = "upfile", required = false) MultipartFile[] files, @RequestPart(value = "upimage", required = false) MultipartFile[] images) throws Exception{
		// 새로운 파일 업로드
		if (files != null) {
			String today = new SimpleDateFormat("yyMMdd").format(new Date());
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
			studyBoard.setFileList(fileInfos);
		}
		
		
		// 새로운 이미지 업로드
		if (images != null) {
			String today = new SimpleDateFormat("yyMMdd").format(new Date());
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
			studyBoard.setImageList(fileInfos);
		}
		
		// 기존 파일 삭제 & article 수정
		if(studyBoardService.deleteFileList(studyBoard.getBid(), filePath, imagePath) && studyBoardService.modifyArticle(studyBoard)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
		}
	}
	
	@DeleteMapping("/{bid}")
	public ResponseEntity<String> delete(@PathVariable("bid") int bid) throws Exception{
		// 기존 파일 삭제 & article 삭제
		if(studyBoardService.deleteFileList(bid, filePath, imagePath) && studyBoardService.deleteArticle(bid)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
		}
	}

}
