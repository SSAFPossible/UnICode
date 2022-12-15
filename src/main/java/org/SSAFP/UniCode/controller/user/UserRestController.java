package org.SSAFP.UniCode.controller.user;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.SSAFP.UniCode.jwt.JwtUtil;
import org.SSAFP.UniCode.model.board.dto.FileInfo;
import org.SSAFP.UniCode.model.user.dto.User;
import org.SSAFP.UniCode.model.user.dto.UserImg;
import org.SSAFP.UniCode.model.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sun.scenario.effect.impl.sw.sse.SSERendererDelegate;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/user")
@CrossOrigin("*")
@Slf4j
public class UserRestController {
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private UserService Service;

	@Autowired
	private JwtUtil jwtService;

	@Value("${file.path.upload-profile}")
	String profilePath;

	@Value("${file.path.upload-access-img}")
	String accessImgPath;

	/**
	 * 회원가입.
	 * 
	 * @param user ( id : String, password : String ... )
	 * @return message
	 */
	@PostMapping("/regist")
	public ResponseEntity<String> regist(@RequestPart(value = "user") User user,
			@RequestPart(value = "profile", required = false) MultipartFile profileImg,
			@RequestPart(value = "accessImg", required = false) MultipartFile accessImg) {
		try {
			// 이미지 업로드
			if (profileImg != null) {
				String today = new SimpleDateFormat("yyMMdd").format(new Date());
				String saveFolder = profilePath + File.separator + today;

				File folder = new File(saveFolder);
				if (!folder.exists()) {
					folder.mkdirs();
				}

				UserImg profile = new UserImg();
				String originFileName = profileImg.getOriginalFilename();
				if (!originFileName.isEmpty()) {
					String saveFileName = System.nanoTime() + originFileName.substring(originFileName.lastIndexOf('.'));
					profile.setSaveFolder(today);
					profile.setOriginFile(originFileName);
					profile.setSaveFile(saveFileName);
					profileImg.transferTo(new File(folder, saveFileName));
				}
				user.setProfile(profile);

			}
			// 이미지 업로드
			if (accessImg != null) {
				String today = new SimpleDateFormat("yyMMdd").format(new Date());
				String saveFolder = accessImgPath + File.separator + today;

				File folder = new File(saveFolder);
				if (!folder.exists()) {
					folder.mkdirs();
				}

				UserImg accImg = new UserImg();
				String originFileName = accessImg.getOriginalFilename();
				if (!originFileName.isEmpty()) {
					String saveFileName = System.nanoTime() + originFileName.substring(originFileName.lastIndexOf('.'));
					accImg.setSaveFolder(today);
					accImg.setOriginFile(originFileName);
					accImg.setSaveFile(saveFileName);
					accessImg.transferTo(new File(folder, saveFileName));
				}
				user.setAccessImg(accImg);

			}
			log.info("regist User Info : {}", user);
			Service.regist(user);
			return new ResponseEntity<String>(SUCCESS, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			return new ResponseEntity<String>(FAIL, HttpStatus.NOT_ACCEPTABLE);
		}
	}

	/**
	 * 로그인.
	 * 
	 * @param user ( id : String, password : hash value )
	 * @return tokens
	 */
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody User user) {
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
	 * 
	 * @param user(email, refreshToken)
	 * @param res
	 * @return
	 */
	@PostMapping("/refresh")
	public ResponseEntity<Map<String, Object>> refreshToken(@RequestBody User user, HttpServletResponse res) {
		Map<String, Object> resultMap = new HashMap<>();

		jwtService.checkAndGetClaims(user.getRefreashToken());

		if (user.getRefreashToken().equals(Service.getRefreshToken(user.getUid()))) {

			String authToken = jwtService.createAuthToken(user.getUid());
			resultMap.put("jwt-auth-token", authToken);
			Map<String, Object> info = jwtService.checkAndGetClaims(authToken);
			resultMap.putAll(info);
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.ACCEPTED);
	}

	@PostMapping("/logout")
	public ResponseEntity<String> logout(@RequestParam String id) {
		log.debug("logout : {}", id);
		Service.logout(id);
		return new ResponseEntity<String>(SUCCESS, HttpStatus.ACCEPTED);
	}

	/**
	 * 아이디 찾기.
	 * 
	 * @param user
	 * @return
	 */
	@PostMapping("find/id")
	public ResponseEntity<String> userIdFind(@RequestBody String email) {
		log.info("find id email : {}", email);
		String uid = Service.FindIdByEmail(email);
		return new ResponseEntity<String>(uid, HttpStatus.OK);
	}

	/**
	 * 비밀번호 찾기.
	 * 
	 * @param user
	 * @return
	 */
	@PostMapping("find/pwd")
	public ResponseEntity<?> userPwdFind(@RequestBody User user) {
		log.info("login User Info : {}", user);
		User FindUser = null;
		return new ResponseEntity<User>(FindUser, HttpStatus.OK);
	}

	/**
	 * 사용자 정보
	 * 
	 * @return user
	 */
	@GetMapping("/{uid}")
	public ResponseEntity<User> userInfo(@PathVariable("uid") String id) {
		log.debug("info : {}");
		User user = Service.getInfo(id);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	/**
	 * 회원정보 수정.
	 * 
	 * @param user
	 * @return user
	 */
	@PutMapping("/info")
	public ResponseEntity<String> modify(@RequestPart(value = "user") User user,
			@RequestPart(value = "profile", required = false) MultipartFile profileImg,
			@RequestPart(value = "accessImg", required = false) MultipartFile accessImg) {
		try {
			// 이미지 업로드
			if (profileImg != null) {
				String today = new SimpleDateFormat("yyMMdd").format(new Date());
				String saveFolder = profilePath + File.separator + today;

				File folder = new File(saveFolder);
				if (!folder.exists()) {
					folder.mkdirs();
				}

				UserImg profile = new UserImg();
				String originFileName = profileImg.getOriginalFilename();
				if (!originFileName.isEmpty()) {
					String saveFileName = System.nanoTime() + originFileName.substring(originFileName.lastIndexOf('.'));
					profile.setSaveFolder(today);
					profile.setOriginFile(originFileName);
					profile.setSaveFile(saveFileName);
					profileImg.transferTo(new File(folder, saveFileName));
				}
				user.setProfile(profile);

			}
			// 이미지 업로드
			if (accessImg != null) {
				String today = new SimpleDateFormat("yyMMdd").format(new Date());
				String saveFolder = accessImgPath + File.separator + today;

				File folder = new File(saveFolder);
				if (!folder.exists()) {
					folder.mkdirs();
				}

				UserImg accImg = new UserImg();
				String originFileName = accessImg.getOriginalFilename();
				if (!originFileName.isEmpty()) {
					String saveFileName = System.nanoTime() + originFileName.substring(originFileName.lastIndexOf('.'));
					accImg.setSaveFolder(today);
					accImg.setOriginFile(originFileName);
					accImg.setSaveFile(saveFileName);
					accessImg.transferTo(new File(folder, saveFileName));
				}
				user.setAccessImg(accImg);

			}
			log.info("regist User Info : {}", user);
			Service.modifyInfo(user);
			return new ResponseEntity<String>(SUCCESS, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			return new ResponseEntity<String>(FAIL, HttpStatus.NOT_ACCEPTABLE);
		}
	}

	/**
	 * 회원 탈퇴.
	 * 
	 * @param String id
	 * @return
	 */
	@DeleteMapping("/{uid}")
	public ResponseEntity<String> delete(@PathVariable("uid") String id) {
		log.info("login User Info : {}", id);
		Service.deleteInfo(id);
		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
	}
}
