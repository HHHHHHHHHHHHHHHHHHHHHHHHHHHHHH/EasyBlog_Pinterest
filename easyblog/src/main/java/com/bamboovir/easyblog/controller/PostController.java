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
import com.bamboovir.easyblog.mapper.BehaviorMapper;
import com.bamboovir.easyblog.mapper.BoardMapper;
import com.bamboovir.easyblog.mapper.ImageMapper;
import com.bamboovir.easyblog.mapper.PostMapper;
import com.bamboovir.easyblog.mapper.TagMapper;
import com.bamboovir.easyblog.mapper.UnAuthUserMapper;
import com.bamboovir.easyblog.mapper.UserMapper;
import com.bamboovir.easyblog.model.BehaviorType;
import com.bamboovir.easyblog.model.Board;
import com.bamboovir.easyblog.model.LoginAuth;
import com.bamboovir.easyblog.model.Post;
import com.bamboovir.easyblog.model.SearchAtom;
import com.bamboovir.easyblog.model.StatusInfo;
import com.bamboovir.easyblog.model.TagType;
import com.bamboovir.easyblog.model.User;
import com.bamboovir.easyblog.model.UserType;
import com.bamboovir.easyblog.storage.StorageFileNotFoundException;
import com.bamboovir.easyblog.storage.StorageService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/post")
public class PostController {
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private PostMapper postMapper;
	
	@Autowired
	private BoardMapper boardMapper;
	
	@Autowired
	private ImageMapper ImageMapper;
	
	@Autowired
	private TagMapper tagMapper;
	
	@Autowired
	private BehaviorMapper behaviorMapper;
	
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
	@GetMapping(value="/{userId}/{postId}")
	@ResponseBody
	public Resource getPostContent(@PathVariable("userId") String userId,
			@PathVariable("postId") String postId) throws IOException {
		Post post = new Post();
		post.setPostId(postId);
		post.setUserId(userId);
		
		Date now = new Date();
		Long nowLongTime = new Long(now.getTime()/1000);
		String behaviorId = UUID.randomUUID().toString();
		behaviorMapper.insertBehavior(behaviorId,userId,postId, BehaviorType.READPOST.toString(),nowLongTime);
		
		Resource postResource = storageService.loadAsResource(post);
		return postResource;
	}
	
	
	@ApiOperation("创建一个Post")
	@PostMapping("/upload")
	public ResponseEntity<StatusInfo> createPost(@RequestParam("file") MultipartFile file,
			@CookieValue(value="userId",defaultValue="") String userId,
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
		        

				String behaviorId = UUID.randomUUID().toString();
				behaviorMapper.insertBehavior(behaviorId,userId,postId, BehaviorType.CREATEPOST.toString(),nowLongTime);
				
		        statusInfo.setStatusInfo("success <- create post:" + newPost.getPostId());
		        return new ResponseEntity<StatusInfo>(statusInfo,HttpStatus.OK);

				}else {
					statusInfo.setStatusInfo("error");
					return new ResponseEntity<StatusInfo>(statusInfo,HttpStatus.OK);
				}
		}
	
	@ApiOperation("更新一个Post的Meta / 需要登陆")
	@PostMapping(value="/updateMeta")
	public ResponseEntity<StatusInfo> updatePostMeta(@RequestBody Post post, 
			@CookieValue(value="userId",defaultValue="") String userId,
		    @CookieValue(value="userAuth",defaultValue="") String userAuth ) {
		StatusInfo statusInfo = new StatusInfo();
		
		if(Auth(userId, userAuth) && postMapper.findPostByID(post.getPostId()).getUserId().equals(userId)) {
	
			postMapper.updateDescriptionByID(post.getDescription(), post.getPostId());
			postMapper.updateIsPrivateByID(post.getIsPrivate(), post.getPostId());
			postMapper.updatePostNameByID(post.getPostName(), post.getPostId());
			//加入Cover Pic 属性
			
			Date now = new Date();
			Long nowLongTime = new Long(now.getTime()/1000);
			String behaviorId = UUID.randomUUID().toString();
			behaviorMapper.insertBehavior(behaviorId,userId,post.getPostId(), BehaviorType.UPDATEPOST.toString(),nowLongTime);
			
			statusInfo.setStatusInfo("success <- Update : " + post.getPostId());
			return new ResponseEntity<StatusInfo>(statusInfo,HttpStatus.OK);
		}
		
		statusInfo.setStatusInfo("error <- Access Denied");
		return new ResponseEntity<StatusInfo>(statusInfo,HttpStatus.OK);
	}
	
	@ApiOperation("通过PostId获取一个Post的Meta / 不需要登陆")
	@RequestMapping(value = "/getMeta", method = RequestMethod.POST)
	public ResponseEntity<Post> getPostMeta(@RequestBody Post post) {
		
		Post returnPost = null;
		returnPost = postMapper.findPostByID(post.getPostId());
		
		if(returnPost != null && returnPost.getIsPrivate() == Boolean.FALSE) {
			
			Date now = new Date();
			Long nowLongTime = new Long(now.getTime()/1000);
			String behaviorId = UUID.randomUUID().toString();
			behaviorMapper.insertBehavior(behaviorId,UserType.UNLOGINUSER.toString(),post.getPostId(), BehaviorType.GETPOST.toString(),nowLongTime);
			
			return new ResponseEntity<Post>(returnPost, HttpStatus.OK);
		}else {
			returnPost = new Post();
			return new ResponseEntity<Post>(returnPost, HttpStatus.OK);
		}  
	}
		
	@ApiOperation("根据 Post id 删除 Post / 需要登陆")
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	public ResponseEntity<StatusInfo> deletePost(@RequestBody Post post,
			@CookieValue(value="userId",defaultValue="") String userId,
			@CookieValue(value="userAuth",defaultValue="") String userAuth ) {
			
			StatusInfo statusInfo = new StatusInfo();
			
			Post postTemp = postMapper.findPostByID(post.getPostId());
			
			if(Auth(userId, userAuth) && (postTemp != null) && (postTemp.getUserId().equals(userId))) {
				postMapper.deleteByPostID(post.getPostId());
				tagMapper.deleteTagByUserPostBoardIDAndType(post.getPostId(), TagType.POST.toString());;
				// 在文件系统中删除 html !!!!
				
				Date now = new Date();
				Long nowLongTime = new Long(now.getTime()/1000);
				String behaviorId = UUID.randomUUID().toString();
				behaviorMapper.insertBehavior(behaviorId,userId,post.getPostId(), BehaviorType.DELETEPOST.getType(),nowLongTime);
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
