package com.bamboovir.easyblog.controller;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import com.bamboovir.easyblog.mapper.BehaviorMapper;
import com.bamboovir.easyblog.mapper.BoardMapper;
import com.bamboovir.easyblog.mapper.ImageMapper;
import com.bamboovir.easyblog.mapper.PostMapper;
import com.bamboovir.easyblog.mapper.TagMapper;
import com.bamboovir.easyblog.mapper.UserMapper;
import com.bamboovir.easyblog.model.Board;
import com.bamboovir.easyblog.model.Image;
import com.bamboovir.easyblog.model.ImageType;
import com.bamboovir.easyblog.model.Post;
import com.bamboovir.easyblog.model.StatusInfo;
import com.bamboovir.easyblog.model.TagType;
import com.bamboovir.easyblog.model.User;
import com.bamboovir.easyblog.storage.StorageFileNotFoundException;
import com.bamboovir.easyblog.storage.StorageService;
import com.openhtmltopdf.util.IOUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "Image Service" , description = "Provide Image Service")
@RestController
@RequestMapping("/img")
public class ImageController {
	
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
    public ImageController(StorageService storageService) {
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
	
	@ApiOperation("Get Image Resource")
	@GetMapping(value="/{imageId}", produces = MediaType.IMAGE_JPEG_VALUE)
	@ResponseBody
	public ResponseEntity<InputStreamResource> getImageContent(@PathVariable("imageId") String imageId) throws IOException {
			Image image = new Image();
			image.setImageId(imageId);
			Resource postResource = storageService.loadAsPicResource(image);
			return ResponseEntity.ok()
					.contentLength(postResource.contentLength())
					.body(new InputStreamResource(postResource.getInputStream()));
	}
		
	@ApiOperation("Upload Image")
	@PostMapping("/upload")
	public ResponseEntity<StatusInfo> handlePostUpload(@RequestParam("file") MultipartFile file,
			@RequestParam("userPostBoardId") String userPostBoardId,
			@RequestParam("imageType") String imageType,
			@CookieValue(value="userId",defaultValue="") String userId,
			    @CookieValue(value="userAuth",defaultValue="") String userAuth) {
			
				StatusInfo statusInfo = new StatusInfo();
				
					if(Auth(userId, userAuth)){
						
					Image image = new Image();
					String imageId = UUID.randomUUID().toString();
					image.setImageId(imageId);
					image.setImageType(imageType);
					image.setUserPostBoardId(userPostBoardId);
					
					image.setUrl("/img/"+imageId);
			        storageService.storePic(file, image);
			        
			        statusInfo.setStatusInfo("success <- upload Image : " + image.getImageId());
			        return new ResponseEntity<StatusInfo>(statusInfo,HttpStatus.OK);

					}else {
						statusInfo.setStatusInfo("error <- void");
						return new ResponseEntity<StatusInfo>(statusInfo,HttpStatus.OK);
					}
	}
	
	@ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }
}
