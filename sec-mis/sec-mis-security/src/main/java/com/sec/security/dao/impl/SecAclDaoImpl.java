package com.sec.security.dao.impl;

import org.springframework.stereotype.Repository;

@Repository
public class SecAclDaoImpl {//extends CriteriaPageDAOImpl<Acl, Long> implements AclDao {

	/*@Override
	protected DetachedCriteria createCriteria(Map<String, String> queryParam) {
		XDetachedCriteria criteria = XDetachedCriteria.forClass(getPersistentClass());

		criteria.xadd(XRestrictions.xeq(Acl.PROP_TYPE, queryParam.get(Acl.PROP_TYPE), Integer.class));

		if (!StringUtils.isEmpty(queryParam.get(Acl.PROP_MENU_ID))) {
			criteria.xadd(XRestrictions.xeq(Acl.PROP_MENU, new Menu(Long.parseLong(queryParam.get(Acl.PROP_MENU_ID))), Menu.class));
		}

		criteria.xadd(XRestrictions.xIlike(Acl.PROP_NAME, queryParam.get(Acl.PROP_NAME), MatchMode.ANYWHERE));

		criteria.xadd(XRestrictions.xeq(Acl.PROP_ENALBED, queryParam.get(Acl.PROP_ENALBED), Boolean.class));

		return criteria;
	}

	public List<Acl> getAclById(Long aclId, boolean eager, String fetchTarget) {

		XDetachedCriteria criteria = XDetachedCriteria.forClass(getPersistentClass());

		if (aclId != null) {
			criteria.xadd(XRestrictions.xeq(Acl.ENTITY_PROPERTY_ID, aclId));
		}

		if (eager) {
			criteria.setFetchMode(fetchTarget, FetchMode.JOIN);
		}

		List<Acl> acls = FindByCriteriaUtils.find(this, criteria);

		if (acls == null || acls.size() == 0) {
			return null;

		} else {
			return acls;
		}

	}
	public void deleteByMenuId(Long... menuIds){
		String[] symbols = new String[menuIds.length];
		Arrays.fill(symbols, "?");

		StringBuilder sql = new StringBuilder();
		sql.append("delete from ");
		sql.append(getPersistentClass().getSimpleName());
		sql.append(" where menu.id in (");
		sql.append(StringUtils.join(symbols, ","));
		sql.append(")");

		FindByHqlUtils.execute(getSession(), sql.toString(), menuIds);
	}*/
}
