package com.sec.security.dao.impl;

import org.springframework.stereotype.Repository;

@Repository
public class SecMenuDaoImpl {//extends CriteriaPageDAOImpl<Menu, Long> implements MenuDao{
	
	
	
/*	public List<Menu> findMenuList(Map<String, String> queryParam)
	{
		 return FindByCriteriaUtils.find(this, this.createCriteria(queryParam));
		
	}
	
	
	protected  DetachedCriteria createCriteria(Map<String, String> queryParam) {
		XDetachedCriteria criteria = XDetachedCriteria
				.forClass(getPersistentClass());

		criteria.xadd(XRestrictions.xeq(Menu.PROP_TYPE, queryParam.get(Menu.PROP_TYPE),
				Integer.class));

		criteria.xadd(XRestrictions.xIlike(Menu.PROP_NAME, queryParam
				.get(Menu.PROP_NAME), MatchMode.ANYWHERE));

		criteria.xadd(XRestrictions.xeq(Menu.PROP_ENABLED, queryParam.get(Menu.PROP_ENABLED),
				Boolean.class));

		return criteria;
	}*/
}
