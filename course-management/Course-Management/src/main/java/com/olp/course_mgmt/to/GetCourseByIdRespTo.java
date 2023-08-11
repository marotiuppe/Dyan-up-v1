package com.olp.course_mgmt.to;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.olp.course_mgmt.entity.Course;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetCourseByIdRespTo {
	private ResponseStatus responseStatus;
	private Course course;
	public ResponseStatus getResponseStatus() {
		return responseStatus;
	}
	public void setResponseStatus(ResponseStatus responseStatus) {
		this.responseStatus = responseStatus;
	}
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}

}
