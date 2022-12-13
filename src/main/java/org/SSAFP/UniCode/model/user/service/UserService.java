package org.SSAFP.UniCode.model.user.service;

import java.util.Map;

import org.SSAFP.UniCode.model.user.dto.User;

public interface UserService {

	public void regist(User user);

	public User signin(String id, String password);

	public void logout(String id);

	public void saveRefreshToken(String id, String refreshToken);

	public String getRefreshToken(String id);
	
	public User getInfo(String id);

	public void putInfo(User user);

	public void deleteInfo(String id);
}
