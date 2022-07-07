package com.sep.myzhxy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sep.myzhxy.controller.pojo.Clazz;
import com.sep.myzhxy.service.ClazzService;
import com.sep.myzhxy.util.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sms/clazzController")
public class ClazzController {
	//http://localhost:9001/sms/clazzController/getClazzsByOpr/1/3
	//http://localhost:9001/sms/clazzController/getClazzsByOpr/1/3?gradeName name
	@Autowired
	private ClazzService clazzService;
	
	@GetMapping("/getClazzs")
	public Result getClazzs() {
		List<Clazz> clazzes = clazzService.getClazzs();
		return Result.ok(clazzes);
	}
	
	@DeleteMapping("/deleteClazz")
	public Result deleteClazz(@RequestBody List<Integer> ids) {
		clazzService.removeByIds(ids);
		return Result.ok();
	}
	
	@PostMapping("/saveOrUpdateClazz")
	public Result saveOrUpdateClazz(@RequestBody Clazz clazz) {
		clazzService.saveOrUpdate(clazz);
		return Result.ok();
	}
	
	@ApiOperation("分页带条件查询班级信息")
	@GetMapping("getClazzsByOpr/{pageNo}/{pageSize}")
	public Result getClazzByOpr(
		@PathVariable("pageNo") int pageNo,
		@PathVariable("pageSize") int pageSize,
		Clazz clazz
	) {
		Page<Clazz> page = new Page<>(pageNo, pageSize);
		IPage<Clazz> iPage = clazzService.getClazzByOpe(page, clazz);
		return Result.ok(iPage);
	}
}
