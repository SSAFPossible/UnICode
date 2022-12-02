package org.SSAFP.UniCode.controller.user;

import java.util.HashMap;
import java.util.Map;

import org.SSAFP.UniCode.jwt.JwtUtil;
import org.SSAFP.UniCode.model.user.dto.User;
import org.SSAFP.UniCode.model.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/user")
@CrossOrigin("*")
@Slf4j
public class UserRestController {
	
	@Autowired
	private UserService Service;
	
	@Autowired
	private JwtUtil jwtService;
	/**
	 * 회원가입.
	 * @param user ( id : String, password : String ...  )
	 * @return message
	 */
	@PostMapping("/regist")
	public ResponseEntity<?> regist(@RequestBody User user){
		log.info("regist User Info : {}", user);
		Map<String, Object> resultMap = new HashMap<>();
		User loginUser = Service.signin(user.getEmail(), user.getPassword());
		resultMap.put("jwt-auth-token", loginUser.getAuthToken());
		Map<String, Object> info = jwtService.checkAndGetClaims(loginUser.getAuthToken());
		resultMap.putAll(info);
		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.ACCEPTED);
	}
	
	/**
	 * 로그인.
	 * @param user ( id : String, password : hash value )
	 * @return tokens
	 */
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody User user){
		log.info("login User Info : {}", user);
		User loginUser = null;
		return new ResponseEntity<User>(loginUser, HttpStatus.OK);
	}
	
	
	/**
	 * 아이디 찾기.
	 * @param user
	 * @return
	 */
	@PostMapping("/findid")
	public ResponseEntity<?> userIdFind(@RequestBody User user){
		log.info("login User Info : {}", user);
		User FindUser = null;
		return new ResponseEntity<User>(FindUser, HttpStatus.OK);
	}
	
	/**
	 * 비밀번호 찾기.
	 * @param user
	 * @return
	 */
	public ResponseEntity<?> userPwdFind(@RequestBody User user){
		log.info("login User Info : {}", user);
		User FindUser = null;
		return new ResponseEntity<User>(FindUser, HttpStatus.OK);
	}
	
	/**
	 *  회원정보 수정.
	 *  @param user
	 * 	@return user
	 */
	public ResponseEntity<?> userUpdate(User user){
		log.info("login User Info : {}", user);
		User updateUser = null;
		return new ResponseEntity<User>(updateUser, HttpStatus.OK);
	}
	
	/**
	 * 회원 탈퇴.
	 * @param user ( String user, password : hashValue )
	 * @return
	 */
	public ResponseEntity<?> userDelete(User user){
		log.info("login User Info : {}", user);
		String userId = null;
		return new ResponseEntity<String>("회원 탈퇴 성공", HttpStatus.OK);
	}
	
}
