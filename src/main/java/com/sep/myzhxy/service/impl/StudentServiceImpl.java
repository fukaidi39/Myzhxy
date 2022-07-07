package com.sep.myzhxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sep.myzhxy.controller.pojo.Admin;
import com.sep.myzhxy.mapper.StudentMapper;
import com.sep.myzhxy.controller.pojo.LoginForm;
import com.sep.myzhxy.controller.pojo.Student;
import com.sep.myzhxy.service.StudentService;
import com.sep.myzhxy.util.MD5;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

@Service("studentServiceImpl")
@Transactional
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {
	
	@Override
	public Student login(LoginForm loginForm) {
		QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("name", loginForm.getUsername());
		queryWrapper.eq("password", MD5.encrypt(loginForm.getPassword()));
		Student student = baseMapper.selectOne(queryWrapper);
		return student;
	}
	
	@Override
	public Student getAdminById(Long userId) {
		QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("id", userId);
		return baseMapper.selectOne(queryWrapper);
	}
	
	@Override
	public IPage<Student> getStudentByOpr(Page<Student> page, Student student) {
		QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
		if (!StringUtils.isEmpty(student.getName())) {
			queryWrapper.eq("name", student.getName());
		}
		if (!StringUtils.isEmpty(student.getClazzName())) {
			queryWrapper.eq("clazz_name", student.getClazzName());
		}
		queryWrapper.orderByDesc("id");
		Page<Student> studentPage = baseMapper.selectPage(page, queryWrapper);
		return studentPage;
	}
}
