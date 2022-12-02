package org.SSAFP.UniCode.model.user.repo;

import org.SSAFP.UniCode.model.user.dto.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRepo {
	public User siginin(String id);
}
