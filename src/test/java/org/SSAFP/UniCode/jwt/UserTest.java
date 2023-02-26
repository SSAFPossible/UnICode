package org.SSAFP.UniCode.jwt;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.Map;

import org.SSAFP.UniCode.model.user.dto.User;
import org.SSAFP.UniCode.model.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Transactional
@Slf4j
public class UserTest {
	
	@Autowired
	UserService uService;
	
	@Test
	public void loginSuccessTest() {
		User user = User.builder().uid("ssafy").password("1234").name("이규원").build();
		uService.regist(user);
		user = uService.signin("ssafy", "1234");
		log.info("login : {}", user);
	}
	
	@Test
	public void loginFailTest() {
		User user = User.builder().uid("ssafy").password("1234").name("이규원").build();
		assertThrows(RuntimeException.class, () -> {			
			uService.signin("ssafy", "1234567890");
		});
	}
	
	@Test
	public void registSuccessTest() {
		User user = User.builder().uid("kyulee").password("1234").name("이규원").build();
		uService.regist(user);
	}
	
	@Test
	public void registFailTest() {
		User user = User.builder().uid("ssafy").password("1234").name("이규원").build();
		assertThrows(DuplicateKeyException.class, () -> {			
			uService.regist(user);
		});
	}
	
	@Test
	public void updateSuccessTest() {
		User user = User.builder().uid("ssafy").password("1234").name("이규원").build();
		uService.regist(user);
		user = User.builder().uid("ssafy").password("123456").name("이규원").build();
		uService.modifyInfo(user);
	}

	@Test
	public void updateFailTest() {
		User user = User.builder().uid("kyulee").password("1234").name("이규원").build();
		assertThrows(RuntimeException.class, () -> {			
			uService.modifyInfo(user);
		});
	}
	
	@Test
	public void getSuccessTest() {
		User user = User.builder().uid("kyulee").password("1234").name("이규원").build();
		uService.regist(user);
		uService.getInfo("kyulee");
	}
	
	@Test
	public void getFailTest() {
		assertThrows(RuntimeException.class, () -> {			
		uService.getInfo("kyulee");
		});
	}
	
	@Test
	public void deleteSuccessTest() {
		User user = User.builder().uid("kyulee").password("1234").name("이규원").build();
		uService.regist(user);
		uService.deleteInfo("kyulee");
	}
	
	@Test
	public void deleteFailTest() {
		assertThrows(RuntimeException.class, () -> {			
		uService.deleteInfo("kyulee");
		});
	}
	
	@Test
	public void getRankList() {
		List<Map<String, Object>> rankList = uService.getRankList();
		log.debug("rank list : {}", rankList);
	}
}
