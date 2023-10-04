package com.talk.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.talk.entity.Chat;
import com.talk.entity.User;
import com.talk.exception.ChatException;
import com.talk.exception.UserException;
import com.talk.request.GroupChatRequest;
import com.talk.request.SingleChatReq;
import com.talk.service.ChatService;
import com.talk.service.UserService;

@RestController
@RequestMapping("api/chat")
public class ChatController {
	
	@Autowired
	private ChatService chatService;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/single")
	public ResponseEntity<Chat> createChatHandler(@RequestBody SingleChatReq reqChat,@RequestHeader("Authorization") String jwt) throws UserException{
		  User user = userService.findUserProfile(jwt);
		  
		   Chat createdChat = chatService.createChat(user, reqChat.getUserId());
		
		return new ResponseEntity<Chat>(createdChat,HttpStatus.CREATED);
	}
	
	@PostMapping("/group")
	public ResponseEntity<Chat> createGroupChatHandler(@RequestBody GroupChatRequest reqChat,@RequestHeader("Authorization") String jwt) throws UserException{
		  User user = userService.findUserProfile(jwt);
		  
		   Chat createdChat = chatService.createGroup(reqChat, user);
		
		return new ResponseEntity<Chat>(createdChat,HttpStatus.CREATED);
	}
	
	
	@GetMapping("/{chatId}")
	public ResponseEntity<Chat> findChatByIdHandler(@PathVariable  Integer chatId ,@RequestHeader("Authorization") String jwt) throws UserException, ChatException{
		  
		   Chat createdChat = chatService.findChatById(chatId);
		
		return new ResponseEntity<Chat>(createdChat,HttpStatus.OK);
	}
	
	
	@GetMapping("/user")
	public ResponseEntity<List<Chat>> findChatByUserHandler(@RequestHeader("Authorization") String jwt) throws UserException{
		  User user = userService.findUserProfile(jwt);
		  
		   List<Chat> chat = chatService.findAllChatByUserId(user.getId());
		
		return new ResponseEntity<>(chat,HttpStatus.OK);
	}
	
	@PutMapping("/{chatId}/add/{userId}")
	public ResponseEntity<Chat> addUsertoGroupHandler(@PathVariable  Integer chatId,@PathVariable Long userId  ,@RequestHeader("Authorization") String jwt) throws UserException, ChatException{
		  User user = userService.findUserProfile(jwt);

		   Chat createdChat = chatService.addUserToGroup(userId, chatId, user);
		
		return new ResponseEntity<Chat>(createdChat,HttpStatus.ACCEPTED);
	}
	@PutMapping("/{chatId}/remove/{userId}")
	public ResponseEntity<String> removeUserFromGroupHandler(@PathVariable  Integer chatId,@PathVariable Long userId  ,@RequestHeader("Authorization") String jwt) throws UserException, ChatException{
		  User user = userService.findUserProfile(jwt);

		  chatService.removeFromGroup(chatId, userId, user);
		
		return new ResponseEntity<>("user removed from group Successfully",HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/delete/{chatId}")
	public ResponseEntity<String> removeUserHandler(@PathVariable  Integer chatId ,@RequestHeader("Authorization") String jwt) throws UserException, ChatException{
		  User user = userService.findUserProfile(jwt);

	 chatService.deleteChat(chatId, user);
		return new ResponseEntity<>("user removed Successfully",HttpStatus.OK);
	}
	

}
