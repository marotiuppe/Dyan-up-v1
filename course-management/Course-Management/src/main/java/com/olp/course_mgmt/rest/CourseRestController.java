package com.olp.course_mgmt.rest;

import java.util.List;

import javax.servlet.http.HttpServletResponse;import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import com.olp.course_mgmt.entity.Course;
import com.olp.course_mgmt.entity.User;
import com.olp.course_mgmt.entity.UserCourseEnrollmentMapping;
import com.olp.course_mgmt.service.CourseServiceHandler;
import com.olp.course_mgmt.to.CreateCourseRespTo;
import com.olp.course_mgmt.to.DeleteCourseRespTo;
import com.olp.course_mgmt.to.EnrollCourseRespTo;
import com.olp.course_mgmt.to.GetAllCourseRespTo;
import com.olp.course_mgmt.to.GetCertificateCourseRespTo;
import com.olp.course_mgmt.to.GetCourseByIdRespTo;
import com.olp.course_mgmt.to.ResponseStatus;
import com.olp.course_mgmt.to.UpdateCourseRespTo;
import com.olp.course_mgmt.user.service.UserServiceHandler;


@RestController
@RequestMapping("/course")
public class CourseRestController {

	private static final Logger lOGGER = LoggerFactory.getLogger(CourseRestController.class);

	@Autowired
	private CourseServiceHandler courseServiceHandler;

	@Autowired
	private UserServiceHandler userServiceHandler;

	@Autowired
	private Environment env;

	// TO VIEW ALL THE COURSES
	@GetMapping("/view")
	public GetAllCourseRespTo getAllCourses() {
		GetAllCourseRespTo respTo = new GetAllCourseRespTo();
		List<Course> courseList = null;
		try {
			courseList = courseServiceHandler.getAllCourses();
			if (courseList != null) {
				ResponseStatus responseStatus = new ResponseStatus();
				responseStatus.setStatusCode(env.getProperty("SUCCESS_STATUS_CODE"));
				responseStatus.setStatusMessage(env.getProperty("SUCCESS_STATUS_MESSAGE"));
				respTo.setResponseStatus(responseStatus);
				respTo.setCourseList(courseList);
			} else {
				ResponseStatus responseStatus = new ResponseStatus();
				responseStatus.setStatusCode(env.getProperty("NO_DATA"));
				responseStatus.setStatusMessage(env.getProperty("NO_DATA_MESSAGE"));
				respTo.setResponseStatus(responseStatus);
				return respTo;
			}
		} catch (Exception e) {
			lOGGER.error(e.getMessage(), e);
			ResponseStatus responseStatus = new ResponseStatus();
			responseStatus.setStatusCode(env.getProperty("FAILURE_STATUS_CODE"));
			responseStatus.setStatusMessage(env.getProperty("FAILURE_STATUS_MESSAGE"));
			respTo.setResponseStatus(responseStatus);
			return respTo;
		}
		return respTo;
	}

	// TO SAVE NEW COURSE
	@PostMapping("/save")
	public CreateCourseRespTo createCourse(@RequestBody Course course) {
		CreateCourseRespTo createCourceResp = new CreateCourseRespTo();
		try {
			
			Course isCourseNameExists = courseServiceHandler.getCourseByName(course.getCourseName());
			if (isCourseNameExists != null) {
				ResponseStatus responseStatus = new ResponseStatus();
				responseStatus.setStatusCode(env.getProperty("COURSE_NAME_ALREADY_EXISTS"));
				responseStatus.setStatusMessage(env.getProperty("COURSE_NAME_ALREADY_EXISTS_MESSAGE"));
				createCourceResp.setResponseStatus(responseStatus);
				return createCourceResp;
			} else {
				courseServiceHandler.createCourse(course);
				ResponseStatus responseStatus = new ResponseStatus();
				responseStatus.setStatusCode(env.getProperty("SUCCESS_STATUS_CODE"));
				responseStatus.setStatusMessage(env.getProperty("SUCCESS_STATUS_MESSAGE"));
				createCourceResp.setResponseStatus(responseStatus);
			}
		} catch (Exception e) {
			lOGGER.error(e.getMessage(), e);
			ResponseStatus responseStatus = new ResponseStatus();
			responseStatus.setStatusCode(env.getProperty("FAILURE_STATUS_CODE"));
			responseStatus.setStatusMessage(env.getProperty("FAILURE_STATUS_MESSAGE"));
			createCourceResp.setResponseStatus(responseStatus);
			return createCourceResp;
		}
		return createCourceResp;
	}

	// TO GET COURSE DETAILS BY COURSE ID
	@GetMapping("/{course_id}")
	public GetCourseByIdRespTo getCourseById(@PathVariable("course_id") Integer courseId) {
		GetCourseByIdRespTo respTo = new GetCourseByIdRespTo();
		try {
			Course existingCourse = courseServiceHandler.getCourseById(courseId);
			if (existingCourse == null) {
				ResponseStatus responseStatus = new ResponseStatus();
				responseStatus.setStatusCode(env.getProperty("COURSE_NOT_EXISTS"));
				responseStatus.setStatusMessage(env.getProperty("COURSE_NOT_EXISTS_MESSAGE"));
				respTo.setResponseStatus(responseStatus);
				return respTo;
			}
			if (existingCourse != null) {
				ResponseStatus responseStatus = new ResponseStatus();
				responseStatus.setStatusCode(env.getProperty("SUCCESS_STATUS_CODE"));
				responseStatus.setStatusMessage(env.getProperty("SUCCESS_STATUS_MESSAGE"));
				respTo.setResponseStatus(responseStatus);
				respTo.setCourse(existingCourse);
			}

		} catch (Exception e) {
			lOGGER.error(e.getMessage(), e);
			ResponseStatus responseStatus = new ResponseStatus();
			responseStatus.setStatusCode(env.getProperty("FAILURE_STATUS_CODE"));
			responseStatus.setStatusMessage(env.getProperty("FAILURE_STATUS_MESSAGE"));
			respTo.setResponseStatus(responseStatus);
			return respTo;
		}
		return respTo;
	}

	// TO UPDATE COURSE BY COURSE ID
	@PostMapping("/update")
	public UpdateCourseRespTo updateCourse(@RequestBody Course updatecourseobj,
			@RequestHeader(required = false) Integer courseId) {
		UpdateCourseRespTo respTo = new UpdateCourseRespTo();
		try {
			Course existingCourse = courseServiceHandler.getCourseById(courseId);
			Course isCourseNameExits = courseServiceHandler.getCourseByName(updatecourseobj.getCourseName());
			if (isCourseNameExits != null) {
				ResponseStatus responseStatus = new ResponseStatus();
				responseStatus.setStatusCode(env.getProperty("COURSE_NAME_ALREADY_EXISTS"));
				responseStatus.setStatusMessage(env.getProperty("COURSE_NAME_ALREADY_EXISTS_MESSAGE"));
				respTo.setResponseStatus(responseStatus);
				return respTo;
			}
			if (existingCourse != null && isCourseNameExits==null) {
				courseServiceHandler.update(courseId, updatecourseobj, existingCourse);
				ResponseStatus responseStatus = new ResponseStatus();
				responseStatus.setStatusCode(env.getProperty("SUCCESS_STATUS_CODE"));
				responseStatus.setStatusMessage(env.getProperty("SUCCESS_STATUS_MESSAGE"));
				respTo.setResponseStatus(responseStatus);
			}

		} catch (Exception e) {
			lOGGER.error(e.getMessage(), e);
			ResponseStatus responseStatus = new ResponseStatus();
			responseStatus.setStatusCode(env.getProperty("FAILURE_STATUS_CODE"));
			responseStatus.setStatusMessage(env.getProperty("FAILURE_STATUS_MESSAGE"));
			respTo.setResponseStatus(responseStatus);
			return respTo;
		}
		return respTo;
	}

	// TO DELETE THE COURSE
	@DeleteMapping("/delete")
	public DeleteCourseRespTo deleteCourse(@RequestHeader(name = "course_id") Integer courseId) {
		DeleteCourseRespTo respTo = new DeleteCourseRespTo();
		try {
			Course isCourseExists = courseServiceHandler.getCourseById(courseId);
			if (isCourseExists == null) {
				ResponseStatus responseStatus = new ResponseStatus();
				responseStatus.setStatusCode(env.getProperty("COURSE_NOT_EXISTS"));
				responseStatus.setStatusMessage(env.getProperty("COURSE_NOT_EXISTS_MESSAGE"));
				respTo.setResponseStatus(responseStatus);
				return respTo;
			}
			courseServiceHandler.deleteCourseById(courseId);
			ResponseStatus responseStatus = new ResponseStatus();
			responseStatus.setStatusCode(env.getProperty("SUCCESS_STATUS_CODE"));
			responseStatus.setStatusMessage(env.getProperty("SUCCESS_STATUS_MESSAGE"));
			respTo.setResponseStatus(responseStatus);

		} catch (Exception e) {
			lOGGER.error(e.getMessage(), e);
			ResponseStatus responseStatus = new ResponseStatus();
			responseStatus.setStatusCode(env.getProperty("FAILURE_STATUS_CODE"));
			responseStatus.setStatusMessage(env.getProperty("FAILURE_STATUS_MESSAGE"));
			respTo.setResponseStatus(responseStatus);
			return respTo;
		}
		return respTo;
	}

	// TO ASSIGN THE USER INTO PARTICULAR COURSE
	@GetMapping("/enroll")
	public EnrollCourseRespTo enrollCourse(@RequestHeader Integer userId, @RequestHeader Integer courseId) {
		EnrollCourseRespTo respTo = new EnrollCourseRespTo();
		try {
			if (userId == null) {
				ResponseStatus responseStatus = new ResponseStatus();
				responseStatus.setStatusCode(env.getProperty("USER_ID_MAND"));
				responseStatus.setStatusMessage(env.getProperty("USER_ID_MAND_MESSAGE"));
				respTo.setResponseStatus(responseStatus);
				return respTo;
			}
			if (courseId == null) {
				ResponseStatus responseStatus = new ResponseStatus();
				responseStatus.setStatusCode(env.getProperty("COURSE_ID_MAND"));
				responseStatus.setStatusMessage(env.getProperty("COURSE_ID_MAND_MESSAGE"));
				respTo.setResponseStatus(responseStatus);
				return respTo;
			}
			Course isCourseExists = courseServiceHandler.getCourseById(courseId);
			if (isCourseExists == null) {
				ResponseStatus responseStatus = new ResponseStatus();
				responseStatus.setStatusCode(env.getProperty("COURSE_NOT_EXISTS"));
				responseStatus.setStatusMessage(env.getProperty("COURSE_NOT_EXISTS_MESSAGE"));
				respTo.setResponseStatus(responseStatus);
				return respTo;
			}
			UserCourseEnrollmentMapping isCourseMapped = userServiceHandler.getLevel(userId, courseId);
			if (isCourseMapped != null) {
				ResponseStatus responseStatus = new ResponseStatus();
				responseStatus.setStatusCode(env.getProperty("COURSE_ALREADY_MAPPED_STATUS_CODE"));
				responseStatus.setStatusMessage(env.getProperty("COURSE_ALREADY_MAPPED_STATUS_MESSAGE"));
				respTo.setResponseStatus(responseStatus);
				return respTo;
			} else {
				courseServiceHandler.enrollCourseToUser(userId, courseId);
				ResponseStatus responseStatus = new ResponseStatus();
				responseStatus.setStatusCode(env.getProperty("SUCCESS_STATUS_CODE"));
				responseStatus.setStatusMessage(env.getProperty("SUCCESS_STATUS_MESSAGE"));
				respTo.setResponseStatus(responseStatus);
				return respTo;
			}

		} catch (Exception e) {
			lOGGER.error(e.getMessage(), e);
			ResponseStatus responseStatus = new ResponseStatus();
			responseStatus.setStatusCode(env.getProperty("FAILURE_STATUS_CODE"));
			responseStatus.setStatusMessage(env.getProperty("FAILURE_STATUS_MESSAGE"));
			respTo.setResponseStatus(responseStatus);
			return respTo;
		}

	}

	// TO GET COURSE CERTIFICATE
	@GetMapping("/getCertificate")
	public GetCertificateCourseRespTo getCourseCertificate(@RequestHeader Integer userId,
			@RequestHeader Integer courseId, HttpServletResponse response) {
		GetCertificateCourseRespTo respTo = new GetCertificateCourseRespTo();
		try {
			Course courseDetails = courseServiceHandler.getCourseById(courseId);
			User userDetails = userServiceHandler.getUserById(userId);
			if (courseDetails == null) {
				ResponseStatus responseStatus = new ResponseStatus();
				responseStatus.setStatusCode(env.getProperty("COURSE_NOT_EXISTS"));
				responseStatus.setStatusMessage(env.getProperty("COURSE_NOT_EXISTS_MESSAGE"));
				respTo.setResponseStatus(responseStatus);
				return respTo;
			}
			if (userDetails == null) {
				ResponseStatus responseStatus = new ResponseStatus();
				responseStatus.setStatusCode(env.getProperty("USER_NOT_EXISTS_CODE"));
				responseStatus.setStatusMessage(env.getProperty("USER_NOT_EXISTS_MESSAGE"));
				respTo.setResponseStatus(responseStatus);
				return respTo;
			}
			UserCourseEnrollmentMapping userCourseEnrollmentMapping = userServiceHandler.getLevel(userId, courseId);
			if(userCourseEnrollmentMapping==null) {
				ResponseStatus responseStatus = new ResponseStatus();
				responseStatus.setStatusCode(env.getProperty("USER_NOT_ASSOCIATED_WITH_COURSE_CODE"));
				responseStatus.setStatusMessage(env.getProperty("USER_NOT_ASSOCIATED_WITH_COURSE_MESSAGE"));
				respTo.setResponseStatus(responseStatus);
				return respTo;
			}
			if (userCourseEnrollmentMapping.getStatus_id() == 3) {
				response.setContentType("application/pdf");
				response.setHeader("Content-Disposition", "attachment; filename=\"hello.pdf\"");
				courseServiceHandler.generatePdf(response, courseDetails, userDetails);
			} else if (userCourseEnrollmentMapping.getStatus_id() == 2) {
				ResponseStatus responseStatus = new ResponseStatus();
				responseStatus.setStatusCode(env.getProperty("COURSE_INPROGRESS_STATUS_CODE"));
				responseStatus.setStatusMessage(env.getProperty("COURSE_INPROGRESS_STATUS_MESSAGE"));
				respTo.setResponseStatus(responseStatus);
				return respTo;
			}

		} catch (Exception e) {
			lOGGER.error(e.getMessage(), e);
			ResponseStatus responseStatus = new ResponseStatus();
			responseStatus.setStatusCode(env.getProperty("FAILURE_STATUS_CODE"));
			responseStatus.setStatusMessage(env.getProperty("FAILURE_STATUS_MESSAGE"));
			respTo.setResponseStatus(responseStatus);
			return respTo;
		}
		return respTo;
	}

}
