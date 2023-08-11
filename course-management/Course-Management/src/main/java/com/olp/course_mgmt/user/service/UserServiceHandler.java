package com.olp.course_mgmt.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.olp.course_mgmt.entity.User;
import com.olp.course_mgmt.entity.UserCourseEnrollmentMapping;
import com.olp.course_mgmt.user.dao.UserDaoService;


@Service
public class UserServiceHandler {
	
	@Autowired
	UserDaoService userRepository;

	public List<User> getAllUsers() {
		List<User>  userList=null;
		try {
			userList = userRepository.getAllUsers();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userList;
	}

	public void createUser(User user) {
		try {
			user.setUserCode(user.getFirstName()+user.getContactNo().substring(0,5));
			userRepository.createUser(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

	public User getUserById(Integer userId) {
		User user = userRepository.getUserById(userId);
		return user;
	}

	public UserCourseEnrollmentMapping getLevel(Integer userId, Integer courseId) {
		UserCourseEnrollmentMapping getCourseLevel = userRepository.getLevel(userId,courseId);

		return getCourseLevel;
	}

}
