package com.olp.course_mgmt.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.olp.course_mgmt.common.CommonConstants;
import com.olp.course_mgmt.dao.CourseDaoService;
import com.olp.course_mgmt.entity.Course;
import com.olp.course_mgmt.entity.User;
import com.olp.course_mgmt.rest.CourseRestController;

@Service
public class CourseServiceHandler {

	private static final Logger lOGGER = LoggerFactory.getLogger(CourseServiceHandler.class);

	@Autowired
	private CourseDaoService courseDaoService;

	@Autowired
	private Environment environment;

	public List<Course> getAllCourses() {
		lOGGER.info(CommonConstants.BEGIN);
		List<Course> courseList = null;
		try {
			courseList = courseDaoService.getAllCourses();
		} catch (Exception ex) {
			lOGGER.error(ex.getMessage(), ex);
		}
		lOGGER.info(CommonConstants.END);
		return courseList;
	}

	public void createCourse(Course course) {
		lOGGER.info(CommonConstants.BEGIN);
		try {
			courseDaoService.createCourse(course);
		} catch (Exception e) {
			lOGGER.error(e.getMessage(), e);
		}
		lOGGER.info(CommonConstants.END);

	}

	public Course getCourseById(Integer courseId) {
		lOGGER.info(CommonConstants.BEGIN);
		Course course = null;
		try {
			course = courseDaoService.getCourseById(courseId);
		} catch (Exception ex) {
			lOGGER.error(ex.getMessage(), ex);
		}
		lOGGER.info(CommonConstants.END);
		return course;
	}

	public void update(Integer courseId, Course updatecourseobj, Course existingCourse) {
		lOGGER.info(CommonConstants.BEGIN);

		try {

			courseDaoService.updateCourse(courseId, updatecourseobj, existingCourse);
		} catch (Exception ex) {
			lOGGER.error(ex.getMessage(), ex);
		}
		lOGGER.info(CommonConstants.END);

	}

	public Course getCourseByName(String name) {
		lOGGER.info(CommonConstants.BEGIN);
		Course course = null;
		try {
			course = courseDaoService.getCourseByName(name);
		} catch (Exception ex) {
			lOGGER.error(ex.getMessage(), ex);
		}
		lOGGER.info(CommonConstants.END);
		return course;
	}

	public void deleteCourseById(Integer courseId) {
		lOGGER.info(CommonConstants.BEGIN);
		try {
			courseDaoService.delCourseById(courseId);
		} catch (Exception ex) {
			lOGGER.error(ex.getMessage(), ex);
		}
		lOGGER.info(CommonConstants.END);

	}

	public void enrollCourseToUser(Integer userId, Integer courseId) {
		lOGGER.info(CommonConstants.BEGIN);
		try {
			courseDaoService.enrollCourseToUser(userId, courseId);

		} catch (Exception ex) {
			lOGGER.error(ex.getMessage(), ex);
		}
		lOGGER.info(CommonConstants.END);

	}

	public void generatePdf(HttpServletResponse response, Course courseDetails, User userDetails)
			throws DocumentException, IOException {
		lOGGER.info(CommonConstants.BEGIN);
		try {
			Document document = new Document(PageSize.A4);
			PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());

			document.open();

			Font font1 = new Font(FontFamily.TIMES_ROMAN, 18, Font.BOLD, BaseColor.DARK_GRAY);
			Paragraph title = new Paragraph("Certificate of Achievement", font1);
			title.setAlignment(Element.ALIGN_CENTER);

			document.add(new Paragraph(title));

			// Add the recipient's name to the document
			Font font2 = new Font(FontFamily.TIMES_ROMAN, 15, Font.BOLD, BaseColor.DARK_GRAY);
			String name = userDetails.getFirstName() + " " + userDetails.getLastName();
			Paragraph recipient = new Paragraph(
					"This is to certify that " + name + " successfully completed " + courseDetails.getDuration()
							+ " of " + courseDetails.getCourseName() + " online course on " + new Date(),
					font2);
			recipient.setAlignment(Element.ALIGN_CENTER);
			recipient.setSpacingBefore(50);
			recipient.setSpacingAfter(50);
			document.add(recipient);
			Font font3 = new Font(FontFamily.TIMES_ROMAN, 18, Font.BOLD, BaseColor.DARK_GRAY);
			Paragraph teamDetails = new Paragraph("Online Learning Platform Team", font3);
			teamDetails.setAlignment(Element.ALIGN_CENTER);
			document.add(teamDetails);

			// Add the date to the document
			Font font4 = new Font(FontFamily.TIMES_ROMAN, 18, Font.BOLD, BaseColor.DARK_GRAY);
			DateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy");
			String date = dateFormat.format(new Date());
			Paragraph dateParagraph = new Paragraph("Date: " + date, font4);
			dateParagraph.setAlignment(Element.ALIGN_BOTTOM);
			dateParagraph.setSpacingBefore(50);
			document.add(dateParagraph);
			document.close();
		} catch (Exception ex) {
			lOGGER.error(ex.getMessage(), ex);
		}
		lOGGER.info(CommonConstants.END);

	}

}
