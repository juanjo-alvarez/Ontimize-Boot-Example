package com.campusdual.springontimize.api.core.service;

import com.ontimize.jee.common.dto.EntityResult;

import java.util.List;
import java.util.Map;

public interface IUserRoleService {

    public EntityResult userroleQuery(Map<String, Object> keyMap, List<String> attrList);
    public EntityResult userroleInsert(Map<String, Object> attrMap);
    public EntityResult userroleUpdate(Map<String, Object> attrMap, Map<String, Object> keyMap);
    public EntityResult userroleDelete(Map<String, Object> keyMap);
    public EntityResult myRoleQuery(Map<String, Object> keyMap, List<String> attrList);
}
