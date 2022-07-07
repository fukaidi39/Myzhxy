package com.sep.myzhxy.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sep.myzhxy.controller.pojo.Admin;
import com.sep.myzhxy.controller.pojo.LoginForm;
import com.sep.myzhxy.controller.pojo.Student;
import com.sep.myzhxy.controller.pojo.Teacher;
import com.sep.myzhxy.service.AdminService;
import com.sep.myzhxy.service.StudentService;
import com.sep.myzhxy.service.TeacherService;
import com.sep.myzhxy.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/sms/system")
public class SystemController {
	@Autowired
	private AdminService adminService;
	@Autowired
	private StudentService studentService;
	@Autowired
	private TeacherService teacherService;
	
	//http://localhost:9001/sms/system/updatePwd/1111/111
	@PostMapping("updatePwd/{oldPwd}/{newPwd}")
	public Result updatePwd(@PathVariable("oldPwd") String oldPwd,
	                        @PathVariable("newPwd") String newPwd,
	                        @RequestHeader("token") String token) {
		boolean expiration = JwtHelper.isExpiration(token);
		if (expiration) {
			return Result.fail().message("token失效，请重新登陆后重试");
		}
		Long userId = JwtHelper.getUserId(token);
		int userType = JwtHelper.getUserType(token);
		oldPwd = MD5.encrypt(oldPwd);
		newPwd = MD5.encrypt(newPwd);
		
		switch (userType) {
			case 1:
				QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
				queryWrapper.eq("id", userId);
				queryWrapper.eq("password", oldPwd);
				Admin one = adminService.getOne(queryWrapper);
				if (one != null) {
					one.setPassword(newPwd);
					adminService.saveOrUpdate(one);
				} else {
					return Result.fail().message("输入原密码错误");
				}
				break;
			case 2:
				QueryWrapper<Student> queryWrapper1 = new QueryWrapper<>();
				queryWrapper1.eq("id", userId);
				queryWrapper1.eq("password", oldPwd);
				Student student = studentService.getOne(queryWrapper1);
				
				if (student != null) {
					student.setPassword(newPwd);
					studentService.saveOrUpdate(student);
				} else {
					return Result.fail().message("输入原密码错误");
				}
				break;
			case 3:
				QueryWrapper<Teacher> queryWrapper2 = new QueryWrapper<>();
				queryWrapper2.eq("id", userId);
				queryWrapper2.eq("password", oldPwd);
				Teacher teacher = teacherService.getOne(queryWrapper2);
				
				if (teacher != null) {
					teacher.setPassword(newPwd);
					teacherService.saveOrUpdate(teacher);
				} else {
					return Result.fail().message("输入原密码错误");
				}
				break;
		}
		return Result.ok();
	}
	
	
	//http://localhost:9001/sms/system/headerImgUpload
	@PostMapping("headerImgUpload")
	public Result headerImgUpload(@RequestPart("multipartFile") MultipartFile multipartFile) {
		String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
		String originalFilename = multipartFile.getOriginalFilename();
		int i = originalFilename.lastIndexOf(".");
		String newFileName = uuid + originalFilename.substring(i);
		String portraitPath = "D:\\IDEA_workspace\\myzhxy\\target\\classes\\public\\upload\\" + newFileName;
		try {
			multipartFile.transferTo(new File(portraitPath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		String path = "upload/" + newFileName;
		return Result.ok(path);
	}
	
	
	@GetMapping("/getInfo")
	public Result getInfoByToken(@RequestHeader("token") String token) {
		boolean expiration = JwtHelper.isExpiration(token);
		if (expiration) {
			return Result.build(null, ResultCodeEnum.TOKEN_ERROR);
		}
		Long userId = JwtHelper.getUserId(token);
		Integer userType = JwtHelper.getUserType(token);
		Map<String, Object> map = new HashMap<>();
		switch (userType) {
			case 1:
				Admin admin = adminService.getAdminById(userId);
				map.put("userType", 1);
				map.put("user", admin);
				break;
			case 2:
				Student student = studentService.getAdminById(userId);
				map.put("userType", 2);
				map.put("user", student);
				break;
			case 3:
				Teacher teacher = teacherService.getAdminById(userId);
				map.put("userType", 3);
				map.put("user", teacher);
				break;
		}
		return Result.ok(map);
	}
	
	@PostMapping("/login")
	public Result login(@RequestBody LoginForm loginForm, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String sessionVerifiCode = (String) session.getAttribute("verifiCode");
		String loginVerifiCode = loginForm.getVerifiCode();
		
		if ("".equals(sessionVerifiCode) || null == sessionVerifiCode) {
			return Result.fail().message("验证码失效，请刷新后重试");
		}
		if (!sessionVerifiCode.equalsIgnoreCase(loginVerifiCode)) {
			return Result.fail().message("验证码错误，请重新输入");
		}
		session.removeAttribute("verifiCode");
		Map<String, Object> map = new HashMap<>();
		switch (loginForm.getUserType()) {
			case 1:
				try {
					Admin admin = adminService.login(loginForm);
					if (null != admin) {
						String token = JwtHelper.createToken(admin.getId().longValue(), 1);
						map.put("token", token);
					} else {
						throw new RuntimeException("用户名或密码有误");
					}
					return Result.ok(map);
				} catch (RuntimeException e) {
					e.printStackTrace();
					return Result.fail().message(e.getMessage());
				}
			case 2:
				try {
					Student student = studentService.login(loginForm);
					if (null != student) {
						String token = JwtHelper.createToken(student.getId().longValue(), 2);
						map.put("token", token);
					} else {
						throw new RuntimeException("用户名或密码有误");
					}
					return Result.ok(map);
				} catch (RuntimeException e) {
					e.printStackTrace();
					return Result.fail().message(e.getMessage());
				}
			case 3:
				try {
					Teacher teacher = teacherService.login(loginForm);
					if (null != teacher) {
						String token = JwtHelper.createToken(teacher.getId().longValue(), 3);
						map.put("token", token);
					} else {
						throw new RuntimeException("用户名或密码有误");
					}
					return Result.ok(map);
				} catch (RuntimeException e) {
					e.printStackTrace();
					return Result.fail().message(e.getMessage());
				}
		}
		return Result.fail().message("查无此用户");
	}
	
	@GetMapping("/getVerifiCodeImage")
	public void getVerifiCodeImage(HttpServletRequest request, HttpServletResponse response) {
		BufferedImage verifiCodeImage = CreateVerifiCodeImage.getVerifiCodeImage();
		String verifiCode = new String(CreateVerifiCodeImage.getVerifiCode());
		HttpSession session = request.getSession();
		session.setAttribute("verifiCode", verifiCode);
		try {
			ImageIO.write(verifiCodeImage, "JPEG", response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
	


