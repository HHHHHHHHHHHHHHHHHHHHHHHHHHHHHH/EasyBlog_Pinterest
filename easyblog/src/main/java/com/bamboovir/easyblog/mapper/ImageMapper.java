package com.bamboovir.easyblog.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ImageMapper {
	
	@Update("CREATE TABLE `easyblog`.`image` (\r\n" + 
			"  `imageId` VARCHAR(100) NOT NULL,\r\n" + 
			"  `imageType` VARCHAR(100) NOT NULL,\r\n" + 
			"  `userPostBoardId` VARCHAR(100) NOT NULL,\r\n" + 
			"  `url` VARCHAR(100) NOT NULL,\r\n" + 
			"  PRIMARY KEY (`imageId`)\r\n" + 
			")\r\n" + 
			"ENGINE = InnoDB;")
	void createImageTable();
	
	@Update("drop table image;")
	void deleteImageTable();

	@Insert("insert into image(imageId,imageType,userPostBoardId,url) values("
			+ "#{imageId},#{imageType},#{userPostBoardId},#{url});")
	void insertImage(@Param("imageId")String imageId,
			@Param("imageType")String imageType,
			@Param("userPostBoardId")String userPostBoardId,
			@Param("url")String url);

	@Delete("delete FROM image where imageId=#{imageId}")
	void deleteImageByID(@Param("imageId") String imageId);
	
	@Delete("delete FROM image where 1 = 1;")
	void deleteAllImage();
	
	@Select("select url from image where userPostBoardId=#{userPostBoardId} AND imageType=#{imageType}")
	List<String> findImageByIDAndType(@Param("userPostBoardId") String userPostBoardId , @Param("imageType") String imageType);
	
	@Select("select url from image where imageId=#{imageId}")
	String findImageByID(@Param("imageId") String imageId);
	
}