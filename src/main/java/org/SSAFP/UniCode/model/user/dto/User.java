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
	private String createdTime;
	private String updatedTime;
	private String email;
	private String github;
	private int access;
	private int score;
	private String authToken;
	private String refreashToken;

	private UserImg accessImg;
	private UserImg profile;

	
}
