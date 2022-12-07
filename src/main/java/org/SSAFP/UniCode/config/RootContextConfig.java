package org.SSAFP.UniCode.config;

import org.SSAFP.UniCode.model.user.repo.UserRepo;
import org.SSAFP.UniCode.model.board.repo.BoardRepo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@MapperScan(basePackageClasses = {BoardRepo.class, UserRepo.class})
public class RootContextConfig {

}
