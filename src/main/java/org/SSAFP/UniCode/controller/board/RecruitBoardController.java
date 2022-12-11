package org.SSAFP.UniCode.controller.board;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.SSAFP.UniCode.model.board.dto.Board;
import org.SSAFP.UniCode.model.board.dto.BoardLike;
import org.SSAFP.UniCode.model.board.dto.FileInfo;
import org.SSAFP.UniCode.model.board.dto.Language;
import org.SSAFP.UniCode.model.board.dto.RecruitBoard;
import org.SSAFP.UniCode.model.board.service.BoardServiceImpl;
import org.SSAFP.UniCode.model.board.service.RecruitBoardServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
@RequestMapping("/recruit")
@Slf4j
public class RecruitBoardController {
	
	private static final Logger logger = LoggerFactory.getLogger(RecruitBoardController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	@Autowired
	@Qualifier("recruitBoardServiceImpl")
	private RecruitBoardServiceImpl recruitBoardService; // 인원 모집 게시판 기능 Service

	@Value("${file.path.upload-files}")
	String filePath;
	
	@Value("${file.path.upload-images}")
	String imagePath;
	
	@PostMapping()
	public ResponseEntity<String> write(@RequestPart(value="recruitBoard") RecruitBoard recruitBoard, @RequestPart(value="language") Language language, @RequestPart(value = "upfile", required = false) MultipartFile[] files, @RequestPart(value = "upimage", required = false) MultipartFile[] images) throws Exception {
		// 파일 업로드
		if (!files[0].getOriginalFilename().equals("")) {
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
			recruitBoard.setFileList(fileInfos);
		}
		
		// 이미지 업로드
		if (!images[0].getOriginalFilename().equals("")) {
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
			recruitBoard.setImageList(fileInfos);
		}
		
		// 글 작성
		if(recruitBoardService.writeArticle(recruitBoard) && recruitBoardService.modifyRecruitInfo(recruitBoard)) {
			// 태그 등록
			if(language.getName().size() > 0) {
				Map<String, Object> tag = new HashMap<>();
				tag.put("bid", recruitBoard.getBid());
				tag.put("language", language);
				if(recruitBoardService.writeTag(tag)) {
					return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
				} else {
					return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
				}
			} else {
				return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
			}
		} else {
			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
		}
	}
	
	@GetMapping("/{bid}")
	public ResponseEntity<RecruitBoard> getRecruitArticle(@PathVariable("bid") int bid) throws Exception{
		return new ResponseEntity<RecruitBoard>(recruitBoardService.getRecruitArticle(bid), HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<List<RecruitBoard>> getRecruitAllArticle(@RequestBody Language language) throws Exception{
		language.setNameSize(language.getName().size());
		return new ResponseEntity<List<RecruitBoard>>(recruitBoardService.getRecruitAllArticle(language), HttpStatus.OK);
	}
	
	@PutMapping
	public ResponseEntity<String> modify(@RequestPart(value="recruitBoard") RecruitBoard recruitBoard, @RequestPart(value="language") Language language, @RequestPart(value = "upfile", required = false) MultipartFile[] files, @RequestPart(value = "upimage", required = false) MultipartFile[] images) throws Exception{
		// 새로운 파일 업로드
		if (!files[0].getOriginalFilename().equals("")) {
			System.out.println("들어왔음");
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
			recruitBoard.setFileList(fileInfos);
		}
		
		// 새로운 이미지 업로드
		if (!images[0].getOriginalFilename().equals("")) {
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
			recruitBoard.setImageList(fileInfos);
		}
		
		// 기존 파일 삭제 & article 수정 & tag 수정
		if(recruitBoardService.deleteFileList(recruitBoard.getBid(), filePath, imagePath) 
				&& recruitBoardService.modifyArticle(recruitBoard) && recruitBoardService.modifyRecruitInfo(recruitBoard)
				&& recruitBoardService.deleteTag(recruitBoard.getBid())) {
			// 새로운 태그 등록
			if(language.getName().size() > 0) {
				Map<String, Object> tag = new HashMap<>();
				tag.put("bid", recruitBoard.getBid());
				tag.put("language", language);
				if(recruitBoardService.writeTag(tag)) {
					return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
				} else {
					return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
				}
			} else {
				return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
			}
		} else {
			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
		}
	}
	
	@DeleteMapping("/{bid}")
	public ResponseEntity<String> delete(@PathVariable("bid") int bid) throws Exception{
		// 기존 파일 삭제 & article 삭제
		if(recruitBoardService.deleteFileList(bid, filePath, imagePath) && recruitBoardService.deleteArticle(bid)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
		}
	}
	
	@PostMapping("/like")
	public ResponseEntity<String> clickLike(@RequestBody BoardLike boardLike) throws Exception {
		boolean like = recruitBoardService.clickLike(boardLike);
		if(like) { // 좋아요 등록
			return new ResponseEntity<String>("true", HttpStatus.OK);
		} else { // 좋아요 취소
			return new ResponseEntity<String>("false", HttpStatus.OK);
		}
	}
}
