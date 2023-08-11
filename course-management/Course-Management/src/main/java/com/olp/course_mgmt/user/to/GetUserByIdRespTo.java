package com.olp.course_mgmt.user.to;

import com.olp.course_mgmt.entity.User;
import com.olp.course_mgmt.to.ResponseStatus;

public class GetUserByIdRespTo {
	private ResponseStatus responseStatus;
	private User userInfo;

	public ResponseStatus getResponseStatus() {
		return responseStatus;
	}

	public void setResponseStatus(ResponseStatus responseStatus) {
		this.responseStatus = responseStatus;
	}

	public User getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(User userInfo) {
		this.userInfo = userInfo;
	}

}
