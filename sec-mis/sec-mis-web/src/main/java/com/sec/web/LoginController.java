package com.sec.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sec.security.utils.SessionUtils;

@Controller
@RequestMapping("/login")
public class LoginController {

	@RequestMapping("")
	public String index() {
		return "login";
	}

	@RequestMapping("/login")
	public String login() {
		if(SessionUtils.getUser()!=null){
			return "redirect:/home.do";
		}
		
		return "user/list";
	}

	@RequestMapping("/success")
	public String loginSuccess() {
		return "login-success";
	}

	@RequestMapping("/failure")
	public String loginFailure() {
		return "login-failure";
	}
}
