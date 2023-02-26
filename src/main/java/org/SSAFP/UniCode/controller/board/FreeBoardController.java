package org.SSAFP.UniCode.controller.board;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.SSAFP.UniCode.model.board.dto.Board;
import org.SSAFP.UniCode.model.board.dto.FileInfo;
import org.SSAFP.UniCode.model.board.service.BoardServiceImpl;
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

@RestController
@RequestMapping("/free")
public class FreeBoardController {

	private static final Logger logger = LoggerFactory.getLogger(FreeBoardController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	@Qualifier("boardServiceImpl")
	private BoardServiceImpl freeBoardService;

	@Value("${file.path.upload-files}")
	String filePath;

	@Value("${file.path.upload-images}")
	String imagePath;

	@PostMapping
	public ResponseEntity<String> write(@RequestPart(value = "freeBoard") Board freeBoard,
			@RequestPart(value = "upfile", required = false) MultipartFile[] files,
			@RequestPart(value = "upimage", required = false) MultipartFile[] images) throws Exception {

		try {
			// 파일 업로드
			if (files!=null) {
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
						String saveFileName = System.nanoTime()
								+ originFileName.substring(originFileName.lastIndexOf('.'));
						fileInfo.setSaveFolder(today);
						fileInfo.setOriginFile(originFileName);
						fileInfo.setSaveFile(saveFileName);
						mfile.transferTo(new File(folder, saveFileName));
					}
					fileInfos.add(fileInfo);
				}
				freeBoard.setFileList(fileInfos);
			}

			// 이미지 업로드
			if (images!=null) {
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
						String saveFileName = System.nanoTime()
								+ originFileName.substring(originFileName.lastIndexOf('.'));
						fileInfo.setSaveFolder(today);
						fileInfo.setOriginFile(originFileName);
						fileInfo.setSaveFile(saveFileName);
						mfile.transferTo(new File(folder, saveFileName));
					}
					fileInfos.add(fileInfo);
				}
				freeBoard.setImageList(fileInfos);
			}

			// 글 작성
			freeBoardService.writeArticle(freeBoard);

			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(FAIL, HttpStatus.OK);
		}
	}

	@GetMapping
	public ResponseEntity<List<Board>> getAllArticle() throws Exception {
		return new ResponseEntity<List<Board>>(freeBoardService.getAllArticle("free"), HttpStatus.OK);
	}

	@GetMapping("/{bid}")
	public ResponseEntity<?> getArticle(@PathVariable("bid") int bid) throws Exception {
		Board registeredFreeArticle = freeBoardService.getArticle(bid);
		if (registeredFreeArticle == null) {
			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Board>(registeredFreeArticle, HttpStatus.OK);
	}

	@PutMapping
	public ResponseEntity<String> modify(@RequestPart(value = "freeBoard") Board freeBoard,
			@RequestPart(value = "upfile", required = false) MultipartFile[] files,
			@RequestPart(value = "upimage", required = false) MultipartFile[] images) throws Exception {

		try {
			// 기존 파일 삭제
			freeBoardService.deleteFileList(freeBoard.getBid(), filePath, imagePath);

			// 새로운 파일 업로드
			if (files!=null) {
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
						String saveFileName = System.nanoTime()
								+ originFileName.substring(originFileName.lastIndexOf('.'));
						fileInfo.setSaveFolder(today);
						fileInfo.setOriginFile(originFileName);
						fileInfo.setSaveFile(saveFileName);
						mfile.transferTo(new File(folder, saveFileName));
					}
					fileInfos.add(fileInfo);
				}
				freeBoard.setFileList(fileInfos);
			}

			// 새로운 이미지 업로드
			if (images!=null) {
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
						String saveFileName = System.nanoTime()
								+ originFileName.substring(originFileName.lastIndexOf('.'));
						fileInfo.setSaveFolder(today);
						fileInfo.setOriginFile(originFileName);
						fileInfo.setSaveFile(saveFileName);
						mfile.transferTo(new File(folder, saveFileName));
					}
					fileInfos.add(fileInfo);
				}
				freeBoard.setImageList(fileInfos);
			}

			// 기존 파일 삭제 & article 수정
			freeBoardService.deleteFileList(freeBoard.getBid(), filePath, imagePath);
			freeBoardService.modifyArticle(freeBoard);

			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(FAIL, HttpStatus.OK);
		}
	}
	
	@DeleteMapping
	public ResponseEntity<String> delete(@RequestBody Board board) throws Exception {
		try {
			// 기존 파일 삭제 & article 삭제
			freeBoardService.deleteFileList(board.getBid(), filePath, imagePath);
			freeBoardService.deleteArticle(board.getBid(), board.getUid());
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(FAIL, HttpStatus.OK);
		}
	}

}