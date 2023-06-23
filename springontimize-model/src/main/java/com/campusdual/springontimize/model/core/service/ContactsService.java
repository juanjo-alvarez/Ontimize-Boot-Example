package com.campusdual.springontimize.model.core.service;

import com.campusdual.springontimize.api.core.service.IContactService;
import com.campusdual.springontimize.model.core.dao.ContactDao;
import com.ontimize.jee.common.dto.EntityResult;
import com.ontimize.jee.server.dao.DefaultOntimizeDaoHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * The type Contacts service.
 */
@Lazy
@Service("ContactsService")
public class ContactsService implements IContactService {

    @Autowired
    private ContactDao contactDao;

    @Autowired
    private DefaultOntimizeDaoHelper daoHelper;
    @Override
    public EntityResult contactQuery(Map<String, Object> keyMap, List<String> attrList) {
        return this.daoHelper.query(contactDao,keyMap,attrList);
    }

    @Override
    public EntityResult contactInsert(Map<String, Object> attrMap) {
        return this.daoHelper.insert(contactDao,attrMap);
    }

    @Override
    public EntityResult contactUpdate(Map<String, Object> attrMap, Map<String, Object> keyMap) {
        return daoHelper.update(contactDao,attrMap,keyMap);
    }

    @Override
    public EntityResult contactDelete(Map<String, Object> keyMap) {
        return daoHelper.delete(contactDao,keyMap);
    }
}
