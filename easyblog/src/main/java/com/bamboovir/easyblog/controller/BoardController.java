package com.bamboovir.easyblog.controller;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bamboovir.easyblog.mapper.BehaviorMapper;
import com.bamboovir.easyblog.mapper.BoardMapper;
import com.bamboovir.easyblog.mapper.ImageMapper;
import com.bamboovir.easyblog.mapper.PostMapper;
import com.bamboovir.easyblog.mapper.TagMapper;
import com.bamboovir.easyblog.mapper.UnAuthUserMapper;
import com.bamboovir.easyblog.mapper.UserMapper;
import com.bamboovir.easyblog.model.BehaviorType;
import com.bamboovir.easyblog.model.Board;
import com.bamboovir.easyblog.model.Post;
import com.bamboovir.easyblog.model.StatusInfo;
import com.bamboovir.easyblog.model.TagType;
import com.bamboovir.easyblog.model.User;
import com.bamboovir.easyblog.model.UserType;
import com.bamboovir.easyblog.storage.StorageFileNotFoundException;
import com.bamboovir.easyblog.storage.StorageService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "Board Service" , description = "Provide Board Service")
@RestController
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private PostMapper postMapper;
	
	@Autowired
	private BoardMapper boardMapper;
	
	@Autowired
	private ImageMapper ImageMapper;
	
	@Autowired
	private TagMapper tagMapper;
	
	@Autowired
	private BehaviorMapper behaviorMapper;
	
    private final StorageService storageService;

    @Autowired
    public BoardController(StorageService storageService) {
        this.storageService = storageService;
    }
    
	Boolean Auth(String userId ,String userAuth) {
		if(userId == null || userAuth == null) {
			return false;
		}
		
		User user = userMapper.findUserByID(userId);
		
		if(user != null && user.getAuthId().equals(userAuth)) {
			return true;
		}else {
			return false;
		}
	}
	
	@ApiOperation("Create Board  <-  Need Login")
	@PostMapping("/create")
	public ResponseEntity<StatusInfo> createBoard(@RequestBody Board board,@CookieValue(value="userId",defaultValue="") String userId,
		    @CookieValue(value="userAuth",defaultValue="") String userAuth ) {
		
			StatusInfo statusInfo = new StatusInfo();
			
				if(Auth(userId, userAuth)) {
					
					Board newBoard = new Board();
					Date now = new Date();
					Long nowLongTime = new Long(now.getTime()/1000);
		
					String boardId = UUID.randomUUID().toString();
					newBoard.setBoardId(boardId);
					newBoard.setBoardName(board.getBoardName());
					newBoard.setCoverPic(board.getCoverPic());
					newBoard.setCreatedAt(nowLongTime);
					newBoard.setDescription(board.getDescription());
					newBoard.setIsPrivate(board.getIsPrivate());
					newBoard.setPostNum(board.getPostNum());
					newBoard.setUrl("/board/"+userId+"/"+boardId+".html");
					
					String behaviorId = UUID.randomUUID().toString();
					behaviorMapper.insertBehavior(behaviorId, userId, boardId, BehaviorType.CREATEBOARD.getType(), nowLongTime);
					
		        statusInfo.setStatusInfo("success <- create board:" + newBoard.getBoardId());
		        return new ResponseEntity<StatusInfo>(statusInfo,HttpStatus.OK);

				}else {
					statusInfo.setStatusInfo("error");
					return new ResponseEntity<StatusInfo>(statusInfo,HttpStatus.OK);
				}
	}
	
	@ApiOperation("UpdateBoardMeta / Need Login")
	@PostMapping(value="/updateMeta")
	public ResponseEntity<StatusInfo> updateBoardMeta(@RequestBody Board board, 
			@CookieValue(value="userId",defaultValue="") String userId,
		    @CookieValue(value="userAuth",defaultValue="") String userAuth ) {
		StatusInfo statusInfo = new StatusInfo();
		
		if(Auth(userId, userAuth) && boardMapper.findBoardByBoardID(board.getBoardId()).getUserId().equals(userId)) {
			
			boardMapper.updateBoardNameByID(board.getBoardName(), board.getBoardId());
			boardMapper.updateCoverPicByID(board.getCoverPic(), board.getBoardId());
			boardMapper.updateDiscriptionByID(board.getDescription(), board.getBoardId());
			boardMapper.updateIsPrivateByID(board.getIsPrivate(), board.getBoardId());
			
			Date now = new Date();
			Long nowLongTime = new Long(now.getTime()/1000);
			String behaviorId = UUID.randomUUID().toString();
			behaviorMapper.insertBehavior(behaviorId, userId, board.getBoardId(), BehaviorType.UPDATEBOARD.getType(),nowLongTime);
			
			statusInfo.setStatusInfo("success <- update : " + board.getBoardId());
			return new ResponseEntity<StatusInfo>(statusInfo,HttpStatus.OK);
			
	}
		statusInfo.setStatusInfo("error <- Access Denied");
		return new ResponseEntity<StatusInfo>(statusInfo,HttpStatus.OK);
	}

	
	@ApiOperation("Get BoardMeta By BoardId <- Do Not Need Login")
	@RequestMapping(value = "/getMeta", method = RequestMethod.POST)
	public ResponseEntity<Board> getBoardMeta(@RequestBody Board board) {
		Board boardTemp = null;
		boardTemp = boardMapper.findBoardByBoardID(board.getBoardId());
		
		if(boardTemp != null && boardTemp.getIsPrivate() == Boolean.FALSE) {
			
			Date now = new Date();
			Long nowLongTime = new Long(now.getTime()/1000);
			String behaviorId = UUID.randomUUID().toString();
			behaviorMapper.insertBehavior(behaviorId,UserType.UNLOGINUSER.toString(), board.getBoardId(), BehaviorType.UPDATEBOARD.getType(),nowLongTime);
			
			return new ResponseEntity<Board>(boardTemp, HttpStatus.OK);
		} else {
			boardTemp = new Board();
			return new ResponseEntity<Board>(boardTemp, HttpStatus.OK);
		}
	}
	
	@ApiOperation("Delete A Board <- Need Login")
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	public ResponseEntity<StatusInfo> delete(@RequestBody Board board,
				@CookieValue(value="userId",defaultValue="") String userId,
			    @CookieValue(value="userAuth",defaultValue="") String userAuth) {
			StatusInfo statusInfo = new StatusInfo();
			Board boardTemp = boardMapper.findBoardByBoardID(board.getBoardId());
			
			if(Auth(userId, userAuth) && (boardTemp != null) && (boardTemp.getUserId().equals(userId))) {
			
				boardMapper.deleteByBoardID(board.getBoardId());
				tagMapper.deleteTagByUserPostBoardIDAndType(board.getBoardId(), TagType.BOARD.toString());
				
				Date now = new Date();
				Long nowLongTime = new Long(now.getTime()/1000);
				String behaviorId = UUID.randomUUID().toString();
				behaviorMapper.insertBehavior(behaviorId,userId, board.getBoardId(), BehaviorType.DELETEBOARD.getType(),nowLongTime);
				
			statusInfo.setStatusInfo("success <- delete");
			return new ResponseEntity<StatusInfo>(statusInfo,HttpStatus.OK);
			
			}else {
				statusInfo.setStatusInfo("error <- Access Denied");
				return new ResponseEntity<StatusInfo>(statusInfo,HttpStatus.OK);
			}
		}
		
	@ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }
	
}
