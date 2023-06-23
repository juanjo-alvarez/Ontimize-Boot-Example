package com.campusdual.springontimize.model.core.dao;

import com.ontimize.jee.server.dao.common.ConfigurationFile;
import com.ontimize.jee.server.dao.jdbc.OntimizeJdbcDaoSupport;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

@Repository(value = "ProductFileDao")
@Lazy
@ConfigurationFile( configurationFile = "dao/ProductFileDao.xml",
        configurationFilePlaceholder = "dao/placeholders.properties")
public class ProductFileDao extends OntimizeJdbcDaoSupport {
    public static final String ATTR_ID = "ID";
    public static final String ATTR_PRODUCT_ID = "PRODUCT_ID";
    public static final String ATTR_NAME = "NAME";
    public static final String ATTR_PATH = "FILE_PATH";

    public static final String ATTR_BASE64 = "BASE64";
}
