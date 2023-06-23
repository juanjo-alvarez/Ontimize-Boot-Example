package com.campusdual.springontimize.api.core.service;

import com.ontimize.jee.common.dto.EntityResult;
import com.ontimize.jee.common.exceptions.OntimizeJEERuntimeException;

import java.util.List;
import java.util.Map;

/**
 * The interface Personal role service.
 */
public interface IPersonalRoleService {
    /**
     * Personal role query entity result.
     *
     * @param keyMap   the key map
     * @param attrList the attr list
     * @return the entity result
     * @throws OntimizeJEERuntimeException the ontimize jee runtime exception
     */
    public EntityResult personalRoleQuery(Map<String, Object> keyMap, List<String> attrList)
            throws OntimizeJEERuntimeException;
}
