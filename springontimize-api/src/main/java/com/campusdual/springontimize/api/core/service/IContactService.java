package com.campusdual.springontimize.api.core.service;

import com.ontimize.jee.common.dto.EntityResult;

import java.util.List;
import java.util.Map;

public interface IContactService {

    public EntityResult contactQuery(Map<?, ?> keyMap, List<?> attrList);
    public EntityResult contactInsert(Map<?, ?> attrMap);
    public EntityResult contactUpdate(Map<?, ?> attrMap, Map<?, ?> keyMap);
    public EntityResult contactDelete(Map<?, ?> keyMap);
}
