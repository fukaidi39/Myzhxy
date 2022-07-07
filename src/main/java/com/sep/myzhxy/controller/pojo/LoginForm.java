package com.sep.myzhxy.controller.pojo;

import lombok.Data;

@Data
public class LoginForm {
	private String username;
	private String password;
	private String verifiCode;
	private int userType;
	
}
