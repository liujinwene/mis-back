package com.sec.mis;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sec.security.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:../classes/spring*.xml" })
public class UserServiceTest extends AbstractJUnit4SpringContextTests {
	@Resource
	private UserService userService;

	@Test
	public void test() {
		userService.getUserById(1L);
		Map<String, String> param = new HashMap<String, String>();
		int start = 0;//Integer.parseInt(request.getParameter("beginRow").toString());
		int limit = 10;//Integer.parseInt(request.getParameter("pageSize").toString());
	//	Page<User> users = userService.findUsers(param, start, limit, null);
	//	Assert.assertNotNull(users);
	}
}
