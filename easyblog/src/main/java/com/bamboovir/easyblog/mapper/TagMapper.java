package com.bamboovir.easyblog.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface TagMapper {
	
	@Update("CREATE TABLE `easyblog`.`tag` (\r\n" + 
			"  `tagId` VARCHAR(100) NOT NULL,\r\n" + 
			"  `tagName` VARCHAR(100) NOT NULL,\r\n" + 
			"  `userPostBoardId` VARCHAR(100) NOT NULL,\r\n" +
			"  `type` VARCHAR(100) NOT NULL,\r\n" + 
			"  PRIMARY KEY (`tagId`)\r\n" + 
			")\r\n" + 
			"ENGINE = InnoDB;")
	void createTagTable();
	
	@Update("drop table tag;")
	void deleteTagTable();

	@Insert("insert into tag(tagId,tagName,userPostBoardId,type) values("
			+ "#{tagId},#{tagName},#{userPostBoardId},#{type});")
	void insertTag(@Param("tagId")String tagId,
			@Param("tagName")String tagName,
			@Param("userPostBoardId")String userPostBoardId,
			@Param("type")String type,
			@Param("url")String url);
	
	@Delete("delete FROM tag where tagId=#{tagId}")
	void deleteTagByID(@Param("tagId") String tagId);
	
	@Delete("delete FROM tag where 1 = 1;")
	void deleteAllTag();
	
	@Delete("delete FROM tag where userPostBoardId=#{userPostBoardId} AND type=#{type}")
	void deleteTagByUserPostBoardIDAndType(@Param("userPostBoardId") String userPostBoardId ,@Param("type") String type);
	
	@Select("select tagName from tag where userPostBoardID=#{userPostBoardId} AND type=#{type}")
	List<String> findTagsByUserPostBoardIDAndType(@Param("userPostBoardId") String userPostBoardId ,@Param("type") String type);
	
	@Select("select tagName from tag where tagId=#{tagId}")
	String  findTagByID(@Param("tagId") String tagId);
	
}
