package com.olp.course_mgmt.to;

import java.util.List;

import com.olp.course_mgmt.entity.Course;

public class GetAllCourseRespTo {
	private ResponseStatus responseStatus;
	private List<Course> courseList;

	public ResponseStatus getResponseStatus() {
		return responseStatus;
	}

	public void setResponseStatus(ResponseStatus responseStatus) {
		this.responseStatus = responseStatus;
	}

	public List<Course> getCourseList() {
		return courseList;
	}

	public void setCourseList(List<Course> courseList) {
		this.courseList = courseList;
	}

}
