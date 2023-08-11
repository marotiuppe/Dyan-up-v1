package com.olp.course_mgmt.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.object.SqlQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.olp.course_mgmt.common.CommonConstants;
import com.olp.course_mgmt.entity.Course;
import com.olp.course_mgmt.service.CourseServiceHandler;

@Repository
@Transactional

public class CourseDaoService {
	
	private static final Logger lOGGER = LoggerFactory.getLogger(CourseDaoService.class);


	@Autowired
	private EntityManager entityManager;

	@Autowired
	private Environment env;

	@SuppressWarnings("deprecation")
	public List<Course> getAllCourses() {
		lOGGER.info(CommonConstants.BEGIN);
		List<Course> coursesList = null;
		try {
			String query = env.getProperty("GET_ALL_COURSE_LIST");
			coursesList = entityManager.createNativeQuery(query, Course.class).getResultList();
		} catch (Exception ex) {
			lOGGER.error(ex.getMessage(), ex);

		}
		lOGGER.info(CommonConstants.END);
		return coursesList;
	}

	public void createCourse(Course course) {
		lOGGER.info(CommonConstants.BEGIN);
		try {
			course.setCreatedAt(new Date());
			Session session = entityManager.unwrap(Session.class);
			session.save(course);
		} catch (Exception ex) {
			lOGGER.error(ex.getMessage(), ex);
		}
		lOGGER.info(CommonConstants.END);

	}

	public Course getCourseById(Integer courseId) {
		lOGGER.info(CommonConstants.BEGIN);
		Course course = null;
		try {
			String sql = env.getProperty("GET_COURSE_BY_ID");
			course = (Course) entityManager.createNativeQuery(sql, Course.class).setParameter("id", courseId)
					.getSingleResult();

		} catch (Exception ex) {
			lOGGER.error(ex.getMessage(), ex);
		}
		lOGGER.info(CommonConstants.END);
		return course;

	}

	public Course getCourseByName(String name) {
		lOGGER.info(CommonConstants.BEGIN);
		Course course = null;
		try {
			String sql = env.getProperty("GET_COURSE_BY_NAME");
			course = (Course) entityManager.createNativeQuery(sql, Course.class).setParameter("name", name)
					.getSingleResult();

		} catch (Exception ex) {
			lOGGER.error(ex.getMessage(), ex);
		}
		lOGGER.info(CommonConstants.END);
		return course;

	}

	public void updateCourse(Integer courseId, Course updatecourseobj, Course existingCourse) {
		lOGGER.info(CommonConstants.BEGIN);
		try {
			existingCourse.setCourseName(updatecourseobj.getCourseName());
			existingCourse.setDuration(updatecourseobj.getDuration());
			existingCourse.setPrice(updatecourseobj.getPrice());
			existingCourse.setModifiedAt(new Date());
			entityManager.merge(existingCourse);
		} catch (Exception ex) {
			lOGGER.error(ex.getMessage(), ex);
		}

	}

	public void delCourseById(Integer courseId) {
		lOGGER.info(CommonConstants.BEGIN);
		try {
			Course course = entityManager.find(Course.class, courseId);
			entityManager.remove(course);
		} catch (Exception ex) {
			lOGGER.error(ex.getMessage(), ex);
		}
		lOGGER.info(CommonConstants.END);


	}

	public void enrollCourseToUser(Integer userId, Integer courseId) {
		lOGGER.info(CommonConstants.BEGIN);
		try {
			String strQuery = env.getProperty("INSERT_USERCOURSE_ENROLLMENT_MAPPING");
			entityManager.createNativeQuery(strQuery).setParameter("user_id", userId)
					.setParameter("course_id", courseId).executeUpdate();
		} catch (Exception ex) {
			lOGGER.error(ex.getMessage(), ex);
		}
		lOGGER.info(CommonConstants.END);


	}

}
