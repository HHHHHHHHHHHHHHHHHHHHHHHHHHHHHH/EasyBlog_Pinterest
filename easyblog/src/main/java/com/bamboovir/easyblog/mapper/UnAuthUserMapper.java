package com.bamboovir.easyblog.mapper;


import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.xpath.operations.String;

import com.bamboovir.easyblog.model.User;

@Mapper
public interface UnAuthUserMapper {

	@Insert("insert into unauthuser(id,type,gender,username,fullname,firstname,lastname,"
			+ "email,"
			+ "phonenumber,gplusurl,domainurl,twitterurl,facebookurl,createdat,"
			+ "country,imagemediumurl,imagesmallurl,imagelargeurl,posts,authid,passwdmd5) values"
			+ "(#{id},#{type},#{gender},#{username},#{fullname},#{firstname},#{lastname},#{email},"
			+ "#{phonenumber},#{gplusurl},#{domainurl},#{twitterurl},#{facebookurl},#{createdat},"
			+ "#{country},#{imagemediumurl},#{imagesmallurl},#{imagelargeurl},#{posts},#{authid},#{passwdmd5});")
	void insertUnAuthUser(@Param("id")String id,
			@Param("type")String type,
			@Param("gender")String gender,
			@Param("username")String username,
			@Param("fullname")String fullname,
			@Param("firstname")String firstname,
			@Param("lastname")String lastname,
			@Param("email")String email,
			@Param("phonenumber")String phonenumber,
			@Param("gplusurl")String gplusurl,
			@Param("domainurl")String domainurl,
			@Param("twitterurl")String twitterurl,
			@Param("facebookurl")String facebookurl,
			@Param("createdat")Long createdat,
			@Param("country")String country,
			@Param("imagemediumurl")String imagemediumurl,
			@Param("imagesmallurl")String imagesmailurl,
			@Param("imagelargeurl")String imagelargeurl,
			@Param("posts")Integer posts,
			@Param("authid")String authid,
			@Param("passwdmd5")String passwdmd5);
	
	@Select("select id as id, type as type,gender as gender,username as username,"
			+ "fullname as fullName,firstname as firstName,lastname as lastName,"
			+ "email as email,"
			+ "phonenumber as phoneNumber,gplusurl as gplusUrl,"
			+ "domainurl as domainUrl,twitterurl as twitterUrl,facebookurl as facebookUrl,"
			+ "createdat as createdAt,country as country,"
			+ "imagemediumurl as imageMediumUrl,"
			+ "imagesmallurl as imageSmailUrl ,imagelargeurl as imageLargeUrl,"
			+ "posts as posts, authid as authId , passwdmd5 as passwdMD5 from unauthuser where id = #{id}")
	User getUnAuthUserById(@Param("id")String id);
	

	@Select("select id as id, type as type,gender as gender,username as username,"
			+ "fullname as fullName,firstname as firstName,lastname as lastName,"
			+ "email as email,"
			+ "phonenumber as phoneNumber,gplusurl as gplusUrl,"
			+ "domainurl as domainUrl,twitterurl as twitterUrl,facebookurl as facebookUrl,"
			+ "createdat as createdAt,country as country,"
			+ "imagemediumurl as imageMediumUrl,"
			+ "imagesmallurl as imageSmailUrl ,imagelargeurl as imageLargeUrl,"
			+ "posts as posts, authid as authId , passwdmd5 as passwdMD5 from unauthuser where username = #{username}")
	User getUnAuthUserByUserName(String userName);
	
	@Select("select id as id, type as type,gender as gender,username as username,"
			+ "fullname as fullName,firstname as firstName,lastname as lastName,"
			+ "email as email,"
			+ "phonenumber as phoneNumber,gplusurl as gplusUrl,"
			+ "domainurl as domainUrl,twitterurl as twitterUrl,facebookurl as facebookUrl,"
			+ "createdat as createdAt,country as country,"
			+ "imagemediumurl as imageMediumUrl,"
			+ "imagesmallurl as imageSmailUrl ,imagelargeurl as imageLargeUrl,"
			+ "posts as posts, authid as authId , passwdmd5 as passwdMD5 from unauthuser where email = #{email}")
	User findUnAuthUserByEmail(@Param("email")String email);
	
	@Delete("")
	void deleteAllUnAuthUser();
	
	@Delete("")
	void deleteUnAuthUser();
				
	@Update("CREATE TABLE `easyblog`.`unauthuser` (\r\n" + 
			"  `id` VARCHAR(100) NOT NULL,\r\n" + 
			"  `type` VARCHAR(45) NOT NULL,\r\n" + 
			"  `gender` VARCHAR(45) NOT NULL,\r\n" + 
			"  `username` VARCHAR(45) NOT NULL,\r\n" + 
			"  `fullname` VARCHAR(45) NOT NULL,\r\n" + 
			"  `firstname` VARCHAR(45) NOT NULL,\r\n" + 
			"  `lastname` VARCHAR(45) NOT NULL,\r\n" + 
			"  `email` VARCHAR(45) NOT NULL,\r\n" + 
			"  `phonenumber` VARCHAR(45) NOT NULL,\r\n" + 
			"  `gplusurl` VARCHAR(100) NOT NULL,\r\n" + 
			"  `domainurl` VARCHAR(100) NOT NULL,\r\n" + 
			"  `twitterurl` VARCHAR(100) NOT NULL,\r\n" + 
			"  `facebookurl` VARCHAR(100) NOT NULL,\r\n" + 
			"  `createdat` BIGINT UNSIGNED NOT NULL,\r\n" + 
			"  `country` VARCHAR(45) NOT NULL,\r\n" + 
			"  `imagemediumurl` VARCHAR(100) NOT NULL,\r\n" + 
			"  `imagesmallurl` VARCHAR(100) NOT NULL,\r\n" + 
			"  `imagelargeurl` VARCHAR(100) NOT NULL,\r\n" + 
			"  `posts` INTEGER UNSIGNED NOT NULL,\r\n" + 
			"  `authid` VARCHAR(100) NOT NULL,\r\n" + 
			"  `passwdmd5` VARCHAR(100) NOT NULL,\r\n" + 
			"  PRIMARY KEY (`id`)\r\n" + 
			")\r\n" + 
			"ENGINE = InnoDB;")
	void createUnAuthUserTable();
	
	@Update("drop table unauthuser;")
	void deleteUnAuthUserTable();

	
}
