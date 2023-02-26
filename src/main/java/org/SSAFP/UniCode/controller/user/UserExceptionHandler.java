package org.SSAFP.UniCode.controller.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class UserExceptionHandler {

	@ExceptionHandler(SignatureException.class)
	public ResponseEntity<String> makeCookie(SignatureException e){
		log.error("jwt token expired",e);
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.UNAUTHORIZED);
	}
}
