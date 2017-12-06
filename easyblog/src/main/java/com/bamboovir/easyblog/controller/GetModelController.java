package com.bamboovir.easyblog.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bamboovir.easyblog.model.Board;
import com.bamboovir.easyblog.model.LoginAuth;
import com.bamboovir.easyblog.model.Post;
import com.bamboovir.easyblog.model.SearchAtom;
import com.bamboovir.easyblog.model.User;

@RestController
@RequestMapping("/get")
public class GetModelController {

		//返回默认User Json
		@RequestMapping(value="/userjson")
		public ResponseEntity<User> getDefaultUser() {
			User user = new User();	
			return new ResponseEntity<User>(user,HttpStatus.OK);	
		}
			
		//返回默认Post Json
		@RequestMapping(value="/postjson")
		public ResponseEntity<Post> getDefaultPost() {
			Post post = new Post();	
			return new ResponseEntity<Post>(post,HttpStatus.OK);	
		}
		
		//返回默认Board Json
		@RequestMapping(value="/boardjson")
		public ResponseEntity<Board> getDefaultBoard() {
			Board board= new Board();	
			return new ResponseEntity<Board>(board,HttpStatus.OK);	
		}
			
		//返回默认 Login auth Json
		@RequestMapping(value="/loginjson")
		public ResponseEntity<LoginAuth> getDefaultLogin() {
			LoginAuth loginauth = new LoginAuth();	
			return new ResponseEntity<LoginAuth>(loginauth,HttpStatus.OK);	
		}
		
		//返回默认 Search Atom Json
		@RequestMapping(value="/searchatomjson")
		public ResponseEntity<SearchAtom> getDefaultSearchAtom() {
		SearchAtom searchAtom = new SearchAtom();	
			return new ResponseEntity<SearchAtom>(searchAtom,HttpStatus.OK);	
		}
}
