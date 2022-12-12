package org.SSAFP.UniCode.jwt;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.SSAFP.UniCode.model.user.dto.User;
import org.SSAFP.UniCode.model.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
		User user = uService.signin("ssafy", "1234");
		log.info("login : {}", user);
	}
	
	@Test
	public void loginFailTest() {
		assertThrows(RuntimeException.class, () -> {			
			uService.signin("ssafy", "1234567890");
		});
	}
	
	@Test
	public void registSuccessTest() {
		User user = User.builder().uid("kyulee").password("1234").name("이규원").profile("img_url").accessImg("access_url").build();
		uService.regist(user);
	}
	
	@Test
	public void registFailTest() {
		User user = User.builder().uid("ssafy").password("1234").name("이규원").profile("img_url").accessImg("access_url").build();
		uService.regist(user);
	}
	
	@Test
	public void updateSuccessTest() {
		User user = User.builder().uid("ssafy").password("1234").name("이규원").profile("img_url").accessImg("access_url").build();
		uService.userUpdate(user);
	}

	@Test
	public void updateFailTest() {
		User user = User.builder().uid("kyulee").password("1234").name("이규원").profile("img_url").accessImg("access_url").build();
		uService.userUpdate(user);
	}
	
}
