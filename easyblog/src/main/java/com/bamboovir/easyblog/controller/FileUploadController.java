package com.bamboovir.easyblog.controller;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bamboovir.easyblog.mapper.PostMapper;
import com.bamboovir.easyblog.mapper.UnAuthUserMapper;
import com.bamboovir.easyblog.mapper.UserMapper;
import com.bamboovir.easyblog.model.Post;
import com.bamboovir.easyblog.model.StatusInfo;
import com.bamboovir.easyblog.model.User;
import com.bamboovir.easyblog.storage.StorageFileNotFoundException;
import com.bamboovir.easyblog.storage.StorageService;


@Controller
public class FileUploadController {
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private PostMapper postMapper;
	
	@Autowired
	private UnAuthUserMapper unAuthUserMapper;
	
    private final StorageService storageService;

    @Autowired
    public FileUploadController(StorageService storageService) {
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
	
    //--------
	
	//查看对于单个post中的图片集的映射
	@GetMapping(value="/pic/{userid}/{postid}/{index}")
	@ResponseBody
	public Resource getPostPics(@PathVariable("userid") String userid,@PathVariable("postid") String postid,@PathVariable("index") Integer index) {
		Post post = new Post();
		post.setPostId(postid);
		post.setUserId(userid);
		Resource picResource = storageService.loadAsPicResource(post,index);
		return picResource;
	}	
	
	@RequestMapping(value="/uploadUserPic",method=RequestMethod.POST)
	public ResponseEntity<StatusInfo> uploadUserPic(@RequestParam("postid") String postid , @CookieValue(value="userId",defaultValue="") String userId,
		    @CookieValue(value="userAuth",defaultValue="") String userAuth) {
		
		StatusInfo statusInfo = new StatusInfo();
		
		if(Auth(userId, userAuth) && postMapper.findPostByID(postid).getUserId().equals(userId)){
			Post post = postMapper.findPostByID(postid);
			post.setPicsUrl("/pic/"+userId+"/"+postid);
			
	        storageService.store(file,newPost);
	        
	        statusInfo.setStatusInfo("success <- create post:" + newPost.getPostId());
	        return new ResponseEntity<StatusInfo>(statusInfo,HttpStatus.OK);

			}else {
				statusInfo.setStatusInfo("error");
				return new ResponseEntity<StatusInfo>(statusInfo,HttpStatus.OK);

			}
			
	return  new ResponseEntity<StatusInfo>(statusInfo,HttpStatus.OK);
	}
	
	//查看 post 文章映射
	@GetMapping(value="/post/{userid}/{postid}")
	@ResponseBody
	public Resource getPostContent(@PathVariable("userid") String userid,@PathVariable("postid") String postid) throws IOException {
		Post post = new Post();
		post.setPostId(postid);
		post.setUserId(userid);
		Resource postResource = storageService.loadAsResource(post);
		
		return postResource;
	}
	
	//上传一篇 post
	@PostMapping("/uploadPost")
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
			newPost.setAvatarUrl(userMapper.findUserByID(userId).getImageSmallUrl());
			newPost.setUrl("/post/"+userId+"/"+postId+".html");
			
	        storageService.store(file,newPost);
	        
	        statusInfo.setStatusInfo("success <- create post:" + newPost.getPostId());
	        return new ResponseEntity<StatusInfo>(statusInfo,HttpStatus.OK);

			}else {
				statusInfo.setStatusInfo("error");
				return new ResponseEntity<StatusInfo>(statusInfo,HttpStatus.OK);

			}
	}
	
	//上传区的静态网页
	@GetMapping("/uploadPost")
    public String listUploadedFiles(Model model) throws IOException {        
        return "/uploadForm.html";
    }
	
    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }
    
    
	//---------
		
	

	/*
    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
    
    @GetMapping("/file/{filename:.+}")
    @ResponseBody
    public Resource servewFile(@PathVariable String filename) {
    	Resource file = storageService.loadAsResource(filename);
        return file;
    }
     
    @PostMapping("/up")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
    		@RequestParam("postinfo") String postinfo,
            RedirectAttributes redirectAttributes) {

        storageService.store(file,new Post());
        System.out.println(postinfo);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/up";
    }
    */
	
}
