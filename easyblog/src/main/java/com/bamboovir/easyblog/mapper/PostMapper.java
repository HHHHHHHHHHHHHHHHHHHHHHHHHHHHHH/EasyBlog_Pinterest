package com.bamboovir.easyblog.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.bamboovir.easyblog.model.Post;
import com.fasterxml.jackson.annotation.JsonProperty;

@Mapper
public interface PostMapper {
    
	@Update("CREATE TABLE `easyblog`.`post` (\r\n" + 
			"  `postId` VARCHAR(100) NOT NULL,\r\n" + 
			"  `userId` VARCHAR(100) NOT NULL,\r\n" + 
			"  `postName` VARCHAR(100) NOT NULL,\r\n" + 
			"  `postTime` BIGINT UNSIGNED NOT NULL,\r\n" + 
			"  `picNum` INTEGER NOT NULL,\r\n" + 
			"  `isPrivate` BOOLEAN NOT NULL,\r\n" + 
			"  `description` VARCHAR(100) NOT NULL,\r\n" + 
			"  `url` VARCHAR(100) NOT NULL,\r\n" + 
			"  PRIMARY KEY (`postId`)\r\n" + 
			")\r\n" + 
			"ENGINE = InnoDB;")
	void createPostTable();
	
	@Update("drop table post;")
	void deletePostTable();

	@Insert("insert into post(postId,userId,postName,postTime,picNum,isPrivate,description,url) values("
			+ "#{postId},#{userId},#{postName},#{postTime},#{picNum},#{isPrivate},#{description},#{url});")
	void insertPost(@Param("postId")String postId,
			@Param("userId")String userId,
			@Param("postName")String postName,
			@Param("postTime")Long postTime,
			@Param("picNum")Integer picNum,
			@Param("isPrivate")Boolean isPrivate,
			@Param("description")String description,
			@Param("url")String url);

	@Delete("delete FROM post where postId=#{postId}")
	void deletePostByID(@Param("postId") String postId);
	
	@Delete("delete FROM post where 1 = 1;")
	void deleteAllPost();
	
	@Delete("delete FROM post where userId=#{userId}")
	void deleteByUserID(@Param("userId") String userId);
	
	@Select("select * from post where userId=#{userId}")
	List<Post> findPostsByUserID(@Param("userId") String userId);
	
	@Select("select * from post where postid=#{postId}")
	Post findPostByID(@Param("postId") String postId);
	
}
