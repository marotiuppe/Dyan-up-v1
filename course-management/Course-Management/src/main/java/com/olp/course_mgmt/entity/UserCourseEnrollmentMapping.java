package com.olp.course_mgmt.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_cource_enrolment_maping")
public class UserCourseEnrollmentMapping {
	
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	Integer id;
	
	@Column(name="user_id")
	Integer userId;
	
	@Column(name="course_id")
	Integer courseId;
	
	@Column(name="status_id")
	Integer status_id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getCourseId() {
		return courseId;
	}

	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}

	public Integer getStatus_id() {
		return status_id;
	}

	public void setStatus_id(Integer status_id) {
		this.status_id = status_id;
	}
	
	
	
	
	
}
