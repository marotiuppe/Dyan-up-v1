package com.olp.course_mgmt.user.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

import com.olp.course_mgmt.entity.User;
import com.olp.course_mgmt.entity.UserCourseEnrollmentMapping;


@Repository
public class UserDaoService {
	
	@Autowired
	private EntityManager entityManager;
	
	@Autowired
	private Environment environment;

	public List<User> getAllUsers() {
		List<User>  userList=null;
		try {
			String strQuery = environment.getProperty("GET_ALL_USERS");
			userList = entityManager.createQuery(strQuery).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userList;
	}

	public void createUser(User user) {
		try {
			Session session= entityManager.unwrap(Session.class);
			user.setCreatedAt(new Date());
			user.setModifiedAt(new Date());
			session.save(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public User getUserById(Integer userId) {
		User user  = entityManager.find(User.class, userId);
		return user;
	}

	public UserCourseEnrollmentMapping getLevel(Integer userId, Integer courseId) {
		UserCourseEnrollmentMapping result = null;
		try {
		String strQuery = environment.getProperty("GET_LEVEL");
		result = (UserCourseEnrollmentMapping) entityManager.createNativeQuery(strQuery,UserCourseEnrollmentMapping.class)
				.setParameter("user_id", userId)
				.setParameter("course_id",courseId).getSingleResult();
		
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
