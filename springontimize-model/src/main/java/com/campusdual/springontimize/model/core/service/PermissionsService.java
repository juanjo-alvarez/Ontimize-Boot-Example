package com.campusdual.springontimize.model.core.service;

import com.campusdual.springontimize.api.core.service.IPermissionsService;
import com.campusdual.springontimize.model.core.dao.MenuDao;
import com.campusdual.springontimize.model.core.dao.RoutesDao;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ontimize.jee.common.dto.EntityResult;
import com.ontimize.jee.common.dto.EntityResultMapImpl;
import com.ontimize.jee.common.exceptions.OntimizeJEERuntimeException;
import com.ontimize.jee.server.dao.DefaultOntimizeDaoHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("PermissionsService")
@Lazy
public class PermissionsService implements IPermissionsService {

    @Autowired
    private MenuDao menuDao;
    @Autowired
    private RoutesDao routeDao;

    @Autowired
    private DefaultOntimizeDaoHelper daoHelper;

    public EntityResult permissionQuery(Map<String, Object> keyMap, List<String> attrList) throws OntimizeJEERuntimeException {
        EntityResult result = new EntityResultMapImpl();
        Map<String,Object> permissions = new HashMap<>();
        addMenuToPermission(permissions);
        addRouteToPermission(permissions);
        Map<String,Object> hResult = new HashMap<>();
        hResult.put("permission",castMapToStringJson(permissions));
        result.addRecord(hResult);
        return(result);
    }

    private String castMapToStringJson(Map<String,Object> map){
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void addMenuToPermission(Map<String, Object> permissions) {
        List columns = Arrays.asList(MenuDao.ATTR_ATTR,MenuDao.ATTR_VISIBLE,MenuDao.ATTR_ENABLED);
        Map<String,Object> keys = new HashMap<>();
        keys.put(MenuDao.ATTR_ROLENAME,getRole());
        EntityResult menuPermisions = this.daoHelper.query(menuDao,keys,columns);
        if(menuPermisions.calculateRecordNumber()>0){
            List menuPermissionsList = new ArrayList();
            for(int i=0; i< menuPermisions.calculateRecordNumber();i++){
                menuPermissionsList.add(switchToLower(menuPermisions.getRecordValues(i)));
            }
            permissions.put("menu",menuPermissionsList);
        }
    }

    private void addRouteToPermission(Map<String, Object> permissions) {
        List columns = Arrays.asList(RoutesDao.ATTR_PERMISSIONID, RoutesDao.ATTR_ENABLED);
        Map<String,Object> keys = new HashMap<>();
        keys.put(RoutesDao.ATTR_ROLENAME,getRole());
        EntityResult routePermisions = this.daoHelper.query(routeDao,keys,columns);
        if(routePermisions.calculateRecordNumber()>0){
            List routePermissionsList = new ArrayList();
            for(int i=0; i< routePermisions.calculateRecordNumber();i++){
                routePermissionsList.add(switchToLower(routePermisions.getRecordValues(i)));
            }
            permissions.put("routes",routePermissionsList);
        }
    }

    private Map<String, Object>  switchToLower(Map<String,Object> map){
        Map<String,Object> result = new HashMap<>();
        map.forEach((k,v) -> {
            if("permissionid".equalsIgnoreCase(k.toLowerCase())){
                result.put("permissionId",v);
            }else{
                result.put(k.toLowerCase(),v);
            }
        });
        return result;
    }

    private String getRole(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getAuthorities().toArray()[0].toString();
    }

    public EntityResult permissionQuery2(Map<String, Object> keyMap, List<String> attrList) throws OntimizeJEERuntimeException {
        ObjectMapper mapper = new ObjectMapper();
        String permissionJson = null;
        Map<String,Object> permisionMap = getPermissions();
        try {
            permissionJson = mapper.writeValueAsString(permisionMap);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        EntityResult result = new EntityResultMapImpl();
        Map<String,Object> permissions = new HashMap<>();
        permissions.put("permission",permissionJson);
        result.addRecord(permissions);
        return(result);
    }

    private Map<String, Object> getPermissions() {
        HashMap<String,Object> result = new HashMap<>();
        addMenuForRole(result);
        addRoutesForRole(result);
        return result;
    }

    private void addRoutesForRole(HashMap<String, Object> result) {
        List<HashMap<String,Object>> routeItems = new ArrayList<>();
        routeItems.add(generateRouteItem("home",isAdmin()));
        routeItems.add(generateRouteItem("homenew",!isAdmin()));
        result.put("routes",routeItems);
    }

    private void addMenuForRole(HashMap<String, Object> result) {
        List<HashMap<String,Object>> menuItems = new ArrayList<>();
        menuItems.add(generateMenuItem("products",true,isAdmin()));
        menuItems.add(generateMenuItem("home",isAdmin(),isAdmin()));
        menuItems.add(generateMenuItem("homenew",!isAdmin(),!isAdmin()));
        result.put("menu",menuItems);
    }

    private HashMap<String, Object> generateMenuItem(String attr, boolean visible, boolean enabled) {
        HashMap<String, Object> result = new HashMap<>();
        result.put("attr",attr);
        result.put("visible",visible);
        result.put("enabled",enabled);
        return result;
    }

    private HashMap<String, Object> generateRouteItem(String permissionId, boolean enabled) {
        HashMap<String, Object> result = new HashMap<>();
        result.put("permissionId",permissionId);
        result.put("enabled",enabled);
        return result;
    }

    private boolean isAdmin(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().toArray()[0].toString();
        String user = auth.getName();
        return "admin".equalsIgnoreCase(role);
    }
}
