package com.bamboovir.easyblog.storage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.bamboovir.easyblog.mapper.ImageMapper;
import com.bamboovir.easyblog.mapper.PostMapper;
import com.bamboovir.easyblog.mapper.UnAuthUserMapper;
import com.bamboovir.easyblog.mapper.UserMapper;
import com.bamboovir.easyblog.markdown.MarkdownUtil;
import com.bamboovir.easyblog.model.Image;
import com.bamboovir.easyblog.model.Post;
import com.mysql.cj.jdbc.jmx.LoadBalanceConnectionGroupManager;

@Service
public class FileSystemStorageService implements StorageService {

    private final Path rootLocation;
    private final Path rootPicLocation;
    private final Path rootTempLocation;
    
    @Autowired
    public FileSystemStorageService(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
        this.rootPicLocation = Paths.get(properties.getPicLocation());
        this.rootTempLocation = Paths.get(properties.getTempLocation());
    }
    
    @Autowired
	private UserMapper userMapper;
	
	@Autowired
	private PostMapper postMapper;
	
	@Autowired
	private UnAuthUserMapper unAuthUserMapper;
	
	@Autowired
	private ImageMapper imageMapper;
	
	@Override
	public void storePic(MultipartFile file, Image image) {
		String filename = image.getImageId()+".jpg";
		
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + filename);
            }
            
            if (filename.contains("..")) {
                // This is a security check
                throw new StorageException(
                        "Cannot store file with relative path outside current directory "
                                + filename);
            }
            
            Files.copy(file.getInputStream(), this.rootPicLocation.resolve(filename),
                    StandardCopyOption.REPLACE_EXISTING);
            
            imageMapper.insertImage(image.getImageId(), image.getImageType(), image.getUserPostBoardId(), image.getUrl()); 
        }
        catch (IOException e) {
            throw new StorageException("Failed to store file " + filename, e);
        }
		
	}

    @Override
    public void store(MultipartFile file, Post post) {
    	
    	String filenameTest = StringUtils.cleanPath(file.getOriginalFilename());
    	
    	if((!filenameTest.contains(".html")) && (!filenameTest.contains(".htm") && (!filenameTest.contains(".md")))) {
    		System.out.println("不属于提交格式");
    		return;
    	}
    	
    	if(filenameTest.contains(".htm")) {
    		String filename = post.getUserId() + "-" + post.getPostId() + ".html";
            
            try {
                if (file.isEmpty()) {
                    throw new StorageException("Failed to store empty file " + filename);
                }
                
                if (filename.contains("..")) {
                    // This is a security check
                    throw new StorageException(
                            "Cannot store file with relative path outside current directory "
                                    + filename);
                }
                
                Files.copy(file.getInputStream(), this.rootLocation.resolve(filename),
                        StandardCopyOption.REPLACE_EXISTING);
                
                postMapper.insertPost(post.getPostId(), 
                		post.getUserId(),
                		post.getPostName(),
                		post.getPostTime(),
                		post.getPicNum(),
                		post.getIsPrivate(),
                		post.getDescription(),
                		post.getUrl());
            }
            catch (IOException e) {
                throw new StorageException("Failed to store file " + filename, e);
            }
    	} else if(filenameTest.contains(".md")){
    		
    		String filename = post.getUserId() + "-" + post.getPostId() + ".html";
            
            try {
                if (file.isEmpty()) {
                    throw new StorageException("Failed to store empty file " + filename);
                }
                
                if (filename.contains("..")) {
                    // This is a security check
                    throw new StorageException(
                            "Cannot store file with relative path outside current directory "
                                    + filename);
                }
                
                MarkdownUtil markdownUtil = new MarkdownUtil();
                markdownUtil.utilFile(file, this.rootLocation.resolve(filename).toString());
                postMapper.insertPost(post.getPostId(), 
                		post.getUserId(),
                		post.getPostName(),
                		post.getPostTime(),
                		post.getPicNum(),
                		post.getIsPrivate(),
                		post.getDescription(),
                		post.getUrl());
            }
            catch (IOException e) {
                throw new StorageException("Failed to store file " + filename, e);
            }
    	}
    	
    }

	@Override
	public Path loadPic(String filename) {
		return rootPicLocation.resolve(filename);
	}
	
    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }
    

    @Override
	public Resource loadAsPicResource(Image image) {
    	
		String filename = image.getImageId()+".jpg";
		
		try {
			Path file = loadPic(filename);
			System.out.println(file.toUri());
			Resource resource = new UrlResource(file.toUri());
            System.out.println(resource.exists());
            System.out.println(resource.isReadable());
            
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageFileNotFoundException(
                        "Could not read file: " + filename);
            }
            
		}catch (Exception e) {
			throw new StorageFileNotFoundException("Could not read file: " + filename, e);
		}
	}
    
    @Override
    public Resource loadAsResource(Post post) {
    	String filename = post.getUserId() + "-" + post.getPostId() + ".html";
    	
        try {
            Path file = load(filename);
            System.out.println(file.toUri());
            Resource resource = new UrlResource(file.toUri());
            System.out.println(resource.exists());
            System.out.println(resource.isReadable());
            
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageFileNotFoundException(
                        "Could not read file: " + filename);
            }
        }
        catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }
    

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
        FileSystemUtils.deleteRecursively(rootPicLocation.toFile());
        FileSystemUtils.deleteRecursively(rootTempLocation.toFile());
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
            Files.createDirectories(rootPicLocation);
            Files.createDirectories(rootTempLocation);
        }
        catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }

}

