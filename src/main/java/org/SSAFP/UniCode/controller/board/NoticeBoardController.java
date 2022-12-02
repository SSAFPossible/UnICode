package org.SSAFP.UniCode.controller.board;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.SSAFP.UniCode.model.board.dto.FileInfo;
import org.SSAFP.UniCode.model.board.dto.NoticeBoard;
import org.SSAFP.UniCode.model.board.service.NoticeBoardServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/notice")
@Slf4j
public class NoticeBoardController {
	
	private static final Logger logger = LoggerFactory.getLogger(NoticeBoardController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private NoticeBoardServiceImpl noticeBoardService;

	@PostMapping()
	// application.properties에서 해당 속성값 가져옴
	public ResponseEntity<String> write(@Value("${file.path.upload-files}") String filePath, @RequestPart("noticeBoard") NoticeBoard noticeBoard, @RequestPart(value = "upfile", required = false) MultipartFile[] files, @RequestPart(value = "upimage", required = false) MultipartFile[] images) throws Exception {
		System.out.println("noticeBoard!!!!! : " + noticeBoard.getTitle());
		
		
		System.out.println("@@@@@@@@@@@@@");
		logger.debug("write noticeBoard : {}", noticeBoard);
		System.out.println("@@@ files : " + files);
		// FileUpload 관련 설정
		logger.debug("MultipartFile.isEmpty : {}", files == null);
		if (files != null) {
			System.out.println("@@@@@@@@@@@@@2");
			String today = new SimpleDateFormat("yyMMdd").format(new Date());
			String saveFolder = filePath + File.separator + today;
			System.out.println("@@@ saveFolder : " + saveFolder);
			logger.debug("저장 폴더 : {}", saveFolder);
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
					logger.debug("원본 파일 이름 : {}, 실제 저장 파일 이름 : {}", mfile.getOriginalFilename(), saveFileName);
					mfile.transferTo(new File(folder, saveFileName));
				}
				fileInfos.add(fileInfo);
			}
			noticeBoard.setFileList(fileInfos);
		}
		
		// ImageUpload 관련 설정
		logger.debug("MultipartFile.isEmpty : {}", images == null);
		if (images != null) {
			String today = new SimpleDateFormat("yyMMdd").format(new Date());
			String saveFolder = filePath + File.separator + today;
			System.out.println("@@@ saveFolder - Image : " + saveFolder);
			logger.debug("저장 폴더 : {}", saveFolder);
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
					logger.debug("원본 파일 이름 : {}, 실제 저장 파일 이름 : {}", mfile.getOriginalFilename(), saveFileName);
					mfile.transferTo(new File(folder, saveFileName));
				}
				fileInfos.add(fileInfo);
			}
			noticeBoard.setImageList(fileInfos);
		}
		
		if(noticeBoardService.writeArticle(noticeBoard)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
		}
	}
}
