package com.sec.security.dao.impl;

import org.springframework.stereotype.Repository;

@Repository
public class SecUserDaoImpl{// extends CriteriaPageDAOImpl<User, Long> implements UserDao {

/*	@Override
	public List<User> getUserById(Long userId, boolean eager, String fetchTarget) {

		XDetachedCriteria criteria = XDetachedCriteria
				.forClass(getPersistentClass());

		if (userId != null) {
			criteria.xadd(XRestrictions.xeq("id", userId));
		}

		if (eager) {
			criteria.setFetchMode(fetchTarget, FetchMode.JOIN);
			criteria.setResultTransformer(XDetachedCriteria.DISTINCT_ROOT_ENTITY);
		}

		List<User> users = FindByCriteriaUtils.find(this, criteria);

		if (users == null || users.size() == 0) {
			return null;

		} else {
			return users;
		}

	}

	@Override
	protected DetachedCriteria createCriteria(Map<String, String> queryParam) {
		XDetachedCriteria criteria = XDetachedCriteria.forClass(getPersistentClass());
		criteria.xadd(XRestrictions.xIlike(Role.PROP_NAME, queryParam.get(User.PROP_USERNAME), MatchMode.ANYWHERE));
		criteria.xadd(XRestrictions.xeq(Role.PROP_ENABLED, queryParam.get(User.PROP_ENABLED), Boolean.class));
		criteria.setFetchMode(User.PROP_ROLES, FetchMode.JOIN);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria;
	}

	@Override
	public User getUserByName(String userName) {
		return this.doGetByHql("from User obj where obj.userName=?", userName);
	}*/

}
