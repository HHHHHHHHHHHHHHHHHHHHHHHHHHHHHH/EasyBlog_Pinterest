package com.bamboovir.easyblog.mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.xpath.operations.String;
import org.hibernate.validator.constraints.Email;

import com.bamboovir.easyblog.model.User;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
	
	@Select("select id as id, type as type,gender as gender,username as username,"
			+ "fullname as fullName,firstname as firstName,lastname as lastName,"
			+ "email as email,"
			+ "phonenumber as phoneNumber,gplusurl as gplusUrl,"
			+ "domainurl as domainUrl,twitterurl as twitterUrl,facebookurl as facebookUrl,"
			+ "createdat as createdAt,country as country,"
			+ "imagemediumurl as imageMediumUrl,"
			+ "imagesmallurl as imageSmailUrl ,imagelargeurl as imageLargeUrl,"
			+ "posts as posts, authid as authId , passwdmd5 as passwdMD5 from user where id = #{id}")
	User findUserByID(@Param("id")String id);
	
	@Select("select id as id, type as type,gender as gender,username as username,"
			+ "fullname as fullName,firstname as firstName,lastname as lastName,"
			+ "email as email,"
			+ "phonenumber as phoneNumber,gplusurl as gplusUrl,"
			+ "domainurl as domainUrl,twitterurl as twitterUrl,facebookurl as facebookUrl,"
			+ "createdat as createdAt,country as country,"
			+ "imagemediumurl as imageMediumUrl,"
			+ "imagesmallurl as imageSmailUrl ,imagelargeurl as imageLargeUrl,"
			+ "posts as posts, authid as authId , passwdmd5 as passwdMD5 from user where username = #{username}")
	User findUserByUserName(@Param("username")String userName);
	
	@Select("select id as id, type as type,gender as gender,username as username,"
			+ "fullname as fullName,firstname as firstName,lastname as lastName,"
			+ "email as email,"
			+ "phonenumber as phoneNumber,gplusurl as gplusUrl,"
			+ "domainurl as domainUrl,twitterurl as twitterUrl,facebookurl as facebookUrl,"
			+ "createdat as createdAt,country as country,"
			+ "imagemediumurl as imageMediumUrl,"
			+ "imagesmallurl as imageSmailUrl ,imagelargeurl as imageLargeUrl,"
			+ "posts as posts, authid as authId , passwdmd5 as passwdMD5 from user where email = #{email}")
	User findUserByEmail(@Param("email")String email);
	
	@Select("select id as id, type as type,gender as gender,username as username,"
			+ "fullname as fullName,firstname as firstName,lastname as lastName,"
			+ "email as email,"
			+ "phonenumber as phoneNumber,gplusurl as gplusUrl,"
			+ "domainurl as domainUrl,twitterurl as twitterUrl,facebookurl as facebookUrl,"
			+ "createdat as createdAt,country as country,"
			+ "imagemediumurl as imageMediumUrl,"
			+ "imagesmallurl as imageSmailUrl ,imageLargeurl as imageLargeUrl,"
			+ "posts as posts, authid as authId , passwdmd5 as passwdMD5 from user")
	List<User> findAllUser();
	
	@Insert("insert into user(id,type,gender,username,fullname,firstname,lastname,email,"
			+ "phonenumber,gplusurl,domainurl,twitterurl,facebookurl,createdat,"
			+ "country,imagemediumurl,imagesmallurl,imagelargeurl,posts,authid,passwdmd5) values"
			+ "(#{id},#{type},#{gender},#{username},#{fullName},#{firstName},#{lastName},#{email},"
			+ "#{phoneNumber},#{gplusUrl},#{domainUrl},#{twitterUrl},#{facebookUrl},#{createdAt},"
			+ "#{country},#{imageMediumUrl},#{imageSmallUrl},#{imageLargeUrl},#{posts},#{authId},#{passwdMD5});")
	void insertUser(
			@Param("id")String id,
			@Param("type")String type,
			@Param("gender")String gender,
			@Param("username")String username,
			@Param("fullName")String fullName,
			@Param("firstName")String firstName,
			@Param("lastName")String lastName,
			@Param("email") String email,
			@Param("phoneNumber")String phoneNumber,
			@Param("gplusUrl")String gplusUrl,
			@Param("domainUrl")String domainUrl,
			@Param("twitterUrl")String twitterUrl,
			@Param("facebookUrl")String facebookUrl,
			@Param("createdAt")Long createdAt,
			@Param("country")String country,
			@Param("imageMediumUrl")String imageMediumUrl,
			@Param("imageSmallUrl")String imageSmailUrl,
			@Param("imageLargeUrl")String imageLargeUrl,
			@Param("posts")Integer posts,
			@Param("authId")String authId,
			@Param("passwdMD5")String passwdMD5);
	
	@Update("")
	void updateUser(User user);
		
	@Delete("")
	void deleteAllUser();
	
	@Delete("")
	void deleteUserByID(String id);
	
	@Update("CREATE TABLE `easyblog`.`user` (\r\n" + 
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
	void createUserTable();
	
	@Update("drop table user;")
	void deleteUserTable();
	

}
