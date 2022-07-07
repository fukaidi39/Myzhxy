package com.sep.myzhxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sep.myzhxy.controller.pojo.Admin;
import com.sep.myzhxy.mapper.TeacherMapper;
import com.sep.myzhxy.controller.pojo.LoginForm;
import com.sep.myzhxy.controller.pojo.Teacher;
import com.sep.myzhxy.service.TeacherService;
import com.sep.myzhxy.util.MD5;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

@Service("teacherServiceImpl")
@Transactional
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {
	
	@Override
	public Teacher login(LoginForm loginForm) {
		QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("name", loginForm.getUsername());
		queryWrapper.eq("password", MD5.encrypt(loginForm.getPassword()));
		Teacher teacher = baseMapper.selectOne(queryWrapper);
		return teacher;
	}
	
	@Override
	public Teacher getAdminById(Long userId) {
		QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("id", userId);
		return baseMapper.selectOne(queryWrapper);
	}
	
	@Override
	public IPage<Teacher> getTeacherByOpr(Page<Teacher> page, Teacher teacher) {
		QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
		if (!StringUtils.isEmpty(teacher.getName())) {
			queryWrapper.like("name", teacher.getName());
		}
		if (!StringUtils.isEmpty(teacher.getClazzName())) {
			queryWrapper.eq("clazz_name", teacher.getClazzName());
		}
		queryWrapper.orderByDesc("id");
		return baseMapper.selectPage(page, queryWrapper);
	}
}
