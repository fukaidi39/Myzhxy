package com.sep.myzhxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sep.myzhxy.mapper.GradeMapper;
import com.sep.myzhxy.controller.pojo.Grade;
import com.sep.myzhxy.service.GradeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import java.util.List;

@Service("gradeServiceImpl")
@Transactional
public class GradeServiceImpl extends ServiceImpl<GradeMapper, Grade> implements GradeService {
	
	@Override
	public IPage<Grade> getBradeByOpr(Page<Grade> page, String gradeName) {
		QueryWrapper<Grade> queryWrapper = new QueryWrapper<>();
		if (!StringUtils.isEmpty(gradeName)) {
			queryWrapper.like("name", gradeName);
		}
		queryWrapper.orderByDesc("id");
		Page<Grade> gradePage = baseMapper.selectPage(page, queryWrapper);
		return gradePage;
	}
	
	@Override
	public List<Grade> getGrades() {
		return baseMapper.selectList(null);
	}
}
