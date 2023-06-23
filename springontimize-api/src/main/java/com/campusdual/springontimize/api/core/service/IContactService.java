package com.campusdual.springontimize.api.core.service;

import com.ontimize.jee.common.dto.EntityResult;

import java.util.List;
import java.util.Map;

/**
 * The interface Contact service.
 */
public interface IContactService {

    /**
     * Contact query entity result.
     *
     * @param keyMap   the key map
     * @param attrList the attr list
     * @return the entity result
     */
    public EntityResult contactQuery(Map<String, Object> keyMap, List<String> attrList);

    /**
     * Contact insert entity result.
     *
     * @param attrMap the attr map
     * @return the entity result
     */
    public EntityResult contactInsert(Map<String, Object> attrMap);

    /**
     * Contact update entity result.
     *
     * @param attrMap the attr map
     * @param keyMap  the key map
     * @return the entity result
     */
    public EntityResult contactUpdate(Map<String, Object> attrMap, Map<String, Object> keyMap);

    /**
     * Contact delete entity result.
     *
     * @param keyMap the key map
     * @return the entity result
     */
    public EntityResult contactDelete(Map<String, Object> keyMap);
}
