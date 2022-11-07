package org.SSAFP.UniCode.model.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
	private String uid;
	private String password;
	private String name;
	private String profile;
	private String createdTime;
	private String accessImg;
	private String email;
	private String github;
	private boolean access;
	private boolean authority;
	private int score;
	
	
}
