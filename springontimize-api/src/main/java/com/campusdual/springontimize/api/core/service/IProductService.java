package com.campusdual.springontimize.api.core.service;

import com.ontimize.jee.common.dto.EntityResult;

import java.util.List;
import java.util.Map;

/**
 * The interface Product service.
 */
public interface IProductService {
    /*Product Entity Operations*/
    /**
     * Product query entity result.
     *
     * @param keyMap   the key map
     * @param attrList the attr list
     * @return the entity result
     */
    public EntityResult productQuery(Map<String, Object> keyMap, List<String> attrList);

    /**
     * Product insert entity result.
     *
     * @param attrMap the attr map
     * @return the entity result
     */
    public EntityResult productInsert(Map<String, Object> attrMap);

    /**
     * Product update entity result.
     *
     * @param attrMap the attr map
     * @param keyMap  the key map
     * @return the entity result
     */
    public EntityResult productUpdate(Map<String, Object> attrMap, Map<String, Object> keyMap);

    /**
     * Product delete entity result.
     *
     * @param keyMap the key map
     * @return the entity result
     */
    public EntityResult productDelete(Map<String, Object> keyMap);

    /*File Entity Operations*/
    /**
     * File query entity result.
     *
     * @param keyMap   the key map
     * @param attrList the attr list
     * @return the entity result
     */
    public EntityResult fileQuery(Map<String, Object> keyMap, List<String> attrList);

    /**
     * File insert entity result.
     *
     * @param attrMap the attr map
     * @return the entity result
     */
    public EntityResult fileInsert(Map<String, Object> attrMap);

    /**
     * File content query entity result.
     *
     * @param keyMap   the key map
     * @param attrList the attr list
     * @return the entity result
     */
    public EntityResult fileContentQuery(Map<String, Object> keyMap, List<String> attrList);
}
