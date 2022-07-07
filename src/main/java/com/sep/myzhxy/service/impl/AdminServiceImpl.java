package com.sep.myzhxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sep.myzhxy.mapper.AdminMapper;
import com.sep.myzhxy.controller.pojo.Admin;
import com.sep.myzhxy.controller.pojo.LoginForm;
import com.sep.myzhxy.service.AdminService;
import com.sep.myzhxy.util.MD5;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

@Service("adminServiceImpl")
@Transactional
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {
	
	@Override
	public Admin login(LoginForm loginForm) {
		QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("name", loginForm.getUsername());
		queryWrapper.eq("password", MD5.encrypt(loginForm.getPassword()));
		Admin admin = baseMapper.selectOne(queryWrapper);
		return admin;
	}
	
	@Override
	public Admin getAdminById(Long userId) {
		QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("id", userId);
		return baseMapper.selectOne(queryWrapper);
	}
	
	@Override
	public IPage<Admin> getAdminByOpr(Page<Admin> page, String adminName) {
		QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
		if (!StringUtils.isEmpty(adminName)) {
			queryWrapper.like("name", adminName);
		}
		queryWrapper.orderByDesc("id");
		Page<Admin> adminPage = baseMapper.selectPage(page, queryWrapper);
		return adminPage;
	}
}
