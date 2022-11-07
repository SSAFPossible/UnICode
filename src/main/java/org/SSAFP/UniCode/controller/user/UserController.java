package org.SSAFP.UniCode.controller.user;

import org.SSAFP.UniCode.model.user.dto.User;
import org.SSAFP.UniCode.model.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
	
	@Autowired
	UserService Service;
	
	@PostMapping("/regist")
	public ResponseEntity<?> userRegister(@ModelAttribute User user){
		
		return null;
	}
}
