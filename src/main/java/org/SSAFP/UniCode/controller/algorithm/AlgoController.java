package org.SSAFP.UniCode.controller.algorithm;

import java.util.List;

import org.SSAFP.UniCode.controller.board.RecruitBoardController;
import org.SSAFP.UniCode.model.algorithm.dto.Algo;
import org.SSAFP.UniCode.model.algorithm.dto.AlgoParam;
import org.SSAFP.UniCode.model.algorithm.service.AlgoServiceImpl;
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

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/algo")
@Slf4j
public class AlgoController {
	
	private static final Logger logger = LoggerFactory.getLogger(RecruitBoardController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	@Autowired
	AlgoServiceImpl algoService;
	
	@PostMapping
	public ResponseEntity<String> registAlgo(@RequestBody Algo algo) throws Exception {
		try {
			algoService.registAlgo(algo);
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(FAIL, HttpStatus.OK);
		}
	}
	
	@PutMapping
	public ResponseEntity<String> modifyAlgo(@RequestBody Algo algo) throws Exception {
		try {
			algoService.modifyAlgo(algo);
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(FAIL, HttpStatus.OK);
		}
	}
	
	@DeleteMapping("/{aid}")
	public ResponseEntity<String> deleteAlgo(@PathVariable int aid) throws Exception {
		try {
			algoService.deleteAlgo(aid);
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(FAIL, HttpStatus.OK);
		}
	}
	
	@GetMapping
	public ResponseEntity<?> getAllAlgo(@RequestBody AlgoParam algoParam) throws Exception {
		try {
			return new ResponseEntity<List<Algo>>(algoService.getAllAlgo(algoParam), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(FAIL, HttpStatus.OK);
		}
	}
	
	@GetMapping("/{aid}")
	public ResponseEntity<?> getAlgo(@PathVariable int aid) throws Exception {
		try {
			return new ResponseEntity<Algo>(algoService.getAlgo(aid), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(FAIL, HttpStatus.OK);
		}
	}
	
	@GetMapping("/pick")
	public ResponseEntity<?> pickAlgo() throws Exception {
		try {
			return new ResponseEntity<List<Algo>>(algoService.pickAlgo(), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(FAIL, HttpStatus.OK);
		}
	}
	
	@PutMapping("/{aid}")
	public ResponseEntity<String> updateSolved(@PathVariable int aid) throws Exception {
		try {
			boolean solved = algoService.updateSolved(aid);
			if(solved) { // 푼 문제로 바꾸기 
				return new ResponseEntity<String>("true", HttpStatus.OK);
			} else { // 안 푼 문제로 바꾸기
				return new ResponseEntity<String>("false", HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(FAIL, HttpStatus.OK);
		}
	}
}