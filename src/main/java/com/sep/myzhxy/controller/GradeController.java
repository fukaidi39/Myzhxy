package com.sep.myzhxy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sep.myzhxy.controller.pojo.Grade;
import com.sep.myzhxy.service.GradeService;
import com.sep.myzhxy.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "年级控制器")
@RequestMapping("/sms/gradeController")
public class GradeController {
	@Autowired
	private GradeService gradeService;
	
	//http://localhost:9001/sms/gradeController/getGrades
	@GetMapping("getGrades")
	@ApiOperation("获取全部年级")
	public Result getGrade() {
		List<Grade> grades = gradeService.getGrades();
		return Result.ok(grades);
	}
	
	//http://localhost:9001/sms/gradeController/deleteGrade
	@ApiOperation(value = "删除Grade信息")
	@DeleteMapping("deleteGrade")
	public Result deleteGrade(
		@ApiParam("要删除的所有的grade的id的json集合") @RequestBody List<Integer> ids) {
		gradeService.removeByIds(ids);
		return Result.ok();
	}
	
	//http://localhost:9001/sms/gradeController/saveOrUpdateGrade
	@ApiOperation("新增或者修改grade，有ID属性是修改，没有则是添加")
	@PostMapping("saveOrUpdateGrade")
	public Result saveOrUpdate(@RequestBody Grade grade) {
		gradeService.saveOrUpdate(grade);
		return Result.ok();
	}
	
	//http://localhost:9001/sms/gradeController/getGrades/1/3
	@ApiOperation("根据年纪名称模糊查询，带分页")
	@GetMapping("/getGrades/{PageNo}/{pageSize}")
	public Result getGrade(@ApiParam("分页查询的页码数") @PathVariable("PageNo") int pageNo,
	                       @ApiParam("分页查询的页大小") @PathVariable("pageSize") int pageSize,
	                       @ApiParam("分页查询的模糊匹配") String gradeName) {
		Page<Grade> page = new Page<>(pageNo, pageSize);
		IPage<Grade> pageRs = gradeService.getBradeByOpr(page, gradeName);
		return Result.ok(pageRs);
	}
	
	
}
