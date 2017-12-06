package com.bamboovir.easyblog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import com.bamboovir.easyblog.mapper.BoardMapper;
import com.bamboovir.easyblog.mapper.ImageMapper;
import com.bamboovir.easyblog.mapper.PostMapper;
import com.bamboovir.easyblog.mapper.TagMapper;
import com.bamboovir.easyblog.mapper.UnAuthUserMapper;
import com.bamboovir.easyblog.mapper.UserMapper;
import com.bamboovir.easyblog.storage.StorageProperties;
import com.bamboovir.easyblog.storage.StorageService;

@SpringBootApplication
@MapperScan("com.bamboovir.easyblog.mapper")
@EnableConfigurationProperties(StorageProperties.class)
class EasyblogApplication {

	public static void main(String[] args) throws InterruptedException{
		SpringApplication.run(EasyblogApplication.class, args);
		
		//SpringApplication.run(EasyblogApplication.class, args);
		
		/*
		while(true){
	        Sender sender = app.getBean(Sender.class);
	        sender.sendMessage();
	        try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    */
	}
	
	@Bean
    CommandLineRunner init(StorageService storageService,
    		UserMapper userMapper,
    		UnAuthUserMapper unAuthUserMapper,
    		PostMapper postMapper,
    		BoardMapper boardMapper,
    		ImageMapper imageMapper,
    		TagMapper tagMapper) {
		
        return (args) -> {
            storageService.deleteAll(); // 删除旧存储
            storageService.init(); // 初始化新存储
            
            
            // 在 easyblog库下创建需要的6个表
            userMapper.createUserTable(); 
            unAuthUserMapper.createUnAuthUserTable();
            postMapper.createPostTable();
            boardMapper.createBoardTable();
            imageMapper.createImageTable();
            tagMapper.createTagTable();
            
            /*
            // 删除创建的6个表
            userMapper.deleteUserTable();;
            unAuthUserMapper.deleteUnAuthUserTable();;
            postMapper.deletePostTable();
            boardMapper.deleteBoardTable();
            imageMapper.deleteImageTable();
            tagMapper.deleteTagTable();
            */
        };
    }
}
