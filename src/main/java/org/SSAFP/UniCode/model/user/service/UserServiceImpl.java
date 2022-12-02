package org.SSAFP.UniCode.model.user.service;

import org.SSAFP.UniCode.jwt.JwtUtil;
import org.SSAFP.UniCode.model.user.dto.User;
import org.SSAFP.UniCode.model.user.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private UserRepo uRepo;
	
	@Override
	public User signin(String email, String password) {
		
		User user = uRepo.siginin(email);
		if(user != null && user.getPassword().equals(password)) {
			String authToken = jwtUtil.createAuthToken(email);
			return User.builder().email(email).authToken(authToken).build();
		}else {
			
			throw new RuntimeException();
		}
	}

}
