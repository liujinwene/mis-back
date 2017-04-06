package com.sec.mis;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sec.security.model.SecRole;
import com.sec.security.service.RoleService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:../classes/spring*.xml" })
public class RoleServiceTest {
	
	@Resource
	private RoleService roleService;
	
	
	@Test
	//@Ignore
	public void find(){
		List<SecRole> roles = roleService.findRoles();
		Assert.assertNotNull(roles);
		
		SecRole role = roleService.findRole(6L,true);
		
		Assert.assertNotNull(role);
	}
	
	@Test
	@Ignore
	public void save(){
		SecRole role = new SecRole();
		role.setCode("test");
		role.setName("test");
		//roleService.saveRole(role);
	}
	@Test
	@Ignore
	public void findbyCode(){
		List<SecRole> roles = roleService.findByCode("ROLE_ADMIN");
		Assert.assertNotNull(roles);
	}
}
