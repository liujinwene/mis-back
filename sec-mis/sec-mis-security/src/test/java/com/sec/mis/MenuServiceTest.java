package com.sec.mis;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sec.security.model.SecMenu;
import com.sec.security.service.MenuService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:../classes/spring*.xml" })
public class MenuServiceTest {
	@Resource
	private MenuService menuService;
	@Resource
	private PasswordEncoder passwordEncoder;
	
	
	@Test
	@Ignore
	public void syncTest(){
		SecMenu menu = new SecMenu();
		menu.setName("test");
		menu.setPosition(1);
		menu.setIcon("test");
		menu.setLocation("#");
		menuService.save(menu);
		menuService.delete(menu.getId());
	}
	@Test
	//@Ignore
	public void findMenus(){
		//List<Menu> menus = menuService.findMenus(false);
	/*	for(Menu menu:menus){
			List<Acl> acls = menu.getAcls();
			if(acls!=null && acls.size()>0){
				acls.get(0);
			}
		}*/
		SecMenu menus = menuService.selectAndChildren(168L);
		Assert.assertNotNull(menus);
	}
	
	private void getMenuIds(List<SecMenu> menus,List<Long> ids){
		if(CollectionUtils.isNotEmpty(menus)){
			for(SecMenu menu:menus){
				ids.add(menu.getId());
				getMenuIds(menu.getChildren(), ids);
			}
		}
	}
	@Test
	@Ignore
	public void deleteAclbyMenuIds(){
		Long ids = 32L;
		menuService.delete(ids);
		//menuService.delete(ids);
		/*List<Long> idss = new ArrayList<Long>();
		for(Long id:ids){
			Menu menu = menuService.findById(id);
			idss.add(menu.getId());
			getMenuIds(menu.getChildren(),idss);
		}
		
		for(Long id:idss){
			System.out.println(id);
		}*/
	}
	
	@Test
	@Ignore
	public void pwd(){
		System.out.println(passwordEncoder.encode("1"));
		System.out.println(passwordEncoder.encode("2"));
	}
}
