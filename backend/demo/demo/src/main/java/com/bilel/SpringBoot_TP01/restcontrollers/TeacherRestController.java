package com.bilel.SpringBoot_TP01.restcontrollers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.bilel.SpringBoot_TP01.entities.Image;
import com.bilel.SpringBoot_TP01.entities.Role;
import com.bilel.SpringBoot_TP01.entities.User;
import com.bilel.SpringBoot_TP01.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.bilel.SpringBoot_TP01.entities.Teacher;

@RestController
@RequestMapping("/api/teachers")
@CrossOrigin("*")
public class TeacherRestController {
	@Autowired
	private TeacherService teacherService;
	@Autowired
	private ImageService imageService;
	@Autowired
	private CourseService courseService;
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;

	@GetMapping("/all")
	List<Teacher> getAllTeachers() {
		return this.teacherService.getAllTeachers();
	}

	@GetMapping
	Page<Teacher> getTeachersPageByPage(@RequestParam("page") int page, @RequestParam("size") int size) {
		return this.teacherService.getTeachersByPage(page, size);
	}

	@GetMapping("/get/{teacherId}")
	Teacher getTeacherById(@PathVariable("teacherId") Long teacherId) {
		return this.teacherService.getTeacher(teacherId);
	}

	@PostMapping("/create")
	Teacher createNewTeacher(
			@RequestBody Teacher teacher
//			@RequestParam("imageId") Long imageId
	) throws IOException {
//		Image image = this.imageService.getImageDetails(imageId);
//		teacher.setImage(image);
		User teacherAsUser = new User(null, teacher.getUserName(), teacher.getEmail(), teacher.getPassword(), true, new ArrayList<Role>());
		Role role = roleService.findRoleById(2L);
		this.userService.saveUser(teacherAsUser);
		this.userService.grantRoleToUser(teacherAsUser, role);
		return this.teacherService.createTeacher(teacher);
	}

	@PatchMapping("/update/{teacherId}")
	Teacher updateTeacher(@PathVariable("teacherId") Long teacherId, @RequestParam("imageId") Long imageId, @RequestBody Teacher teacher) throws IOException {
		Image image = this.imageService.getImageDetails(imageId);

//		teacher.setImage(image);
		teacher.setTeacherId(teacherId);
		return this.teacherService.updateTeacher(teacher);
	}

	@DeleteMapping("/delete/{teacherId}")
	void deleteTeacher(@PathVariable("teacherId") Long teacherId) {
//		Teacher teacher = this.teacherService.getTeacher(teacherId);
//
//		try {
//			Files.delete(
//					Paths.get(
//							System.getProperty("user.home") + "/images/" + teacher.getImagePath()
//					)
//			);
//		} catch (IOException exp) {
//			exp.printStackTrace();
//		}
		this.imageService.deleteTeacherImages(teacherId);
		this.courseService.deleteTeacherCourses(teacherId);
		this.teacherService.deleteTeacherById(teacherId);
	}
}
