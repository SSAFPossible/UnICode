package org.SSAFP.UniCode.jwt;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtUtil {
	
	@Value("${jwt.salt}")
	private String salt;
	
	@Value("${jwt.expmin}")
	private Long expireMin;
	
	public String createAuthToken(String email) {
		return create(email, "authToken", expireMin);
	}

	private String create(String email, String subject, Long expireMin) {
		final JwtBuilder builder = Jwts.builder();
		// payload 설정.
		// 토큰 제목과 유효기간을 설정.
		builder.setSubject(subject).setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * expireMin));
		
		// 담고 싶은 정보 설정.
		if(email != null) {
			builder.claim("user", email);
		}
		
		// signature - secret key를 이용한 암호화.
		builder.signWith(SignatureAlgorithm.HS256, salt.getBytes());
		
		// 직렬화 설정.
		final String jwt = builder.compact();
		log.debug("토큰 발행 {} ",jwt);
		return jwt;
	}
	
	public Map<String, Object> checkAndGetClaims(String jwt){
		Jws<Claims> claims = Jwts.parser().setSigningKey(salt.getBytes()).parseClaimsJws(jwt);
		log.debug("claims : {}", claims);
		return claims.getBody();
	}

}
