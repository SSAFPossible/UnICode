package org.SSAFP.UniCode.controller.comment;

import java.util.List;

import org.SSAFP.UniCode.controller.board.NoticeBoardController;
import org.SSAFP.UniCode.model.comment.dto.Comment;
import org.SSAFP.UniCode.model.comment.service.CommentServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.experimental.StandardException;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/comment")
@Slf4j
public class CommentController {
	private static final Logger logger = LoggerFactory.getLogger(CommentController.class);
	
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	CommentServiceImpl commentService;

	@PostMapping("/first")
	public ResponseEntity<String> writeFirstComment(@RequestBody Comment comment) {
		try {
			commentService.writeFirstComment(comment);
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(FAIL, HttpStatus.OK);
		}
	}
	
	@PostMapping("/second")
	public ResponseEntity<String> writeSecondComment(@RequestBody Comment comment) {
		try {
			commentService.writeSecondComment(comment);
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(FAIL, HttpStatus.OK);
		}
	}

	@PutMapping
	public ResponseEntity<String> modifyComment(@RequestBody Comment comment) {
		try {
			commentService.modifyComment(comment);
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(FAIL, HttpStatus.OK);
		}
	}

	@PutMapping("/{cid}")
	public ResponseEntity<String> deleteComment(@PathVariable int cid) {
		try {
			commentService.deleteComment(cid);
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(FAIL, HttpStatus.OK);
		}
	}
	
	@GetMapping("/{bid}")
	public ResponseEntity<List<Comment>> getComment(@PathVariable int bid) throws Exception {
		logger.info("getArticle - 호출 : " + bid);
		logger.debug("debug");
		return new ResponseEntity<List<Comment>>(commentService.getComment(bid),HttpStatus.OK);
	}
	
}
