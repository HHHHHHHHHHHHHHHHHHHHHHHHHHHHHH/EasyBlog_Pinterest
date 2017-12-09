package com.bamboovir.easyblog.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface RootMapper {

	@Update("CREATE SCHEMA easyblog;")
	void createRootSchema();
	
	@Delete("DROP SCHEMA easyblog;")
	void deleteRootSchema();
}
