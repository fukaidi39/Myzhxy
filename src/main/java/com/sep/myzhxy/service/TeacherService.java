package com.sep.myzhxy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sep.myzhxy.controller.pojo.LoginForm;
import com.sep.myzhxy.controller.pojo.Teacher;

public interface TeacherService extends IService<Teacher> {
	Teacher login(LoginForm loginForm);
	
	Teacher getAdminById(Long userId);
	
	IPage<Teacher> getTeacherByOpr(Page<Teacher> page, Teacher teacher);
}
