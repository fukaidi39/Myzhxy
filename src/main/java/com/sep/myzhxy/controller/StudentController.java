package com.sep.myzhxy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sep.myzhxy.controller.pojo.Student;
import com.sep.myzhxy.service.StudentService;
import com.sep.myzhxy.util.MD5;
import com.sep.myzhxy.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sms/studentController")
public class StudentController {
	@Autowired
	private StudentService studentService;
	
	//http://localhost:9001/sms/studentController/delStudentById
	@DeleteMapping("/delStudentById")
	public Result deleteStudent(@RequestBody List<Integer> Ids) {
		studentService.removeByIds(Ids);
		return Result.ok();
	}
	
	//http://localhost:9001/sms/studentController/addOrUpdateStudent
	@PostMapping("/addOrUpdateStudent")
	public Result addOrUpdateStudent(@RequestBody Student student) {
		Integer id = student.getId();
		if (null == id || 0 == id) {
			student.setPassword(MD5.encrypt(student.getPassword()));
		}
		studentService.saveOrUpdate(student);
		return Result.ok();
	}
	
	@GetMapping("/getStudentByOpr/{pageNo}/{pageSize}")
	public Result getStudentByOpr(@PathVariable("pageNo") int pageNo,
	                              @PathVariable("pageSize") int pageSize,
	                              Student student) {
		Page<Student> page = new Page(pageNo, pageSize);
		IPage<Student> studentIPage = studentService.getStudentByOpr(page, student);
		return Result.ok(studentIPage);
	}
}
