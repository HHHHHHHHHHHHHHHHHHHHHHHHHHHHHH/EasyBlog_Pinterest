package com.bamboovir.easyblog.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.bamboovir.easyblog.model.Board;


@Mapper
public interface BoardMapper {
	
	@Update("CREATE TABLE `easyblog`.`board` (\r\n" + 
			"  `boardId` VARCHAR(100) NOT NULL,\r\n" + 
			"  `userId` VARCHAR(100) NOT NULL,\r\n" + 
			"  `createdAt` BIGINT UNSIGNED NOT NULL,\r\n" + 
			"  `boardName` VARCHAR(100) NOT NULL,\r\n" +  
			"  `discription` VARCHAR(100) NOT NULL,\r\n" + 
			"  `coverPic` VARCHAR(100) NOT NULL,\r\n" +  
			"  `postNum` INTEGER NOT NULL,\r\n" + 
			"  `isPrivate` BOOLEAN NOT NULL,\r\n" +
			"  `url` VARCHAR(100) NOT NULL,\r\n" + 
			"  PRIMARY KEY (`boardId`)\r\n" + 
			")\r\n" + 
			"ENGINE = InnoDB;")
	void createBoardTable();
	
	@Update("drop table board;")
	void deleteBoardTable();

	@Insert("insert into board(postid,posttime,userid,logname,url,picsurl,avatarurl,tags,picnum) values("
			+ "#{postid},#{posttime},#{userid},#{logname},#{url},#{picsurl},#{avatarurl},#{tags},#{picnum},#{isPrivate});")
	void insertBoard(@Param("postid")String postid,
			@Param("posttime")Long posttime,
			@Param("userid")String userid,
			@Param("logname")String logname,
			@Param("url")String url,
			@Param("picsurl")String picsurl,
			@Param("avatarurl")String avatarurl,
			@Param("tags")String tags,
			@Param("picnum")Integer picnum,
			@Param("isPrivate")Boolean isprivate);

	@Delete("delete FROM post where postid=#{postid}")
	void deleteBoardByID(@Param("postid") String postid);
	
	@Delete("delete FROM post where 1 = 1;")
	void deleteAllBoard();
	
	@Delete("delete FROM post where userid=#{userid}")
	void deleteByID(@Param("userid") String userid);
	
	@Select("select * from post where userid=#{userid}")
	List<Board> findBoardByUserID(@Param("userid") String userid);
	
	@Select("select * from post where postid=#{postid}")
	Board findBoardByID(@Param("postid") String postid);
}