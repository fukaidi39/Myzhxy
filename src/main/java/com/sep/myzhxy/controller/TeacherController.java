package com.sep.myzhxy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sep.myzhxy.controller.pojo.Teacher;
import com.sep.myzhxy.service.TeacherService;
import com.sep.myzhxy.util.MD5;
import com.sep.myzhxy.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sms/teacherController")
public class TeacherController {
	@Autowired
	private TeacherService teacherService;
	
	//http://localhost:9001/sms/teacherController/getTeachers/1/3
	@GetMapping("/getTeachers/{pageNo}/{pageSize}")
	public Result getTeachers(@PathVariable("pageNo") int pageNo,
	                          @PathVariable("pageSize") int pageSize,
	                          Teacher teacher) {
		Page<Teacher> page = new Page<>(pageNo, pageSize);
		IPage<Teacher> iPage = teacherService.getTeacherByOpr(page, teacher);
		return Result.ok(iPage);
	}
	
	@PostMapping("/saveOrUpdateTeacher")
	public Result saveOrUpdateTeacher(@RequestBody Teacher teacher) {
		if (teacher.getId() == null || teacher.getId() == 0) {
			teacher.setPassword(MD5.encrypt(teacher.getPassword()));
		}
		teacherService.saveOrUpdate(teacher);
		return Result.ok();
	}
	
	@DeleteMapping("/deleteTeacher")
	public Result deleteTeacher(@RequestBody List<Integer> ids) {
		teacherService.removeByIds(ids);
		return Result.ok();
	}
}
