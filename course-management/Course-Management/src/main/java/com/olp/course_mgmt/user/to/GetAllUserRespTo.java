package com.olp.course_mgmt.user.to;

import java.util.List;

import com.olp.course_mgmt.entity.User;
import com.olp.course_mgmt.to.ResponseStatus;

public class GetAllUserRespTo {
	private ResponseStatus responseStatus;
	private List<User> userList;

	public ResponseStatus getResponseStatus() {
		return responseStatus;
	}

	public void setResponseStatus(ResponseStatus responseStatus) {
		this.responseStatus = responseStatus;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

}
