package com.sep.myzhxy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sep.myzhxy.controller.pojo.LoginForm;
import com.sep.myzhxy.controller.pojo.Student;

public interface StudentService extends IService<Student> {
	Student login(LoginForm loginForm);
	
	Student getAdminById(Long userId);
	
	IPage<Student> getStudentByOpr(Page<Student> page, Student student);
}
