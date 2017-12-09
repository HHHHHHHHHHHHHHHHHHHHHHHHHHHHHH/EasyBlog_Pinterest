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

	@Insert("insert into board(boardId,userId,createdAt,boardName,discription,coverPic,postNum,isPrivate,url) values("
			+ "#{ boardId},#{userId},#{createdAt},#{boardName},#{discription},#{coverPic},#{postNum},#{isPrivate},#{url});")
	void insertBoard(@Param("boardId")String boardId,
			@Param("userId")String userid,
			@Param("createdAt")String createdAt,
			@Param("boardName")String boardName,
			@Param("discription")String discription,
			@Param("coverPic")String coverPic,
			@Param("postNum")Integer picnum,
			@Param("isPrivate")Boolean isprivate,
			@Param("url") String url);
	
	@Update("UPDATE board SET boardName=#{boardName} WHERE boardId=#{boardId}")
	void updateBoardNameByID(@Param("boardName") String boardName , @Param("boardId") String boardId);
	
	@Update("UPDATE board SET userId=#{userId} WHERE boardId=#{boardId}")
	void updateUserIdByID(@Param("userId") String userId , @Param("boardId") String boardId);
	
	@Update("UPDATE board SET discription=#{discription} WHERE boardId=#{boardId}")
	void updateDiscriptionByID(@Param("discription") String discription , @Param("boardId") String boardId);
	
	@Update("UPDATE board SET discription=#{discription} WHERE boardId=#{boardId}")
	void updateBoardIdByID(@Param("discription") String discription , @Param("boardId") String boardId);
	
	@Update("UPDATE board SET coverPic=#{coverPic} WHERE boardId=#{boardId}")
	void updateCoverPicByID(@Param("coverPic") String coverPic , @Param("boardId") String boardId);
	
	@Update("UPDATE board SET postNum=#{postNum} WHERE boardId=#{boardId}")
	void updatePostNumByID(@Param("postNum") String postNum , @Param("boardId") String boardId);
	
	@Update("UPDATE board SET isPrivate=#{isPrivate} WHERE boardId=#{boardId}")
	void updateIsPrivateByID(@Param("isPrivate") Boolean isPrivate , @Param("boardId") String boardId);
	
	@Update("UPDATE board SET url=#{url} WHERE boardId=#{boardId}")
	void updateUrlByID(@Param("url") String url , @Param("boardId") String boardId);
	
	@Delete("delete FROM board where 1 = 1;")
	void deleteAllBoard();
	
	@Delete("delete FROM board where boardId=#{boardId}")
	void deleteByBoardID(@Param("boardId") String boardId);
	
	@Select("select * from board where userId=#{userId} AND isPrivate=#{isPrivate}")
	List<Board> findBoardByUserID(@Param("userId") String userId ,@Param("isPrivate") Boolean isPrivate);
	
	@Select("select * from board where boardId=#{boardId}")
	Board findBoardByBoardID(@Param("boardId") String boardId);
}