package com.sep.myzhxy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sep.myzhxy.controller.pojo.Clazz;

import java.util.List;

public interface ClazzService extends IService<Clazz> {
	IPage<Clazz> getClazzByOpe(Page<Clazz> page, Clazz clazz);
	
	List<Clazz> getClazzs();
}
