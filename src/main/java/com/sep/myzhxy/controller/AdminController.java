package com.sep.myzhxy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sep.myzhxy.controller.pojo.Admin;
import com.sep.myzhxy.service.AdminService;
import com.sep.myzhxy.util.MD5;
import com.sep.myzhxy.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sms/adminController")
public class AdminController {
	@Autowired
	private AdminService adminService;
	
	@GetMapping("/getAllAdmin/{pageNo}/{pageSize}")
	public Result getAllAdmin(@PathVariable("pageNo") int pageNo,
	                          @PathVariable("pageSize") int pageSize,
	                          String adminName
	) {
		Page<Admin> page = new Page(pageNo, pageSize);
		IPage<Admin> iPage = adminService.getAdminByOpr(page, adminName);
		return Result.ok(iPage);
	}
	
	//http://localhost:9001/sms/adminController/saveOrUpdateAdmin
	@PostMapping("/saveOrUpdateAdmin")
	public Result saveOrUpdate(@RequestBody Admin admin) {
		Integer id = admin.getId();
		if (null == id || 0 == id) {
			admin.setPassword(MD5.encrypt(admin.getPassword()));
		}
		adminService.saveOrUpdate(admin);
		return Result.ok();
	}
	
	@DeleteMapping("/deleteAdmin")
	public Result deleteAdmin(@RequestBody List<Integer> ids) {
		adminService.removeByIds(ids);
		return Result.ok();
	}
}
