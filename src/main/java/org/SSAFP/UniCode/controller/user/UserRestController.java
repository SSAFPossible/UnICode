package org.SSAFP.UniCode.controller.user;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.SSAFP.UniCode.jwt.JwtUtil;
import org.SSAFP.UniCode.model.user.dto.User;
import org.SSAFP.UniCode.model.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/user")
@CrossOrigin("*")
@Slf4j
public class UserRestController {
	
	static public final String SECCESS = "seccess";
	
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
	public ResponseEntity<String> regist(@RequestBody User user){
		log.info("regist User Info : {}", user);
		Service.regist(user);
		return new ResponseEntity<String>(SECCESS, HttpStatus.ACCEPTED);
	}
	
	/**
	 * 로그인.
	 * @param user ( id : String, password : hash value )
	 * @return tokens
	 */
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody User user){
		log.info("login User Info : {}", user);
		Map<String, Object> resultMap = new HashMap<>();
		
		User loginUser = Service.signin(user.getUid(), user.getPassword());
		resultMap.put("jwt-auth-token", loginUser.getAuthToken());
		
		Map<String, Object> info = jwtService.checkAndGetClaims(loginUser.getAuthToken());
		resultMap.putAll(info);
		return new ResponseEntity<User>(loginUser, HttpStatus.ACCEPTED);
	}
	
	
	/**
	 * 토큰 재발급
	 * @param user(email, refreshToken)
	 * @param res
	 * @return
	 */
	@PostMapping("/refresh")
	public ResponseEntity<Map<String, Object>> refreshToken(@RequestBody User user, HttpServletResponse res){
		Map<String,Object> resultMap = new HashMap<>();
		
		jwtService.checkAndGetClaims(user.getRefreashToken());
		
		if (user.getRefreashToken().equals(Service.getRefreshToken(user.getUid()))) {
			
			String authToken = jwtService.createAuthToken(user.getUid());
			resultMap.put("jwt-auth-token", authToken);
			Map<String, Object> info = jwtService.checkAndGetClaims(authToken);
			resultMap.putAll(info);
		}
		return new ResponseEntity<Map<String,Object>>(resultMap, HttpStatus.ACCEPTED);
	}
	
	@PostMapping("/logout")
	public ResponseEntity<String> logout(@RequestParam String email){
		log.debug("logout : {}", email);
		Service.logout(email);
		return new ResponseEntity<String>(SECCESS, HttpStatus.ACCEPTED);
	}
	
	/**
	 * 아이디 찾기.
	 * @param user
	 * @return
	 */
	@PostMapping("find/id")
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
	@PostMapping("find/pwd")
	public ResponseEntity<?> userPwdFind(@RequestBody User user){
		log.info("login User Info : {}", user);
		User FindUser = null;
		return new ResponseEntity<User>(FindUser, HttpStatus.OK);
	}
	
	
	/**
	 * 사용자 정보
	 * @return user
	 */
	@GetMapping("/{id}")
	public ResponseEntity<User> userInfo(@PathVariable("id") String id){
		log.debug("info : {}");
		User user = Service.getInfo(id);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	/**
	 *  회원정보 수정.
	 *  @param user
	 * 	@return user
	 */
	@PutMapping("/info")
	public ResponseEntity<String> userUpdate(@RequestBody User user){
		log.info("login User Info : {}", user);
		Service.putInfo(user);
		return new ResponseEntity<String>(SECCESS, HttpStatus.OK);
	}
	
	/**
	 * 회원 탈퇴.
	 * @param String id
	 * @return
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<String> userDelete(@PathVariable("id") String id){
		log.info("login User Info : {}", id);
		Service.deleteInfo(id);
		return new ResponseEntity<String>(SECCESS, HttpStatus.OK);
	}
}