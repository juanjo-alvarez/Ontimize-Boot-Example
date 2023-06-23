package com.campusdual.springontimize.model.core.dao;


import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import com.ontimize.jee.server.dao.common.ConfigurationFile;
import com.ontimize.jee.server.dao.jdbc.OntimizeJdbcDaoSupport;


@Repository(value = "UserRoleDao")
@Lazy
@ConfigurationFile(
	configurationFile = "dao/UserRoleDao.xml",
	configurationFilePlaceholder = "dao/placeholders.properties")
public class UserRoleDao extends OntimizeJdbcDaoSupport {
	public static final String QUERY_USERROLE = "userRole";

	public static final String ATTT_USER_ = "USER_";
	public static final String ATTT_ROLENAME ="ROLENAME";
}
