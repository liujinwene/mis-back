package com.sec.security.dao.impl;

import org.springframework.stereotype.Repository;

@Repository
public class SecRoleDaoImpl{// extends CriteriaPageDAOImpl<Role, Long> implements RoleDao {

	/*public List<Role> getRoleById(Long roleId, boolean eager, String fetchTarget) {
		

		XDetachedCriteria criteria = XDetachedCriteria
				.forClass(getPersistentClass());

		if(roleId!=null)
		{
			criteria.xadd(XRestrictions.xeq("id", roleId));
		}
		
		
		if (eager) {
			
			criteria.setFetchMode(Role.PROP_ACLS, FetchMode.JOIN);
			criteria.setResultTransformer(XDetachedCriteria.DISTINCT_ROOT_ENTITY);
			
		}

		List<Role> roles = FindByCriteriaUtils.find(this, criteria);
		
		

		if (roles == null || roles.size() == 0) {
			return null;
		} else {
			//roles.get(0).getMenus().get(0);//
			return roles;
		}

	}

	@Override
	protected DetachedCriteria createCriteria(Map<String, String> queryParam) {
		XDetachedCriteria criteria = XDetachedCriteria.forClass(getPersistentClass());
		criteria.xadd(XRestrictions.xIlike(Role.PROP_NAME, queryParam.get(Role.PROP_NAME), MatchMode.ANYWHERE));
		criteria.xadd(XRestrictions.xeq(Role.PROP_ENABLED, queryParam.get(Role.PROP_ENABLED), Boolean.class));
		return criteria;
	}
*/
}
