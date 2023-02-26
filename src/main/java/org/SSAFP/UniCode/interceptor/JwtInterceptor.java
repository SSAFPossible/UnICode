package org.SSAFP.UniCode.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.SSAFP.UniCode.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtInterceptor implements HandlerInterceptor{
	@Autowired
	private JwtUtil jwtUtil;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if(request.getMethod().equals("OPTIONS")) {
			return true;
		}
		String authToken = request.getHeader("jwt-auth-token");
		log.debug("경로 : {}, 토큰: {}", request.getServletPath(), authToken);
		if(authToken != null){
			jwtUtil.checkAndGetClaims(authToken);
			return true;
		}else {
			throw new RuntimeException("인증 토큰이 없습니다.");
		}
	}
	
}
