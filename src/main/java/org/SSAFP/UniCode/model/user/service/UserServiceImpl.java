package org.SSAFP.UniCode.model.user.service;

import java.util.HashMap;
import java.util.Map;

import org.SSAFP.UniCode.jwt.JwtUtil;
import org.SSAFP.UniCode.model.user.dto.User;
import org.SSAFP.UniCode.model.user.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private UserRepo uRepo;
	
	@Override
	public void regist(User user) {
		int result = uRepo.regist(user);
		if (result == 0) {
			throw new RuntimeException();
		}
	}
	
	@Override
	public User signin(String id, String password) {	
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", id);
		map.put("password", password);
		int result = uRepo.siginin(map);
		if(result != 0) {
			String authToken = jwtUtil.createAuthToken(id);
			String refreshToken = jwtUtil.createRefreshToken();
			
			saveRefreshToken(id, refreshToken);
			
			return User.builder().uid(id).authToken(authToken).build();
		} else {
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
	public User getInfo(String id) {
		User user = uRepo.getInfo(id);
		if (user != null) {
			return user;
		} else {
			throw new RuntimeException();
		}
	}
	
	@Override
	public void putInfo(User user) {
		int result = uRepo.putInfo(user);
		if (result == 0) {
			throw new RuntimeException();
		}
	}

	@Override
	public void deleteInfo(String id) {
		int result = uRepo.deleteInfo(id);
		if (result == 0) {
			throw new RuntimeException();
		}
	}
}