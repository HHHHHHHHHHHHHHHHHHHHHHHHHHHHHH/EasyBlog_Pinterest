package com.bamboovir.easyblog.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bamboovir.easyblog.mapper.BehaviorMapper;
import com.bamboovir.easyblog.mapper.BoardMapper;
import com.bamboovir.easyblog.mapper.ImageMapper;
import com.bamboovir.easyblog.mapper.PostMapper;
import com.bamboovir.easyblog.mapper.TagMapper;
import com.bamboovir.easyblog.mapper.UnAuthUserMapper;
import com.bamboovir.easyblog.mapper.UserMapper;
import com.bamboovir.easyblog.model.Behavior;
import com.bamboovir.easyblog.model.BehaviorType;
import com.bamboovir.easyblog.model.Post;
import com.bamboovir.easyblog.model.SearchAtom;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "searchService" ,description = "provide search service")
@RestController
@RequestMapping("/search")
public class SearchController {

	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private PostMapper postMapper;
	
	@Autowired
	private UnAuthUserMapper unAuthUserMapper;
	
	@Autowired
	private BoardMapper boardMapper;
	
	@Autowired
	private ImageMapper ImageMapper;
	
	@Autowired
	private TagMapper tagMapper;
	
	@Autowired
	private BehaviorMapper behaviorMapper;
	
	//需要暴露给用户的只有 : 搜索的 是 User ，Post ，Board ，Channle 
	@ApiOperation("Input : SearchAtom -> return recommend SearchAtom")
	@RequestMapping(value="/searchAtom",method=RequestMethod.POST)
	public ResponseEntity<List<SearchAtom>> ajaxSearch(@RequestBody SearchAtom searchAtom){
		List<SearchAtom> returnSearchAtomList = new ArrayList<>();
		
		Date now = new Date();
		Long nowLongTime = new Long(now.getTime()/1000);
		String behaviorId = UUID.randomUUID().toString();
		behaviorMapper.insertBehavior(behaviorId,"userId","postId", BehaviorType.SEARCHQUERY.getType(),nowLongTime);
		
		return new ResponseEntity<List<SearchAtom>>(returnSearchAtomList, HttpStatus.OK);
	}
	
	@ApiOperation("直接根据SearchAtom 返回 Post")
	@RequestMapping(value="/search",method=RequestMethod.POST)
	public ResponseEntity<List<Post>> Search(@RequestBody SearchAtom searchAtom){
		//做一次搜索，返回post
		List<Post> returnSearchAtomList = new ArrayList<>();
		return new ResponseEntity<List<Post>>(returnSearchAtomList, HttpStatus.OK);
	}
	
	@ApiOperation("根据Tag 构造 搜索 < User | Post | Board > 的 URL")
	@RequestMapping(value="/getAtomByTag",method=RequestMethod.POST)
	public ResponseEntity<List<SearchAtom>> getAtomByTag(@RequestBody SearchAtom searchAtom){
		List<SearchAtom> returnSearchAtomList = new ArrayList<>();
		return new ResponseEntity<List<SearchAtom>>(returnSearchAtomList, HttpStatus.OK);
	}
	
	@ApiOperation("根据Title 构造 搜索 < User | Post | Board > 的 URL")
	@RequestMapping(value="/getAtomByTitle",method=RequestMethod.POST)
	public ResponseEntity<List<SearchAtom>> getAtomByTime(@RequestBody SearchAtom searchAtom){
		List<SearchAtom> returnSearchAtomList = new ArrayList<>();
		return new ResponseEntity<List<SearchAtom>>(returnSearchAtomList, HttpStatus.OK);
	}
	
	@ApiOperation("根据title搜索") 
	@RequestMapping(value="/title/{titleName}",method=RequestMethod.POST)
	public ResponseEntity<List<Post>> searchByTitle(@PathVariable("titleName") String titleName){
		List<Post> postList = new ArrayList<>();
		return new ResponseEntity<List<Post>>(postList, HttpStatus.OK);
	}
	
	@ApiOperation("根据tag搜索")
	@RequestMapping(value="/tag/{tagName}",method=RequestMethod.POST)
	public ResponseEntity<List<Post>> searchByTag(@PathVariable("tagName") String tagName){
		List<Post> postList = new ArrayList<>();
		return new ResponseEntity<List<Post>>(postList, HttpStatus.OK);
	}
	
}
