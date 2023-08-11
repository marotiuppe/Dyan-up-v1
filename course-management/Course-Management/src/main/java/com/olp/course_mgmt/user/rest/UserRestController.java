package com.olp.course_mgmt.user.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.olp.course_mgmt.entity.User;
import com.olp.course_mgmt.to.ResponseStatus;
import com.olp.course_mgmt.user.service.UserServiceHandler;
import com.olp.course_mgmt.user.to.CreateUserRespTo;
import com.olp.course_mgmt.user.to.GetAllUserRespTo;
import com.olp.course_mgmt.user.to.GetUserByIdRespTo;

@RestController
@RequestMapping("/user")
public class UserRestController {

	@Autowired
	UserServiceHandler userServiceHandler;

	@Autowired
	private Environment env;

	@GetMapping("/view")
	public GetAllUserRespTo getAllUsers() {
		GetAllUserRespTo respTo = new GetAllUserRespTo();
		List<User> userList = null;
		try {
			userList = userServiceHandler.getAllUsers();
			if (userList != null && userList.size() > 0) {
				ResponseStatus responseStatus = new ResponseStatus();
				responseStatus.setStatusCode(env.getProperty("SUCCESS_STATUS_CODE"));
				responseStatus.setStatusMessage(env.getProperty("SUCCESS_STATUS_MESSAGE"));
				respTo.setResponseStatus(responseStatus);
				respTo.setUserList(userList);
			}
			if (userList == null || userList.size() == 0) {
				ResponseStatus responseStatus = new ResponseStatus();
				responseStatus.setStatusCode(env.getProperty("NO_DATA"));
				responseStatus.setStatusMessage(env.getProperty("NO_DATA_MESSAGE"));
				respTo.setResponseStatus(responseStatus);
				return respTo;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return respTo;
	}

	@GetMapping("/{course_id}")
	public GetUserByIdRespTo getUserById(@PathVariable("user_id") Integer userId) {
		GetUserByIdRespTo respTo = new GetUserByIdRespTo();
		try {
			User userDetails = userServiceHandler.getUserById(userId);
			if (userDetails == null) {
				ResponseStatus responseStatus = new ResponseStatus();
				responseStatus.setStatusCode(env.getProperty("COURSE_NOT_EXISTS"));
				responseStatus.setStatusMessage(env.getProperty("COURSE_NOT_EXISTS_MESSAGE"));
				respTo.setResponseStatus(responseStatus);
				return respTo;
			}
			if (userDetails != null) {
				ResponseStatus responseStatus = new ResponseStatus();
				responseStatus.setStatusCode(env.getProperty("SUCCESS_STATUS_CODE"));
				responseStatus.setStatusMessage(env.getProperty("SUCCESS_STATUS_MESSAGE"));
				respTo.setResponseStatus(responseStatus);
				respTo.setUserInfo(userDetails);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return respTo;
	}

	@PostMapping("/registerUser")
	public CreateUserRespTo createCourse(@RequestBody User user) {
		CreateUserRespTo createCourceResp = new CreateUserRespTo();
		try {

			userServiceHandler.createUser(user);
			ResponseStatus responseStatus = new ResponseStatus();
			responseStatus.setStatusCode(env.getProperty("SUCCESS_STATUS_CODE"));
			responseStatus.setStatusMessage(env.getProperty("SUCCESS_STATUS_MESSAGE"));
			createCourceResp.setResponseStatus(responseStatus);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return createCourceResp;
	}

}
