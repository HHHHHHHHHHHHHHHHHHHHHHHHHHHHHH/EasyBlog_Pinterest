package com.bamboovir.easyblog.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import com.bamboovir.easyblog.model.Behavior;

@Mapper
public interface BehaviorMapper {
    
	@Update("CREATE TABLE `easyblog`.`behavior` (\r\n" + 
			"  `behaviorId` VARCHAR(100) NOT NULL,\r\n" + 
			"  `masterId` VARCHAR(100) NOT NULL,\r\n" + 
			"  `slaveId` VARCHAR(100) NOT NULL,\r\n" + 
			"  `behaviorType` VARCHAR(100) NOT NULL,\r\n" + 
			"  `behaviorTime` BIGINT UNSIGNED NOT NULL,\r\n" + 
			"  PRIMARY KEY (`behaviorId`)\r\n" + 
			")\r\n" + 
			"ENGINE = InnoDB;")
	void createBehaviorTable();
	
	@Update("drop table behavior;")
	void deleteBehaviorTable();

	@Insert("insert into behavior (behaviorId,masterId,slaveId,behaviorType,behaviorTime) values("
			+ "#{behaviorId},#{masterId},#{slaveId},#{behaviorType},#{behaviorTime});")
	void insertBehavior(@Param("behaviorId")String behaviorId,
			@Param("masterId")String masterId,
			@Param("slaveId")String slaveId,
			@Param("behaviorType")String behaviorType,
			@Param("behaviorTime")Long behaviorTime);

	@Delete("delete FROM behavior where behaviorId=#{behaviorId}")
	void deleteBehaviorByID(@Param("behaviorId") String behaviorId);
	
	@Delete("delete FROM behavior where 1 = 1;")
	void deleteAllBehavior();
	
	@Select("select * from behavior where masterId=#{masterId} AND behaviorType=#{behaviorType}")
	List<Behavior> findBehaviorByMasterIDAndBehaviorType(@Param("masterId") String masterId,@Param("behaviorType") String behaviorType);
	
	@Select("select * from behavior where slaveId=#{slaveId} AND behaviorType=#{behaviorType}")
	List<Behavior> findBehaviorBySlaveIDAndBehaviorType(@Param("slaveId") String slaveId,@Param("behaviorType") String behaviorType);
	
	@Select("select * from behavior where behaviorId=#{behaviorId}")
	Behavior findBehaviorByID(@Param("behaviorId") String behaviorId);
	
}
