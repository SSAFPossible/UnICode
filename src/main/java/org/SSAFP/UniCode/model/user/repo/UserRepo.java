package org.SSAFP.UniCode.model.user.repo;

import java.util.Map;

import org.SSAFP.UniCode.model.user.dto.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRepo {

	public void regist(User user);
	
	public User siginin(Map<String, String> map);

	public void logout(String id);
	
	public void saveRefreshToken(Map<String, String> map);

	public String getRefreshToken(String id);

	public void userUpdate(User user);

	public void userDelete(String id);

}
