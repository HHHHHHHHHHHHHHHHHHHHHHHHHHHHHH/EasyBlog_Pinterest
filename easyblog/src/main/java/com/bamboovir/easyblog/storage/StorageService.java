package com.bamboovir.easyblog.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.bamboovir.easyblog.model.Post;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {

	void init();
	
    void deleteAll();
    //------------------
    // Post : html or markdown;
    void store(MultipartFile file, Post post);

    Path load(String filename);

    Resource loadAsResource(Post post);
    
    //------------------
    // Pic : Pics
    void storePic(MultipartFile file, Post post);
    
    Path loadPic(String filename);
    
    Resource loadAsPicResource(Post post,Integer index);
    
}
