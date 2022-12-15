package org.SSAFP.UniCode.model.user.repo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.SSAFP.UniCode.model.user.dto.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRepo {

	public boolean regist(User user);
	
	public int siginin(Map<String, String> map);
	
	public void saveRefreshToken(Map<String, String> map);

	public String getRefreshToken(String id);

	public User getInfo(String id);
	
	public boolean modifyInfo(User user);

	public boolean deleteInfo(String id);

	public boolean uploadProfile(User user);

	public boolean modifyProfile(User user);
	
	public boolean deleteProfile(User user);

	public boolean getProfile(User user);

	public boolean uploadAccessImg(User user);

	public boolean modifyAccessImg(User user);
	
	public boolean deleteAccessImg(User user);

	public boolean getAccessImg(User user);
	
	public String FindIdByEamil(String email);

	public boolean updateScore(Map<String, Object> scoreInfo);

	public List<Map<String, Object>> getRankList();
}
