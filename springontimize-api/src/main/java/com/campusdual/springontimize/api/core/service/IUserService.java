package com.campusdual.springontimize.api.core.service;


import java.util.List;
import java.util.Map;

import com.ontimize.jee.common.dto.EntityResult;


/**
 * The interface User service.
 */
public interface IUserService {
	/*User Entity Operations*/

	/**
	 * User query entity result.
	 *
	 * @param keyMap   the key map
	 * @param attrList the attr list
	 * @return the entity result
	 */
	public EntityResult userQuery(Map<String, Object> keyMap, List<String> attrList);

	/**
	 * User insert entity result.
	 *
	 * @param attrMap the attr map
	 * @return the entity result
	 */
	public EntityResult userInsert(Map<String, Object> attrMap);

	/**
	 * User update entity result.
	 *
	 * @param attrMap the attr map
	 * @param keyMap  the key map
	 * @return the entity result
	 */
	public EntityResult userUpdate(Map<String, Object> attrMap, Map<String, Object> keyMap);

	/**
	 * User delete entity result.
	 *
	 * @param keyMap the key map
	 * @return the entity result
	 */
	public EntityResult userDelete(Map<String, Object> keyMap);
}
