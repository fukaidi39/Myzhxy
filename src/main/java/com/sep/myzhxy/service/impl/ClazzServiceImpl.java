package com.sep.myzhxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sep.myzhxy.mapper.ClazzMapper;
import com.sep.myzhxy.controller.pojo.Clazz;
import com.sep.myzhxy.service.ClazzService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import java.util.List;

@Service("clazzServiceImpl")
@Transactional
public class ClazzServiceImpl extends ServiceImpl<ClazzMapper, Clazz> implements ClazzService {
	
	@Override
	public IPage<Clazz> getClazzByOpe(Page<Clazz> page, Clazz clazz) {
		QueryWrapper<Clazz> queryWrapper = new QueryWrapper<>();
		String gradeName = clazz.getGradeName();
		if (!StringUtils.isEmpty(gradeName)) {
			queryWrapper.like("grade_name", gradeName);
		}
		String name = clazz.getName();
		if (!StringUtils.isEmpty(name)) {
			queryWrapper.like("name", name);
		}
		queryWrapper.orderByDesc("id");
		return baseMapper.selectPage(page, queryWrapper);
	}
	
	@Override
	public List<Clazz> getClazzs() {
		return baseMapper.selectList(null);
	}
}
