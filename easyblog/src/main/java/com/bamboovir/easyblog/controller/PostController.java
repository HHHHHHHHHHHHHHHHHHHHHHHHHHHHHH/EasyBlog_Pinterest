package com.bamboovir.easyblog.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ExceptionHandler;
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

import com.bamboovir.easyblog.email.JavaEmail;
import com.bamboovir.easyblog.mapper.BoardMapper;
import com.bamboovir.easyblog.mapper.ImageMapper;
import com.bamboovir.easyblog.mapper.PostMapper;
import com.bamboovir.easyblog.mapper.TagMapper;
import com.bamboovir.easyblog.mapper.UnAuthUserMapper;
import com.bamboovir.easyblog.mapper.UserMapper;
import com.bamboovir.easyblog.model.LoginAuth;
import com.bamboovir.easyblog.model.Post;
import com.bamboovir.easyblog.model.SearchAtom;
import com.bamboovir.easyblog.model.StatusInfo;
import com.bamboovir.easyblog.model.User;
import com.bamboovir.easyblog.storage.StorageFileNotFoundException;
import com.bamboovir.easyblog.storage.StorageService;

@RestController
@RequestMapping("/post")
public class PostController {
	
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
	
    private final StorageService storageService;

    @Autowired
    public PostController(StorageService storageService) {
        this.storageService = storageService;
    }
    
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
	
	//获取 post 文章映射
	@GetMapping(value="/{userid}/{postid}")
	@ResponseBody
	public Resource getPostContent(@PathVariable("userid") String userid,@PathVariable("postid") String postid) throws IOException {
		Post post = new Post();
		post.setPostId(postid);
		post.setUserId(userid);
		Resource postResource = storageService.loadAsResource(post);
		return postResource;
	}
	
	//创建一个Post
	@PostMapping("/upload")
	public ResponseEntity<StatusInfo> handlePostUpload(@RequestParam("file") MultipartFile file,@CookieValue(value="userId",defaultValue="") String userId,
		    @CookieValue(value="userAuth",defaultValue="") String userAuth ) {
			StatusInfo statusInfo = new StatusInfo();
			
				if(Auth(userId, userAuth)) {
			    Post newPost = new Post();
			    Date now = new Date();      
				Long nowLongTime = new Long(now.getTime()/1000);
				String postId = UUID.randomUUID().toString();
				newPost.setPostId(postId);
				newPost.setUserId(userId);
				newPost.setPostTime(nowLongTime);
				newPost.setUrl("/post/"+userId+"/"+postId+".html");
				
		        storageService.store(file,newPost);
		        
		        statusInfo.setStatusInfo("success <- create post:" + newPost.getPostId());
		        return new ResponseEntity<StatusInfo>(statusInfo,HttpStatus.OK);

				}else {
					statusInfo.setStatusInfo("error");
					return new ResponseEntity<StatusInfo>(statusInfo,HttpStatus.OK);

				}
		}
	
	//更新一个Post的原信息
	@PostMapping(value="/update")
	public ResponseEntity<StatusInfo> updatePostMeta(@RequestBody Post post, 
			@CookieValue(value="userId",defaultValue="") String userId,
		    @CookieValue(value="userAuth",defaultValue="") String userAuth ) {
		StatusInfo statusInfo = new StatusInfo();
		
		statusInfo.setStatusInfo("error <- Access Denied");
		return new ResponseEntity<StatusInfo>(statusInfo,HttpStatus.OK);
	}
	
	//删除 一个 User 的 全部 Post
	@RequestMapping(value="/deletePostOfUser",method=RequestMethod.POST)
	public ResponseEntity<StatusInfo> deletePostOfUser(@RequestParam String userid,
				@CookieValue(value="userId",defaultValue="") String userId,
			    @CookieValue(value="userAuth",defaultValue="") String userAuth) {
			StatusInfo statusInfo = new StatusInfo();
			
			if(Auth(userId, userAuth) && (userId.equals(userid))) {
			postMapper.deleteByUserID(userid);
			// 在文件系统中删除 html ！！！
			statusInfo.setStatusInfo("success <- delete");
			return new ResponseEntity<StatusInfo>(statusInfo,HttpStatus.OK);
			}else {
				statusInfo.setStatusInfo("error <- Access Denied");
				return new ResponseEntity<StatusInfo>(statusInfo,HttpStatus.OK);
			}
		}
		
	//根据 Post id 删除 Post
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	public ResponseEntity<StatusInfo> deletePost(@RequestBody Post post,
			@CookieValue(value="userId",defaultValue="") String userId,
			@CookieValue(value="userAuth",defaultValue="") String userAuth ) {
			
			StatusInfo statusInfo = new StatusInfo();
			
			if(Auth(userId, userAuth) && (postMapper.findPostByID(post.getPostId()).getUserId().equals(userId))) {
				postMapper.deletePostByID(post.getPostId());// 在数据库中删除 post
				// 在文件系统中删除 html !!!!
				statusInfo.setStatusInfo("success <- delete post:" + post.getPostId());
				return new ResponseEntity<StatusInfo>(statusInfo,HttpStatus.OK);
			}else {
				statusInfo.setStatusInfo("error <- Access Denied");
				return new ResponseEntity<StatusInfo>(statusInfo,HttpStatus.OK);
			}
		}
	
	
	@ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }
	
}
