package com.bamboovir.easyblog.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bamboovir.easyblog.model.Behavior;
import com.bamboovir.easyblog.model.Board;
import com.bamboovir.easyblog.model.Channle;
import com.bamboovir.easyblog.model.LoginAuth;
import com.bamboovir.easyblog.model.Post;
import com.bamboovir.easyblog.model.SearchAtom;
import com.bamboovir.easyblog.model.User;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/get")
public class GetModelController {

		@ApiOperation("Get Default User Json")
		@GetMapping(value="/userjson")
		public ResponseEntity<User> getDefaultUser() {
			User user = new User();	
			return new ResponseEntity<User>(user,HttpStatus.OK);	
		}
			
		@ApiOperation("Get Default Post Json")
		@GetMapping(value="/postjson")
		public ResponseEntity<Post> getDefaultPost() {
			Post post = new Post();	
			return new ResponseEntity<Post>(post,HttpStatus.OK);	
		}
		
		@ApiOperation("Get Default Board Json")
		@GetMapping(value="/boardjson")
		public ResponseEntity<Board> getDefaultBoard() {
			Board board= new Board();	
			return new ResponseEntity<Board>(board,HttpStatus.OK);	
		}
			
		@ApiOperation("Get DEfault LoginAuth Json")
		@GetMapping(value="/loginjson")
		public ResponseEntity<LoginAuth> getDefaultLogin() {
			LoginAuth loginauth = new LoginAuth();	
			return new ResponseEntity<LoginAuth>(loginauth,HttpStatus.OK);	
		}
		
		@ApiOperation("Get Default SearchAtom Json")
		@GetMapping(value="/searchatomjson")
		public ResponseEntity<SearchAtom> getDefaultSearchAtom() {
		SearchAtom searchAtom = new SearchAtom();	
			return new ResponseEntity<SearchAtom>(searchAtom,HttpStatus.OK);	
		}
		
		@ApiOperation("Get Default Behavior Json")
		@GetMapping(value="/behaviorjson")
		public ResponseEntity<Behavior> getDefaultBehavior() {
			Behavior behavior = new Behavior();	
			return new ResponseEntity<Behavior>(behavior,HttpStatus.OK);	
		}
		
		@ApiOperation("Get Default Channle Json")
		@GetMapping(value="/channlejson")
		public ResponseEntity<Channle> getDefaultChannle() {
			Channle channle = new Channle();	
			return new ResponseEntity<Channle>(channle,HttpStatus.OK);	
		}
		
}
