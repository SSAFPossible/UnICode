package org.SSAFP.UniCode.model.user.service;

import java.util.HashMap;
import java.util.Map;

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
	public void regist(User user) {
		uRepo.regist(user);
	}
	
	@Override
	public User signin(String id, String password) {	
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", id);
		map.put("password", password);
		User user = uRepo.siginin(map);
		if(user != null) {
			String authToken = jwtUtil.createAuthToken(id);
			String refreshToken = jwtUtil.createRefreshToken();
			
			saveRefreshToken(id, refreshToken);
			
			return User.builder().uid(id).authToken(authToken).build();
		}else {
			throw new RuntimeException();
		}
	}

	@Override
	public void logout(String id) {
		uRepo.logout(id);
	}
	
	
	@Override
	public void saveRefreshToken(String id, String refreshToken) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", id);
		map.put("refreshToken", refreshToken);
		uRepo.saveRefreshToken(map);
	}
	
	
	@Override
	public String getRefreshToken(String id) {
		return uRepo.getRefreshToken(id);
	}

	@Override
	public void userUpdate(User user) {
		uRepo.userUpdate(user);
	}

	@Override
	public void userDelete(String id) {
		uRepo.userDelete(id);
	}
}
