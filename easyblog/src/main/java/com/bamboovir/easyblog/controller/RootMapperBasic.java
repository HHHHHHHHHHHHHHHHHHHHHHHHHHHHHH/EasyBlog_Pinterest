package com.bamboovir.easyblog.controller;

import java.util.Date;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bamboovir.easyblog.mapper.BehaviorMapper;
import com.bamboovir.easyblog.mapper.BoardMapper;
import com.bamboovir.easyblog.mapper.ImageMapper;
import com.bamboovir.easyblog.mapper.PostMapper;
import com.bamboovir.easyblog.mapper.TagMapper;
import com.bamboovir.easyblog.mapper.UnAuthUserMapper;
import com.bamboovir.easyblog.mapper.UserMapper;
import com.bamboovir.easyblog.model.BehaviorType;
import com.bamboovir.easyblog.model.LoginAuth;
import com.bamboovir.easyblog.model.StatusInfo;
import com.bamboovir.easyblog.model.User;

import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping("/")
public class RootMapperBasic {

	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private PostMapper postMapper;
	
	@Autowired
	private UnAuthUserMapper unAuthUserMapper;
	
	@Autowired
	private BoardMapper boardMapper;
	
	@Autowired
	private ImageMapper ImageMapper;
	
	@Autowired
	private TagMapper tagMapper;
	
	@Autowired
	private BehaviorMapper behaviorMapper;
	
	@ApiOperation("登陆验证 - 涵盖了 用户名-密码 方式 与 邮箱密码方式")
	@RequestMapping(value="/loginAuth",method=RequestMethod.POST)
	public ResponseEntity<StatusInfo> login(@RequestBody LoginAuth auth,HttpServletResponse response) {

		StatusInfo statusInfo = new StatusInfo();
		
		if(auth == null || auth.getPasswd() == null || auth.getPasswd().equals("")) {
			statusInfo.setStatusInfo("error <- password");
			return new ResponseEntity<StatusInfo>(statusInfo,HttpStatus.OK);
		}
		
		if(auth.getEmail() != null && !auth.getEmail().equals("")) {
			statusInfo = loginByEmail(auth, response);
			if(statusInfo.getStatusInfo().contains("success")) {
				return new ResponseEntity<StatusInfo>(statusInfo,HttpStatus.OK);
			}
		}
		
		if(auth.getUsernm() != null && !auth.getUsernm().equals("")) {
			statusInfo = loginByUserName(auth, response);
			if(statusInfo.getStatusInfo().contains("success")) {
				return new ResponseEntity<StatusInfo>(statusInfo,HttpStatus.OK);
			}
		}
		
			statusInfo.setStatusInfo("error <- void");
			return new ResponseEntity<StatusInfo>(statusInfo,HttpStatus.OK);
		
	}
	
	
	@ApiOperation("登陆验证 - 用户名-密码方式")
	@RequestMapping(value="/loginAuthByUserName",method=RequestMethod.POST)
	public StatusInfo loginByUserName(@RequestBody LoginAuth auth,HttpServletResponse response) {

		StatusInfo statusInfo = new StatusInfo();
		
		if(auth.getPasswd() == null || auth.getUsernm() == null) {
			
			statusInfo.setStatusInfo("error <- param error");
			return statusInfo;
		}
		
		User user = userMapper.findUserByUserName(auth.getUsernm());
		
		if(user != null && user.getPasswdMD5().equals(DigestUtils.md5Hex(auth.getPasswd()))) {			
			
			Cookie userIdCookie = new Cookie("userId", user.getId());
			Cookie userAuthCookie = new Cookie("userAuth",user.getAuthId());
			
			userIdCookie.setMaxAge(60*15); //设定 cookie 过期时间 15 分钟
			userAuthCookie.setMaxAge(60*15); //设定 cookie 过期时间 15 分钟
			response.addCookie(userIdCookie);
			response.addCookie(userAuthCookie);
			
			Date now = new Date();
			Long nowLongTime = new Long(now.getTime()/1000);
			String behaviorId = UUID.randomUUID().toString();
			behaviorMapper.insertBehavior(behaviorId,user.getId(),user.getId(), BehaviorType.LOGIN.getType(),nowLongTime);
					
			statusInfo.setStatusInfo("success <- user:" + user.getId());
			return statusInfo;
		}else {
			statusInfo.setStatusInfo("error <- error data");
			return statusInfo;
		}	
	}
	
	@ApiOperation("登陆验证 - 邮箱-密码方式")
	@RequestMapping(value="/loginAuthByEmail",method=RequestMethod.POST)
	public StatusInfo loginByEmail(@RequestBody LoginAuth auth,HttpServletResponse response) {

		StatusInfo statusInfo = new StatusInfo();
		
		if(auth.getPasswd() == null || auth.getEmail() == null) {
			statusInfo.setStatusInfo("error <- param error");
			return statusInfo;
		}
		
		User user = userMapper.findUserByEmail(auth.getEmail());
		
		if(user != null && user.getPasswdMD5().equals(DigestUtils.md5Hex(auth.getPasswd()))) {			
			Cookie userIdCookie = new Cookie("userId", user.getId());
			Cookie userAuthCookie = new Cookie("userAuth",user.getAuthId());
			
			userIdCookie.setMaxAge(60*15); //设定 cookie 过期时间 15 分钟
			userAuthCookie.setMaxAge(60*15); //设定 cookie 过期时间 15 分钟
			response.addCookie(userIdCookie);
			response.addCookie(userAuthCookie);
			
			
			Date now = new Date();
			Long nowLongTime = new Long(now.getTime()/1000);
			String behaviorId = UUID.randomUUID().toString();
			behaviorMapper.insertBehavior(behaviorId,user.getId(),user.getId(), BehaviorType.LOGIN.getType(),nowLongTime);
	
			statusInfo.setStatusInfo("success <- user:" + user.getId());
			return statusInfo;
		}else {
			statusInfo.setStatusInfo("error <- error data");
			return statusInfo;
		}
		
	}
	
	@ApiOperation("Logout")
	@RequestMapping(value="/logout", method=RequestMethod.POST)
	public ResponseEntity<StatusInfo> logout(HttpServletResponse response,
			@CookieValue(value="userId",defaultValue="") String userId,
		    @CookieValue(value="userAuth",defaultValue="") String userAuth) {
		
			StatusInfo statusInfo = new StatusInfo();
		
			Cookie userIdCookie = new Cookie("userId","");
			Cookie userAuthCookie = new Cookie("userAuth","");
			
			response.addCookie(userIdCookie);
			response.addCookie(userAuthCookie);
			
			
			Date now = new Date();
			Long nowLongTime = new Long(now.getTime()/1000);
			String behaviorId = UUID.randomUUID().toString();
			behaviorMapper.insertBehavior(behaviorId,userId,userId, BehaviorType.LOGOUT.getType(),nowLongTime);
				
			statusInfo.setStatusInfo("success <- logout : "+ userId);
			return new ResponseEntity<StatusInfo>(statusInfo,HttpStatus.OK);
	}
	
}
