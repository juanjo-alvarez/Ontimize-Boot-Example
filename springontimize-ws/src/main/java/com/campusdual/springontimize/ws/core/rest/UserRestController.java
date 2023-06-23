package com.campusdual.springontimize.ws.core.rest;


import com.ontimize.jee.common.dto.EntityResultMapImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.campusdual.springontimize.api.core.service.IUserService;
import com.ontimize.jee.common.dto.EntityResult;
import com.ontimize.jee.server.rest.ORestController;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/users")
public class UserRestController extends ORestController<IUserService> {

	@Autowired
	private IUserService userSrv;

	@Override
	public IUserService getService() {
		return this.userSrv;
	}

	@RequestMapping(
		value = "/login",
		method = RequestMethod.POST,
		produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<EntityResult> login() {
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
