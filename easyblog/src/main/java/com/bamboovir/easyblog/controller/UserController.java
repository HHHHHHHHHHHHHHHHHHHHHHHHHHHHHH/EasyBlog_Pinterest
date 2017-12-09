package com.bamboovir.easyblog.controller;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.Md5Crypt;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitterReturnValueHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bamboovir.easyblog.model.User;
import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.options.MutableDataSet;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import com.bamboovir.easyblog.email.JavaEmail;
import com.bamboovir.easyblog.image.ImageHelper;
import com.bamboovir.easyblog.mapper.BehaviorMapper;
import com.bamboovir.easyblog.mapper.BoardMapper;
import com.bamboovir.easyblog.mapper.ImageMapper;
import com.bamboovir.easyblog.mapper.PostMapper;
import com.bamboovir.easyblog.mapper.TagMapper;
import com.bamboovir.easyblog.mapper.UnAuthUserMapper;
import com.bamboovir.easyblog.mapper.UserMapper;
import com.bamboovir.easyblog.markdown.MarkdownUtil;
import com.bamboovir.easyblog.model.BehaviorType;
import com.bamboovir.easyblog.model.Board;
import com.bamboovir.easyblog.model.LoginAuth;
import com.bamboovir.easyblog.model.Post;
import com.bamboovir.easyblog.model.SearchAtom;
import com.bamboovir.easyblog.model.StatusInfo;
import com.bamboovir.easyblog.model.TagType;

@RestController
@RequestMapping("/user")
public class UserController {
	
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
	
	Boolean Auth(String userId ,String userAuth) {
		if(userId == null || userAuth == null) {
			return false;
		}
		
		User user = userMapper.findUserByID(userId);
		
		if(user != null && user.getAuthId().equals(userAuth)) {
			return true;
		}else {
			return false;
		}
	}
	
	@ApiOperation("新创建用户 / 此时用户为未验证 / 向用户发送邮箱")
	@RequestMapping(value="/create",method=RequestMethod.POST)
	public ResponseEntity<StatusInfo> createUser(@RequestBody LoginAuth auth) {
		StatusInfo statusInfo = new StatusInfo();
		// 发送电子邮件(thread)( kafka )  !!!
		// 20 mins ( CleanUnAuthUser )   !!!
		if(unAuthUserMapper.getUnAuthUserByUserName(auth.getUsernm()) != null || userMapper.findUserByUserName(auth.getUsernm()) != null) {
			statusInfo.setStatusInfo("error <- already have this username");
			return new ResponseEntity<StatusInfo>(statusInfo,HttpStatus.OK);
		}
		
		if(unAuthUserMapper.findUnAuthUserByEmail(auth.getEmail()) != null || userMapper.findUserByEmail(auth.getUsernm()) != null) {
			statusInfo.setStatusInfo("error <- already have this email");
			return new ResponseEntity<StatusInfo>(statusInfo,HttpStatus.OK);
		}
		
		User newUser = new User();
		UUID userId = UUID.randomUUID();
		UUID authid = UUID.randomUUID(); 
		Date now = new Date();      
		Long nowLongTime = new Long(now.getTime()/1000);
		newUser.setUserName(auth.getUsernm());
		newUser.setAuthId(authid.toString());
		newUser.setId(userId.toString());
		newUser.setCreatedAt(nowLongTime);
		newUser.setPasswdMD5(DigestUtils.md5Hex(auth.getPasswd()));
		newUser.setEmail(auth.getEmail());
		newUser.setType("user");
		
		unAuthUserMapper.insertUnAuthUser(newUser.getId(), newUser.getType(),newUser.getGender(),
				newUser.getUserName(),newUser.getFullName(),newUser.getFirstName(), 
				newUser.getLastName(),newUser.getEmail(),newUser.getPhoneNumber(),newUser.getGplusUrl(),newUser.getDomainUrl(),newUser.getTwitterUrl(),
				newUser.getFacebookUrl(),newUser.getCreatedAt(),newUser.getCountry(),newUser.getImageMediumUrl(),newUser.getImageSmallUrl(), 
				newUser.getImageLargeUrl(),newUser.getPosts(), newUser.getAuthId(),newUser.getPasswdMD5());
		
		String authURL = "http://localhost:8080/user/emailAuth/" + newUser.getAuthId() + "/" + newUser.getId();
		
		
		String behaviorId = UUID.randomUUID().toString();
		behaviorMapper.insertBehavior(behaviorId,userId.toString(),userId.toString(), BehaviorType.CREATEUSER.getType(),nowLongTime);
		
		
		JavaEmail javaEmail = new JavaEmail();
		javaEmail.sendEmail("hd945@mail.missouri.edu", authURL);
		
		statusInfo.setStatusInfo("success <- create user:" + userId + " At " + nowLongTime);
		return new ResponseEntity<StatusInfo>(statusInfo,HttpStatus.OK);
	}
	 
	@ApiOperation("验证邮件 中内嵌的 URL 用户点击即可认证 ，5 分钟 后自动失效 / 重定向到成功验证页面 ！！！")
	@RequestMapping(value="/emailAuth/{authid}/{userid}",method=RequestMethod.GET)
	public ResponseEntity<StatusInfo> emailAuth(@PathVariable("authid") String authid,@PathVariable("userid") String userid) {
		StatusInfo statusInfo = new StatusInfo();
		
		User unAuthUser = unAuthUserMapper.getUnAuthUserById(userid);
		
		if(unAuthUser == null) {
			statusInfo.setStatusInfo("error <- 数据库中没有该用户");
			return new ResponseEntity<StatusInfo>(statusInfo,HttpStatus.OK);
		}
		
		if((unAuthUser.getAuthId().equals(authid)) && (unAuthUser.getId().equals(userid))) {
			
			unAuthUser.setAuthId(UUID.randomUUID().toString()); // 为用户新创建一个随机AuthID
			
			userMapper.insertUser(unAuthUser.getId(), unAuthUser.getType(),unAuthUser.getGender(),
					unAuthUser.getUserName(),unAuthUser.getFullName(),unAuthUser.getFirstName(), 
					unAuthUser.getLastName(),unAuthUser.getEmail(),unAuthUser.getPhoneNumber(),unAuthUser.getGplusUrl(),unAuthUser.getDomainUrl(),unAuthUser.getTwitterUrl(),
					unAuthUser.getFacebookUrl(),unAuthUser.getCreatedAt(),unAuthUser.getCountry(),unAuthUser.getImageMediumUrl(),unAuthUser.getImageSmallUrl(), 
					unAuthUser.getImageLargeUrl(),unAuthUser.getPosts(), unAuthUser.getAuthId(),unAuthUser.getPasswdMD5()); // 将用户从未验证区转移到验证区
			
			statusInfo.setStatusInfo("success <- auth user:" + unAuthUser.getId());
			return new ResponseEntity<StatusInfo>(statusInfo,HttpStatus.OK);
			
		}else {
			statusInfo.setStatusInfo("error <- 验证失败 ");
			return new ResponseEntity<StatusInfo>(statusInfo,HttpStatus.OK);
		}
		
	}
	

	@ApiOperation("获取一个用户的所有 Public Board")
	@RequestMapping(value="/getPublicBoardOfUser",method=RequestMethod.POST)
	public ResponseEntity<List<Board>> getPublicBoardOfUser(@RequestBody User user) {
		List<Board> board = boardMapper.findBoardByUserID(user.getId(), Boolean.FALSE);
		
		return new ResponseEntity<List<Board>>(board,HttpStatus.OK);
	}
	
	@ApiOperation("获取一个用户的所有 Private Board")
	@RequestMapping(value="/getPriavteBoardOfUser",method=RequestMethod.POST)
	public ResponseEntity<List<Board>> getPrivateBoardOfUser(@RequestBody User user) {
		List<Board> board = boardMapper.findBoardByUserID(user.getId(), Boolean.TRUE);
		
		return new ResponseEntity<List<Board>>(board,HttpStatus.OK);
	}

	@ApiOperation("获取一个用户的所有 Public Post")
	@RequestMapping(value="/getPublicPostOfUser",method=RequestMethod.POST)
	public ResponseEntity<List<Post>> getPublicPostOfUser(@RequestBody User user) {
		
		List<Post> posts = postMapper.findPostsByUserID(user.getId(),Boolean.FALSE);
		
		return new ResponseEntity<List<Post>>(posts,HttpStatus.OK);
		
	}
	
	@ApiOperation("获取一个用户的所有 Private Post")
	@RequestMapping(value="/getPrivatePostOfUser",method=RequestMethod.POST)
	public ResponseEntity<List<Post>> getPrivatePostOfUser(@RequestBody User user) {
		
		List<Post> posts = postMapper.findPostsByUserID(user.getId(),Boolean.FALSE);
		
		return new ResponseEntity<List<Post>>(posts,HttpStatus.OK);
		
	}
	
	
	@ApiOperation("更新已注册用户信息 ")
	@RequestMapping(value="/updateMate",method=RequestMethod.POST)
	public ResponseEntity<StatusInfo> updateUser(@RequestBody User user,
			@CookieValue(value="userId",defaultValue="") String userId,
		    @CookieValue(value="userAuth",defaultValue="") String userAuth){

		StatusInfo statusInfo = new StatusInfo();
		
		User userTemp = null;
		userTemp = userMapper.findUserByID(userId);
		if(Auth(userId, userAuth) && userTemp != null && userTemp.getId().equals(userId)) {
			
			userMapper.updateUserCountryByID(user.getCountry(),user.getId());
			userMapper.updateUserDomainUrlByID(user.getDomainUrl(),user.getId());
			userMapper.updateUserEmailByID(user.getEmail(), user.getId());
			userMapper.updateUserFacebookUrlByID(user.getFacebookUrl(),user.getId());
			userMapper.updateUserFirstnameByID(user.getFirstName(),user.getId());
			userMapper.updateUserFullnameByID(user.getFullName(), user.getId());
			userMapper.updateUserGenderByID(user.getGender(), user.getId());
			userMapper.updateUserGplusUrlByID(user.getGplusUrl(),user.getId());
			userMapper.updateUserImageMediumUrlByID(user.getImageMediumUrl(),user.getId());
			userMapper.updateUserImageSmallUrlByID(user.getImageSmallUrl(),user.getId());
			userMapper.updateUserLastnameByID(user.getLastName(),user.getId());
			userMapper.updateUserPhonenumberByID(user.getPhoneNumber(), user.getId());
			userMapper.updateUserTwitterUrlByID(user.getTwitterUrl(), user.getId());
			
			Date now = new Date();
			Long nowLongTime = new Long(now.getTime()/1000);
			String behaviorId = UUID.randomUUID().toString();
			behaviorMapper.insertBehavior(behaviorId,userId,userId, BehaviorType.UPDATEUSER.toString(),nowLongTime);
			
			statusInfo.setStatusInfo("success <- Update : " + user.getId());
			return new ResponseEntity<StatusInfo>(statusInfo,HttpStatus.OK);
		}
		
		statusInfo.setStatusInfo("error <- void");
		return new ResponseEntity<StatusInfo>(statusInfo,HttpStatus.OK);
		
	}
	
	//----
	/*
	@RequestMapping(value="/test")
	public String hello() throws IOException {
		
		
		MutableDataSet options = new MutableDataSet();

        Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();
        File file = new File("C:\\Users\\BamBoo\\Desktop\\Blog\\jianli.md");
        Reader reader = new FileReader(file);
        Node document = parser.parseReader(reader);
        String html = renderer.render(document);  // "<p>This is <em>Sparta</em></p>\n"
        System.out.println(html);
		FileUtils.writeStringToFile(new File("C:\\Users\\BamBoo\\Desktop\\Blog\\jianli.html"), html);
		
		
		
		// 发送邮件测试
		for(int i = 0; i < 20 ; i++) {
		JavaEmail javaEmail = new JavaEmail();
		javaEmail.sendEmail("wangyjknight@126.com", "6666666666");
		}
		
		
		// markdown to html test
		MarkdownUtil markdownUtil = new MarkdownUtil(); 
		markdownUtil.example();
		
		
		
		// 图片压缩测试
		ImageHelper.scaleImageWithParams("C:\\Users\\BamBoo\\Pictures\\666.jpg", "C:\\Users\\BamBoo\\Pictures\\999.jpg", 300, 300, false, "jpg");
		
	return "<html>hello!</html>";	
	}
	
	@RequestMapping(value="/cookie")
	public String cookie(@CookieValue(value = "Counter",defaultValue = "0") Long counter,
			HttpServletResponse response) {
			counter++;
			Cookie cookie = new Cookie("Counter", counter.toString());
			cookie.setMaxAge(20);
			response.addCookie(cookie);
	return "<html>hello! " + counter + "</html>";	
	}
	

	@RequestMapping(value = "/posts", method = RequestMethod.POST)
	public ResponseEntity<List<Post>> update(@RequestBody List<Post> posts) {

	    posts.stream().forEach(e -> e.setDescription("666"));

	    return new ResponseEntity<List<Post>>(posts, HttpStatus.OK);
	}
	
	*/
	//----------------------------------------------
	
}
