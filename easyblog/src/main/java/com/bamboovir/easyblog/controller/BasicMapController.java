package com.bamboovir.easyblog.controller;

import static org.mockito.Mockito.RETURNS_DEEP_STUBS;

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

import com.bamboovir.easyblog.email.JavaEmail;
import com.bamboovir.easyblog.image.ImageHelper;
import com.bamboovir.easyblog.mapper.BoardMapper;
import com.bamboovir.easyblog.mapper.ImageMapper;
import com.bamboovir.easyblog.mapper.PostMapper;
import com.bamboovir.easyblog.mapper.TagMapper;
import com.bamboovir.easyblog.mapper.UnAuthUserMapper;
import com.bamboovir.easyblog.mapper.UserMapper;
import com.bamboovir.easyblog.markdown.MarkdownUtil;
import com.bamboovir.easyblog.model.Board;
import com.bamboovir.easyblog.model.LoginAuth;
import com.bamboovir.easyblog.model.Post;
import com.bamboovir.easyblog.model.SearchAtom;
import com.bamboovir.easyblog.model.StatusInfo;

@RestController
public class BasicMapController {
	
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
	

	@RequestMapping(value="/test")
	public String hello() throws IOException {
		
		/*
		MutableDataSet options = new MutableDataSet();

        Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();
        File file = new File("C:\\Users\\BamBoo\\Desktop\\Blog\\jianli.md");
        Reader reader = new FileReader(file);
        Node document = parser.parseReader(reader);
        String html = renderer.render(document);  // "<p>This is <em>Sparta</em></p>\n"
        System.out.println(html);
		FileUtils.writeStringToFile(new File("C:\\Users\\BamBoo\\Desktop\\Blog\\jianli.html"), html);
		*/
		
		/*
		// 发送邮件测试
		for(int i = 0; i < 20 ; i++) {
		JavaEmail javaEmail = new JavaEmail();
		javaEmail.sendEmail("wangyjknight@126.com", "6666666666");
		}
		*/
		/*
		// markdown to html test
		MarkdownUtil markdownUtil = new MarkdownUtil(); 
		markdownUtil.example();
		*/
		
		/* 
		// 图片压缩测试
		ImageHelper.scaleImageWithParams("C:\\Users\\BamBoo\\Pictures\\666.jpg", "C:\\Users\\BamBoo\\Pictures\\999.jpg", 300, 300, false, "jpg");
		*/
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
	
	@RequestMapping(value="/deleteCookie")
	public String deleteCookie(
			HttpServletResponse response) {
			Cookie cookie = new Cookie("Counter","0");
			cookie.setMaxAge(60);
			response.addCookie(cookie);
	return "<html>hello! " + "delete cookie" + "</html>";	
	}
		
	@RequestMapping(value = "/updateJsontext", method = RequestMethod.POST)
	public ResponseEntity<User> update(@RequestBody User user) {

	    if (user != null) {
	        user.setFirstName("Huiming");
	    }

	    return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/posts", method = RequestMethod.POST)
	public ResponseEntity<List<Post>> update(@RequestBody List<Post> posts) {

	    posts.stream().forEach(e -> e.setDescription("666"));

	    return new ResponseEntity<List<Post>>(posts, HttpStatus.OK);
	}
	
	@RequestMapping(value="/sqltest/{id}",method=RequestMethod.GET)
	public String sqlTest(@PathVariable("id")Integer id) {
		
	userMapper.findUserByID(id.toString()).printString();
	
	return "<html>hello!</html>";	
	}
	
	//----------------------------------------------
	
	//新创建用户 post 映射
	@RequestMapping(value="/createUser",method=RequestMethod.POST)
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
		
		String authURL = "http://localhost:8080/" + newUser.getAuthId() + "/" + newUser.getId();
		
		JavaEmail javaEmail = new JavaEmail();
		javaEmail.sendEmail(newUser.getEmail(), authURL);
		
		statusInfo.setStatusInfo("success <- create user:" + userId + " At " + nowLongTime);
		return new ResponseEntity<StatusInfo>(statusInfo,HttpStatus.OK);
	}
	
		//新创建超级用户 post 映射（测试）
		@RequestMapping(value="/createSuperUser",method=RequestMethod.POST)
		public ResponseEntity<StatusInfo> createSuperUser(@RequestBody LoginAuth auth) {
			StatusInfo statusInfo = new StatusInfo();
			
			if(userMapper.findUserByUserName(auth.getUsernm()) == null) {
			User newSuperUser = new User();
			UUID userSuperId = UUID.randomUUID();
			UUID authSuperid = UUID.randomUUID(); 
			Date now = new Date();      
			Long nowLongTime = new Long(now.getTime()/1000);
			newSuperUser.setAuthId(authSuperid.toString());
			newSuperUser.setId(userSuperId.toString());
			newSuperUser.setCreatedAt(nowLongTime);
			newSuperUser.setUserName(auth.getUsernm());
			newSuperUser.setPasswdMD5(DigestUtils.md5Hex(auth.getPasswd()));
			newSuperUser.setEmail(auth.getEmail());
			newSuperUser.setType("SuperUser");
			
			
			userMapper.insertUser(newSuperUser.getId(), newSuperUser.getType(),newSuperUser.getGender(),
					newSuperUser.getUserName(),newSuperUser.getFullName(),newSuperUser.getFirstName(), 
					newSuperUser.getLastName(),newSuperUser.getEmail(),newSuperUser.getPhoneNumber(),newSuperUser.getGplusUrl(),newSuperUser.getDomainUrl(),newSuperUser.getTwitterUrl(),
					newSuperUser.getFacebookUrl(),newSuperUser.getCreatedAt(),newSuperUser.getCountry(),newSuperUser.getImageMediumUrl(),newSuperUser.getImageSmallUrl(), 
					newSuperUser.getImageLargeUrl(),newSuperUser.getPosts(), newSuperUser.getAuthId(),newSuperUser.getPasswdMD5());
		
			statusInfo.setStatusInfo("success <- create user:" + userSuperId + " At " + nowLongTime);
			return new ResponseEntity<StatusInfo>(statusInfo,HttpStatus.OK);
			}else {
				statusInfo.setStatusInfo("error <- already have this username");
				return new ResponseEntity<StatusInfo>(statusInfo,HttpStatus.OK);
			}
		}
	
	//验证邮件 中内嵌的 URL 用户点击即可认证 ，5 分钟 后自动失效 () 
	//返回或重定向到成功验证页面 ！！！ 
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
	
	@RequestMapping(value="/loginAuth",method=RequestMethod.POST)
	public ResponseEntity<StatusInfo> login(@RequestBody LoginAuth auth,HttpServletResponse response) {

		StatusInfo statusInfo = new StatusInfo();
		
		if(auth == null || auth.getPasswd() == null || auth.getPasswd().equals("")) {
			statusInfo.setStatusInfo("error <- password");
			return new ResponseEntity<StatusInfo>(statusInfo,HttpStatus.OK);
		}
		
		if(auth.getEmail() != null && !auth.getEmail().equals("")) {
			return loginByEmail(auth, response);
		}else if(auth.getUsernm() != null && !auth.getUsernm().equals("")) {
			return loginByUserName(auth, response);
		}else {
			statusInfo.setStatusInfo("error <- void");
			return new ResponseEntity<StatusInfo>(statusInfo,HttpStatus.OK);
		}
		
	}
	
	
	
	@RequestMapping(value="/loginAuthByUserName",method=RequestMethod.POST)
	public ResponseEntity<StatusInfo> loginByUserName(@RequestBody LoginAuth auth,HttpServletResponse response) {

		StatusInfo statusInfo = new StatusInfo();
		
		if(auth.getPasswd() == null || auth.getUsernm() == null) {
			
			statusInfo.setStatusInfo("error <- param error");
			return new ResponseEntity<StatusInfo>(statusInfo,HttpStatus.OK);
		}
		
		User user = userMapper.findUserByUserName(auth.getUsernm());
		
		if(user != null && user.getPasswdMD5().equals(DigestUtils.md5Hex(auth.getPasswd()))) {			
			Cookie userIdCookie = new Cookie("userId", user.getId());
			Cookie userAuthCookie = new Cookie("userAuth",user.getAuthId());
			
			userIdCookie.setMaxAge(60*15); //设定 cookie 过期时间 15 分钟
			userAuthCookie.setMaxAge(60*15); //设定 cookie 过期时间 15 分钟
			response.addCookie(userIdCookie);
			response.addCookie(userAuthCookie);
			
			statusInfo.setStatusInfo("success <- user:" + user.getId());
			return new ResponseEntity<StatusInfo>(statusInfo,HttpStatus.OK);
		}else {
			statusInfo.setStatusInfo("error <- error data");
			return new ResponseEntity<StatusInfo>(statusInfo,HttpStatus.OK);
		}	
	}
	
	@RequestMapping(value="/loginAuthByEmail",method=RequestMethod.POST)
	public ResponseEntity<StatusInfo> loginByEmail(@RequestBody LoginAuth auth,HttpServletResponse response) {

		StatusInfo statusInfo = new StatusInfo();
		
		if(auth.getPasswd() == null || auth.getEmail() == null) {
			statusInfo.setStatusInfo("error <- param error");
			return new ResponseEntity<StatusInfo>(statusInfo,HttpStatus.OK);
		}
		
		User user = userMapper.findUserByEmail(auth.getEmail());
		
		if(user != null && user.getPasswdMD5().equals(DigestUtils.md5Hex(auth.getPasswd()))) {			
			Cookie userIdCookie = new Cookie("userId", user.getId());
			Cookie userAuthCookie = new Cookie("userAuth",user.getAuthId());
			
			userIdCookie.setMaxAge(60*15); //设定 cookie 过期时间 15 分钟
			userAuthCookie.setMaxAge(60*15); //设定 cookie 过期时间 15 分钟
			response.addCookie(userIdCookie);
			response.addCookie(userAuthCookie);
			
			statusInfo.setStatusInfo("success <- user:" + user.getId());
			return new ResponseEntity<StatusInfo>(statusInfo,HttpStatus.OK);
		}else {
			statusInfo.setStatusInfo("error <- error data");
			return new ResponseEntity<StatusInfo>(statusInfo,HttpStatus.OK);
		}
		
	}
	
	@RequestMapping(value="/logout")
	public void logout(HttpServletResponse response) {
			Cookie userIdCookie = new Cookie("userId","");
			Cookie userAuthCookie = new Cookie("userAuth","");
			
			response.addCookie(userIdCookie);
			response.addCookie(userAuthCookie);
	}

	@RequestMapping(value="/getPostOfUser",method=RequestMethod.POST)
	public ResponseEntity<List<Post>> getPostOfUser(@RequestParam String userid) {
		
		List<Post> posts = postMapper.findPostsByUserID(userid);
		
		return new ResponseEntity<List<Post>>(posts,HttpStatus.OK);
		
	}
		
	@RequestMapping(value="/getPostOfSuperUser")
	public ResponseEntity<List<Post>> getPostOfSuperUser() {
		
		List<Post> posts = new ArrayList<>();
		
		List<User> users = userMapper.findAllUser();
		
		if(users == null) {
			return new ResponseEntity<List<Post>>(posts,HttpStatus.OK);
		}
		
		posts = postMapper.findPostsByUserID(users.get(0).getId());
		
		return new ResponseEntity<List<Post>>(posts,HttpStatus.OK);
		
	}
	
	//---------------
	
	//更新已注册用户信息 
	@RequestMapping(value="/updateUser",method=RequestMethod.POST)
	public ResponseEntity<StatusInfo> updateUser(@RequestBody User user) {
		StatusInfo statusInfo = new StatusInfo();
		//if(can not found from user.userid) return status: can not found userid;
		//else update by userid; return success;
			//查询数据库 根据 user.id
			//根据 id 更新 user 内容
	return new ResponseEntity<StatusInfo>(statusInfo,HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/searchAtom",method=RequestMethod.POST)
	public ResponseEntity<List<SearchAtom>> ajaxSearch(@RequestBody SearchAtom searchAtom){
		//取得 input 中的 query
		//模糊搜索 。。
		List<SearchAtom> returnSearchAtomList = new ArrayList<>();
		
		return new ResponseEntity<List<SearchAtom>>(returnSearchAtomList, HttpStatus.OK);
	}
	
	@RequestMapping(value="/search",method=RequestMethod.POST)
	public ResponseEntity<List<Post>> Search(@RequestBody SearchAtom searchAtom){
		//做一次搜索，返回post
		List<Post> returnSearchAtomList = new ArrayList<>();
		return new ResponseEntity<List<Post>>(returnSearchAtomList, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/ajaxPost", method = RequestMethod.POST)
	public ResponseEntity<List<Post>> ajaxPost(@RequestBody Post post) {
		
		List<Post> returnPostList = new ArrayList<>();
	    return new ResponseEntity<List<Post>>(returnPostList, HttpStatus.OK);
	}
	
}
