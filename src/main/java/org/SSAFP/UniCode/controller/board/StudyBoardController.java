package org.SSAFP.UniCode.controller.board;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.SSAFP.UniCode.model.board.dto.BoardLike;
import org.SSAFP.UniCode.model.board.dto.FileInfo;
import org.SSAFP.UniCode.model.board.dto.StudyBoard;
import org.SSAFP.UniCode.model.board.dto.StudyBoardParam;
import org.SSAFP.UniCode.model.board.service.StudyBoardServiceImpl;
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
@RequestMapping("/study")
@Slf4j
public class StudyBoardController {
	private static final Logger logger = LoggerFactory.getLogger(NoticeBoardController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	@Qualifier("studyBoardServiceImpl")
	private StudyBoardServiceImpl studyBoardService;

	@Value("${file.path.upload-files}")
	String filePath;

	@Value("${file.path.upload-images}")
	String imagePath;

	// StudyBoard 작성
	@PostMapping
	public ResponseEntity<String> writeStudyBoard(@RequestPart(value = "studyBoard") StudyBoard studyBoard,
			@RequestPart(value = "upfile", required = false) MultipartFile[] files,
			@RequestPart(value = "upimage", required = false) MultipartFile[] images) throws Exception {
		System.out.println(studyBoard.getUid());

		try {
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
						String saveFileName = System.nanoTime()
								+ originFileName.substring(originFileName.lastIndexOf('.'));
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
						String saveFileName = System.nanoTime()
								+ originFileName.substring(originFileName.lastIndexOf('.'));
						fileInfo.setSaveFolder(today);
						fileInfo.setOriginFile(originFileName);
						fileInfo.setSaveFile(saveFileName);
						mfile.transferTo(new File(folder, saveFileName));
					}
					fileInfos.add(fileInfo);
				}
				studyBoard.setImageList(fileInfos);
			}

			studyBoardService.writeArticle(studyBoard);
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(FAIL, HttpStatus.OK);
		}

	}

	// 태그별로 StudyBoard 가져오기
	// studyBoardParam
	// - category : cs/algo/job
	// - tag : info/ ques
	// - uid : 내가 쓴 글 태그 클릭 시 user 정보
	// - sortLike : 좋아요 순 정렬 여부 true : 좋아요 순 정렬 / false : created_time정렬
	@GetMapping
	public ResponseEntity<List<StudyBoard>> getAllStudyBoard(@RequestBody StudyBoardParam studyBoardParam)
			throws Exception {

		List<String> categoryTag = new LinkedList<String>();
		if (studyBoardParam.getCategory().size() > 0) {
			for (String category : studyBoardParam.getCategory()) {
				if (studyBoardParam.getTag().size() > 0) {
					for (String tag : studyBoardParam.getTag()) {
						categoryTag.add(category + "_" + tag);
					}
				} else {
					categoryTag.add(category + "_info");
					categoryTag.add(category + "_ques");
				}
			}
		} else if (studyBoardParam.getTag().size() > 0) {
			for (String tag : studyBoardParam.getTag()) {
				categoryTag.add("cs_" + tag);
				categoryTag.add("algo_" + tag);
				categoryTag.add("job_" + tag);
			}
		}
		studyBoardParam.setCategorySize(studyBoardParam.getCategory().size());
		studyBoardParam.setTagSize(studyBoardParam.getTag().size());

		studyBoardParam.setCategoryTag(categoryTag);
		return new ResponseEntity<List<StudyBoard>>(studyBoardService.getAllStudyBoard(studyBoardParam), HttpStatus.OK);
	}

//	// 좋아요 증가
//	@PutMapping("/increaseLike/{bid}")
//	public ResponseEntity<String> increaseLike(@PathVariable int bid) throws Exception {
//		// 기존 파일 삭제 & article 삭제
//		if (studyBoardService.increaseLike(bid)) {
//			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
//		} else {
//			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
//		}
//	}

	// 하나의 studyBoard 조회
	@GetMapping("/{bid}")
	public ResponseEntity<StudyBoard> getStudyBoard(@PathVariable("bid") int bid) throws Exception {
		return new ResponseEntity<StudyBoard>(studyBoardService.getStudyBoard(bid), HttpStatus.OK);
	}

	// 작성한 StudyBoard 수정
	@PutMapping
	public ResponseEntity<String> modify(@RequestPart(value = "studyBoard") StudyBoard studyBoard,
			@RequestPart(value = "upfile", required = false) MultipartFile[] files,
			@RequestPart(value = "upimage", required = false) MultipartFile[] images) throws Exception {
		// 새로운 파일 업로드
		try {
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
						String saveFileName = System.nanoTime()
								+ originFileName.substring(originFileName.lastIndexOf('.'));
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
						String saveFileName = System.nanoTime()
								+ originFileName.substring(originFileName.lastIndexOf('.'));
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
			studyBoardService.deleteFileList(studyBoard.getBid(), filePath, imagePath);
			studyBoardService.modifyArticle(studyBoard);
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(FAIL, HttpStatus.OK);
		}
	}

	// StudyBoard 삭제
	@DeleteMapping("/{bid}")
	public ResponseEntity<String> delete(@PathVariable("bid") int bid) throws Exception {
		try {
			// 기존 파일 삭제 & article 삭제
			studyBoardService.deleteFileList(bid, filePath, imagePath);
			studyBoardService.deleteArticle(bid);
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<String>(FAIL, HttpStatus.OK);
		}
	}

	@PostMapping("/like")
	public ResponseEntity<String> clickLike(@RequestBody BoardLike boardLike) throws Exception {
		try {
			boolean like = studyBoardService.clickLike(boardLike);
			if (like) { // 좋아요 등록
				return new ResponseEntity<String>("true", HttpStatus.OK);
			} else { // 좋아요 취소
				return new ResponseEntity<String>("false", HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>(FAIL, HttpStatus.OK);
		}
	}

}
