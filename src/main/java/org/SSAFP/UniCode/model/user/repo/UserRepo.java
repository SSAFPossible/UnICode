package org.SSAFP.UniCode.model.user.repo;

import java.util.Map;

import org.SSAFP.UniCode.model.user.dto.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRepo {

	public int regist(User user);
	
	public int siginin(Map<String, String> map);

	public void logout(String id);
	
	public void saveRefreshToken(Map<String, String> map);

	public String getRefreshToken(String id);

	public User getInfo(String id);
	
	public int putInfo(User user);

	public int deleteInfo(String id);
}
