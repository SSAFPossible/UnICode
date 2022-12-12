package org.SSAFP.UniCode.jwt;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.startsWithIgnoringCase;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class JwtTest {
	
	@Autowired
	JwtUtil util;
	
	@Autowired
	MockMvc MockMvc;
	
	@Test
	public void tokenGenTest() {
		// given
		String email = "kyoowon95@gmail.com";
		// when
		String token = util.createAuthToken(email);
		// then
		assertNotNull(token);
		log.debug(token);
	}
	
	@Test
	public void contentTest() {
		// given
		String email = "kyoowon95@gmail.com";
		// when
		String token = util.createAuthToken(email);
		Map<String, Object> content = util.checkAndGetClaims(token);
		// then
		assertEquals(content.get("sub"), "authToken");
		assertEquals(content.get("user"), email);
	}
	
	@Test
	public void wrongTokenTest() {
		// given
		String fakeToken = "fakeToken";
		// when-then
		assertThrows(MalformedJwtException.class, ()-> {
			util.checkAndGetClaims(fakeToken);
		});
	}
	
	@Test
	public void expireTest() {
		// given
		String fakeToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhdXRoVG9rZW4iLCJleHAiOjE2Njg0Njk5MzIsInVzZXIiOiJreW9vd29uOTVAZ21haWwuY29tIn0.inTxrTniKZ2Uxd7pSMlVrh6XMU0PHBRCgdbrEbkp2no";
		//when-then
		assertThrows(SignatureException.class, ()->{
			util.checkAndGetClaims(fakeToken);
		});
	}
	
	@Test
	@DisplayName("정보 전달해서 로그인 후 토큰을 잘 얻을 수 있는지 확인한다.")
	public void testLogin() throws Exception {
		// given
		Map<String, String> map = new HashMap<String, String>();
		map.put("email", "ssafy");
		map.put("password", "1234");
		String content = new ObjectMapper().writeValueAsString(map);
		
		//when
		MockHttpServletRequestBuilder builder = post("/api/user/login").contentType("application/json").content(content);
		ResultActions action = MockMvc.perform(builder);
		
		log.info("action : {}", action);
		//then
		action.andExpect(status().is(200)).andExpect(jsonPath("$.sub", equalTo("authToken"))).andExpect(jsonPath("$.user", equalTo(map.get("uid"))));
		
	}
	
	@Test
	@DisplayName("정상적인 토큰을 받았을 때 원하는 정보를 받을 수 있는지 확인한다.")
	public void testGetInfoSuccess() throws Exception{
		// given
		String token = util.createAuthToken("ssafy");
		
		MockHttpServletRequestBuilder requestBuilder = get("/api/info").header("jwt-auth-token", token);
		ResultActions action = MockMvc.perform(requestBuilder);
		
		action.andExpect(status().is(200)).andExpect(jsonPath("$.info", startsWithIgnoringCase("응답 받았습니다.")));

	}
	
	@Test
	@DisplayName("비 정상적인 토큰을 받았을 때 예외가 발생하는지 확인한다.")
	public void testGetInfoFail() {
		// given
		String token = "malfomed token";
		
		MockHttpServletRequestBuilder requestBuilder = get("/api/info").header("jwt-auth-token", token);
		
		assertThrows(Exception.class, () -> {
			MockMvc.perform(requestBuilder);
		});
	}

}
