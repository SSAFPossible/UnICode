package org.SSAFP.UniCode.model.user.service;

import org.SSAFP.UniCode.model.user.dto.User;

public interface UserService {
	
	/**
	 * 
	 * @param email
	 * @param password
	 * @return User (token을 담아서 전달)
	 */
	public User signin(String email, String password);
}
